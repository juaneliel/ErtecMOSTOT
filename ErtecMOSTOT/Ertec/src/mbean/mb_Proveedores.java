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
	private Proveedores proSelected;
	private Proveedores proveedorAdd = new Proveedores();	
	
	@PostConstruct	
	public void init(){
		this.recargarLista();
	}	
	
	public void recargarLista(){
		this.lista= dao.getLista();		
	}
	
	public String add(){
	 	String salida="/paginas/proveedores.xhtml";			
		System.out.println("entro en addadicional");
		if(dao.add(proveedorAdd)){
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Agregado","Se agrego el proveedor "+proveedorAdd.getNombre());
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
	    lista.remove(p);
		}
		else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error no se pudo eliminar ");
	    FacesContext.getCurrentInstance().addMessage(null, message);			
		}
		//return "/paginas/funcionarios.xhtml?faces-redirect=true";
        //this.recargarLista();
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

	public Proveedores getProSelected() {
		return proSelected;
	} 
	public void setProSelected(Proveedores proSelected) {
		this.proSelected = proSelected;
	}

	public Proveedores getProveedorAdd() {
		return proveedorAdd;
	}

	public void setProveedorAdd(Proveedores proveedorAdd) {
		this.proveedorAdd = proveedorAdd;
	}
	
}
