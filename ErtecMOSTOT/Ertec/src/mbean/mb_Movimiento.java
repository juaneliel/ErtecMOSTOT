package mbean;
 
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
	private List<NexoMovimiento> listaNexos;
	private ArrayList<Arrendamiento> listaArrendamientos;
	private ArrayList<NexoMovimiento> listaTemporalNexos;
	private Movimiento movSelected;
	private NexoMovimiento nexSelected;
	private Movimiento movimientoAdd = new Movimiento();
	private NexoMovimiento nexoAdd = new NexoMovimiento();
	
	//private ArrayList<Codigo> listaCodigos;
	private ArrayList<Cod_Movimiento> listaCodMovimientosComun;
	
  private ArrayList<Cod_Movimiento> listaCodMovimientosEnOt;
	
	
	private ArrayList<Referencia> listaReferencias;
	  
	private int spinnerDevolucion;
	private Map<Integer,Integer> mapaArrendamiento = new HashMap<Integer, Integer>(); 
	
	@PostConstruct
	public void init(){
		//this.listaCodigos=dao.getListaCodigos();
		movimientoAdd.setFecha(new Date());
	  this.listaCodMovimientosComun=dao.getListaCodMovimientosComun();
	  this.listaCodMovimientosEnOt=dao.getListaCodMovimientosEnOT();
	  this.listaNexos=new ArrayList<NexoMovimiento>();
		this.listaReferencias=dao.getListaReferencias();
		this.lista=dao.getLista();	
		this.verReferencia=false;
		this.verCotizacion=false;
		this.verCosto=false;
		verCliente=false;
		verContrato=false;
	}
	
	
	public String add(){		
		String salida=null;  
		if(movimientoAdd.getCliente() !=null){
			movimientoAdd.setNombreCliente(movimientoAdd.getCliente().getNombre());
    } 
		
		if (DAO_Movimiento.add (movimientoAdd)){
			salida= "/paginas/movimientos.xhtml?faces-redirect=true";
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Agregado", "Se agrego el movimiento "+movimientoAdd.getMovimientoID());
	    FacesContext.getCurrentInstance().addMessage(null, message);
	    this.lista.add(movimientoAdd);
	    this.movSelected=movimientoAdd;
	    movimientoAdd=new Movimiento();
		}
		else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo agregar el movimiento");
	    FacesContext.getCurrentInstance().addMessage(null, message);
		}
		System.out.println(">>AGREGAR"+movimientoAdd.getMovimientoID());
		return salida; 
		
	}
	
	
	 
	//seccion devolver
	public void recargarListaArrendamientos (int clienteID){ 
	  //this.movimientoAdd.setClienteID(clienteID);
		this.spinnerDevolucion=0;
	  this.mapaArrendamiento.clear();
	  this.listaArrendamientos =  dao.getArrendado ( clienteID);
	}
	
	public void actualizarDevolver(int idArr){
	  mapaArrendamiento.put(idArr,this.spinnerDevolucion);
	  //en caso de que se ponga a 0 se lo saca del mapa ya que no se devolvera
	  //ningun elemento
	  if(this.spinnerDevolucion == 0){
	   mapaArrendamiento.remove(idArr);	 
	  } 
	  System.out.println("mapa:"+mapaArrendamiento);
	}
	
	public String devolver(){ 
	  System.out.println("mapa:"+mapaArrendamiento);	  
	  //se usa movSelected tipo X y se le pone como otID el valor otid del mb      
//    movSelected.setComprobante(0);
//    movSelected.setCotizacion(BigDecimal.ZERO);
//    movSelected.setContrato(null);
//    if(movimientoAdd.getCliente() !=null){
//      aux.setNombreCliente(movimientoAdd.getCliente().getNombre());
//    }
//    aux.setReferencia(this.otID);
//    aux.setTipoOT("D");
//    aux.setTipoReferencia("OT");  
//    aux.setFecha(new Date());  
//    aux.setCodigoMovimientoID(5);
	  this.listaNexos.addAll(dao.devolver(mapaArrendamiento, movSelected.getMovimientoID()));	  
	  //se debe vaciar la lista
    this.mapaArrendamiento.clear();
    
    return  "/paginas/movimientos.xhtml?faces-redirect=true";
	}
	
	
	//otra seccio
	
	public void initAddNexo(){
		this.actualizarCotizacionYContrato(movSelected.getCodigoMovimientoID());
	}
	
	public void ajustesAddMovimentoOT(Cliente cliente, int referencia){
		this.movimientoAdd.setCliente(cliente);
		this.movimientoAdd.setReferencia(referencia);
		this.movimientoAdd.setCodigoMovimientoID(4);//se inicializa con este valor
		this.actualizarCotizacionYContrato(movimientoAdd.getCodigoMovimientoID());
		this.listaNexos=new ArrayList<NexoMovimiento>();
	}
 
	//inicializa el valor inicial de codmov
	public void initAddMov (int cod, Cliente cli){
		movimientoAdd.setCodigoMovimientoID(cod);
		movimientoAdd.setCliente(cli);
		this.actualizarCotizacionYContrato(cod);
	}
	
	

	

	
	
	
	
	public void recargarMovimientoSelected(int idot){
		this.movimientoAdd.setReferencia(idot);
		this.lista=dao.getMovimientosOT(idot);
	}
	
	public void recargarNexoSelected(){
		this.listaNexos=dao.getNexos(this.movSelected.getMovimientoID());
		actualizarCotizacionYContrato(movSelected.getCodigoMovimientoID());
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
  	System.out.println("entro en movimiento update");
    NexoMovimiento o= (NexoMovimiento) event.getObject();
   
    
//    if(o.getMovimientoID()==0){
//    	//this.listaTemporalNexos.
//    	System.out.println("salio en editarnexo mbmov");
//    	return;
//    }
//    
    
    if(dao.updateNexo(o)){        
      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizado", "Se actualizo el item "+o.getNexoMovimientoID());
          FacesContext.getCurrentInstance().addMessage(null, message); 
          //recargarLista();
    }
    else{
      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo actualizar el item");
          FacesContext.getCurrentInstance().addMessage(null, message);           
    }   
      
  }




	public void delete(Movimiento f){ 
		if (dao.delete(f) ){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Borrado", "Se elimino el movimiento "+f.getMovimientoID());
	        FacesContext.getCurrentInstance().addMessage(null, message);			
	    lista.remove(f);
		}
		else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error no se elimino el movimiento intente borrar todos los items del movimiento ");
	        FacesContext.getCurrentInstance().addMessage(null, message);			
		}
		//return "/paginas/funcionarios.xhtml?faces-redirect=true";
        //recargarLista ();
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

  public String addNexo(boolean temporal){
//  	 if(temporal){ 
//  		nexoAdd.setMovimientoID(0);		
//  		this.listaNexos.add(nexoAdd);
//  		nexoAdd=new NexoMovimiento();
//  		return null;
//  	}  	
		String salida=null;			
		System.out.println("entro en addnexo");
		if(dao.addNexo(movSelected.getMovimientoID(), nexoAdd)){
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Agregado","Se agrego el Item al movimiento");
			context.addMessage(null,mensaje);
			listaNexos.add(nexoAdd);
			this.nexSelected=nexoAdd;
			nexoAdd=new NexoMovimiento();
			//this.recargarListaAdicionales(numeroID);
		}
		else{
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error","No se pudo agregar el item");
			context.addMessage(null,mensaje);
		} 
		return salida;		
	}
	
  
