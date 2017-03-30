package model.DAO;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import model.Articulo;
import model.Cliente;
import model.Contrato;
import model.DatosGlobale;
import model.NexoMovimiento;
import util.JpaUtil;

public class DAO_Cliente {

	
	public boolean add(Cliente f){
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


	
	public boolean update(Cliente c){ 
		EntityManager em=JpaUtil.getEntityManager();
		boolean salida = false;
		try{	
			Cliente aux = em.find(Cliente.class, c.getClienteID() );	
			em.getTransaction().begin();
			aux.setClienteID( c.getClienteID());
			aux.setCredito(c.getCredito());
			aux.setDirCliente(c.getDirCliente());
			aux.setLocCliente(c.getLocCliente());
			aux.setNombre(c.getNombre());
			aux.setRucCliente(c.getRucCliente());
			aux.setTelCliente(c.getTelCliente());
			aux.setCuentaCorriente(c.getCuentaCorriente());
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
	
	public boolean delete(Cliente f){ 
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
	
	
	public ArrayList<Cliente> getLista(){
		
		
		String consulta="";
		ArrayList<Cliente> salida= new ArrayList<Cliente>();
				
		//se verifica si es numerico se busca por el id sino se busca por nombre
		 consulta="SELECT cli FROM Cliente cli Where nombre <> 'Inexistente' ORDER BY clienteID DESC"; 
				

		System.out.println("Consulta: "+consulta);
		EntityManager em=JpaUtil.getEntityManager();
		TypedQuery<Cliente> query= em.createQuery(consulta, Cliente.class);
		try{
			System.out.println(">>>consulta>"+ consulta);
			salida =  (ArrayList<Cliente>) query.getResultList();
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
	
	public static ArrayList<Cliente> completarCliente(String buscar){
		String consulta="";
		ArrayList<Cliente> salida= new ArrayList<Cliente>();
				
		//se verifica si es numerico se busca por el id sino se busca por nombre
		try {
			int idCliente = Integer.parseInt(buscar);
			consulta="SELECT cli FROM Cliente cli Where nombre <> 'Inexistente' and clienteID like '"+idCliente+"%'";
			
		} catch (NumberFormatException nfe){
			consulta="SELECT cli FROM Cliente cli Where nombre <> 'Inexistente' and nombre like '%"+buscar+"%'";
		}
				

		System.out.println("Consulta: "+consulta);
		EntityManager em=JpaUtil.getEntityManager();
		TypedQuery<Cliente> query= em.createQuery(consulta, Cliente.class);
		query.setMaxResults(20);
		try{
			System.out.println(">>>consulta>"+ consulta);
			salida =  (ArrayList<Cliente>) query.getResultList();
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
	
	public static ArrayList<Contrato> getContratosCliente(int clienteID){
		ArrayList<Contrato> salida = new ArrayList<Contrato>();
		EntityManager em=JpaUtil.getEntityManager();  
		try{ 
			String consulta ="Select n From Contrato n where  clienteID = "+ clienteID + "and retirado='NO'" +
					"ORDER BY contratoID DESC";
			System.out.println("Consulta getContratosCliente"+consulta);
			TypedQuery<Contrato> consultaFuncionario= em.createQuery(consulta, Contrato.class);
			ArrayList<Contrato> auxLista = (ArrayList<Contrato>) consultaFuncionario.getResultList(); 
			System.out.println(">>>>" + auxLista); 
			salida= auxLista;  
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
			
	public boolean addContrato(Contrato c){
		System.out.println("addContrato "+c.getContratoID());
  	EntityManager em=JpaUtil.getEntityManager(); 
  	DatosGlobale dg = em.find(DatosGlobale.class, 1);//se obtiene los valores de la primer entidad hay que mejorar esta forma de hacerlo  	 
  	if(c.getTipo().equals("A")){
  		int seq=dg.getArrendamiento_Seq();
  		c.setContratoID(seq);
  		dg.setArrendamiento_Seq(++seq);
  	}
  	else {
			if(c.getTipo().equals("M")){
				int seq=dg.getMantenimiento_Seq();
    		c.setContratoID(seq);
    		dg.setMantenimiento_Seq(++seq);
			}
		}	 
		boolean salida=false;
		try{			
			em.getTransaction().begin();
			em.persist(c);
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
	
	public boolean addDireccion(Contrato c){
		System.out.println("adddireccion "+c.getContratoID());
  	EntityManager em=JpaUtil.getEntityManager();  		
		boolean salida=false;
		try{			
			em.getTransaction().begin();
			em.persist(c);
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
	
	public boolean deleteContrato(Contrato o){ 
		EntityManager em=JpaUtil.getEntityManager();
		boolean salida = false;
		try{			
			em.getTransaction().begin();
			em.remove(em.contains(o) ? o : em.merge(o));
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
	
	public boolean updateContrato(Contrato o){ 
		EntityManager em=JpaUtil.getEntityManager();
		boolean salida = false;
		try{	
			Contrato aux = em.find(Contrato.class, o.getId() );	
			em.getTransaction().begin();
			
			aux.setCliente(o.getCliente());
			aux.setCorredorID(o.getCorredorID());
			aux.setDireccion(o.getDireccion());
			aux.setEquipo(o.getEquipo());
			aux.setFechaFin(o.getFechaFin());
			aux.setFechaInicio(o.getFechaInicio());
			aux.setLocalidad(o.getLocalidad());
			aux.setRetirado(o.getRetirado());
			aux.setTipo(o.getTipo());
			aux.setZona(o.getZona());			
			aux.setTopeMesesVisita(o.getTopeMesesVisita());
			
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
	
	
}
