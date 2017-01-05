package mbean;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;

import model.Cliente;
import model.Contrato;
import model.Funcionario;
import model.Reclamo;
import model.DAO.DAO_Cliente;
import model.DAO.DAO_Reclamo;
import model.DAO.DAO_infoService;

@ManagedBean(name="mb_Reclamo", eager = true)
@SessionScoped

public class mb_Reclamo {

  private List<Boolean> list;
  private ArrayList<Reclamo> lista;
  private DAO_Reclamo dao=new DAO_Reclamo();
  private int id;
  private short antel=0;
  private int clienteID=0;
  private String codigo;
  private short conmutador=0;
  private String contacto;
  private short energia=0;
  private String estado;
  private Date fechaReclamado;
  private Date fechaVisita;
  private Time horaReclamado;
  private String myr;
  private String myrEstado;
  private String observaciones;
  private String ordenreparacion;
  private Object reclamo;
  private short red=0;
  private int funcionarioID;
  private short telefonos=0;
  private String urgente;
  private Cliente clienteOBJ;
  private Funcionario funcionarioOBJ;
  private int contratoID;
  
  private ArrayList<Reclamo> listaPendientes;
  private ArrayList<Reclamo> listaAnuladas;
  private ArrayList<Reclamo> listaVisitadas;
  private ArrayList<DAO_infoService> filtrado;
  private Contrato contratoOBJ;
  
  
  private ArrayList<Contrato> listaSinVisitar;
  
  private Date fechaIni;
  private Date fechaFin;
  private DAO_infoService infoServiceSelected;
  
  
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

  @PostConstruct  
  public void init(){
    this.recargar();
    list = Arrays.asList(true, true, true, true, true,true, true, true, true, true, true,
        true, false, false, false, false, true, false, false);
  } 
  
  public void recargar() {
    this.filtrado=dao.filtrarInformeVisitadosPorFechas(this.fechaIni, this.fechaFin, true);
    this.lista=dao.getLista();
    this.listaPendientes=dao.getListaPendientes();
    this.listaSinVisitar=dao.filtrarSinVisitar(this.fechaIni, this.fechaFin,true);
    System.out.println("mb tamaño reclamos "+lista.size());
  } 
  
  public void filtrarInformeVisitadosPorFechas(){
    filtrado=dao.filtrarInformeVisitadosPorFechas(this.fechaIni, this.fechaFin, true);
  }
  
  public void filtrarSinVisitar(){
    this.listaSinVisitar=dao.filtrarSinVisitar(this.fechaIni, this.fechaFin,true);
  }
  
  public String add(){
    String salida=null;
    Reclamo aux = new Reclamo();
    
   
    //aux.setCliente(clienteOBJ);
    if(clienteOBJ!=null){
      aux.setClienteID(clienteOBJ.getClienteID());
      aux.setNombreCliente(clienteOBJ.getNombre());
    } 
    aux.setFechaReclamado(this.getFechaReclamado());
    aux.setUrgente(this.getUrgente()); 
    aux.setContacto(this.getContacto());
    aux.setReclamo(this.getReclamo().toString());    
    if(this.contratoOBJ!=null){
      aux.setContratoID(contratoOBJ.getId());
      aux.setCodigo(contratoOBJ.getTipo()+contratoOBJ.getContratoID()); 
    }
     
    aux.setAntel(this.getAntel());
    aux.setConmutador(conmutador);
    aux.setEnergia(getEnergia());
    aux.setEstado("pendiente");
    aux.setFechaVisita(this.getFechaVisita());
    aux.setHoraReclamado(this.getHoraReclamado());
    aux.setMyr(this.getMyr());
    aux.setMyrEstado("pen");
    aux.setObservaciones(this.getObservaciones());
    aux.setOrdenreparacion(this.getOrdenreparacion());
    aux.setRed(this.getRed());
    //aux.setFuncionario(null);
    aux.setTelefonos(getTelefonos());
    
    
    
    
    if (dao.add (aux)){
      salida= "/paginas/reclamos.xhtml?faces-redirect=true";
      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Agregado", "El reclamo se agrego exitosamente");
          FacesContext.getCurrentInstance().addMessage(null, message);
    }
    else{
      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Agregado", "Error al agregar reclamo");
          FacesContext.getCurrentInstance().addMessage(null, message);
    }
    System.out.println(">>AGREGAR"+aux.getId() );
    recargar ();
    return salida;
  }
  
 
  
  public void delete(Reclamo o){ 
    if (dao.delete(o) ){
      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Borrado", "Se elimino el reclamo "+o.getId());
          FacesContext.getCurrentInstance().addMessage(null, message);      
    }
    else{
      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error no se elimino el reclamo "+o.getId());
          FacesContext.getCurrentInstance().addMessage(null, message);      
    }
    //return "/paginas/funcionarios.xhtml?faces-redirect=true";
        recargar ();
  }
 
 
  public void editReclamo (RowEditEvent event) {
    
    Reclamo o= (Reclamo) event.getObject();
    
    System.out.println("codigo de reclamo: "+o.getCodigo());
      if(dao.update(o)){       
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizado", "Se actualizo el reclamo ");
            FacesContext.getCurrentInstance().addMessage(null, message); 
            //this.recargarListaAdicionales(numeroID);
      }
      else{
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo actualizar");
            FacesContext.getCurrentInstance().addMessage(null, message);           
      }   
      recargar ();
//      if(!o.getEstado().equals("pendiente")){
//        this.listaPendientes=dao.getListaPendientes();
//      }
//      this.listaPendientes=dao.getListaPendientes();
    }
  
  
  
