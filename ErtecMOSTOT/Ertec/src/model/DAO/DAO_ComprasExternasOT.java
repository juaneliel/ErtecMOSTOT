package model.DAO;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import model.Articulo;
import model.ComprasExternasOT;
import util.JpaUtil;

public class DAO_ComprasExternasOT {

	public void addComprasExternasOT(ComprasExternasOT m){
		try{
			EntityManager em=JpaUtil.getEntityManager(); 
			em.getTransaction().begin();
			em.persist(m);
			em.getTransaction().commit();
		}
		catch (Exception e){
			e.printStackTrace();
		}				
	}
	
	public ArrayList<ComprasExternasOT> getListaComprasExternasOTs(){
		EntityManager em=JpaUtil.getEntityManager();
		TypedQuery<ComprasExternasOT> consultaFuncionario= em.createNamedQuery("Funcionario.findAll", ComprasExternasOT.class);
		
		try{
			return (ArrayList<ComprasExternasOT>) consultaFuncionario.getResultList();
		}
		catch (Exception e){
			return null;
		}
	}
	
	public ComprasExternasOT findComprasExternasOT(ComprasExternasOT m){
		return null;
	}
	
	public boolean update (ComprasExternasOT a){ 
		boolean salida=false;
		EntityManager em=JpaUtil.getEntityManager();
		try{
			
			
			ComprasExternasOT aux = em.find(ComprasExternasOT.class, a.getId());			
			
			em.getTransaction().begin();
			
//			if(a.getArticulo()!=null){
//			  aux.setArticuloID(a.getArticulo().getArticuloID());
//			}			
			aux.setCantidad(a.getCantidad());
			aux.setId(a.getId());
			aux.setFecha(a.getFecha());
			aux.setMoneda(a.getMoneda());
			aux.setOtid(a.getOtid());
			aux.setPrecio_Unitario(a.getPrecio_Unitario());
			if(a.getProveedor()!=null){
			  aux.setProveedorID(a.getProveedor().getProveedorID()); 
			} 		 
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
	
	public void deleteComprasExternasOT(ComprasExternasOT m){ 
		try{
			EntityManager em=JpaUtil.getEntityManager();
			em.getTransaction().begin();
			em.remove(em.contains(m) ? m : em.merge(m));
			em.flush();
			em.getTransaction().commit();
			em.close();
		}
		catch (Exception e){
			e.printStackTrace();
		}		
	}
}
