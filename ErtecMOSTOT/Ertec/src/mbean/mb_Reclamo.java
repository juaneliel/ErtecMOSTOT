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
@ViewScoped

public class mb_Reclamo {

  private List<Boolean> list;
  private ArrayList<Reclamo> lista;
  private DAO_Reclamo dao=new DAO_Reclamo();

  private ArrayList<Reclamo> listaPendientes;
  private ArrayList<Reclamo> listaAnuladas;
  private ArrayList<Reclamo> listaVisitadas;
  private ArrayList<DAO_infoService> filtrado; 
  private Reclamo reclamoAdd=new Reclamo();
  
  
  private ArrayList<Contrato> listaSinVisitar;
  
  private Date fechaIni;
  private Date fechaFin;
  private DAO_infoService infoServiceSelected;
  private Reclamo reclamoSelected;
  
  
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
    
    if(reclamoAdd.getCliente()!=null){ 
    	reclamoAdd.setNombreCliente(reclamoAdd.getCliente().getNombre());
    } 
     System.out.println(">>>>reclamoaddcontrato "+reclamoAdd.getContrato());
    if(reclamoAdd.getContrato()!=null){
      reclamoAdd.setCodigo(reclamoAdd.getContrato().getTipo()+reclamoAdd.getContrato().getContratoID()); 
    } 
    reclamoAdd.setMyrEstado("pen");
    reclamoAdd.setFuncionario(null);
    reclamoAdd.setEstado("pendiente");
    
    if (dao.add (reclamoAdd)){
      salida= "/paginas/reclamos.xhtml?faces-redirect=true";
      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Agregado", "Se agrego el reclamo "+reclamoAdd.getId());
      FacesContext.getCurrentInstance().addMessage(null, message);
      this.listaPendientes.add(reclamoAdd);
      this.lista.add(reclamoAdd);
    }
    else{
      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", " No se pudo agregar el reclamo");
          FacesContext.getCurrentInstance().addMessage(null, message);
    }
    System.out.println(">>AGREGAR"+reclamoAdd.getId() );
    //recargar ();
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
 
 
  public void onRowReclamoSelect(){
  	
  }
  
  public void editReclamo (RowEditEvent event) {
    
    Reclamo o= (Reclamo) event.getObject();
    
    System.out.println("codigo de reclamo: "+o.getCodigo()+" - funcioario"+o.getFuncionario());
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


  public void setList(List<Boolean> list) {
    this.list = list;
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


 
  public ArrayList<Contrato> getListaSinVisitar() {
    return listaSinVisitar;
  }

  public void setListaSinVisitar(ArrayList<Contrato> listaSinVisitar) {
    this.listaSinVisitar = listaSinVisitar;
  }

	public Reclamo getReclamoSelected() {
		return reclamoSelected;
	}

	public void setReclamoSelected(Reclamo reclamoSelected) {
		this.reclamoSelected = reclamoSelected;
	}

	public Reclamo getReclamoAdd() {
		return reclamoAdd;
	}

	public void setReclamoAdd(Reclamo reclamoAdd) {
		this.reclamoAdd = reclamoAdd;
	} 
  
}