  public DAO_infoService getInfoServiceSelected() {
    return infoServiceSelected;
  }

  public void setInfoServiceSelected(DAO_infoService infoServiceSelected) {
    this.infoServiceSelected = infoServiceSelected;
  }

  public List<Boolean> getList() {
    return list;
  }
  
  public void onToggle(ToggleEvent e) {
    list.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
  }
  
  
  
  
  public ArrayList<Reclamo> getLista() {
    return lista;
  }

  public void setLista(ArrayList<Reclamo> lista) {
    this.lista = lista;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public short getAntel() {
    return antel;
  }

  public void setAntel(short antel) {
    this.antel = antel;
  }

  

 
  public int getClienteID() {
    return clienteID;
  }

  public void setClienteID(int clienteID) {
    this.clienteID = clienteID;
  }

  public String getCodigo() {
    return codigo;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  public short getConmutador() {
    return conmutador;
  }

  public void setConmutador(short conmutador) {
    this.conmutador = conmutador;
  }

  public String getContacto() {
    return contacto;
  }

  public void setContacto(String contacto) {
    this.contacto = contacto;
  }

  public short getEnergia() {
    return energia;
  }

  public void setEnergia(short energia) {
    this.energia = energia;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public Date getFechaReclamado() {
    return fechaReclamado;
  }

  public void setFechaReclamado(Date fechaReclamado) {
    this.fechaReclamado = fechaReclamado;
  }

  public Date getFechaVisita() {
    return fechaVisita;
  }

  public void setFechaVisita(Date fechaVisita) {
    this.fechaVisita = fechaVisita;
  }

  public Time getHoraReclamado() {
    return horaReclamado;
  }

  public void setHoraReclamado(Time horaReclamado) {
    this.horaReclamado = horaReclamado;
  }

  public String getMyr() {
    return myr;
  }

  public void setMyr(String myr) {
    this.myr = myr;
  }

  public String getMyrEstado() {
    return myrEstado;
  }

  public void setMyrEstado(String myrEstado) {
    this.myrEstado = myrEstado;
  }

 

  public String getObservaciones() {
    return observaciones;
  }

  public void setObservaciones(String observaciones) {
    this.observaciones = observaciones;
  }

  public void setList(List<Boolean> list) {
    this.list = list;
  }

  public String getOrdenreparacion() {
    return ordenreparacion;
  }

  public void setOrdenreparacion(String ordenreparacion) {
    this.ordenreparacion = ordenreparacion;
  }

  public Object getReclamo() {
    return reclamo;
  }

  public void setReclamo(Object reclamo) {
    this.reclamo = reclamo;
  }

  public short getRed() {
    return red;
  }

  public void setRed(short red) {
    this.red = red;
  }


 
  public Funcionario getFuncionarioOBJ() {
    return funcionarioOBJ;
  }

  public void setFuncionarioOBJ(Funcionario funcionarioOBJ) {
    this.funcionarioOBJ = funcionarioOBJ;
  }

  public int getFuncionarioID() {
    return funcionarioID;
  }

  public void setFuncionarioID(int funcionarioID) {
    this.funcionarioID = funcionarioID;
  }

  public short getTelefonos() {
    return telefonos;
  }

  public void setTelefonos(short telefonos) {
    this.telefonos = telefonos;
  }

  public String getUrgente() {
    return urgente;
  }

  public void setUrgente(String urgente) {
    this.urgente = urgente;
  }

  public Cliente getClienteOBJ() {
    return clienteOBJ;
  }

  public void setClienteOBJ(Cliente clienteOBJ) {
    //this.listaContratos=DAO_Cliente.getContratosCliente(clienteOBJ.getClienteID());
    this.clienteOBJ = clienteOBJ;
  }

  public ArrayList<Reclamo> getListaPendientes() {
    return listaPendientes;
  }

  public void setListaPendientes(ArrayList<Reclamo> listaPendientes) {
    this.listaPendientes = listaPendientes;
  }

  public ArrayList<Reclamo> getListaAnuladas() {
    return listaAnuladas;
  }

  public void setListaAnuladas(ArrayList<Reclamo> listaAnuladas) {
    this.listaAnuladas = listaAnuladas;
  }

  public ArrayList<Reclamo> getListaVisitadas() {
    return listaVisitadas;
  }

  public void setListaVisitadas(ArrayList<Reclamo> listaVisitadas) {
    this.listaVisitadas = listaVisitadas;
  }

  public ArrayList<DAO_infoService> getFiltrado() {
    return filtrado;
  }

  public void setFiltrado(ArrayList<DAO_infoService> filtrado) {
    this.filtrado = filtrado;
  }

  public int getContratoID() {
    return contratoID;
  }

  public void setContratoID(int contratoID) {
    this.contratoID = contratoID;
  }

   public Contrato getContratoOBJ() {
    return contratoOBJ;
  }

  public void setContratoOBJ(Contrato contratoOBJ) {
    this.contratoOBJ = contratoOBJ;
  }

  public ArrayList<Contrato> getListaSinVisitar() {
    return listaSinVisitar;
  }

  public void setListaSinVisitar(ArrayList<Contrato> listaSinVisitar) {
    this.listaSinVisitar = listaSinVisitar;
  } 
  
}
