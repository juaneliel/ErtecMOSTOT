package model.DAO;

import java.text.SimpleDateFormat;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import mbean.mb_Usuario;
import model.Adicional;
import model.Arrendamiento;
import model.Articulo;
import model.Cod_Movimiento;
import model.Contrato;
import model.Funcionario;
import model.ManoObra;
import model.Movimiento;
import model.NexoMovimiento;
import model.Ot;
import model.Referencia;
import util.JpaUtil;

public class DAO_Movimiento {
	
	
	public boolean addNexo(int movid, NexoMovimiento nexo){
		EntityManager em=JpaUtil.getEntityManager(); 
		boolean salida=false;
		try{			
			em.getTransaction().begin();	
			Movimiento movimiento = em.find(Movimiento.class,movid);
			Articulo a =em.find(Articulo.class,nexo.getArticulo().getArticuloID());
			//compra en plaza, aumenta stock y modifica los valores del articulo
			acomodarNexo(movimiento,nexo,a);
			//salida por arrendamiento
			if(movimiento.getCodigoMovimientoID()==25){
				Arrendamiento arrendamiento=new Arrendamiento();
				arrendamiento.setArticulo(a);
				arrendamiento.setCantidad(nexo.getCantidad().intValue());
				arrendamiento.setCosto(nexo.getCosto());
				arrendamiento.setFecha(movimiento.getFecha());
				arrendamiento.setMovimientoID(movimiento.getMovimientoID()); 
				arrendamiento.setSaldo(nexo.getCantidad().intValue()); 
				em.persist(arrendamiento);				
			}
			movimiento.getNexos().add(nexo);
			em.getTransaction().commit(); 
			salida = true;
		}
		catch (Exception e){
			e.printStackTrace();
		 }finally{ 
		   if(em.isOpen() ){
		     em.close();
		   }		
		}
		return salida;
	}
	
	public static boolean add(Movimiento movimiento){
		EntityManager em=JpaUtil.getEntityManager(); 
		boolean salida=false;
		try{			
			Contrato con=movimiento.getContrato();
			em.getTransaction().begin();
			em.persist(movimiento);			
			em.getTransaction().commit(); 
			salida = true;
		}
		catch (Exception e){
			e.printStackTrace();
		 }finally{ 
		   if(em.isOpen() ){
		     em.close();
		   }		
		}
		return salida;
	}
	
	//se podria poner en el codigo algo que indique la estrategia a usar
	private static void acomodarNexo(Movimiento movimiento,NexoMovimiento nexo, Articulo a){
		mb_Usuario service = (mb_Usuario) 
    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mb_Usuario"); 
		Cod_Movimiento cm=service.getMapaCodMov().get(movimiento.getCodigoMovimientoID());	
		
		if(cm.getSumarStockYCostos()==1){	
			entradaStockConCosto(movimiento,nexo,a);
		}
		
		else {
			if(cm.getSumarStock()==1){	
				a.setStock(a.getStock().add(nexo.getCantidad())); 	
			}
			else{
				if(cm.getRestarStock()==1){	
					a.setStock(a.getStock().subtract(nexo.getCantidad())); 	
				}
			}
		}
	}
	
  public static Map<Integer,Cod_Movimiento> cargarCodMov(){
  	Map<Integer,Cod_Movimiento> salida = new HashMap<Integer, Cod_Movimiento>(); 
  	
  	EntityManager em=JpaUtil.getEntityManager();
  	String consulta = "SELECT c FROM Cod_Movimiento c ";
  	TypedQuery<Cod_Movimiento> query= em.createQuery(consulta, Cod_Movimiento.class); 
  	try{
			ArrayList<Cod_Movimiento> auxlist= (ArrayList<Cod_Movimiento>) query.getResultList();
			for(Cod_Movimiento c : auxlist){
				salida.put(c.getCodMov_ID(), c);
			}
		}
		catch (Exception e){
			e.printStackTrace();			
		}finally{ 
	    	  if(em.isOpen() ){
				  em.close();
			  }		
		}
  	return salida;
  }
	
