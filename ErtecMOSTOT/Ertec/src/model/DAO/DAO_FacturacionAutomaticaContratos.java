package model.DAO;

import java.math.BigDecimal;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import model.Articulo;
import model.Cliente;
import model.Contrato;
import model.FacturacionAutomaticaContratos;
import util.JpaUtil;

public class DAO_FacturacionAutomaticaContratos {

	public static ArrayList<Contrato> getContratosFrecuencia(String frecuencia){
		String consulta = "Select c from contratos where retirado = 'N' and frecuencia = '"+frecuencia+"'";
		ArrayList<Contrato> salida = new ArrayList<Contrato>();
		EntityManager em=JpaUtil.getEntityManager();
		TypedQuery<Contrato> query= em.createQuery(consulta, Contrato.class);
		try{
			System.out.println(">>>consulta>"+ consulta);
			salida =  (ArrayList<Contrato>) query.getResultList();
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
	
	public static ArrayList<Contrato> getContratos(){
		String consulta = "Select c from contrato where retirado = 'N'";
		ArrayList<Contrato> salida = new ArrayList<Contrato>();
		EntityManager em=JpaUtil.getEntityManager();
		TypedQuery<Contrato> query= em.createQuery(consulta, Contrato.class);
		try{
			System.out.println(">>>consulta>"+ consulta);
			salida =  (ArrayList<Contrato>) query.getResultList();
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
	
	public static boolean graboFacturaAutomatica(Contrato contrato,  BigDecimal importe,BigDecimal iva, BigDecimal total,String descripcion,String descripcionAuxiliar){
 
    FacturacionAutomaticaContratos fac = new FacturacionAutomaticaContratos();
    fac.setCliente(contrato.getCliente());
    fac.setContrato(contrato);
    fac.setDescripcion(descripcion);
    fac.setDescripcionAuxiliar(descripcionAuxiliar);
    fac.setImporte(importe);
    fac.setIva(iva);
    fac.setTotal(total);
     
    EntityManager em=JpaUtil.getEntityManager(); 
 		boolean salida=false;
 		try{			
 			em.getTransaction().begin();
 			em.persist(fac);
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

	public static void deleteAll(){
		
	}
	
	public static boolean updateCuota(Contrato c){
		boolean salida=false;
		EntityManager em=JpaUtil.getEntityManager();
		try{			
			em.getTransaction().begin();
			em.merge(c);
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
	
}
