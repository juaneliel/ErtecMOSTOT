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
	
	public boolean update (ComprasExternasOT ce){ 
		EntityManager em=JpaUtil.getEntityManager();
		boolean salida=false;
		try{			
			em.getTransaction().begin();
			em.merge(ce);
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