//	public  String addmovdesdemenu() {
//		this.codigoMovimientoID=0;
//		this.listaCodMovimientosComun=dao.getListaCodMovimientosComun();
//	  this.listaCodMovimientosEnOt=dao.getListaCodMovimientosEnOT();	
//		this.listaReferencias=dao.getListaReferencias();
//		this.verReferencia=false;
//		this.verCotizacion=false;
//		this.verCosto=false;
//		this.clienteID=0;
//		this.listaNexos=new ArrayList<NexoMovimiento>();
//		this.listaTemporalNexos=new ArrayList<NexoMovimiento>();		 
//		String salida= "/paginas/addmovimiento.xhtml?faces-redirect=true"; 
//		return salida;
//	} 
  
	public void delTemporalListaNexos(NexoMovimiento nexo){
		this.listaNexos.remove(nexo);	
	}
  
  
	public void deleteNexo(NexoMovimiento nexo,boolean temporal){ 
		HttpServletRequest servletContext = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
  	String realPath=(String) servletContext.getServletPath(); 
  	System.out.println("contexto deletenexo: "+realPath);
  	//se elimina de una lista temporal
  	if(temporal){ 
			this.listaNexos.remove(nexo);			
			return;
		}		
		if (dao.deleteNexo(movSelected.getMovimientoID(),nexo) ){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Borrado", "Se elimino el Item");
	    FacesContext.getCurrentInstance().addMessage(null, message);	
	    this.listaNexos.remove(nexo);
		}
		else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error no se pudo eliminar el item ");
	        FacesContext.getCurrentInstance().addMessage(null, message);			
		}
		//return "/paginas/funcionarios.xhtml?faces-redirect=true";
    
	}

	
	
	public void actualizarTipoReferencia (int codRef) { 
		
	}
	
	
	public void recargarListaNexos(int movimientoID){
		 this.listaNexos = dao.getNexos(movimientoID);		
	}
	 
	 

	public ArrayList<Movimiento> getLista() {
		return lista;
	}

	public void setLista(ArrayList<Movimiento> lista) {
		this.lista = lista;
	}



