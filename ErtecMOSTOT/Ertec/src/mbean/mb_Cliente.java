package mbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.event.RowEditEvent;

import model.Cliente;
import model.Contrato;
import model.DAO.DAO_Cliente; 


@ManagedBean (name="mb_Cliente")
@SessionScoped
public class mb_Cliente implements Serializable{

  private static final long serialVersionUID = 1L;
  
	private DAO_Cliente dao=new DAO_Cliente(); 
	private ArrayList<Cliente> lista=new ArrayList<Cliente>();
 
	
	private int clienteID; 
	private String credito; 
	private String dirCliente; 
	private String locCliente; 
	private String nombre; 
	private String rucCliente; 
	private String telCliente;
	private ArrayList <Contrato> listaContratos =new ArrayList <Contrato>();
	private ArrayList<Cliente> listaClientesOBJ=new ArrayList<Cliente>();
	 
	private int id;  
	private int contratoID; 
	private int corredorID; 
	private String direccion; 
	private String equipo;  
	private Date fechaFin;  
	private Date fechaInicio; 
	private String localidad; 
	private String retirado; 
	private String tipo; 
	private String zona;
	private int cuentaCorriente;
	
	
	
	
	
	public void editContrato(RowEditEvent event){
		Contrato con= (Contrato) event.getObject();
		
    	if(dao.updateContrato(con)){    		
    		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizado", "Se actualizo el contrato");
            FacesContext.getCurrentInstance().addMessage(null, message); 
            //recargarLista();
    	}
    	else{
    		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo actualizar el contrato");
            FacesContext.getCurrentInstance().addMessage(null, message);           
    	}  	
        System.out.println("entro en cliente update");
	}
	
	
	public void agregarDireccion(int clienteID,int contratoID,String tipo){
	  this.contratoID=contratoID;
	  this.clienteID=clienteID;
	  this.tipo=tipo;
	}
	
	
	public void setClienteIDYNombre(int clienteID, String nombre){
	  this.clienteID=clienteID;
	  this.nombre=nombre;
	}
	
	
	
	public void setListaClientesOBJ(ArrayList<Cliente> listaClientesOBJ) {
		this.listaClientesOBJ = listaClientesOBJ;
	}

	@PostConstruct	
	public void init(){
		this.recargar();
	}
	
	public void recargar(){
		this.lista=dao.getLista();
		System.out.println("lista de clientes "+ lista.size());
	}
	
	public ArrayList <Cliente> completarCliente(String query){
		this.listaClientesOBJ = DAO_Cliente.completarCliente(query); 
		return listaClientesOBJ;
		
	}
	
	
	
	public void onRowEdit(RowEditEvent event) {
		
		Cliente f= (Cliente) event.getObject();
		
		
    	if(dao.update(f)){    		
    		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizado", "Se actualizo el cliente ");
            FacesContext.getCurrentInstance().addMessage(null, message); 
            //recargarLista();
    	}
    	else{
    		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo actualizar el cliente");
            FacesContext.getCurrentInstance().addMessage(null, message);           
    	}  	
        System.out.println("entro en cliente update");
    }
	
	
	public void limpiarVariables(){
		setClienteID(0);
		setCorredorID(0);
		setDireccion("");
		setEquipo("");
		setFechaFin(null);
		setFechaInicio(null);
		setLocalidad("");
		setRetirado("NO");
		setTipo("");
		setZona("");
	}
	