	private static Articulo entradaStockConCosto(Movimiento m,NexoMovimiento nexo, Articulo a){
    
    BigDecimal cdCopia=a.getCostoDolares();
    BigDecimal cpCopia=a.getCostoPesos();
    
    BigDecimal cotizacion = m.getCotizacion().setScale(3,BigDecimal.ROUND_HALF_UP);
    BigDecimal costoPesos = nexo.getCosto().setScale(3,BigDecimal.ROUND_HALF_UP); 
    BigDecimal costoDolares= costoPesos.divide(cotizacion,3,BigDecimal.ROUND_HALF_UP);
    BigDecimal stock = a.getStock().setScale(3,BigDecimal.ROUND_HALF_UP);
    BigDecimal cantidad = nexo.getCantidad().setScale(3,BigDecimal.ROUND_HALF_UP);
    
    BigDecimal cPV= (stock.multiply(a.getCostoPesos())).setScale(3,BigDecimal.ROUND_HALF_UP);
    BigDecimal cPN= (cantidad.multiply(costoPesos)).setScale(3,BigDecimal.ROUND_HALF_UP);
    BigDecimal auxsuma=(cPV.add(cPN)).setScale(3,BigDecimal.ROUND_HALF_UP);
    BigDecimal auxdivisor=(stock.add(cantidad)).setScale(3,BigDecimal.ROUND_HALF_UP);      
    BigDecimal auxCP=auxsuma.divide(auxdivisor,2,BigDecimal.ROUND_HALF_UP); 
    a.setCostoPesos(auxCP);    
    
    BigDecimal cDV= (stock.multiply(a.getCostoDolares())).setScale(3,BigDecimal.ROUND_HALF_UP); 
    BigDecimal cDN= (cantidad.multiply(costoDolares)).setScale(3,BigDecimal.ROUND_HALF_UP); 
    auxsuma=(cDV.add(cDN)).setScale(3,BigDecimal.ROUND_HALF_UP); 
    auxdivisor=(stock.add(cantidad)).setScale(3,BigDecimal.ROUND_HALF_UP); 
    BigDecimal auxCD= auxsuma.divide(auxdivisor,2,BigDecimal.ROUND_HALF_UP);  
    a.setCostoDolares(auxCD);
    
    a.setUltimoCostoDolares(cdCopia);
    a.setUltimoCostoPesos(cpCopia); 
		a.setStock(stock.add(nexo.getCantidad()));
    return a;		
	}	
	
	public static String getDescripcionMov(int cod){
		EntityManager em=JpaUtil.getEntityManager();
		return em.find(Cod_Movimiento.class,cod).getDescripcion();		
	}
	
	//es el contrato  por su id entidad
	//se listan ademas los arrendamientos que fueron hechos sin contratos asociados a movimientos
	public ArrayList<Arrendamiento> getArrendados (int cId){
	  ArrayList<Arrendamiento> salida = new ArrayList<Arrendamiento>();
	    
	  try{       
      EntityManager em=JpaUtil.getEntityManager();      
      String consulta ="SELECT a FROM Movimiento m, Arrendamiento a "+
                       " WHERE a.movimientoID = m.movimientoID "
                       + "and (  m.contrato  IS NULL or m.contrato.id= "+ cId + ")"+
                       " ORDER BY a.id DESC ";
      System.out.println("Consulta arrendados "+consulta);
      TypedQuery<Arrendamiento> consultaFuncionario= em.createQuery(consulta, Arrendamiento.class);
      ArrayList<Arrendamiento> auxLista = (ArrayList<Arrendamiento>) consultaFuncionario.getResultList();
      
      System.out.println("getArrendado size" + auxLista.size());
      
      salida= auxLista;
    }
    catch (Exception e){
      e.printStackTrace();;
    }   
	  return salida;
	}
	
	public boolean updateNexo(NexoMovimiento nexo){
		EntityManager em=JpaUtil.getEntityManager();
		boolean salida=false;
		try{			
			em.getTransaction().begin();
			em.merge(nexo);
      em.getTransaction().commit();
			salida= true;
		}catch (Exception e){
			e.printStackTrace();			
		}finally{ 
			if(em.isOpen() ){
			  em.close();
		  }		
		}
		return salida;		
	}
	
	
	
	
	public boolean update(Movimiento m){ 
		EntityManager em=JpaUtil.getEntityManager();
		boolean salida=false;
		try{			
			em.getTransaction().begin();
			em.merge(m);
      em.getTransaction().commit();
			salida= true;
		}catch (Exception e){
			e.printStackTrace();			
		}finally{ 
			if(em.isOpen() ){
			  em.close();
		  }		
		}
		return salida;
	}	
	
