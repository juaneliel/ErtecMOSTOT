package mbean;
 
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.RowEditEvent;

import model.Adicional;
import model.Arrendamiento;
import model.Articulo;
import model.Cliente;
import model.Cod_Movimiento;
import model.Contrato;
import model.Movimiento;
import model.NexoMovimiento;
import model.Referencia;
import model.DAO.DAO_Movimiento; 


@ManagedBean (name="mb_Movimiento")
@ViewScoped
public class mb_Movimiento {

	private DAO_Movimiento dao=new DAO_Movimiento();
	 

	private int movimientoID;
	private int clienteID;
	private int codigoMovimientoID;
	private int comprobante;
	private int contratoID;
	private BigDecimal cotizacion;
	private Date fecha;
	private String nombreCliente;
	private int nroMovimiento;
	private int referencia;
	private String tipoOT;
	private String tipoReferencia;
	private BigDecimal cantidadArticulo;
	private BigDecimal costo=BigDecimal.ZERO;
	private boolean verCotizacion;
	private boolean verReferencia;
	private boolean verCliente;
	private boolean verCosto;
	private boolean verContrato;
	private boolean EditarCotizacion;
	private boolean EditarReferencia;
	private boolean EditarCliente;
	private boolean EditarCosto;
	private boolean EditarContrato;
	private int otID;
	
	private ArrayList<Movimiento> lista;
	private ArrayList<NexoMovimiento> listaNexos;
	private ArrayList<Arrendamiento> listaArrendamientos;
	private ArrayList<NexoMovimiento> listaTemporalNexos;
	
	//private ArrayList<Codigo> listaCodigos;
	private ArrayList<Cod_Movimiento> listaCodMovimientosComun;
	
  private ArrayList<Cod_Movimiento> listaCodMovimientosEnOt;
	
	
	private ArrayList<Referencia> listaReferencias;
	
	private Cliente clienteOBJ;
	private Articulo articuloOBJ;
	private int spinnerDevolucion;
	private Map<Integer,Integer> mapaArrendamiento = new HashMap<Integer, Integer>();
	private Contrato contratoOBJ;
	
	@PostConstruct
	public void init(){
		//this.listaCodigos=dao.getListaCodigos();

	  this.listaCodMovimientosComun=dao.getListaCodMovimientosComun();
	  this.listaCodMovimientosEnOt=dao.getListaCodMovimientosEnOT();
	  this.listaNexos=new ArrayList<NexoMovimiento>();
		this.listaReferencias=dao.getListaReferencias();
		this.recargarLista();
		this.verReferencia=false;
		this.verCotizacion=false;
		this.verCosto=false;
		verCliente=false;
		verContrato=false;
	}
	
	public void recargarLista(){
		this.lista=dao.getLista();		
	}
	
	public void recargarListaArrendamientos (int clienteID){ 
	  this.clienteID=clienteID;
	  this.mapaArrendamiento.clear();
	  this.listaArrendamientos =  dao.getArrendado ( clienteID);
	}
	
	public void actualizarDevolver(int idArr){
	  mapaArrendamiento.put(idArr,this.spinnerDevolucion);
	  
	  if(this.spinnerDevolucion == 0){
	   mapaArrendamiento.remove(idArr);	 
	  } 
	  System.out.println("mapa:"+mapaArrendamiento);
	}
	
	public void ajustesAddMovimentoOT(Cliente cliente, int referencia){
		this.clienteOBJ=cliente;
		this.referencia=referencia;
		this.codigoMovimientoID=4;
		this.actualizarCotizacionYContrato(codigoMovimientoID);
	}
 
	
	
