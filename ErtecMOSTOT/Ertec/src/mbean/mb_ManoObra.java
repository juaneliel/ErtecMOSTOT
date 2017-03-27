package mbean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext; 

import org.primefaces.event.RowEditEvent;
import model.Cliente;
import model.CodigosMO;
import model.Funcionario;
import model.ManoObra;
import model.DAO.DAO_Funcionario;
import model.DAO.DAO_ManoObra; 

@ManagedBean (name="mb_ManoObra")
@ViewScoped
public class mb_ManoObra {

	private String cliente;
	private int codigo;
	private Date fecha;
	private int funcionarioID;
	private int myr;
	private int NContrato;
	private int ordenTrabajo;
	private int manoObraID;
	private String TContrato;
	private int tipo;
	private String tipoHora;
	private String tipoMano;
	private BigDecimal totalHora;
	
	Date fechaIni;
	Date fechaFin;
	
	private Date fechaIniBuscar;
	private Date fechaFinBuscar;
	private int funIDBuscar;
	private int otIDBuscar;
	private String tipoHoraBuscar;
	private String tipoManoBuscar;
	
	private ArrayList <Funcionario> listaFuncionarios;
	
	private DAO_ManoObra dao=new DAO_ManoObra();
	private DAO_Funcionario daoF=new DAO_Funcionario();
	private ArrayList<ManoObra> lista ;
	private ArrayList<CodigosMO> listaCodMO ;	 
	private ManoObra moSelected;
	private ManoObra manodeobraAdd=new ManoObra(); 
	
	private Funcionario funcionarioOBJ;
	private Cliente clienteOBJ;
	
	private CodigosMO codigosMOOBJ;	
	private ArrayList<CodigosMO> listaCodigosMOOBJ;
	
	
	//funcionalidad basica
	
	@PostConstruct
	public void init(){	  
	  System.out.println("MBmo init");
		this.listaCodMO=dao.getListaCodMO();
		this.listaFuncionarios=daoF.getListaFuncionarios();
		this.recargarLista();
		this.codigosMOOBJ=dao.getCodigosMO(301); 
	}
	
	public  String listarDesdeMenu() {
		String salida= "/paginas/manodeobras.xhtml?faces-redirect=true"; 
		return salida;
	} 

	public String add(){
		
		System.out.println("MBmo add"); 
		String salida= null;
    	
    	ManoObra o = new ManoObra();
    	o.setCliente(clienteOBJ); 
    	o.setClienteNombre(clienteOBJ.getNombre());
		o.setCodigo(this.getCodigosMOOBJ().getCodigo());
		o.setFecha(fecha);
		o.setFuncionario(funcionarioOBJ); 
		o.setMyr(myr);
		o.setNContrato(NContrato);
		o.setOrdenTrabajo(ordenTrabajo);
		o.setManoObraID(manoObraID);
		o.setTContrato(TContrato);
		o.setTipo(this.getCodigosMOOBJ().getTipo());
		o.setTipoHora(tipoHora);
		o.setTipoMano(tipoMano);
		o.setTotalHora(totalHora);

		if(DAO_ManoObra.add(o)){
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Agregado","Se agrego la mano de obra "+o.getManoObraID());
			context.addMessage(null,mensaje);
			this.recargarLista();
			salida= "/paginas/manodeobras.xhtml?faces-redirect=true";
		}
		else{
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error","No se pudo agregar");
			context.addMessage(null,mensaje);
		}
		
		return salida;
	}

	
	public void clickEdit(ManoObra o){
		funcionarioOBJ = o.getFuncionario();
		System.out.println("entro en click edit");
	}
	
	public ArrayList <CodigosMO> completarCodigosMO(String query){
		this.listaCodigosMOOBJ=dao.completarCodigosMO(query);
		return listaCodigosMOOBJ;
	}

	public void onRowEdit(RowEditEvent event) {		
		
		
		ManoObra o= (ManoObra) event.getObject();
		//o.setClienteNombre(o.getCliente().getNombre());
			
    	if(dao.update(o)){    		
    		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizado", "Se actualizo la mano de obra "+o.getManoObraID());
            FacesContext.getCurrentInstance().addMessage(null, message); 
            //recargarLista();
    	}
    	else{
    		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo actualizar");
            FacesContext.getCurrentInstance().addMessage(null, message);           
    	}  	
    	System.out.println(">>>>onrow");
        
    }
	
