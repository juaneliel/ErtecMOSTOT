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

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;

import model.Articulo;
import model.Cliente;
import model.Contrato;
import model.Funcionario;
import model.Reclamo;
import model.VentaContado;
import model.DAO.DAO_Cliente;
import model.DAO.DAO_Reclamo;
import model.DAO.DAO_infoService;

@ManagedBean(name="mb_Reclamo", eager = true)
@ViewScoped

public class mb_Reclamo {

  private List<Boolean> list;
  private ArrayList<Reclamo> lista;
  private DAO_Reclamo dao=new DAO_Reclamo();
private int tipocliente=0;
  private ArrayList<Reclamo> listaPendientes;
  private ArrayList<Reclamo> listaAnuladas;
  private ArrayList<Reclamo> listaVisitadas;
  private ArrayList<DAO_infoService> filtrado; 
  private Reclamo reclamoAdd=new Reclamo();
  private VentaContado vcAdd=new VentaContado();
  private VentaContado vcSelected=new VentaContado();
  private ArrayList <VentaContado> listavc=new ArrayList <VentaContado>();
  private boolean habEdiRec;
  
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
  
  public void initDetalleReclamo(){
  	habEdiRec=false;    
  }
  
  public void initAddReclamo(){
  	this.reclamoAdd.setFechaReclamado(new Date());
  	mb_Usuario service = (mb_Usuario) 
    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mb_Usuario"); 
  	this.reclamoAdd.setContacto(service.getUsuarioLogueado().getNombre());
  }
  
  public void recargar() {
  	recargarvc();
  	recargarpendientes();
    this.filtrado=dao.filtrarInformeVisitadosPorFechas(this.fechaIni, this.fechaFin, true);
    this.lista=dao.getLista();
    this.listaSinVisitar=dao.filtrarSinVisitar(this.fechaIni, this.fechaFin,true);
    System.out.println("mb tamaño reclamos "+lista.size());
  } 
  
  public String recargarvc(){
    this.listavc=dao.getListaVC();
    System.out.println("tamano listavc "+listavc.size());
  	return "/paginas/ventacontado.xhtml?faces-redirect=true"; 
  }
  
  public String recargarpendientes(){
    this.listaPendientes=dao.getListaPendientes();
  	return "/paginas/reclamospendientes.xhtml?faces-redirect=true"; 
  }
  
  
  
  public void updateReclamoSelected(){
  	dao.update(this.reclamoSelected);
  }
  
  public void actualizarVC(){
  	
  }
  
  public void filtrarInformeVisitadosPorFechas(){
    filtrado=dao.filtrarInformeVisitadosPorFechas(this.fechaIni, this.fechaFin, true);
  }
  
  public void filtrarSinVisitar(){
    this.listaSinVisitar=dao.filtrarSinVisitar(this.fechaIni, this.fechaFin,true);
  }
  
  public void addvc(){
  	if (dao.addvc (vcAdd)){
      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Agregado", "Se agrego la venta contado "+vcAdd.getId());
      FacesContext.getCurrentInstance().addMessage(null, message);
      this.listavc.add(vcAdd); 
    }
    else{
      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", " No se pudo agregar el reclamo");
          FacesContext.getCurrentInstance().addMessage(null, message);
    }
  }
  
  public void deletevc(VentaContado vc){ 
		if (dao.deletevc(vc) ){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Borrado", "Se elimino la venta contado "+vc.getId());
      FacesContext.getCurrentInstance().addMessage(null, message);	
      listavc.remove(vc);
      //usado para recargar cuando se borra filtrado
      DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
          .findComponent("formvc:datatablevc");
      if (dataTable != null) {
      	dataTable.reset();
      }
		}
		else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se puede eliminar la venta contado");
	        FacesContext.getCurrentInstance().addMessage(null, message);			
		}
		//return "/paginas/funcionarios.xhtml?faces-redirect=true";
        
	}
  
  public String add(){
    String salida=null;    
    if(tipocliente==1){ 
    	reclamoAdd.setNombreCliente(reclamoAdd.getCliente().getNombre());
    	if(reclamoAdd.getContrato()!=null){
        reclamoAdd.setCodigo(reclamoAdd.getContrato().getTipo()+reclamoAdd.getContrato().getContratoID()); 
        reclamoAdd.setEquipo(reclamoAdd.getContrato().getEquipo());
    	} 
    }
    else{
    		reclamoAdd.setNombreCliente(reclamoAdd.getVentaContado().getCliente());
    		reclamoAdd.setCodigo(reclamoAdd.getVentaContado().getCodigo());
    		reclamoAdd.setEquipo(reclamoAdd.getVentaContado().getEquipo());
    }
    System.out.println(">>>>reclamoaddcontrato "+reclamoAdd.getContrato());   
    reclamoAdd.setMyrEstado("pen");
    reclamoAdd.setFuncionario(null);
    reclamoAdd.setEstado("pendiente");    
    if (dao.add (reclamoAdd)){
      salida= "/paginas/reclamos.xhtml?faces-redirect=true";
      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Agregado", "Se agrego el reclamo "+reclamoAdd.getId());
      FacesContext.getCurrentInstance().addMessage(null, message);
      this.listaPendientes.add(reclamoAdd);
      this.lista.add(reclamoAdd);
      reclamoAdd=new Reclamo();
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
 
 
  public void editvc (RowEditEvent event) {    
    VentaContado o= (VentaContado) event.getObject();  
    if(dao.updatevc(o)){       
      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizado", "Se actualizo la Venta contado ");
      FacesContext.getCurrentInstance().addMessage(null, message);  
    }
    else{
      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo actualizar");
      FacesContext.getCurrentInstance().addMessage(null, message);           
    }   
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

	public boolean isHabEdiRec() {
		return habEdiRec;
	}

	public void setHabEdiRec(boolean habEdiRec) {
		this.habEdiRec = habEdiRec;
	}

	public int getTipocliente() {
		return tipocliente;
	}

	public void setTipocliente(int tipocliente) {
		this.tipocliente = tipocliente;
	}

	public VentaContado getVcAdd() {
		return vcAdd;
	}

	public void setVcAdd(VentaContado vcAdd) {
		this.vcAdd = vcAdd;
	}

	public VentaContado getVcSelected() {
		return vcSelected;
	}

	public void setVcSelected(VentaContado vcSelected) {
		this.vcSelected = vcSelected;
	}

	public ArrayList<VentaContado> getListavc() {
		return listavc;
	}

	public void setListavc(ArrayList<VentaContado> listavc) {
		this.listavc = listavc;
	}
	
	
}