	public String devolver(){ 
	  System.out.println("mapa:"+mapaArrendamiento);
	  
	  //se crea un movimiento de tipo X y se le pone como otID el valor otid del mb  
    
    Movimiento aux=new Movimiento();      
    aux.setComprobante(0);
    aux.setCotizacion(BigDecimal.ZERO);
    aux.setContratoID(0);
    if(clienteOBJ !=null){
      aux.setNombreCliente(clienteOBJ.getNombre());
    }
    aux.setReferencia(this.otID);
    aux.setTipoOT("D");
    aux.setTipoReferencia("OT");  
    aux.setFecha(new Date());  
    aux.setCodigoMovimientoID(5);
    aux.setClienteID(clienteID); 

	  //
    dao.devolver(mapaArrendamiento, aux);
	  
	  //se debe vaciar la lista
    this.mapaArrendamiento.clear();
    
    return  "/paginas/movimientos.xhtml?faces-redirect=true";
	}
	
	
	public String add(){
		String salida=null; 
		
		Movimiento aux=new Movimiento();			
		aux.setComprobante(comprobante);
		aux.setCotizacion(cotizacion);
		aux.setContratoID(contratoID);
		if(clienteOBJ !=null){
      aux.setNombreCliente(clienteOBJ.getNombre());
      aux.setClienteID(clienteOBJ.getClienteID()); 
    }
		aux.setReferencia(referencia);
		aux.setTipoOT(tipoOT);
		aux.setTipoReferencia(tipoReferencia);  
		aux.setFecha(fecha);
		aux.setCodigoMovimientoID(codigoMovimientoID);
		
			
		
		if (DAO_Movimiento.add (aux, this.listaNexos)){
			salida= "/paginas/movimientos.xhtml?faces-redirect=true";
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Agregado", "Se agrego el movimiento "+aux.getMovimientoID());
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}
		else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo agregar el movimiento");
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}
		System.out.println(">>AGREGAR"+aux.getMovimientoID());
		recargarLista ();
		return salida; 
		
	}
	
	
	public void onRowEdit(RowEditEvent event) {
		
		Movimiento f= (Movimiento) event.getObject();
		
		
  	if(dao.update(f)){    		
  		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizado", "Se actualizo el movimiento "+f.getMovimientoID());
          FacesContext.getCurrentInstance().addMessage(null, message); 
          //recargarLista();
  	}
  	else{
  		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo actualizar");
          FacesContext.getCurrentInstance().addMessage(null, message);           
  	}  	
      System.out.println("entro en movimiento update");
  }
	
  public void editarNexo(RowEditEvent event) {
    
    NexoMovimiento o= (NexoMovimiento) event.getObject();
    
    
    if(this.movimientoID==0){
    	//this.listaTemporalNexos.
    	return;
    }
    
    
    if(dao.updateNexo(o)){        
      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizado", "Se actualizo el item "+o.getNexoMovimientoID());
          FacesContext.getCurrentInstance().addMessage(null, message); 
          //recargarLista();
    }
    else{
      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo actualizar el item");
          FacesContext.getCurrentInstance().addMessage(null, message);           
    }   
      System.out.println("entro en movimiento update");
  }




	public void delete(Movimiento f){ 
		if (dao.delete(f) ){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Borrado", "Se elimino el movimiento "+f.getMovimientoID());
	        FacesContext.getCurrentInstance().addMessage(null, message);			
		}
		else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error no se elimino el movimiento intente borrar todos los items del movimiento ");
	        FacesContext.getCurrentInstance().addMessage(null, message);			
		}
		//return "/paginas/funcionarios.xhtml?faces-redirect=true";
        recargarLista ();
	}
 

	

	//se seleccionan el valor para hacer update	
	public String selecToUpdateMovimiento(Movimiento m){		
		//agregar las variables
		getVariables(m);		
		return "/paginas/updatemovimiento.xhtml?faces-redirect=true";
	}
	
	//se efectua el update
	public String update(){
		Movimiento m = setVariables();
		dao.update (m);
		limpiarVariables();
		return "/paginas/movimientos.xhtml?faces-redirect=true";
	}
	

	
	//se le se setean los valores al objeto movimiento
	private Movimiento setVariables(){
		Movimiento aux=new Movimiento();
		aux.setMovimientoID(movimientoID);			
		aux.setComprobante(comprobante);
		aux.setCotizacion(cotizacion);
		aux.setContratoID(contratoID);
		aux.setNombreCliente(nombreCliente);
		aux.setNroMovimiento(nroMovimiento);
		aux.setReferencia(referencia);
		aux.setTipoOT(tipoOT);
		aux.setTipoReferencia(tipoReferencia);  
		aux.setFecha(fecha);
		aux.setCodigoMovimientoID(codigoMovimientoID);
		aux.setClienteID(clienteID);
		return aux;
	}
	
	//se toman los valores del objeto movimiento a las variables del mb
	private void getVariables(Movimiento f){
		this.clienteID=f.getClienteID();
		this.codigoMovimientoID=f.getCodigoMovimientoID();
		this.comprobante=f.getComprobante();
		this.contratoID=f.getContratoID();
		this.cotizacion=f.getCotizacion();
		this.fecha=f.getFecha();
		this.movimientoID=f.getMovimientoID();
		this.nombreCliente=f.getNombreCliente();
		this.nroMovimiento=f.getNroMovimiento();
		this.referencia=f.getReferencia();
		this.tipoOT=f.getTipoOT();
		this.tipoReferencia=f.getTipoReferencia();	
	}	
	
	
	//usado para ingreso de movimiento si se habilitan los campos de cotizacion o de contrato
	public void actualizarCotizacionYContrato(int cod){
		Cod_Movimiento cm=dao.getCodMovimiento(cod);
		this.listaReferencias=dao.getListaCMPorTipoRef(cod);
		this.verCosto=cm.getVerCosto()==1;
		this.verCotizacion=cm.getVerCotizacion()==1;
		this.verReferencia=cm.getVerReferencia()==1;
		this.verCliente=cm.getVerCliente()==1;
		this.verContrato=cm.getVerContrato()==1;
		System.out.println("<<<vercliente "+isVerCliente());
		this.EditarCosto=cm.getEditarCosto()==1;
		this.EditarCotizacion=cm.getEditarCotizacion()==1;
		this.EditarReferencia=cm.getEditarReferencia()==1; 
		this.EditarCliente=cm.getEditarCliente()==1;
		this.EditarContrato=cm.getEditarContrato()==1;
		
		
		
		
		System.out.println("codigo en actualizar mov "+cod); 	 
	}

	
	public boolean isVerCliente() {
    return verCliente;
  }

  public void setVerCliente(boolean verCliente) {
    this.verCliente = verCliente;
  }

  public Map<Integer, Integer> getMapaArrendamiento() {
    return mapaArrendamiento;
  }

  public void setMapaArrendamiento(Map<Integer, Integer> mapaArrendamiento) {
    this.mapaArrendamiento = mapaArrendamiento;
  }

  public String addNexo(){
  	HttpServletRequest servletContext = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
  	String realPath=(String) servletContext.getServletPath(); 
  	System.out.println("contexto addnexo: "+realPath);
  	//se agrega a una lista temporal
  	//if(realPath.contains("addmovimiento.xhtml")){
  	if(listaNexos.isEmpty() || (!listaNexos.isEmpty()&&listaNexos.get(0).getMovimientoID()==0) ){
  		NexoMovimiento nexo=new NexoMovimiento();
  		nexo.setArticuloID(articuloOBJ.getArticuloID());
  		nexo.setCantidad(this.cantidadArticulo);
  		nexo.setCosto(costo);
  		nexo.setFecha(fecha);
  		nexo.setMovimientoID(0);		
  		nexo.setArticulo(articuloOBJ);
  		this.listaNexos.add(nexo);
  		return null;
  	}
  	
  	
  	
  	
		String salida=null;
		NexoMovimiento nexo=new NexoMovimiento();
		nexo.setArticulo(articuloOBJ);//verificar
		nexo.setArticuloID(articuloOBJ.getArticuloID());
		nexo.setCantidad(this.cantidadArticulo);
		nexo.setCosto(costo);
		nexo.setFecha(fecha);
		nexo.setMovimientoID(movimientoID);		
		
		
		System.out.println("entro en addnexo");
		if(dao.addNexo(nexo)){
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Agregado","Se agrego el Item al movimiento");
			context.addMessage(null,mensaje);
			//this.recargarListaAdicionales(numeroID);
		}
		else{
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error","No se pudo agregar el item");
			context.addMessage(null,mensaje);
		} 
		return salida;		
	}
	
  
	public  String addmovdesdemenu() {
		this.codigoMovimientoID=0;
		this.listaCodMovimientosComun=dao.getListaCodMovimientosComun();
	  this.listaCodMovimientosEnOt=dao.getListaCodMovimientosEnOT();
	
		this.listaReferencias=dao.getListaReferencias();
		this.verReferencia=false;
		this.verCotizacion=false;
		this.verCosto=false;
		this.clienteID=0;
		this.listaNexos=new ArrayList<NexoMovimiento>();
		this.listaTemporalNexos=new ArrayList<NexoMovimiento>();
		limpiarVariables();
		
		
		String salida= "/paginas/addmovimiento.xhtml?faces-redirect=true"; 
		return salida;
	} 
  
  
  
	public void deleteNexo(NexoMovimiento nexo){ 
		HttpServletRequest servletContext = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
  	String realPath=(String) servletContext.getServletPath(); 
  	System.out.println("contexto deletenexo: "+realPath);
  	//se agrega a una lista temporal
  	if(realPath.contains("addmovimiento.xhtml")){ 
			this.listaNexos.remove(nexo);			
			return;
		}
		
		
		
		int idM=nexo.getMovimientoID();
		if (dao.deleteNexo(nexo) ){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Borrado", "Se elimino el Item");
	        FacesContext.getCurrentInstance().addMessage(null, message);			
		}
		else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error no se pudo eliminar el item ");
	        FacesContext.getCurrentInstance().addMessage(null, message);			
		}
		//return "/paginas/funcionarios.xhtml?faces-redirect=true";
    this.recargarListaNexos(idM);		
	}

	
	
	public void actualizarTipoReferencia (int codRef) { 
		
	}
	
	
	public void recargarListaNexos(int movimientoID){
		 this.listaNexos = dao.getNexos(movimientoID);		
	}
	
	
	private void limpiarVariables(){
		this.referencia=0;
		this.cantidadArticulo=BigDecimal.ZERO;
		this.costo=BigDecimal.ZERO;
		this.cotizacion=BigDecimal.ZERO;
		this.movimientoID=0;
		
	}
	
 
	
	public void findMovimiento(){
		Movimiento f=this.setVariables();		
		dao.findMovimiento(f);
	}		
	
	

	public int getClienteID() {
		return clienteID;
	}


	public void setClienteID(int clienteID) {
		this.clienteID = clienteID;
	}


	public int getCodigoMovimientoID() {
		return codigoMovimientoID;
	}


	public void setCodigoMovimientoID(int codigoMovimientoID) {
		this.codigoMovimientoID = codigoMovimientoID;
	}


	public int getComprobante() {
		return comprobante;
	}


	public void setComprobante(int comprobante) {
		this.comprobante = comprobante;
	}


	public int getContratoID() {
		return contratoID;
	}


	public void setContratoID(int contratoID) {
		this.contratoID = contratoID;
	}


	public BigDecimal getCotizacion() {
		return cotizacion;
	}


	public void setCotizacion(BigDecimal cotizacion) {
		this.cotizacion = cotizacion;
	}


	public String getNombreCliente() {
		return nombreCliente;
	}


	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}


	public int getNroMovimiento() {
		return nroMovimiento;
	}


	public void setNroMovimiento(int nroMovimiento) {
		this.nroMovimiento = nroMovimiento;
	}


	public int getReferencia() {
		return referencia;
	}


	public void setReferencia(int referencia) {
		this.referencia = referencia;
	}


	public String getTipoOT() {
		return tipoOT;
	}


	public void setTipoOT(String tipoOT) {
		this.tipoOT = tipoOT;
	}


	public String getTipoReferencia() {
		return tipoReferencia;
	}


	public void setTipoReferencia(String tipoReferencia) {
		this.tipoReferencia = tipoReferencia;
	}

	public int getMovimientoID() {
		return movimientoID;
	}

	public void setMovimientoID(int movimientoID) {
		this.movimientoID = movimientoID;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public ArrayList<Movimiento> getLista() {
		return lista;
	}

	public void setLista(ArrayList<Movimiento> lista) {
		this.lista = lista;
	}

	public ArrayList<NexoMovimiento> getListaNexos() {
		return listaNexos;
	}

	public void setListaNexos(ArrayList<NexoMovimiento> listaNexo) {
		this.listaNexos = listaNexo;
	}

//	public ArrayList<Codigo> getListaCodigos() {
//		return listaCodigos;
//	}
//
//	public void setListaCodigos(ArrayList<Codigo> listaCodigos) {
//		this.listaCodigos = listaCodigos;
//	}




	public ArrayList<Referencia> getListaReferencias() {
		return listaReferencias;
	}

	public ArrayList<Cod_Movimiento> getListaCodMovimientosComun() {
    return listaCodMovimientosComun;
  }

  public void setListaCodMovimientosComun(ArrayList<Cod_Movimiento> listaCodMovimientosComun) {
    this.listaCodMovimientosComun = listaCodMovimientosComun;
  }

  public ArrayList<Cod_Movimiento> getListaCodMovimientosEnOt() {
    return listaCodMovimientosEnOt;
  }

  public void setListaCodMovimientosEnOt(ArrayList<Cod_Movimiento> listaCodMovimientosEnOt) {
    this.listaCodMovimientosEnOt = listaCodMovimientosEnOt;
  }

  public void setListaReferencias(ArrayList<Referencia> listaReferencias) {
		this.listaReferencias = listaReferencias;
	}

	public boolean isVerCotizacion() {
		return verCotizacion;
	}

	public void setVerCotizacion(boolean verCotizacion) {
		this.verCotizacion = verCotizacion;
	}

	public boolean isVerReferencia() {
		return verReferencia;
	}

	public void setVerReferencia(boolean verReferencia) {
		this.verReferencia = verReferencia;
	}

	public Cliente getClienteOBJ() {
		return clienteOBJ;
	}

	public void setClienteOBJ(Cliente clienteOBJ) {
		this.clienteOBJ = clienteOBJ;
	}

	public Articulo getArticuloOBJ() {
		return articuloOBJ;
	}

	public void setArticuloOBJ(Articulo articuloOBJ) {
		this.articuloOBJ = articuloOBJ;
	}

 
	public BigDecimal getCantidadArticulo() {
    return cantidadArticulo;
  }

  public void setCantidadArticulo(BigDecimal cantidadArticulo) {
    this.cantidadArticulo = cantidadArticulo;
  }

  public BigDecimal getCosto() {
		return costo;
	}

	public void setCosto(BigDecimal costo) {
		this.costo = costo;
	}
	
  public ArrayList<Arrendamiento> getListaArrendamientos() {
    return listaArrendamientos;
  }

  public void setListaArrendamientos(ArrayList<Arrendamiento> listaArrendamientos) {
    this.listaArrendamientos = listaArrendamientos;
  }

  public int getOtID() {
    return otID;
  }

  public void setOtID(int otID) {
    this.otID = otID;
  }

  public int getSpinnerDevolucion() {
    return spinnerDevolucion;
  }

  public void setSpinnerDevolucion(int spinnerDevolucion) {
    this.spinnerDevolucion = spinnerDevolucion;
  }

	public Contrato getContratoOBJ() {
		return contratoOBJ;
	}

	public void setContratoOBJ(Contrato contratoOBJ) {
		this.contratoOBJ = contratoOBJ;
	}

	public boolean isVerCosto() {
		return verCosto;
	}

	public void setVerCosto(boolean verCosto) {
		this.verCosto = verCosto;
	}

	public ArrayList<NexoMovimiento> getListaTemporalNexos() {
		return listaTemporalNexos;
	}

	public void setListaTemporalNexos(ArrayList<NexoMovimiento> listaTemporalNexos) {
		this.listaTemporalNexos = listaTemporalNexos;
	}

	public boolean isVerContrato() {
		return verContrato;
	}

	public void setVerContrato(boolean verContrato) {
		this.verContrato = verContrato;
	}

	public boolean isEditarCotizacion() {
		return EditarCotizacion;
	}

	public void setEditarCotizacion(boolean editarCotizacion) {
		EditarCotizacion = editarCotizacion;
	}

	public boolean isEditarReferencia() {
		return EditarReferencia;
	}

	public void setEditarReferencia(boolean editarReferencia) {
		EditarReferencia = editarReferencia;
	}

	public boolean isEditarCliente() {
		return EditarCliente;
	}

	public void setEditarCliente(boolean editarCliente) {
		EditarCliente = editarCliente;
	}

	public boolean isEditarCosto() {
		return EditarCosto;
	}

	public void setEditarCosto(boolean editarCosto) {
		EditarCosto = editarCosto;
	}

	public boolean isEditarContrato() {
		return EditarContrato;
	}

	public void setEditarContrato(boolean editarContrato) {
		EditarContrato = editarContrato;
	}

 

	
}
