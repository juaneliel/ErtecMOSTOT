package model.DAO;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import model.Proveedores;
import model.Usuario;
import model.UsuarioLogin;
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
	
	public boolean updateClave(UsuarioLogin u,String claveVieja,String claveNueva,String email){
		EntityManager em=JpaUtil.getEntityManager();
		boolean salida = false;
		try{	
			em.getTransaction().begin();
			Usuario aux = em.find(Usuario.class, u.getNombre());	
			String textoSinEncriptar=claveVieja; 
			String cleveMD5=DigestUtils.md5Hex(textoSinEncriptar); 	 
			if(aux.getClave().equals(cleveMD5)){				 
				textoSinEncriptar=claveNueva; 
				cleveMD5=DigestUtils.md5Hex(textoSinEncriptar); 			
				aux.setClave(cleveMD5);				
				salida=true;
				System.out.println("entro en update clave");
			}	
			aux.setEmail(email);
			em.getTransaction().commit();
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
			aux.setAccesoAdmin(u.getAccesoAdmin());
			aux.setAccesoArticulos(u.getAccesoArticulos());			
			//String textoSinEncriptar=u.getClave(); 
		//	String cleveMD5=DigestUtils.md5Hex(textoSinEncriptar); 			
		//	aux.setClave(cleveMD5);
			aux.setAccesoClientes(u.getAccesoClientes());
			aux.setAccesoFichaPersonal(u.getAccesoFichaPersonal());
			aux.setAccesoFuncionarios(u.getAccesoFuncionarios());
			aux.setAccesoManodeobra(u.getAccesoManodeobra());
			aux.setAccesoMovimientos(u.getAccesoMovimientos());
			aux.setAccesoOt(u.getAccesoOt());
			aux.setAccesoProveedores(u.getAccesoProveedores());
			aux.setAccesoReclamos(u.getAccesoProveedores());  
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
			e.printStackTrace();
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
