package model.DAO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import model.Adicional;
import model.Arrendamiento;
import model.Articulo;
import model.Cod_Movimiento; 

import model.Funcionario;
import model.Movimiento;
import model.NexoMovimiento;
import model.Ot;
import model.Referencia;
import util.JpaUtil;

public class DAO_Movimiento {
	
	
	public static boolean add(Movimiento f){
		EntityManager em=JpaUtil.getEntityManager(); 
		boolean salida=false;
		try{			
			em.getTransaction().begin();
			em.persist(f);
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


	public ArrayList<Arrendamiento> getArrendado (int idCliente){
	  ArrayList<Arrendamiento> salida = new ArrayList<Arrendamiento>();
	    
	  try{       
      EntityManager em=JpaUtil.getEntityManager();      
      String consulta ="SELECT a FROM Movimiento m, Arrendamiento a "+
                       "WHERE a.movimientoID = m.movimientoID and m.clienteID=0 "+
                       "ORDER BY id DESC ";
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
	
	public boolean updateNexo(NexoMovimiento o){
	  EntityManager em=JpaUtil.getEntityManager();
    boolean salida = false;
    try{  
      NexoMovimiento aux = em.find(NexoMovimiento.class, o.getNexoMovimientoID());
      em.getTransaction().begin();             
      aux.setCantidad(o.getCantidad());
      aux.setCosto(o.getCosto());
      aux.setFecha(o.getFecha());
      if(o.getArticulo()!=null){
        aux.setArticuloID(o.getArticulo().getArticuloID());
      }           
      em.getTransaction().commit();
      salida=true;
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
	
	public boolean update(Movimiento m){ 
		EntityManager em=JpaUtil.getEntityManager();
		boolean salida = false;
		try{	
			Movimiento aux = em.find(Movimiento.class, m.getMovimientoID());
			em.getTransaction().begin();			
			aux.setMovimientoID(m.getMovimientoID());			
			aux.setComprobante(m.getComprobante());
			aux.setCotizacion(m.getCotizacion());
			aux.setContratoID(m.getContratoID());
			aux.setNombreCliente(m.getNombreCliente());
			aux.setNroMovimiento(m.getNroMovimiento());
			aux.setReferencia(m.getReferencia());
			aux.setTipoOT(m.getTipoOT());
			aux.setTipoReferencia(m.getTipoReferencia());  
			aux.setFecha(m.getFecha());
			aux.setCodigoMovimientoID(m.getCodigoMovimientoID());
			if(m.getCliente()!=null){
			  aux.setClienteID(m.getCliente().getClienteID());
			  aux.setNombreCliente(m.getCliente().getNombre());
			}			
			em.getTransaction().commit();
			salida=true;
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

	public boolean addNexo(NexoMovimiento nexo){
		EntityManager em=JpaUtil.getEntityManager(); 
		boolean salida=false; 
		Movimiento m = em.find(Movimiento.class, nexo.getMovimientoID());
		Articulo a=null;
		if(m.getCodigoMovimientoID()==1||m.getCodigoMovimientoID()==2||m.getCodigoMovimientoID()==3){
      a = em.find(Articulo.class, nexo.getArticuloID());
      
      
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

    }
		
		try{			
			em.getTransaction().begin();
			em.persist(nexo);
			 
			if (a!=null){
			  em.persist(a);
			}			
			
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
	
	public static ArrayList <Movimiento> getMovimientosOT(long idOT){	
		ArrayList <Movimiento> salida = new ArrayList <Movimiento>();
		try{
			//a partir del id de la ot se deben de buscar en la tabala de movimientos
			//aquellos que sean "TipoReferencia" OT
			//y que Referencia sea el id de la ot pasado
			
			EntityManager em=JpaUtil.getEntityManager();
			
			String consulta ="Select m From Movimiento m where (m.tipoReferencia = 'OR' OR m.tipoReferencia = 'OT') and m.referencia = "+idOT + " ORDER BY MovimientoID DESC";
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
	
	
	
	
	public ArrayList <NexoMovimiento> getNexos(int movimientoID){
		ArrayList <NexoMovimiento> salida= new ArrayList <NexoMovimiento> ();
		
		try{			
			EntityManager em=JpaUtil.getEntityManager();
			
			String consulta ="Select n From NexoMovimiento n where  n.movimientoID = "+ movimientoID;
			System.out.println("Consulta movimiento "+consulta);
			TypedQuery<NexoMovimiento> consultaFuncionario= em.createQuery(consulta, NexoMovimiento.class);
			ArrayList<NexoMovimiento> auxLista = (ArrayList<NexoMovimiento>) consultaFuncionario.getResultList();
			
			System.out.println(">>>>" + auxLista.size());
			
			salida= auxLista;
		}
		catch (Exception e){
			e.printStackTrace();;
		}				
		return salida;
	}
	
	public boolean deleteNexo(NexoMovimiento nexo){
		EntityManager em=JpaUtil.getEntityManager();
		boolean salida = false;
		try{		
			em.getTransaction().begin();	
			Movimiento mov= em.find(Movimiento.class,nexo.getMovimientoID());
			mov.getNexos().remove(nexo);				
			em.remove(em.contains(nexo) ? nexo : em.merge(nexo));
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
	
	
	
	
	
	
	
	public Cod_Movimiento getCodMovimiento(int cod){
		EntityManager em=JpaUtil.getEntityManager();
		return em.find(Cod_Movimiento.class, cod);
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

	
	public boolean devolver(Map<Integer,Integer> mapaArrendamiento,Movimiento mov ){
	  EntityManager em=JpaUtil.getEntityManager(); 
    boolean salida=false;
    int movID=0;
    try{     
      em.getTransaction().begin();
      em.persist(mov);
      em.flush(); 
      movID=mov.getMovimientoID();
      
      
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
        nexo.setArticuloID(arr.getArticuloID());
        nexo.setCantidad(BigDecimal.valueOf(devolucion));
        nexo.setCosto(arr.getCosto());
        nexo.setMovimientoID(movID); 
        
        em.persist(nexo);
        
        em.getTransaction().commit();    
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
//	
	
	
}
