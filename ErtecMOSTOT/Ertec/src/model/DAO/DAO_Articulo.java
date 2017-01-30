package model.DAO;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import model.Articulo;
import model.Funcionario;
import model.Movimiento;
import model.Articulo;
import model.Articulo;
import util.JpaUtil;

public class DAO_Articulo {

	public boolean add (Articulo o){
		EntityManager em=JpaUtil.getEntityManager(); 
		boolean salida=false;
		try{			
			em.getTransaction().begin();
			em.persist(o);
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


	public boolean delete (Articulo f){ 
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
	
	
	public ArrayList<Articulo> getLista (){
		EntityManager em=JpaUtil.getEntityManager();
		TypedQuery<Articulo> consultaFuncionario= em.createNamedQuery("Articulo.findAll", Articulo.class);
		ArrayList<Articulo> salida = new ArrayList<Articulo>();
		try{
			salida= (ArrayList<Articulo>) consultaFuncionario.getResultList();
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
	
	public static Articulo findArticulo(int idA){
		EntityManager em=JpaUtil.getEntityManager();		
		return em.find(Articulo.class,idA );	
	}
	
	
	public ArrayList<Articulo> completarArticulo(String buscar){
		ArrayList<Articulo> salida= new ArrayList<Articulo>();
		
		String consulta="";
		//se verifica si es numerico se busca por el id sino se busca por nombre
		try {
			int idArticulo = Integer.parseInt(buscar);
			consulta="SELECT art FROM Articulo art Where articuloID like '"+idArticulo+"%' ";
			
		} catch (NumberFormatException nfe){
			consulta="SELECT art FROM Articulo art Where descripcion like   '%"+
					buscar+"%' ";
		}
		

		System.out.println("Consulta: "+consulta);
		EntityManager em=JpaUtil.getEntityManager();
		TypedQuery<Articulo> query= em.createQuery(consulta, Articulo.class);
		try{
			System.out.println(">>>consulta>"+ consulta);
			salida =  (ArrayList<Articulo>) query.getResultList();
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
	
	
	
	
	
	
	
	
	public ArrayList<Articulo> findArticulo(Articulo a){
		String consulta="SELECT art FROM Articulo art Where ";
		int contador=0;
		
		if (a.getArticuloID() !=0){			
			consulta+= " articuloID like '"+a.getArticuloID()+"%' ";
			++contador;
		}
		
		if (! a.getDescripcion().equals("")){
			if(contador>0){
				consulta+=" AND ";
			}
			consulta+= " descripcion like '%"+a.getDescripcion()+"%'";
			++contador;
		} 	
			
		

		
		if (contador==0){
			consulta = "SELECT fun FROM Articulo fun";
		}
		
		EntityManager em=JpaUtil.getEntityManager();
		TypedQuery<Articulo> consultaArticulo= em.createQuery(consulta, Articulo.class);
		try{
			System.out.println(">>>consulta>"+ consulta);
			return (ArrayList<Articulo>) consultaArticulo.getResultList();
		}
		catch (Exception e){
			e.printStackTrace();
			return new ArrayList<Articulo>();
		}		
	}
	
	
	public void deleteArticulo(Articulo a){ 
		try{
			EntityManager em=JpaUtil.getEntityManager();
			em.getTransaction().begin();
			em.remove(em.contains(a) ? a : em.merge(a));
			em.flush();
			em.getTransaction().commit();
			em.close();
		}
		catch (Exception e){
			e.printStackTrace();
		}		
	}
	
	public boolean update (Articulo a){ 
		boolean salida=false;
		EntityManager em=JpaUtil.getEntityManager();
		try{
			
			
			Articulo aux = em.find(Articulo.class, a.getArticuloID());			
			
			em.getTransaction().begin();
			
			aux.setArticuloID(a.getArticuloID());
			aux.setCalidad(a.getCalidad());
			aux.setCostoDolares(a.getCostoDolares());
			aux.setCostoPesos(a.getCostoPesos());
			aux.setDescripcion(a.getDescripcion());
			aux.setMedida(a.getMedida());
			aux.setObservacion(a.getObservacion());
			aux.setPrecioVenta(a.getPrecioVenta());
			aux.setStock(a.getStock());
			aux.setStockMinimo(a.getStockMinimo());
			aux.setUltimoCostoDolares(a.getUltimoCostoDolares());
			aux.setUltimoCostoPesos(a.getUltimoCostoPesos());
			
			em.flush();
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
	
	public static ArrayList<DAO_infoMovDeArticulos> filtrarInfoMovArticulo (int articuloID,Date fechaIni, Date fechaFin){
		ArrayList<DAO_infoMovDeArticulos> salida=new ArrayList<DAO_infoMovDeArticulos>();
		
		System.out.println("filtrarInfoMovArticulo "+fechaIni+" "+fechaFin);
		
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String fi = "";
    String ff="";
    if(fechaIni!=null){
      fi = formatter.format(fechaIni); 
    }
    else{
    	
    }
    
    if (fechaFin!=null){
      ff = formatter.format(fechaFin); 
    }
    else{
      ff = formatter.format(new Date()); 
    }
		  
		  
		  
		  
		  
		String consulta="Select n.movimientoID,m.codigoMovimientoID , m.referencia,n.cantidad,n.costo,m.nombreCliente,m.fecha"
				+ " from NexoMovimiento n, Movimiento m where n.articuloID=" +articuloID+ " and m.movimientoID = n.movimientoID and m.fecha BETWEEN '" +
				fi + "' AND '" + ff + "'" ;
			
		EntityManager em=JpaUtil.getEntityManager();
		
	   try{	       
	      System.out.println(">>>consulta>"+ consulta);     
	      Query q = em.createQuery(consulta);            
	      List < Object[]>r = (List<Object[]>) q.getResultList();
	      
	      for (Object[] results : r){ 
	      	DAO_infoMovDeArticulos aux = new DAO_infoMovDeArticulos();
	        aux.setMovimientoID(Long.parseLong(results[0].toString()));
	      	aux.setCodigoMovimientoID(Integer.parseInt(results[1].toString()));
	      	aux.setReferencia(Integer.parseInt(results[2].toString()));
	      	aux.setCantidad( new BigDecimal(results[3].toString()));
	      	if(results[4]!=null){
		      	aux.setCosto(new BigDecimal(results[4].toString()));
	      	}
	      	else{
	      		aux.setCosto(BigDecimal.ZERO);
	      	}
	      	if(results[5]!=null){
		      	aux.setNombreCliente(results[5].toString());	      		
	      	}
	      	else{
	      		aux.setNombreCliente("");
	      	}
	      	aux.setFecha(formatter.parse(results[6].toString()));
	      	 
	        salida.add(aux);
	        
	      } 
	    }
	   catch(Exception e){
	      e.printStackTrace();
	    }
	   finally{ 
	  	  if(em!=null ){
				  em.close();
			  }		
		}
		
		System.out.println(">>>filtrado por fechas: "+salida.size());
		return salida;
	}
	
	
}
