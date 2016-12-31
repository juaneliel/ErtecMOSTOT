package mbean;

import java.math.BigDecimal;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext; 

import org.primefaces.event.RowEditEvent;

import model.Adicional;
import model.Proveedores; 
import model.DAO.DAO_Proveedor;



@ManagedBean
@SessionScoped
public class mb_Proveedores {

	private DAO_Proveedor dao=new DAO_Proveedor();
	
	private ArrayList<Proveedores> lista;
	
	private int proveedorID; 
	private String direccion; 
	private String email; 
	private int fax; 
	private String nombre; 
	private String observacion; 
	private BigDecimal ruc; 
	private int telefono;
	
	
	
	@PostConstruct	
	public void init(){
		this.recargarLista();
	}
	
	
	
	
	
	
	
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void recargarLista(){
		this.lista= dao.getLista();		
	}
	
	public String add(){
	 	String salida="/paginas/proveedores.xhtml";
		Proveedores p=new Proveedores();
		p.setDireccion(direccion);
		p.setEmail(email);
		p.setFax(fax);
		p.setNombre(nombre);
		p.setObservacion(observacion);
		p.setProveedorID(proveedorID);
		p.setRuc(ruc);
		p.setTelefono(telefono);		
		
		
		System.out.println("entro en addadicional");
		if(dao.add(p)){
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Agregado","Se agrego el proveedor");
			context.addMessage(null,mensaje);
			this.recargarLista();
		}
		else{
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error","No se pudo agregar");
			context.addMessage(null,mensaje);
		}
		return salida;
		 
	}
	public void delete(Proveedores p){
		
		if (dao.delete(p) ){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Borrado", "Se elimino el provedor  "+p.getProveedorID());
	        FacesContext.getCurrentInstance().addMessage(null, message);			
		}
		else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error no se pudo eliminar ");
	        FacesContext.getCurrentInstance().addMessage(null, message);			
		}
		//return "/paginas/funcionarios.xhtml?faces-redirect=true";
        this.recargarLista();
        System.out.println("entro en delete adicional");		
	}

	 

	public void editProveedor (RowEditEvent event) {
		
		Proveedores p= (Proveedores) event.getObject();
		
		
    	if(dao.update(p)){    		
    		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizado", "Se actualizo el proveedor "+p.getProveedorID());
            FacesContext.getCurrentInstance().addMessage(null, message); 
    	}
    	else{
    		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo actualizar");
            FacesContext.getCurrentInstance().addMessage(null, message);           
    	}  	
        
    }

	 

	 
	public ArrayList<Proveedores> getLista() {
		return lista;
	}

	public void setLista(ArrayList<Proveedores> lista) {
		this.lista = lista;
	}

	public int getProveedorID() {
		return proveedorID;
	}

	public void setProveedorID(int proveedorID) {
		this.proveedorID = proveedorID;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getFax() {
		return fax;
	}

	public void setFax(int fax) {
		this.fax = fax;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public BigDecimal getRuc() {
		return ruc;
	}

	public void setRuc(BigDecimal ruc) {
		this.ruc = ruc;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}
	
	
}