//	public ArrayList<Codigo> getListaCodigos() {
//		return listaCodigos;
//	}
//
//	public void setListaCodigos(ArrayList<Codigo> listaCodigos) {
//		this.listaCodigos = listaCodigos;
//	}




	public List<NexoMovimiento> getListaNexos() {
		return listaNexos;
	}


	public void setListaNexos(List<NexoMovimiento> listaNexos) {
		this.listaNexos = listaNexos;
	}


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

	public Movimiento getMovSelected() {
		return movSelected;
	}

	public void setMovSelected(Movimiento movSelected) {
		this.movSelected = movSelected;
	}

	public Movimiento getMovimientoAdd() {
		return movimientoAdd;
	}

	public void setMovimientoAdd(Movimiento movimientoAdd) {
		this.movimientoAdd = movimientoAdd;
	}

	public NexoMovimiento getNexoAdd() {
		return nexoAdd;
	}

	public void setNexoAdd(NexoMovimiento nexoAdd) {
		this.nexoAdd = nexoAdd;
	}

	public NexoMovimiento getNexSelected() {
		return nexSelected;
	}

	public void setNexSelected(NexoMovimiento nexSelected) {
		this.nexSelected = nexSelected;
	}

 

	
}









//
//
//public String add(){		
//	String salida=null;  
//	if(movimientoAdd.getCliente() !=null){
//		movimientoAdd.setNombreCliente(movimientoAdd.getCliente().getNombre());
//		//movimientoAdd.setClienteID(movimientoAdd.getCliente().getClienteID()); 
//  } 
//	
//	if (DAO_Movimiento.add (movimientoAdd, this.listaNexos)){
//		salida= "/paginas/movimientos.xhtml?faces-redirect=true";
//		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Agregado", "Se agrego el movimiento "+movimientoAdd.getMovimientoID());
//        FacesContext.getCurrentInstance().addMessage(null, message);
//    this.lista.add(movimientoAdd);
//    this.movSelected=movimientoAdd;
//	}
//	else{
//		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo agregar el movimiento");
//        FacesContext.getCurrentInstance().addMessage(null, message);
//	}
//	System.out.println(">>AGREGAR"+movimientoAdd.getMovimientoID());
//	//recargarLista ();
//	return salida; 
//	
//}