	public boolean delete(Movimiento f){ 
		EntityManager em=JpaUtil.getEntityManager();
		boolean salida = false;
		try{			
			em.getTransaction().begin();
			em.remove(em.contains(f) ? f : em.merge(f));
			//em.flush();
			em.getTransaction().commit();
			salida = true;
		}
		catch (Exception e){
			e.printStackTrace();	
		}finally{ 
	    	  if(em.isOpen() ){
				  em.close();
			  }		
		}
		return salida;
	}
	 
	
	
 
	
	public Movimiento findMovimiento(Movimiento m){
		return null;
	}
	
	public Movimiento getMovimiento(int idm){
		try{			
			EntityManager em=JpaUtil.getEntityManager();			
			return em.find(Movimiento.class, idm);	
		}
		catch (Exception e){
			e.printStackTrace();;
		}
		return null;
	}


	
	

	
	
	
	public static ArrayList <Movimiento> getMovimientosOT(long idOT){	
		ArrayList <Movimiento> salida = new ArrayList <Movimiento>();
		try{
			//a partir del id de la ot se deben de buscar en la tabla de movimientos
			//aquellos que sean "TipoReferencia" OT
			//y que Referencia sea el id de la ot pasado
			
			EntityManager em=JpaUtil.getEntityManager();
			
			//String consulta ="Select m From Movimiento m where (m.tipoReferencia = 'OR' OR m.tipoReferencia = 'OT') and m.referencia = "+idOT + " ORDER BY MovimientoID DESC";
			String consulta ="Select m From Movimiento m where (m.nombreCliente <> NULL) and m.referencia = "+idOT + " ORDER BY MovimientoID DESC";
			
			System.out.println("Consulta movimiento "+consulta);
			TypedQuery<Movimiento> consultaFuncionario= em.createQuery(consulta, Movimiento.class);
			ArrayList<Movimiento> auxLista = (ArrayList<Movimiento>) consultaFuncionario.getResultList();
			
			System.out.println(">>>>" + auxLista.size());
			
			salida= auxLista;
		}
		catch (Exception e){
			e.printStackTrace();;
		}		
		return salida;		
	}
	
	public ArrayList<Movimiento> getLista(){
		EntityManager em=JpaUtil.getEntityManager();
		TypedQuery<Movimiento> consultaFuncionario= em.createNamedQuery("Movimiento.findAll", Movimiento.class);
		ArrayList<Movimiento> salida = new ArrayList<Movimiento>();
		try{
			salida= (ArrayList<Movimiento>) consultaFuncionario.getResultList();
		}
		catch (Exception e){
			e.printStackTrace();			
		}finally{ 
	    	  if(em.isOpen() ){
				  em.close();
			  }		
		}
		return salida;
	}
	