	public void delete(ManoObra o){ 
		if(dao.delete(o)){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Borrado", "Se elimino MO del funcionario "+ o.getFuncionario().getFuncionarioID());
	        FacesContext.getCurrentInstance().addMessage(null, message);
	        System.out.println("MBmo delete");
	        this.recargarLista();
		}
		else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Borrado", "Error no se pudo borrar "+ o.getFuncionario().getFuncionarioID()  );
	    FacesContext.getCurrentInstance().addMessage(null, message);
		} 		
	}	
	

	
	public ArrayList<CodigosMO> codigosMOOT(){
		return dao.getCodigosMOOT();
	}
	
	public ArrayList<CodigosMO> codigosMONoOT(){
		return dao.getCodigosMONoOT();
	}
	
	public ArrayList<ManoObra> getLista(){	
		return this.lista;
	}
	
	public void recargarLista(){
		this.listaFuncionarios=daoF.getListaFuncionarios();
		this.lista = dao.getLista ();
		System.out.println(">>>MBmo recargarlista");
	} 
	
	public void filtrarPorFechas(){
		this.lista=dao.getFiltradaPorFechas(fechaIni,fechaFin); 
	}
	
//	public ArrayList<ManoObra> filtrarPorFechasManoObraOT(){
//		return dao.getFiltradaPorFechasYOT(fechaIni, fechaFin, ordenTrabajo);
//	}
	
	

	
	public void getMOPorFiltros (){
		//se transforman los valores ingresados a sus correspondientes valores para ser buscados en el dao		
		
		this.lista  =  this.dao.getMOPorFiltros (fechaIniBuscar, fechaFinBuscar, funIDBuscar, otIDBuscar, tipoHoraBuscar, tipoManoBuscar);
	}
	
	
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public int getFuncionarioID() {
		return funcionarioID;
	}
	public void setFuncionarioID(int funcionarioID) {
		this.funcionarioID = funcionarioID;
	}
	public int getMyr() {
		return myr;
	}
	public void setMyr(int myr) {
		this.myr = myr;
	}
	public int getNContrato() {
		return NContrato;
	}
	public void setNContrato(int nContrato) {
		NContrato = nContrato;
	}
	public int getOrdenTrabajo() {
		return ordenTrabajo;
	}
	public void setOrdenTrabajo(int ordenTrabajo) {
		this.ordenTrabajo = ordenTrabajo;
	}

	public int getManoObraID() {
		return manoObraID;
	}

	public void setManoObraID(int manoObraID) {
		this.manoObraID = manoObraID;
	}

	public String getTContrato() {
		return TContrato;
	}
	public void setTContrato(String tContrato) {
		TContrato = tContrato;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public String getTipoHora() {
		return tipoHora;
	}
	
	public void setTipoHora(String tipoHora) {
		this.tipoHora = tipoHora;
	}
	public String getTipoMano() {
		return tipoMano;
	}
	public void setTipoMano(String tipoMano) {
		this.tipoMano = tipoMano;
	}
	public BigDecimal getTotalHora() {
		return totalHora;
	}
	public void setTotalHora(BigDecimal totalHora) {
		this.totalHora = totalHora;
	}

	public Date getFechaIniBuscar() {
		return fechaIniBuscar;
	}

	public void setFechaIniBuscar(Date fechaIniBuscar) {
		this.fechaIniBuscar = fechaIniBuscar;
	}

	public Date getFechaFinBuscar() {
		return fechaFinBuscar;
	}

	public void setFechaFinBuscar(Date fechaFinBuscar) {
		this.fechaFinBuscar = fechaFinBuscar;
	}

	public int getFunIDBuscar() {
		return funIDBuscar;
	}

	public void setFunIDBuscar(int funIDBuscar) {
		this.funIDBuscar = funIDBuscar;
	}

	public int getOtIDBuscar() {
		return otIDBuscar;
	}

	public void setOtIDBuscar(int otIDBuscar) {
		this.otIDBuscar = otIDBuscar;
	}

	public String getTipoHoraBuscar() {
		return tipoHoraBuscar;
	}

	public void setTipoHoraBuscar(String tipoHoraBuscar) {
		this.tipoHoraBuscar = tipoHoraBuscar;
	}

	public String getTipoManoBuscar() {
		return tipoManoBuscar;
	}

	public void setTipoManoBuscar(String tipoManoBuscar) {
		this.tipoManoBuscar = tipoManoBuscar;
	}

	public ArrayList<Funcionario> getListaFuncionarios() {
		return listaFuncionarios;
	}

	public void setListaFuncionarios(ArrayList<Funcionario> listaFuncionarios) {
		this.listaFuncionarios = listaFuncionarios;
	}

	public void setLista(ArrayList<ManoObra> lista) {
		this.lista = lista;
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



	public ArrayList<CodigosMO> getListaCodMO() {
		return listaCodMO;
	}



	public void setListaCodMO(ArrayList<CodigosMO> listaCodMO) {
		this.listaCodMO = listaCodMO;
	}

	public Funcionario getFuncionarioOBJ() {
		return funcionarioOBJ;
	}

	public void setFuncionarioOBJ(Funcionario funcionarioOBJ) {
		this.funcionarioOBJ = funcionarioOBJ;
	}



	public CodigosMO getCodigosMOOBJ() {
		return codigosMOOBJ;
	}

	public void setCodigosMOOBJ(CodigosMO codigosMOOBJ) {
		this.codigosMOOBJ = codigosMOOBJ;
	}

	public Cliente getClienteOBJ() {
		return clienteOBJ;
	}

	public void setClienteOBJ(Cliente clienteOBJ) {
		this.clienteOBJ = clienteOBJ;
	}

	public ArrayList<CodigosMO> getListaCodigosMOOBJ() {
		return listaCodigosMOOBJ;
	}

	public void setListaCodigosMOOBJ(ArrayList<CodigosMO> listaCodigosMOOBJ) {
		this.listaCodigosMOOBJ = listaCodigosMOOBJ;
	}

	public ManoObra getMoSelected() {
		return moSelected;
	}

	public void setMoSelected(ManoObra moSelected) {
		this.moSelected = moSelected;
	}

	public ManoObra getManodeobraAdd() {
		return manodeobraAdd;
	}

	public void setManodeobraAdd(ManoObra manodeobraAdd) {
		this.manodeobraAdd = manodeobraAdd;
	}
	
	
}





//public void find(){
//	ManoObra o = this.setVariables();	
//	this.lista  = dao.find(o);
//	System.out.println("Funcionarios hallados" + lista.size() );
//}


//funciones extra

//se le se setean los valores al objeto funcionario
//private ManoObra setVariables(){
//	ManoObra o = new ManoObra();
//	o.setCliente(cliente);
//	o.setCodigo(codigo);
//	o.setFecha(fecha);
//	o.setFuncionarioID(funcionarioID);
//	o.setMyr(myr);
//	o.setNContrato(NContrato);
//	o.setOrdenTrabajo(ordenTrabajo);
//	o.setManoObraID(manoObraID);
//	o.setTContrato(TContrato);
//	o.setTipo(tipo);
//	o.setTipoHora(tipoHora);
//	o.setTipoMano(tipoMano);
//	o.setTotalHora(totalHora); 
//	return o;
//}

////se toman los valores del objeto funcionario a las variables del mb
//private void getVariables(ManoObra o){ 
//	this.setCliente(o.getCliente());
//	this.setCodigo(o.getCodigo());
//	this.setFecha(o.getFecha());
//	this.setFuncionarioID(o.getFuncionarioID());
//	this.setMyr(o.getMyr());
//	this.setNContrato(o.getNContrato());
//	this.setOrdenTrabajo(o.getOrdenTrabajo());
//	this.setManoObraID(o.getManoObraID());
//	this.setTContrato(o.getTContrato());
//	this.setTipo(o.getTipo());
//	this.setTipoHora(o.getTipoHora());
//	this.setTipoMano(o.getTipoMano());
//	this.setTotalHora(o.getTotalHora());		
//}	




//public ArrayList <Funcionario> completarFuncionario(String query){
//	this.listaFuncionariosOBJ=dao.completarFuncionario(query);
//	return listaFuncionariosOBJ;
//}


////se seleccionan el valor para hacer update	
//public void selecToUpdate(ManoObra o){		
//	//agregar las variables
//	getVariables(o);		
//	//return "/paginas/updatefuncionario.xhtml?faces-redirect=true";
//}

////se efectua el update
//public void update(){
//	ManoObra o = setVariables();
//	dao.update(o);
//	limpiarVariables();
//	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizado", "Se actualizo el funcionario "+o.getFuncionarioID());
//    FacesContext.getCurrentInstance().addMessage(null, message);
//	recargarLista();
//	//return "/paginas/funcionarios.xhtml?faces-redirect=true";
//}











