package model.DAO;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import model.Articulo; 
import model.CodigosMO;
import model.Funcionario;
import model.ManoObra;
import model.Movimiento;
import util.JpaUtil;

public class DAO_ManoObra {
	
	public static boolean add(ManoObra o){
		boolean salida=false;
		EntityManager em=JpaUtil.getEntityManager(); 
		try{			
			em.getTransaction().begin();
			em.persist(o);
			em.getTransaction().commit();
			salida= true;
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

	public ArrayList<ManoObra> find(ManoObra o){
		
//		String consulta="SELECT fun FROM Funcionario fun Where ";
//		int contador=0;
//		
//		if (f.getFuncionarioID()!=0){			
//			consulta+= " funcionarioID= '"+f.getFuncionarioID()+"' ";
//			++contador;
//		}
//		
//		if (! f.getNombre().equals("")){
//			if(contador>0){
//				consulta+=" AND ";
//			}
//			consulta+= " nombre= '"+f.getNombre()+"' ";
//			++contador;
//		}
//		
//		if (! f.getEmail().equals("")){
//			if(contador>0){
//				consulta+=" AND ";
//			}
//			consulta+= " email = '"+f.getEmail()+ "' ";
//			++contador;
//		}
//		if (! f.getTelefono().equals("")){
//			if(contador>0){
//				consulta+=" AND ";
//			}
//			consulta+= " telefono= "+f.getFuncionarioID();
//			++contador;
//		} 
//		if (contador==0){
//			consulta = "SELECT fun FROM Funcionario fun";
//		}
//		
//		EntityManager em=JpaUtil.getEntityManager();
//		TypedQuery<Funcionario> consultaFuncionario= em.createQuery(consulta, Funcionario.class);
//		try{
//			System.out.println(">>>consulta>"+ consulta);
//			return (ArrayList<Funcionario>) consultaFuncionario.getResultList();
//		}finally{ 
//	  	  if(em!=null ){
//				  em.close();
//			  }		
//		}
		
		return null;
		
	}
	
	public ArrayList<ManoObra> getLista(){
		ArrayList<ManoObra> salida = new ArrayList<ManoObra>() ;
		EntityManager em=JpaUtil.getEntityManager();
		TypedQuery<ManoObra> query= em.createNamedQuery("ManoObra.findAll", ManoObra.class);
		
		try{
		  System.out.println(">>>DAOmo getlista>");
			salida = (ArrayList<ManoObra>) query.getResultList();
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
	
	public ArrayList<ManoObra> getFiltradaPorFechas(Date fechaIni, Date fechaFin){
		ArrayList<ManoObra> salida=new ArrayList<ManoObra>();
	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	  String fi;
	  String ff;
		String consulta="Select mo from ManoObra mo "; 
		 
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
		TypedQuery<ManoObra> query= em.createQuery(consulta, ManoObra.class);
		try{
			System.out.println(">>>consulta>"+ consulta);
			salida= (ArrayList<ManoObra>) query.getResultList();
		}finally{ 
	  	  if(em!=null ){
				  em.close();
			  }		
		}
		
		System.out.println(">>>filtrado por fechas: "+salida.size());
		return salida;
	}

	
	public static ArrayList<ManoObra> getFiltradaPorFechasYOT(Date fechaIni, Date fechaFin, int idOT){
		ArrayList<ManoObra> salida=new ArrayList<ManoObra>();
		String consulta="Select mo from ManoObra mo ";
		if(idOT!=0){
			consulta+=" where ordenTrabajo = " +  idOT;			
		} 
		else{
			consulta+=" where ordenTrabajo <> " +  idOT;	
		}
		
		
		  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		  String fi=null;
		  String ff=null;
		 
		 if(fechaIni!=null){
			 fi = formatter.format(fechaIni);
			 if(fechaFin!=null){
				 ff = formatter.format(fechaFin);
				 consulta+=" AND fecha BETWEEN '" +	fi + "' AND '" + ff + "'";					
			 }
			 else{
				 consulta+=" AND fecha >= '" +	fi + "'";					
			 }
		 }
		 else{
			 if(fechaFin!=null){
				 ff = formatter.format(fechaFin);
				 consulta+=" AND fecha <= '" +	ff + "'";	
			 }
		 }
		
			
		EntityManager em=JpaUtil.getEntityManager();
		TypedQuery<ManoObra> consultaFuncionario= em.createQuery(consulta, ManoObra.class);
		try{
			System.out.println(">>>consulta>"+ consulta);
			salida= (ArrayList<ManoObra>) consultaFuncionario.getResultList();
		}finally{ 
	  	  if(em!=null ){
				  em.close();
			  }		
		}		
		return salida;
	}
	
	
	public boolean delete(ManoObra o){ 
		boolean salida=false;
		EntityManager em=JpaUtil.getEntityManager();
		try{			
			em.getTransaction().begin();
			em.remove(em.contains(o) ? o : em.merge(o));			
			em.getTransaction().commit();
			salida= true;
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
	
	
	public boolean update(ManoObra o){ 
		EntityManager em=JpaUtil.getEntityManager();
		boolean salida=false;
		try{			
			em.getTransaction().begin();
			em.merge(o);
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
	
	
	//las fechas son obligatorias
	public ArrayList<ManoObra> getMOPorFiltros(Date fechaIni, Date fechaFin,int funID, int otID, String tipoHora,String tipoMano ){
		
		String consulta = "SELECT fun FROM ManoObra fun WHERE fecha BETWEEN "+ fechaIni +" AND "+fechaFin ;
		 
		
		if (funID!=0){
			consulta += " AND  ordenTrabajo = " + otID;
		}
		
		if (otID!=0){
			consulta += " AND  ordenTrabajo = " + otID;
		}
		
		if (tipoHora!=null){
			consulta += " AND  ordenTrabajo = '" + tipoHora + "'";
		}	

		if (tipoMano!=null){
			consulta += " AND  ordenTrabajo = '" + tipoMano + "'";
		} 
		
		
		ArrayList<ManoObra> salida= new ArrayList<ManoObra>(); 
		
		EntityManager em=JpaUtil.getEntityManager();
		TypedQuery<ManoObra> query= em.createQuery(consulta, ManoObra.class);
		try{
			System.out.println(">>>consulta>"+ consulta);
			salida=  (ArrayList<ManoObra>) query.getResultList();
		}catch (Exception e){
			e.printStackTrace();	
		}finally{ 
			if(em!=null ){
				em.close();
			}		
		}
		
		return salida;
	}
	
	
	
	public ArrayList<CodigosMO> getListaCodMO(){
		EntityManager em=JpaUtil.getEntityManager();
		TypedQuery<CodigosMO> consulta = em.createNamedQuery("CodigosMO.findAll", CodigosMO.class);
		
		try{
			return (ArrayList<CodigosMO>) consulta.getResultList();
		}
		catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static ArrayList <ManoObra> getManoObraOT(long idOT){	
		ArrayList <ManoObra> salida = new ArrayList <ManoObra>();
		try{
			//a partir del id de la ot se deben de buscar en la tabala de movimientos
			//aquellos que sean "TipoReferencia" OT
			//y que Referencia sea el id de la ot pasado
			
			EntityManager em=JpaUtil.getEntityManager();
			
			String consulta ="Select mo From ManoObra mo where  mo.ordenTrabajo = "+idOT;
			System.out.println("Consulta ManoObra "+consulta);
			TypedQuery<ManoObra> query = em.createQuery(consulta, ManoObra.class);
			ArrayList<ManoObra> auxLista = (ArrayList<ManoObra>) query.getResultList();
			
			System.out.println(">>>>" + auxLista);
			
			salida= auxLista;
		}
		catch (Exception e){
			e.printStackTrace();;
		}		
		return salida;		
	}
	
	
//	public ArrayList<Funcionario> completarFuncionario(String buscar){
//		ArrayList<Funcionario> salida= new ArrayList<Funcionario>();
//		
//		
//		String consulta="";
//		//se verifica si es numerico se busca por el id sino se busca por nombre
//		try {
//			int idFuncionario = Integer.parseInt(buscar);
//			consulta="SELECT fun FROM Funcionario fun Where funcionarioID like '%"+idFuncionario+"' ";
//			
//		} catch (NumberFormatException nfe){
//			consulta="SELECT fun FROM Funcionario fun Where nombre like   '%"+
//					buscar+"%' ";
//		}
//		
//		System.out.println("Consulta: "+consulta);
//		EntityManager em=JpaUtil.getEntityManager();
//		TypedQuery<Funcionario> query= em.createQuery(consulta, Funcionario.class);
//		try{
//			System.out.println(">>>consulta>"+ consulta);
//			salida =  (ArrayList<Funcionario>) query.getResultList();
//		}
//		catch (Exception e){
//			e.printStackTrace();
//		}finally{ 
//	    	  if(em.isOpen() ){
//				  em.close();
//			  }		
//		}		
//		return salida;
//	}
	
	
	
	
	public CodigosMO getCodigosMO(int idCod){
		EntityManager em=JpaUtil.getEntityManager();
		try{
			CodigosMO salida = em.find(CodigosMO.class, idCod);
			System.out.println("getcodigosmo dao " + salida.getCodigo() );			  
			return salida;
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<CodigosMO> getCodigosMOOT (){
		ArrayList<CodigosMO> salida= new ArrayList<CodigosMO>();
		String consulta="SELECT cod FROM CodigosMO cod Where tipo = 2";

		System.out.println("Consulta: "+consulta);
		EntityManager em=JpaUtil.getEntityManager();
		TypedQuery<CodigosMO> query= em.createQuery(consulta, CodigosMO.class);
		try{
			System.out.println(">>>consulta>"+ consulta);
			salida =  (ArrayList<CodigosMO>) query.getResultList();
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
	
	
	public ArrayList<CodigosMO> getCodigosMONoOT (){
		ArrayList<CodigosMO> salida= new ArrayList<CodigosMO>();
		String consulta="SELECT cod FROM CodigosMO cod Where tipo <> 2";

		System.out.println("Consulta: "+consulta);
		EntityManager em=JpaUtil.getEntityManager();
		TypedQuery<CodigosMO> query= em.createQuery(consulta, CodigosMO.class);
		try{
			System.out.println(">>>consulta>"+ consulta);
			salida =  (ArrayList<CodigosMO>) query.getResultList();
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
	
	
	
	
	
	public ArrayList<CodigosMO> completarCodigosMO(String buscar){
		ArrayList<CodigosMO> salida= new ArrayList<CodigosMO>();
		String consulta="SELECT pro FROM CodigosMO pro Where descripcion like   '%"+
		buscar+"%' ";

		System.out.println("Consulta: "+consulta);
		EntityManager em=JpaUtil.getEntityManager();
		TypedQuery<CodigosMO> query= em.createQuery(consulta, CodigosMO.class);
		try{
			System.out.println(">>>consulta>"+ consulta);
			salida =  (ArrayList<CodigosMO>) query.getResultList();
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
	
	
	
	

}
