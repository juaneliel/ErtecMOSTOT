package model.DAO;
 
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
 
import model.Proveedores;
import util.JpaUtil;

public class DAO_Proveedor {
	
	public boolean add(Proveedores p)  {
		boolean salida=false;
		EntityManager em=JpaUtil.getEntityManager(); 
		try{			
			em.getTransaction().begin();
			em.persist(p);
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
 
	
	public ArrayList<Proveedores> getLista(){
		EntityManager em=JpaUtil.getEntityManager();
		TypedQuery<Proveedores> consultaFuncionario= em.createNamedQuery("Proveedores.findAll", Proveedores.class);
		
		try{
			return (ArrayList<Proveedores>) consultaFuncionario.getResultList();
		}
		catch (Exception e){
			return null;
		}
	}
	
	public boolean update(Proveedores p){
		boolean salida=false;
		
		EntityManager em=JpaUtil.getEntityManager();
		try{	
			Proveedores aux = em.find(Proveedores.class, p.getProveedorID());			
			
			em.getTransaction().begin();
			
			//seteos
			aux.setDireccion(p.getDireccion());
			aux.setEmail(p.getEmail());
			aux.setFax(p.getFax());
			aux.setNombre(p.getNombre());
			aux.setObservacion(p.getObservacion());
			aux.setProveedorID(p.getProveedorID());
			aux.setRuc(p.getRuc());
			aux.setTelefono(p.getTelefono());
			em.getTransaction().commit();
			em.close();
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
	
	public boolean delete (Proveedores p){ 
		boolean salida=false;
		EntityManager em=JpaUtil.getEntityManager();
		try{			
			em.getTransaction().begin();
			em.remove(em.contains(p) ? p : em.merge(p));
			em.flush();
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
	
	
	
}
