package mbean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.persistence.Column;
import javax.persistence.Id;

import org.primefaces.event.RowEditEvent;

import model.Articulo;
import model.Funcionario;
import model.DAO.DAO_Articulo;
import model.DAO.DAO_Funcionario;
import model.DAO.DAO_infoMovDeArticulos;
import model.DAO.DAO_infoService;

@ManagedBean
@SessionScoped
public class mb_Articulo {

	
	private int articuloID;
	private String calidad;
	private BigDecimal costoDolares;
	private BigDecimal costoPesos;
	private String descripcion;
	private int medida;
	private String observacion;
	private BigDecimal precioVenta;
	private BigDecimal stock;
	private BigDecimal stockMinimo;
	private BigDecimal ultimoCostoDolares;
	private BigDecimal ultimoCostoPesos;
	private DAO_Articulo dao=new DAO_Articulo();
  private Date fechaIni;
  private Date fechaFin;
  private ArrayList<DAO_infoMovDeArticulos> filtrado;
	
	private ArrayList<Articulo> lista=new ArrayList<Articulo>();
	private Articulo artSelected;
	private Articulo articuloOBJ;
	private ArrayList<Articulo> listaArticulosOBJ=new ArrayList<Articulo>(); 

	public String add(){
		
		String salida=null;
		Articulo f = setVariables(); 
		if (dao.add (f)){
			salida=  "/paginas/articulos.xhtml?faces-redirect=true";
		}
		else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Agregado", "Error");
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}
		System.out.println(">>AGREGAR"+f.getDescripcion());
		recargarLista ();
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
	
	public void delete(Articulo f){ 
		if (dao.delete(f) ){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Borrado", "Se elimino el articulo "+f.getDescripcion());
	        FacesContext.getCurrentInstance().addMessage(null, message);			
		}
		else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se puede eliminar el articulo "+f.getDescripcion() + " es posible que este referenciado en un movimiento "+f.getDescripcion());
	        FacesContext.getCurrentInstance().addMessage(null, message);			
		}
		//return "/paginas/funcionarios.xhtml?faces-redirect=true";
        recargarLista ();
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

	//se seleccionan el valor para hacer update	
	public String selecToUpdateArticulo(Articulo f){		
		//agregar las variables
		getVariables(f);		
		return "/paginas/updatearticulos.xhtml?faces-redirect=true";
	}
	
	//se efectua el update
	public void updateArticulo(){  		
		Articulo a = setVariables();
		dao.update (a);
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizado", "Se actualizo el articulo "+a.getArticuloID());
        FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	public ArrayList <Articulo> completarArticulo(String query){
		this.listaArticulosOBJ=dao.completarArticulo(query);
		return listaArticulosOBJ;
	}
	
	
	
	//se le se setean los valores al objeto funcionario
	private Articulo setVariables(){
		Articulo aux=new Articulo();
		
		aux.setArticuloID(getArticuloID());
		aux.setCalidad(getCalidad());
		aux.setCostoDolares(getCostoDolares());
		aux.setCostoPesos(getCostoPesos());
		aux.setDescripcion(getDescripcion());
		aux.setMedida(getMedida());
		aux.setObservacion(getObservacion());
		aux.setPrecioVenta(getPrecioVenta());
		aux.setStock(getStock());
		aux.setStockMinimo(getStockMinimo());
		aux.setUltimoCostoDolares(getUltimoCostoDolares());
		aux.setUltimoCostoPesos(getUltimoCostoPesos());
		
		return aux;
	}
	
	//se toman los valores del objeto funcionario a las variables del mb
	private void getVariables(Articulo a){
		setArticuloID(a.getArticuloID());
		setCalidad(a.getCalidad());
		setCostoDolares(a.getCostoDolares());
		setCostoPesos(a.getCostoPesos());
		setDescripcion(a.getDescripcion());
		setMedida(a.getMedida());
		setObservacion(a.getObservacion());
		setPrecioVenta(a.getPrecioVenta());
		setStock(a.getStock());
		setStockMinimo(a.getStockMinimo());
		setUltimoCostoDolares(a.getUltimoCostoDolares());
		setUltimoCostoPesos(a.getUltimoCostoPesos());
		
	}	
	
	@PostConstruct
	public void init (){
		recargarLista();
	}
	
	public void recargarLista(){
		lista=dao.getLista ();
	}
	
	public void limpiarVariables(){
		setArticuloID(0);
		setCalidad("");
		setCostoDolares( BigDecimal.ZERO  );
		setCostoPesos(  BigDecimal.ZERO  );
		setDescripcion("");
		setMedida( 0  );
		setObservacion("");
		setPrecioVenta(  BigDecimal.ZERO  );
		setStock(  BigDecimal.ZERO  );
		setStockMinimo(  BigDecimal.ZERO  );
		setUltimoCostoDolares(  BigDecimal.ZERO  );
		setUltimoCostoPesos(  BigDecimal.ZERO  );
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
	public String getCalidad() {
		return calidad;
	}
	public void setCalidad(String calidad) {
		this.calidad = calidad;
	}
	public BigDecimal getCostoDolares() {
		return costoDolares;
	}
	public void setCostoDolares(BigDecimal costoDolares) {
		this.costoDolares = costoDolares;
	}
	public BigDecimal getCostoPesos() {
		return costoPesos;
	}
	public void setCostoPesos(BigDecimal costoPesos) {
		this.costoPesos = costoPesos;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getMedida() {
		return medida;
	}
	public void setMedida(int medida) {
		this.medida = medida;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public BigDecimal getPrecioVenta() {
		return precioVenta;
	}
	public void setPrecioVenta(BigDecimal precioVenta) {
		this.precioVenta = precioVenta;
	}
	public BigDecimal getStock() {
		return stock;
	}
	public void setStock(BigDecimal stock) {
		this.stock = stock;
	}
	public BigDecimal getStockMinimo() {
		return stockMinimo;
	}
	public void setStockMinimo(BigDecimal stockMinimo) {
		this.stockMinimo = stockMinimo;
	}
	public BigDecimal getUltimoCostoDolares() {
		return ultimoCostoDolares;
	}
	public void setUltimoCostoDolares(BigDecimal ultimoCostoDolares) {
		this.ultimoCostoDolares = ultimoCostoDolares;
	}
	public BigDecimal getUltimoCostoPesos() {
		return ultimoCostoPesos;
	}
	public void setUltimoCostoPesos(BigDecimal ultimoCostoPesos) {
		this.ultimoCostoPesos = ultimoCostoPesos;
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
	
}
