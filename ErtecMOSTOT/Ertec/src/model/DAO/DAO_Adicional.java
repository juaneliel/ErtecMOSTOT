package model.DAO;

import java.util.ArrayList;

import javax.persistence.EntityManager;

import javax.persistence.TypedQuery;
import model.Adicional;
import model.Ot;
import util.JpaUtil;

public class DAO_Adicional {

	public boolean addAdicional(Adicional a, Ot ot){
		EntityManager em=JpaUtil.getEntityManager(); 
		try{			
			em.getTransaction().begin();
			em.persist(a);		
			em.persist(ot);
			em.getTransaction().commit();
			em.close();
			return true;
		}
		catch (Exception e){
			e.printStackTrace();
		}finally{ 
		  if(em.isOpen()){
			  em.close();
		  }		 
		}
		return false;
	}


	
	public ArrayList<Adicional> getListaAdicinales(){
		EntityManager em=JpaUtil.getEntityManager();
		TypedQuery<Adicional> query= em.createNamedQuery("Adicional.findAll", Adicional.class);
		
		try{
			return (ArrayList<Adicional>) query.getResultList();
		}
		catch (Exception e){
			return null;
		}
	}
	
	public void deleteAdicional(Adicional a){ 
		EntityManager em=JpaUtil.getEntityManager();
		try{			
			em.getTransaction().begin();
			em.remove(em.contains(a) ? a : em.merge(a));
			em.flush();
			em.getTransaction().commit();
			em.close();
		}
		catch (Exception e){
			e.printStackTrace();	
	    }finally{ 
			  if(em.isOpen()){
				  em.close();
			  }		
		}
	}
	
	
	public boolean updateAdicional(Adicional a){ 
		EntityManager em=JpaUtil.getEntityManager(); 
		boolean salida=false;
		try{			
			em.getTransaction().begin();
			em.persist(a);
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
}




















//
//public ArrayList<Adicional> findFuncionario(Adicional f){
//  
//String consulta="SELECT fun FROM Funcionario fun Where ";
//int contador=0;
//
//if (f.getFuncionarioID()!=0){     
//  consulta+= " funcionarioID= '"+f.getFuncionarioID()+"' ";
//  ++contador;
//}
//
//if (! f.getNombre().equals("")){
//  if(contador>0){
//    consulta+=" AND ";
//  }
//  consulta+= " nombre= '"+f.getNombre()+"' ";
//  ++contador;
//}
//
//if (! f.getEmail().equals("")){
//  if(contador>0){
//    consulta+=" AND ";
//  }
//  consulta+= " email = '"+f.getEmail()+ "' ";
//  ++contador;
//}
//if (! f.getTelefono().equals("")){
//  if(contador>0){
//    consulta+=" AND ";
//  }
//  consulta+= " telefono= "+f.getFuncionarioID();
//  ++contador;
//} 
//if (contador==0){
//  consulta = "SELECT fun FROM Funcionario fun";
//}
//
//EntityManager em=JpaUtil.getEntityManager();
//TypedQuery<Funcionario> consultaFuncionario= em.createQuery(consulta, Funcionario.class);
//try{
//  System.out.println(">>>consulta>"+ consulta);
//  return (ArrayList<Funcionario>) consultaFuncionario.getResultList();
//}
//catch (Exception e){
//  e.printStackTrace();
//  return new ArrayList<Funcionario>();
//}
//return null;
//}


