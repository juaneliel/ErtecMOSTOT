package model.DAO;

import java.util.ArrayList; 

import javax.persistence.EntityManager; 
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import model.Adicional;
import model.Articulo;
import model.Cliente;
import model.ComprasExternasOT;
import model.Funcionario;
import model.ManoObra;
import model.Movimiento;
import model.NexoMovimiento;
import model.Ot;
import model.Proveedores;
import model.TipoOT;
import util.JpaUtil;


public class DAO_OT {
	
	
	public boolean update(Ot ot){
		EntityManager em=JpaUtil.getEntityManager();
		boolean salida = false;
		try{	
			Ot aux = em.find(Ot.class, ot.getId()  );			
			
			em.getTransaction().begin();
			
			aux.setAreaID(ot.getAreaID());
			aux.setArrendamiento(ot.getArrendamiento());
			aux.setAdicionales(ot.getAdicionales());
			aux.setC(ot.getC());
			aux.setC_Corriente(ot.getC_Corriente());
			if(ot.getCliente()!=null){
			  aux.setNroCliente(ot.getCliente().getClienteID());
			  aux.setClienteNombre(ot.getCliente().getNombre());
			}			
			aux.setComprasExternas(ot.getComprasExternas());
			aux.setFactura(ot.getFactura());
			aux.setFechaFacturada(ot.getFechaFacturada());
			aux.setFechaInicio(ot.getFechaInicio());
			aux.setFechaTerminada(ot.getFechaTerminada());
			aux.setMantenimiento(ot.getMantenimiento());
			aux.setId(ot.getId());
			aux.setOC(ot.getOC());
			aux.setPedido(ot.getPedido());
			aux.setPresupuesto(ot.getPresupuesto());
			aux.setProceso(ot.getProceso());
			aux.setR(ot.getR());
			aux.setTipoID(ot.getTipoID());
			aux.setTrabajo(ot.getTrabajo());
			

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
	
	public boolean delete(Ot ot){
		EntityManager em=JpaUtil.getEntityManager();
		boolean salida = false;
		try{			
			em.getTransaction().begin();
			em.remove(em.contains(ot) ? ot : em.merge(ot));
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
	
	
	public boolean add(Ot ot){
		EntityManager em=JpaUtil.getEntityManager(); 
		boolean salida=false;
		try{			
			em.getTransaction().begin();
			em.persist(ot);
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
	
	
	
	
	public ArrayList<Ot> getListaOT(){
		EntityManager em=JpaUtil.getEntityManager();
		TypedQuery<Ot> query= em.createNamedQuery("Ot.findAll", Ot.class);
		
		try{
			return (ArrayList<Ot>) query.getResultList();
		}
		catch (Exception e){
			return null;
		}
	}
	
	
	public ArrayList<TipoOT> getTiposOT(){
		EntityManager em=JpaUtil.getEntityManager();
		TypedQuery<TipoOT> query= em.createNamedQuery("TipoOT.findAll",TipoOT.class);
		
		try{
			return (ArrayList<TipoOT>) query.getResultList();
		}
		catch (Exception e){
			return null;
		}
	}
	
	
	
	
	@Transactional
	public ArrayList<Ot> getLista(){
		
		ArrayList<Ot> salida = new ArrayList<Ot>();
		String consulta="SELECT o FROM Ot o ORDER BY Id DESC";
		
		System.out.println("Consulta: "+consulta);
		EntityManager em=JpaUtil.getEntityManager();
		TypedQuery<Ot> consultaFuncionario= em.createQuery(consulta, Ot.class);
		try{
			System.out.println(">>>consulta>"+ consulta);
			salida =  (ArrayList<Ot>) consultaFuncionario.getResultList();
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
	
	
	
	public ArrayList <Movimiento> getListaMovimientosOT(long idOT){	
		 return DAO_Movimiento.getMovimientosOT(idOT);
	}
	
	public ArrayList <ManoObra> getListaManoObraOT(long idOT){	
		 return DAO_ManoObra.getManoObraOT(idOT);
	}
	
	
	
	public Ot getOT(int idOT) {	
	 
		EntityManager em=JpaUtil.getEntityManager();		
		
		try{
			return em.find(Ot.class,idOT);
		}
		catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	public ArrayList <Adicional> getAdicionales(int idOT){
		EntityManager em=JpaUtil.getEntityManager();		
		
		try{
			Ot ot= em.find(Ot.class,idOT);
			return new ArrayList <Adicional> (ot.getAdicionales());
		}
		catch (Exception e){
			e.printStackTrace();
			return null;
		}		
	}
	
	public ArrayList<NexoMovimiento> getMovimientos(){
		 
		ArrayList<NexoMovimiento> salida = new ArrayList<NexoMovimiento>();
		String consulta="SELECT m FROM Movimiento m, Ot ot WHERE m.movimientoID= Ot.referencia and Ot.tipoOT <> NULL  ORDER BY m.movimientoID DESC";
		
		System.out.println("Consulta: "+consulta);
		EntityManager em=JpaUtil.getEntityManager();
		TypedQuery<NexoMovimiento> consultaFuncionario= em.createQuery(consulta, NexoMovimiento.class);
		try{
			System.out.println(">>>consulta>"+ consulta);
			salida =  (ArrayList<NexoMovimiento>) consultaFuncionario.getResultList();
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
	
	public ArrayList<ComprasExternasOT> getComprasExternas (int idOT){
		EntityManager em=JpaUtil.getEntityManager();		
		if(idOT==0){
			System.err.println("DAO_OTGetComprasExternas con idOT=0");;
			return new ArrayList<ComprasExternasOT>();
		}
		try{
			Ot ot= em.find(Ot.class,idOT);
			return new ArrayList <ComprasExternasOT> (ot.getComprasExternas());
		}
		catch (Exception e){
			System.out.println("DaoOtGetCE "+idOT);
			e.printStackTrace();
			return new ArrayList<ComprasExternasOT>();
		}		
	}
	
	
	
	public boolean addMovimientoOT(Movimiento m){
		ArrayList<NexoMovimiento> nexos= new ArrayList<NexoMovimiento>();//arreglar esto
		return DAO_Movimiento.add(m,nexos);
	}
	public boolean addManoObraOT(ManoObra mo){
		return DAO_ManoObra.add(mo);
	}
	
	public boolean addAdicionalOT(Adicional a){ 
		boolean salida=false;
		EntityManager em=JpaUtil.getEntityManager();		
		try{ 
			Ot ot = em.find(Ot.class,a.getOtid()); 
			
			em.getTransaction().begin();			
			em.persist(a);
			ot.getAdicionales().add(a);
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
	
	public boolean addCompraExternaOT(ComprasExternasOT ce){
		boolean salida= false;

		EntityManager em=JpaUtil.getEntityManager();		
		try{
//			Ot ot= em.find(Ot.class,ce.getOtid()); 
//			em.getTransaction().begin();
//			ot.getComprasExternas().add(ce);		
//			em.flush();
//			em.getTransaction().commit();
//			em.close();
			System.out.println("addcompra>>");
		//	Articulo articulo = em.find(Articulo.class, ce.getArticuloID());
			Proveedores proveedor = em.find(Proveedores.class, ce.getProveedorID());
			em.getTransaction().begin();
			ce.setProveedor(proveedor);
			//ce.setArticulo(articulo);
			em.persist(ce);
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
	
//	public ArrayList<Articulo> completarArticulo(String buscar){
//		ArrayList<Articulo> salida= new ArrayList<Articulo>();
//		
//		String consulta="";
//		//se verifica si es numerico se busca por el id sino se busca por nombre
//		try {
//			int idArticulo = Integer.parseInt(buscar);
//			consulta="SELECT art FROM Articulo art Where articuloID like '"+idArticulo+"%' ";
//			
//		} catch (NumberFormatException nfe){
//			consulta="SELECT art FROM Articulo art Where descripcion like   '%"+
//					buscar+"%' ";
//		}
//		
//
//		System.out.println("Consulta: "+consulta);
//		EntityManager em=JpaUtil.getEntityManager();
//		TypedQuery<Articulo> query= em.createQuery(consulta, Articulo.class);
//		try{
//			System.out.println(">>>consulta>"+ consulta);
//			salida =  (ArrayList<Articulo>) query.getResultList();
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
	
	public ArrayList<Proveedores> completarProveedor(String buscar){
		ArrayList<Proveedores> salida= new ArrayList<Proveedores>();
		
		
		String consulta="";
		//se verifica si es numerico se busca por el id sino se busca por nombre
		try {
			int idArticulo = Integer.parseInt(buscar);
			consulta="SELECT pro FROM Proveedores pro Where proveedorID like '"+idArticulo+"%' ";
			
		} catch (NumberFormatException nfe){
			consulta="SELECT pro FROM Proveedores pro Where nombre like   '%"+
					buscar+"%' ";
		}
		

		System.out.println("Consulta: "+consulta);
		EntityManager em=JpaUtil.getEntityManager();
		TypedQuery<Proveedores> query= em.createQuery(consulta, Proveedores.class);
		try{
			System.out.println(">>>consulta>"+ consulta);
			salida =  (ArrayList<Proveedores>) query.getResultList();
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
	
	public boolean addAdicional(Adicional adi){
		EntityManager em=JpaUtil.getEntityManager(); 
		boolean salida=false;
		try{			
			em.getTransaction().begin();
			em.persist(adi);
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
	
	
	
	
	public boolean updateAdicional(Adicional adi){
		EntityManager em=JpaUtil.getEntityManager();
		boolean salida = false;
		try{	
			Adicional aux = em.find(Adicional.class, adi.getAdicionalID()  );			
			
			em.getTransaction().begin();
			
			aux.setAdicionalID(adi.getAdicionalID());
			aux.setEspecificacion(adi.getEspecificacion());
			aux.setOtid(adi.getOtid());
						
			
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
	
	public boolean updateCompraExterna (ComprasExternasOT ce){
		EntityManager em=JpaUtil.getEntityManager();
		boolean salida = false;
		try{	
			ComprasExternasOT aux = em.find(ComprasExternasOT.class,ce.getId()  );			

			//Articulo articulo = em.find(Articulo.class, ce.getArticuloID());
			//Proveedores proveedor = em.find(Proveedores.class, ce.getProveedorID());		
			Proveedores proveedor =ce.getProveedor();
			

			em.getTransaction().begin();
			
		//	aux.setArticulo(articulo);
			aux.setNombreArticulo(ce.getNombreArticulo());
			//aux.setArticuloID(articulo.getArticuloID());
			aux.setCantidad(ce.getCantidad());
			aux.setId(ce.getId());
			aux.setFecha(ce.getFecha());
			aux.setMoneda(ce.getMoneda());
			aux.setOtid(ce.getOtid());
			aux.setPrecio_Unitario(ce.getPrecio_Unitario());
			if(proveedor!=null){
				aux.setProveedor(proveedor);
				aux.setProveedorID(proveedor.getProveedorID());		
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
	
	
	public boolean deleteAdicional(Adicional adi){
		EntityManager em=JpaUtil.getEntityManager();
		boolean salida = false;
		try{		
			em.getTransaction().begin();	
			Ot ot= em.find(Ot.class,adi.getOtid());
			ot.getAdicionales().remove(adi);				
			em.remove(em.contains(adi) ? adi : em.merge(adi));
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
	
	public boolean deleteCompraExterna(ComprasExternasOT ce){
		EntityManager em=JpaUtil.getEntityManager();
		boolean salida = false;
		try{		
			em.getTransaction().begin();	
			Ot ot= em.find(Ot.class,ce.getOtid());
			ot.getAdicionales().remove(ce);				
			em.remove(em.contains(ce) ? ce : em.merge(ce));
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
	
//	public boolean deleteManoObra(ManoObra mo){
//		EntityManager em=JpaUtil.getEntityManager();
//		boolean salida = false;
//		try{		
//			em.getTransaction().begin();	
//			Ot ot= em.find(Ot.class,mo.getOrdenTrabajo());
//			ot.getAdicionales().remove(mo);				
//			em.remove(em.contains(mo) ? mo : em.merge(mo));
//			//em.flush();
//			em.getTransaction().commit();
//			salida = true;
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
//	public boolean deleteMovimiento(Movimiento mov){
//		EntityManager em=JpaUtil.getEntityManager();
//		boolean salida = false;
//		try{		
//			em.getTransaction().begin();	
//			Ot ot= em.find(Ot.class,mov.getReferencia());
//			ot.getAdicionales().remove(mov);				
//			em.remove(em.contains(mov) ? mov : em.merge(mov));
//			//em.flush();
//			em.getTransaction().commit();
//			salida = true;
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
	
}