	public String add(){
		String salida=null;
		Cliente f = new Cliente();
		f.setClienteID(clienteID);
		f.setCredito(credito);
		f.setDirCliente(dirCliente);
		f.setLocCliente(locCliente);
		f.setNombre(nombre);
		f.setRucCliente(rucCliente);
		f.setTelCliente(telCliente);
		f.setCuentaCorriente(cuentaCorriente);
		limpiarVariables();
		if (dao.add (f)){
			salida= "/paginas/clientes.xhtml?faces-redirect=true";
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Agregado", "Se agrego el cliente: "+ f.getNombre());
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}
		else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo agregar el cliente");
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}
		System.out.println(">>AGREGAR cliente"+f.getClienteID() );		
		recargar ();
		this.limpiarVariables();
		return salida;
	}
	
	public void delete(Cliente f){ 
		if (dao.delete(f) ){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Borrado", "Se elimino el cliente "+f.getNombre());
	        FacesContext.getCurrentInstance().addMessage(null, message);			
		}
		else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error no se elimino el cliente "+f.getNombre() + "es posible que este en uso por una OT"  );
	        FacesContext.getCurrentInstance().addMessage(null, message);			
		}
		//return "/paginas/funcionarios.xhtml?faces-redirect=true";
        recargar ();
	}
	
	 public void deleteContrato(Contrato o){ 
	    if (dao.deleteContrato(o) ){
	      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Borrado", "Se elimino el contrato "+o.getId());
	          FacesContext.getCurrentInstance().addMessage(null, message);      
	    }
	    else{
	      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error no se elimino el contrato "+o.getId() );
	          FacesContext.getCurrentInstance().addMessage(null, message);      
	    }
	    //return "/paginas/funcionarios.xhtml?faces-redirect=true";
	    this.recargarListaContratos(o.getClienteID());
	  }
	

	
	public void recargarListaContratos(int cliID){
		System.out.println("recargarlista");
		this.listaContratos=dao.getContratosCliente(cliID);
		
	}
	
	
	public ArrayList<Cliente> getListaClientesOBJ(){		
		return this.listaClientesOBJ;
	}
	
	public String addContrato(){
		String salida= null;
		Contrato c = new Contrato();
		c.setContratoID(contratoID);
			
		c.setClienteID(clienteID);
		c.setCorredorID(corredorID);
		c.setDireccion(direccion);
		c.setEquipo(equipo);
		c.setFechaFin(fechaFin);
		c.setFechaInicio(fechaInicio);
		c.setLocalidad(localidad);
		c.setRetirado(retirado);
		c.setTipo(tipo);
		c.setZona(zona);
		
		
		if (dao.addContrato (c)){
			//salida= "/paginas/clientes.xhtml?faces-redirect=true";
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Agregado", "Se agrego el contrato ");
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}
		else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Agregado", "Error al agregar el contrato");
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}
		System.out.println(">>AGREGAR contrato"+c.getContratoID() );		
		this.recargarListaContratos(clienteID);
		limpiarVariablesContrato();
		return salida;
	}

	public void limpiarVariablesContrato(){
		setContratoID(0);
		
		setClienteID(0);
		setCorredorID(0);
		setDireccion("");
		setEquipo("");
		setFechaFin(null);
		setFechaInicio(null);
		setLocalidad("");
		setRetirado("NO");
		setTipo("");
		setZona("");
	}
	
	public ArrayList<Cliente> getLista() {
		System.out.println("lista de clientes "+ lista.size());
		return lista;
	}

	public void setLista(ArrayList<Cliente> lista) {
		this.lista = lista;
	}

	public int getClienteID() {
		return clienteID;
	}

	public void setClienteID(int clienteID) {
		this.clienteID = clienteID;
	}

 

	public String getCredito() {
		return credito;
	}


	public void setCredito(String credito) {
		this.credito = credito;
	}


	public String getDirCliente() {
		return dirCliente;
	}

	public void setDirCliente(String dirCliente) {
		this.dirCliente = dirCliente;
	}

	public String getLocCliente() {
		return locCliente;
	}

	public void setLocCliente(String locCliente) {
		this.locCliente = locCliente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRucCliente() {
		return rucCliente;
	}

	public void setRucCliente(String rucCliente) {
		this.rucCliente = rucCliente;
	}

	public String getTelCliente() {
		return telCliente;
	}

	public void setTelCliente(String telCliente) {
		this.telCliente = telCliente;
	}

	public ArrayList<Contrato> getListaContratos() {
		return listaContratos;
	}

	public void setListaContratos(ArrayList<Contrato> listaContratos) {
		this.listaContratos = listaContratos;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getContratoID() {
		return contratoID;
	}

	public void setContratoID(int contratoID) {
		this.contratoID = contratoID;
	}

	public int getCorredorID() {
		return corredorID;
	}

	public void setCorredorID(int corredorID) {
		this.corredorID = corredorID;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEquipo() {
		return equipo;
	}

	public void setEquipo(String equipo) {
		this.equipo = equipo;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

 

	public String getRetirado() {
		return retirado;
	}


	public void setRetirado(String retirado) {
		this.retirado = retirado;
	}


	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}


	public int getCuentaCorriente() {
		return cuentaCorriente;
	}


	public void setCuentaCorriente(int cuentaCorriente) {
		this.cuentaCorriente = cuentaCorriente;
	}
	
	
	
	
	
	
}
