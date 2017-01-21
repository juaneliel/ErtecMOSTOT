package model.DAO;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import model.Proveedores;
import model.Usuario;
import util.JpaUtil;
import org.apache.commons.codec.digest.DigestUtils;

public class DAO_Usuario {
	public boolean add(Usuario f){
		EntityManager em=JpaUtil.getEntityManager(); 
		boolean salida=false;
		try{	 
			String textoSinEncriptar=f.getClave(); 
			String cleveMD5=DigestUtils.md5Hex(textoSinEncriptar); 			
			f.setClave(cleveMD5);
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
	
	
	public boolean delete(Usuario f){ 
		EntityManager em=JpaUtil.getEntityManager();
		boolean salida = false;
		try{			
			em.getTransaction().begin();
			em.remove(em.contains(f) ? f : em.merge(f));
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
	
	public boolean update(Usuario u){ 
		EntityManager em=JpaUtil.getEntityManager();
		boolean salida = false;
		try{	
			Usuario aux = em.find(Usuario.class, u.getNombre());			
			
			em.getTransaction().begin();
			aux.setAdmin(u.getAdmin());
			aux.setArticulos(u.getArticulos());
			
			//String textoSinEncriptar=u.getClave(); 
		//	String cleveMD5=DigestUtils.md5Hex(textoSinEncriptar); 			
		//	aux.setClave(cleveMD5);
			aux.setClientes(u.getClientes());
			aux.setFuncionarios(u.getFuncionarios());
			aux.setManodeobra(u.getManodeobra());
			aux.setMovimientos(u.getMovimientos());
			aux.setNombre(u.getNombre());
			aux.setOt(u.getOt());
			aux.setProveedores(u.getProveedores());
			aux.setReclamos(u.getReclamos());
			aux.setEmail(u.getEmail());
			
			//em.flush();
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
	
	public static Usuario login(String nombre,String clave){
		try{
			EntityManager em=JpaUtil.getEntityManager(); 
			Usuario u =em.find(Usuario.class,nombre );
			if(u==null){
				return null;
			}
			String claveMD5=DigestUtils.md5Hex(clave); 		
			System.out.println("clavelogin "+claveMD5+" "+u.getClave());
			if(claveMD5.equals(u.getClave())){
				return u;
			}
			else{
				return null;
			}
		}
		catch(Exception e){

		}
		return null;
	}
	
	public ArrayList<Usuario> getLista(){
		EntityManager em=JpaUtil.getEntityManager();
		TypedQuery<Usuario> consulta= em.createNamedQuery("Usuario.findAll", Usuario.class);
		
		try{
			return (ArrayList<Usuario>) consulta.getResultList();
		}
		catch (Exception e){
			return null;
		}
	}
}
