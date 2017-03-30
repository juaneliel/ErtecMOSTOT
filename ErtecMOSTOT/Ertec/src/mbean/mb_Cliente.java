package mbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

import model.Cliente;
import model.Contrato;
import model.DAO.DAO_Cliente; 


@ManagedBean (name="mb_Cliente")
@ViewScoped
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
	 
	
	private Cliente clienteAdd=new Cliente();
	private Cliente cliSelected;
	private Contrato conSelected;
	private Contrato contratoAdd=new Contrato();
	
	
	
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
	
	
 
	
	
	public void setClienteIDYNombre(int clienteID, String nombre){
	  this.clienteID=clienteID;
	  this.nombre=nombre;
	}
	
	
	
	 

	@PostConstruct	
	public void init(){
		this.lista=dao.getLista();
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
	
	public String add(){
		String salida=null;
		if (dao.add (clienteAdd)){
			salida= "/paginas/clientes.xhtml";
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Agregado", "Se agrego el cliente: "+ clienteAdd.getNombre());
	        FacesContext.getCurrentInstance().addMessage(null, message);
	        //recargar ();
	        this.lista.add(clienteAdd);
	        clienteAdd=new Cliente();
	        RequestContext reqCtx = RequestContext.getCurrentInstance();
	        reqCtx.update("formcliente:datatablecliente");
		}
		else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo agregar el cliente");
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}
		System.out.println(">>AGREGAR cliente"+clienteAdd.getClienteID() );		
//		recargar ();
//		this.limpiarVariables();
		return salida;
	}
	
	public void delete(Cliente f){ 
		if (dao.delete(f) ){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Borrado", "Se elimino el cliente "+f.getNombre());
      FacesContext.getCurrentInstance().addMessage(null, message);	
      lista.remove(f); 
      DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
          .findComponent("formcliente:datatablecliente");
      if (dataTable != null) {
      	dataTable.reset();
      }
		}
		else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error no se elimino el cliente "+f.getNombre() + "es posible que este en uso por una OT"  );
	        FacesContext.getCurrentInstance().addMessage(null, message);			
		}
		//return "/paginas/funcionarios.xhtml?faces-redirect=true";
    //recargar ();
	}
	
	 public void deleteContrato(Contrato o){ 
	    if (dao.deleteContrato(o) ){
	      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Borrado", "Se elimino el contrato "+o.getId());
        FacesContext.getCurrentInstance().addMessage(null, message); 
        this.listaContratos.remove(o);
        DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
            .findComponent("formcontratos:datatablecontratos");
        if (dataTable != null) {
        	dataTable.reset();
        }   
	    }
	    else{
	      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error no se elimino el contrato "+o.getId() );
	          FacesContext.getCurrentInstance().addMessage(null, message);      
	    }
	    //return "/paginas/funcionarios.xhtml?faces-redirect=true";
//	    this.recargarListaContratos(o.getClienteID());
	  }
	

	
	public void recargarListaContratos(int cliID){
		System.out.println("recargarlista");
		this.listaContratos=dao.getContratosCliente(cliID);		
	}
	
	public void setListaContratosCliSel(){
		this.listaContratos= dao.getContratosCliente(this.cliSelected.getClienteID());		
	}
	
 
	
	public void previoAddDireccion(){
		contratoAdd.setCliente(conSelected.getCliente());
		contratoAdd.setContratoID(conSelected.getContratoID());
		contratoAdd.setTipo(conSelected.getTipo());	
		contratoAdd.setFechaInicio(conSelected.getFechaInicio());	
		contratoAdd.setFechaFin(conSelected.getFechaFin());	 
		contratoAdd.setTopeMesesVisita(conSelected.getTopeMesesVisita());
	}

	public String addDireccionContrato(){
		String salida= null;
		
		if (dao.addDireccion(contratoAdd)){
			//salida= "/paginas/clientes.xhtml?faces-redirect=true";
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Agregado", "Se agrego la direccion al contrato "+contratoAdd.getTipo()+contratoAdd.getContratoID());
	        FacesContext.getCurrentInstance().addMessage(null, message);
	        this.listaContratos.add(contratoAdd);
	    		contratoAdd=new Contrato();
		}
		else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo agregar la direccion");
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}
		System.out.println(">>AGREGAR contrato"+contratoAdd.getContratoID() );		
		
		return salida;
	}
	
	public String addContrato(){
		String salida= null;
		contratoAdd.setCliente(this.cliSelected);
		if (dao.addContrato (contratoAdd)){
			//salida= "/paginas/clientes.xhtml?faces-redirect=true";
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Agregado", "Se agrego el contrato "+contratoAdd.getTipo()+contratoAdd.getContratoID());
	        FacesContext.getCurrentInstance().addMessage(null, message);
	    		this.listaContratos.add(contratoAdd);
	    		contratoAdd=new Contrato();
		}
		else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Agregado", "Error al agregar el contrato");
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}
		System.out.println(">>AGREGAR contrato"+contratoAdd.getContratoID() );		

		return salida;
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

	public Cliente getClienteAdd() {
		return clienteAdd;
	}


	public void setClienteAdd(Cliente clienteAdd) {
		this.clienteAdd = clienteAdd;
	}


	public Cliente getCliSelected() {
		return cliSelected;
	}


	public void setCliSelected(Cliente cliSelected) {
		this.cliSelected = cliSelected;
	}


	public Contrato getConSelected() {
		return conSelected;
	}


	public void setConSelected(Contrato conSelected) {
		this.conSelected = conSelected;
	}


	public Contrato getContratoAdd() {
		return contratoAdd;
	}


	public void setContratoAdd(Contrato contratoAdd) {
		this.contratoAdd = contratoAdd;
	}
	
 
	
	
	
}