	public Movimiento getMovimientoCompleto(int movid){
		try{			
			EntityManager em=JpaUtil.getEntityManager();
			em.getTransaction().begin();
			Movimiento mov=em.find(Movimiento.class, movid);			
			mov.getNexos().size();	
			em.getTransaction().commit();
			return mov;
		}
		catch (Exception e){
			e.printStackTrace();;
		}				
		return null;
	}
	
	
	public List <NexoMovimiento> getNexos(int movid){
		List<NexoMovimiento> salida= new ArrayList <NexoMovimiento> ();		
		try{			
			EntityManager em=JpaUtil.getEntityManager();
			em.getTransaction().begin();
			Movimiento mov=em.find(Movimiento.class, movid);			
			mov.getNexos().size();	
			salida= mov.getNexos();
			em.getTransaction().commit();
		}
		catch (Exception e){
			e.printStackTrace();;
		}				
		return salida;
	}
	
	
	//este metodo es el mas dificil deberia hacer una recorrida de los movimientos
	//desde una base inicial para determinar por fechas como se fue acomodando 
	//el articulo
	public boolean deleteNexo(NexoMovimiento nexo){
		EntityManager em=JpaUtil.getEntityManager();
		boolean salida = false;
		try{		
			em.getTransaction().begin();				
			em.remove(em.contains(nexo) ? nexo : em.merge(nexo));
			em.getTransaction().commit();
			salida = true;
		}
		catch (Exception e){
			e.printStackTrace();	
		}finally{ 
	    	  if(em.isOpen() ){
				  em.close();
			  }		
		}
		return salida;
	}
	
//	
//	public boolean update (Movimiento m){ 
//		boolean salida=false;
//		try{
//			
//			EntityManager em=JpaUtil.getEntityManager();
//			
//			Movimiento aux = em.find(Movimiento.class, m.getMovimientoID());			
//			
//			em.getTransaction().begin();
//			 
//			
//			aux.setMovimientoID(m.getMovimientoID());			
//			aux.setComprobante(m.getComprobante());
//			aux.setCotizacion(m.getCotizacion());
//			aux.setContratoID(m.getContratoID());
//			aux.setNombreCliente(m.getNombreCliente());
//			aux.setNroMovimiento(m.getNroMovimiento());
//			aux.setReferencia(m.getReferencia());
//			aux.setTipoOT(m.getTipoOT());
//			aux.setTipoReferencia(m.getTipoReferencia());  
//			aux.setFecha(m.getFecha());
//			aux.setCodigoMovimientoID(m.getCodigoMovimientoID());
//			aux.setClienteID(m.getClienteID());
//			
//			em.flush();
//			em.getTransaction().commit();
//			em.close();
//			salida=true;
//		}
//		catch (Exception e){
//			e.printStackTrace();
//		}		
//		return salida;
//	}
	public ArrayList<Referencia> getListaReferencias(){
		EntityManager em=JpaUtil.getEntityManager();
		TypedQuery<Referencia> consulta = em.createNamedQuery("Referencia.findAll", Referencia.class);
		
		try{
			return (ArrayList<Referencia>) consulta.getResultList();
		}
		catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	public ArrayList<Referencia> getListaCMPorTipoRef(int cod){
		ArrayList <Referencia> salida= new ArrayList <Referencia> ();
		
		try{			
			EntityManager em=JpaUtil.getEntityManager();
			
			String consulta ="Select n From Referencia n where  n.codigoMovimientoID = "+ cod;
			System.out.println("Consulta movimiento "+consulta);
			TypedQuery<Referencia> consultaFuncionario= em.createQuery(consulta, Referencia.class);
			ArrayList<Referencia> auxLista = (ArrayList<Referencia>) consultaFuncionario.getResultList();
			
			System.out.println(">>>>" + auxLista);
			
			salida= auxLista;
		}
		catch (Exception e){
			e.printStackTrace();;
		}				
		return salida;
	}
	
	
	
	//Arreglar aca para que pregunte por ver en ot o ver en no ot
	
	private ArrayList<Cod_Movimiento> getListaCodMovimientos(boolean esOT){
	  ArrayList <Cod_Movimiento> salida= new ArrayList <Cod_Movimiento> ();
    
    try{      
      String consulta="";
      EntityManager em=JpaUtil.getEntityManager();
            
      if(esOT){
        consulta ="Select cm From Cod_Movimiento cm where  cm.verEnOT = 1";
      }
      else{
        consulta ="Select cm From Cod_Movimiento cm where  cm.verNoOT = 1";
      }
            
      System.out.println("Consulta movimiento "+consulta);
      TypedQuery<Cod_Movimiento> consultaFuncionario= em.createQuery(consulta, Cod_Movimiento.class);
      ArrayList<Cod_Movimiento> auxLista = (ArrayList<Cod_Movimiento>) consultaFuncionario.getResultList();
      
      System.out.println(">>>>" + auxLista);
      
      salida= auxLista;
    }
    catch (Exception e){
      e.printStackTrace();;
    }       
    return salida;

	}
	
	
	
  public ArrayList<Cod_Movimiento> getListaCodMovimientosEnOT(){
    return  getListaCodMovimientos(true);
  }
	
  
  public ArrayList<Cod_Movimiento> getListaCodMovimientosComun(){
    return  getListaCodMovimientos(false);
  }
		
	public static String getCodigoString(int cod){
	  try{     
      EntityManager em=JpaUtil.getEntityManager();      
      Cod_Movimiento cm= em.find(Cod_Movimiento.class, cod);
      return cm.getDescripcion();
    }
    catch (Exception e){
      e.printStackTrace();;
    }
    return null;
	     
	}

	
	public ArrayList<NexoMovimiento> devolver(Map<Integer,Integer> mapaArrendamiento,int movid ){
	  EntityManager em=JpaUtil.getEntityManager(); 
	  ArrayList<NexoMovimiento> salida=new ArrayList<NexoMovimiento>();
    try{     
    	em.getTransaction().begin();
      Movimiento mov =em.find(Movimiento.class, movid);
      mov.getNexos().size();
//      em.flush(); 
//      movID=mov.getMovimientoID();
      
      
      Iterator<Integer> it = mapaArrendamiento.keySet().iterator();
      while(it.hasNext()){
        Integer key = it.next();      
        int devolucion=mapaArrendamiento.get(key);                     
        Arrendamiento arr= em.find(Arrendamiento.class, key);
        //reducir el saldo
        int saldo = arr.getSaldo()-devolucion;
        arr.setSaldo(saldo); 
        //em.flush();
        //crear nuevo nexo con idmov
        NexoMovimiento nexo= new NexoMovimiento();
        Articulo a =em.find(Articulo.class,arr. getArticulo().getArticuloID() );
        nexo.setArticulo(a);
        nexo.setCantidad(BigDecimal.valueOf(devolucion));
        nexo.setCosto(arr.getCosto());
        //nexo.setMovimientoID(mov.getMovimientoID());  
        a.setStock(a.getStock().add(nexo.getCantidad()));        
        //em.persist(nexo);
        mov.getNexos().add(nexo);
        salida.add(nexo);
      }
      em.getTransaction().commit(); 
    }
    catch (Exception e){
    	salida=new ArrayList<NexoMovimiento>();
      e.printStackTrace();
     }finally{ 
       if(em.isOpen() ){
         em.close();
       }    
    }  
    return salida;    
	}
	
	public ArrayList<Movimiento> movimientosPorFechas(Date fechaIni, Date fechaFin){
		ArrayList<Movimiento> salida=new ArrayList<Movimiento>();
	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	  String fi;
	  String ff;
		String consulta="Select mov from Movimiento mov "; 
		 
	  if (fechaIni!=null){
	  	fi = formatter.format(fechaIni);
	  	if(fechaFin!=null){
	  		ff = formatter.format(fechaFin);
	  		consulta+="where fecha BETWEEN '" +
	  				fi + "' AND '" + ff + "'" ;
	  	}
	  	else{
	  		consulta+=" where fecha >= '"+ fi+"'" ;
	  	}
	  }
	  else{
	  	if(fechaFin!=null){
	  		ff = formatter.format(fechaFin);
	  		consulta+=" where fecha <= '"+ ff+"'" ;
	  	}
	  }
		EntityManager em=JpaUtil.getEntityManager();
		TypedQuery<Movimiento> query= em.createQuery(consulta, Movimiento.class);
		try{
			System.out.println(">>>consulta>"+ consulta);
			salida= (ArrayList<Movimiento>) query.getResultList();
		}finally{ 
	  	  if(em!=null ){
				  em.close();
			  }		
		}
		
		System.out.println(">>>filtrado por fechas: "+salida.size());
		return salida;
	}
	
}
	
//	public ArrayList<Codigo> getListaCodigos(){
//		EntityManager em=JpaUtil.getEntityManager();
//		TypedQuery<Codigo> consulta = em.createNamedQuery("Codigo.findAll", Codigo.class);
//		
//		try{
//			return (ArrayList<Codigo>) consulta.getResultList();
//		}
//		catch (Exception e){
//			e.printStackTrace();
//			return null;
//		}
//	}
//	


//public boolean addNexo(NexoMovimiento nexo){
//	
//	EntityManager em=JpaUtil.getEntityManager(); 
//	boolean salida=false; 
//	try{			
//		Movimiento m = em.find(Movimiento.class, nexo.getMovimientoID());
//		Articulo a= nexo.getArticulo();      
//		acomodarNexo(m,nexo,a);
//		em.getTransaction().begin();
//		em.persist(nexo);
//		
//	//salida por arrendamiento
//		if(m.getCodigoMovimientoID()==25){
//			Arrendamiento arrendamiento=new Arrendamiento();
//			arrendamiento.setArticulo(a);
//			arrendamiento.setCantidad(nexo.getCantidad().intValue());
//			arrendamiento.setMovimientoID(m.getMovimientoID()); 
//			arrendamiento.setSaldo(nexo.getCantidad().intValue());
//			em.persist(arrendamiento);
//		}
//		
//		
//		em.getTransaction().commit(); 
//		salida = true;
//	}
//	catch (Exception e){
//		e.printStackTrace();
//	 }finally{ 
//    	  if(em.isOpen() ){
//			  em.close();
//		  }		
//	}
//	return salida;
//}









//public boolean addListNexos(ArrayList<NexoMovimiento> nexos){
//EntityManager em=JpaUtil.getEntityManager(); 
//boolean salida=false; 
//try{
//em.getTransaction().begin();
//for (NexoMovimiento nexo : nexos){
//	Movimiento m = em.find(Movimiento.class, nexo.getMovimientoID());
//	Articulo a=null;
//	
//		//si son de los 3 tipos hay que realizar una cuenta
//		if(m!=null && ( m.getCodigoMovimientoID()==1||m.getCodigoMovimientoID()==2||m.getCodigoMovimientoID()==3 )){
//      a = nexo.getArticulo();      
//      
//      BigDecimal cdCopia=a.getCostoDolares();
//      BigDecimal cpCopia=a.getCostoPesos();
//      
//      BigDecimal cotizacion = m.getCotizacion().setScale(3,BigDecimal.ROUND_HALF_UP);
//      BigDecimal costoPesos = nexo.getCosto().setScale(3,BigDecimal.ROUND_HALF_UP); 
//      BigDecimal costoDolares= costoPesos.divide(cotizacion,3,BigDecimal.ROUND_HALF_UP);
//      BigDecimal stock = a.getStock().setScale(3,BigDecimal.ROUND_HALF_UP);
//      BigDecimal cantidad = nexo.getCantidad().setScale(3,BigDecimal.ROUND_HALF_UP);
//      
//      BigDecimal cPV= (stock.multiply(a.getCostoPesos())).setScale(3,BigDecimal.ROUND_HALF_UP);
//      BigDecimal cPN= (cantidad.multiply(costoPesos)).setScale(3,BigDecimal.ROUND_HALF_UP);
//      BigDecimal auxsuma=(cPV.add(cPN)).setScale(3,BigDecimal.ROUND_HALF_UP);
//      BigDecimal auxdivisor=(stock.add(cantidad)).setScale(3,BigDecimal.ROUND_HALF_UP);      
//      BigDecimal auxCP=auxsuma.divide(auxdivisor,2,BigDecimal.ROUND_HALF_UP); 
//      a.setCostoPesos(auxCP);	      
//      
//      BigDecimal cDV= (stock.multiply(a.getCostoDolares())).setScale(3,BigDecimal.ROUND_HALF_UP); 
//      BigDecimal cDN= (cantidad.multiply(costoDolares)).setScale(3,BigDecimal.ROUND_HALF_UP); 
//      auxsuma=(cDV.add(cDN)).setScale(3,BigDecimal.ROUND_HALF_UP); 
//      auxdivisor=(stock.add(cantidad)).setScale(3,BigDecimal.ROUND_HALF_UP); 
//      BigDecimal auxCD= auxsuma.divide(auxdivisor,2,BigDecimal.ROUND_HALF_UP);  
//      a.setCostoDolares(auxCD);
//      
//      a.setUltimoCostoDolares(cdCopia);
//      a.setUltimoCostoPesos(cpCopia);	
//    }
//		
//	
//	
//	
//	em.persist(nexo);
//	 
//	if (a!=null){
//	  em.persist(a);
//	}			
//	
//	em.getTransaction().commit(); 				
//}
//salida = true;
//}
//catch (Exception e){
//e.printStackTrace();
//}finally{ 
//	if(em.isOpen() ){
//		em.close();
//	}		
//}	
//return salida;
//}
//
//
