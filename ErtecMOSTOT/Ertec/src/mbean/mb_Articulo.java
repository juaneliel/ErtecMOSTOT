package mbean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.persistence.Column;
import javax.persistence.Id;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.RowEditEvent;

import model.Articulo;
import model.Funcionario;
import model.DAO.DAO_Articulo;
import model.DAO.DAO_Funcionario;
import model.DAO.DAO_infoMovDeArticulos;
import model.DAO.DAO_infoService;

@ManagedBean
@ViewScoped
public class mb_Articulo {

	
	private int articuloID;
 
	private DAO_Articulo dao=new DAO_Articulo();
  private Date fechaIni;
  private Date fechaFin;
  private ArrayList<DAO_infoMovDeArticulos> filtrado;
	
	private ArrayList<Articulo> lista=new ArrayList<Articulo>();
	private Articulo artSelected;
	private Articulo articuloOBJ;
	private Articulo articuloAdd=new Articulo();
	private ArrayList<Articulo> listaArticulosOBJ=new ArrayList<Articulo>(); 

	@PostConstruct
	public void init (){
		recargarLista();
	}
	
	public String add(){
		
		String salida=null;
		if (dao.add (articuloAdd)){
			salida=  "/paginas/articulos.xhtml";			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Agregado", "Se agrego el articulo "+articuloAdd.getDescripcion());
      FacesContext.getCurrentInstance().addMessage(null, message);
      //recargarLista ();
      this.lista.add(articuloAdd);
      articuloAdd=new Articulo();
		}
		else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo agregar");
	    FacesContext.getCurrentInstance().addMessage(null, message);
		}
		System.out.println(">>AGREGAR"+articuloAdd.getDescripcion());
		return salida;
	}
	
	public void filtrarInfoMovArticulo(){
		 filtrado=dao.filtrarInfoMovArticulo(articuloID,fechaIni,fechaFin);
	}
	
	public void limpiarFiltrado(){
		this.articuloID=this.artSelected.getArticuloID();
		System.out.println("limpiarFiltrado "+articuloID);
		this.filtrado=new ArrayList<DAO_infoMovDeArticulos>();		
	}
	
	public void delete(Articulo a){ 
		if (dao.delete(a) ){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Borrado", "Se elimino el articulo "+a.getDescripcion());
      FacesContext.getCurrentInstance().addMessage(null, message);	
      lista.remove(a);
      //usado para recargar cuando se borra filtrado
      DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
          .findComponent("formarticulos:datatablearticulos");
      if (dataTable != null) {
      	dataTable.reset();
      }
		}
		else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se puede eliminar el articulo "+a.getDescripcion() + " es posible que este referenciado en un movimiento "+a.getDescripcion());
	        FacesContext.getCurrentInstance().addMessage(null, message);			
		}
		//return "/paginas/funcionarios.xhtml?faces-redirect=true";
        
	}
	
	
	
	public void onRowEdit(RowEditEvent event) {
		
		Articulo f= (Articulo) event.getObject();
		
		
    	if(dao.update(f)){    		
    		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizado", "Se actualizo el articulo "+f.getDescripcion());
            FacesContext.getCurrentInstance().addMessage(null, message); 
            //recargarLista();
    	}
    	else{
    		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo actualizar");
            FacesContext.getCurrentInstance().addMessage(null, message);           
    	}  	
        
    }


	
	public void recargarLista(){
		lista=dao.getLista ();
	}
	
		
	 
	public void validarArticuloID(FacesContext f, UIComponent u, Object o) throws ValidatorException {
      if ( dao.findArticulo ( (Integer)o )!=null) {
         throw new ValidatorException(new FacesMessage("Id repetido","Ya existe un articulo con el id que quiere crear"));
      }
	}
	
	public int getArticuloID() {
		return articuloID;
	}
	public void setArticuloID(int articuloID) {
		this.articuloID = articuloID;
	}
	
	
	public ArrayList<Articulo> getLista() {
		return lista;
	}

	public void setLista(ArrayList<Articulo> lista) {
		this.lista = lista;
	}

//	public ArrayList<Articulo> getArticulos() {
//		this.findArticulo();
//		return articulos;
//	}
//
//	public void setArticulos(ArrayList<Articulo> articulos) {
//		this.articulos = articulos;
//	}	
	
	public Articulo getArticuloOBJ() {
		return articuloOBJ;
	}

	public void setArticuloOBJ(Articulo articuloOBJ) {
		this.articuloOBJ = articuloOBJ;
	}

	public ArrayList<Articulo> getListaArticulosOBJ() {
		return listaArticulosOBJ;
	}

	public void setListaArticulosOBJ(ArrayList<Articulo> listaArticulosOBJ) {
		this.listaArticulosOBJ = listaArticulosOBJ;
	}

	public Date getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(Date fechaIni) {
		this.fechaIni = fechaIni;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public ArrayList<DAO_infoMovDeArticulos> getFiltrado() {
		return filtrado;
	}

	public void setFiltrado(ArrayList<DAO_infoMovDeArticulos> filtrado) {
		this.filtrado = filtrado;
	}

	public Articulo getArtSelected() {
		return artSelected;
	}

	public void setArtSelected(Articulo artSelected) {
		this.artSelected = artSelected;
	}

  public String describimeSeleccionado(){
    if(this.artSelected==null){
    	return "nulo";
    }
    	
  	return this.artSelected.getDescripcion();
  }

	public Articulo getArticuloAdd() {
		return articuloAdd;
	}

	public void setArticuloAdd(Articulo articuloAdd) {
		this.articuloAdd = articuloAdd;
	}
	
  
}
