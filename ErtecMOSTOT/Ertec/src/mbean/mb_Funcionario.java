package mbean;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream; 
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.wizard.Wizard;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;

import java.util.ArrayList;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean; 
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.servlet.ServletContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.UploadedFile;

import model.ActividadAnterior;
import model.Capacitacion;
import model.Educacion;
import model.FichaPersonal;
import model.Funcionario;
import model.DAO.DAO_Funcionario;


@ManagedBean(name="mb_Funcionario")
@ViewScoped
public class mb_Funcionario {

	private DAO_Funcionario dao=new DAO_Funcionario();
	private ArrayList <Funcionario> lista; 
	private ArrayList<Funcionario> funcionariosHallados=new ArrayList<Funcionario>();
	private String urlImpresion;
	private Funcionario funcionarioOBJ;
	private ArrayList<Funcionario> listaFuncionariosOBJ=new ArrayList<Funcionario>(); 
  private String nombreArchivo="foto.jpg";
	private Funcionario funSelected=new Funcionario();	
	private Funcionario funcionarioAdd=new Funcionario();	
	private FileUploadEvent eventUpload;
	private Educacion educacion=new Educacion();
	private Capacitacion capacitacion=new Capacitacion();
	private ActividadAnterior actividadAnterior=new ActividadAnterior();
	private boolean habEdiFun;
	private UploadedFile file;
	private boolean skip;
	private byte[] foto;
	
	@PostConstruct
	public void init(){
		this.recargarLista ();
		//revisarVencimiento();
		//funcionarioAdd.setFicha(new FichaPersonal());
	}
	
	public void preRender() {
    if (!FacesContext.getCurrentInstance().isPostback()) {
    	habEdiFun=false;    	
    }
	}
	
	private String destination="/archivosFichaPersonal/";
	
  public void upload(FileUploadEvent event) {  
      FacesMessage msg = new FacesMessage("Exito ", event.getFile().getFileName() + " se subio el archivo.");  
      FacesContext.getCurrentInstance().addMessage(null, msg);
      // Do what you want with the file     
      eventUpload=event;
//      this.funSelected.setFoto(eventUpload.getFile().getContents());
//      dao.update(funSelected);
      
      try {
      	//nombreArchivo event.getFile().getFileName()
      	//habria que tomar el final del archivo y agregarselo al nombre
      	
          copyFile(this.nombreArchivo, event.getFile().getInputstream());
      } catch (IOException e) {
          e.printStackTrace();
      }
  }  

	public void generarFicha(){
		dao.generarFicha();
	}
	  
	  
	public void subirArchivoNombrado(){
		
		try {
		//nombreArchivo event.getFile().getFileName()
		//habria que tomar el final del archivo y agregarselo al nombre
		    copyFile(this.nombreArchivo, this.eventUpload.getFile().getInputstream());
		} catch (IOException e) {
		    e.printStackTrace();
		}	
	}
	 
	public void upload(){
	  FacesMessage msg = new FacesMessage("Success! ",file.getFileName() + " is uploaded.");  
	  FacesContext.getCurrentInstance().addMessage(null, msg);
	  // Do what you want with the file    
	  this.funSelected.setFoto(file.getContents());
	}
	
	public void subirArchivo(FileUploadEvent evento){
		foto=evento.getFile().getContents();
	}
	  
  public void copyFile(String fileName, InputStream in) {
  	try {	    
  		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
       .getExternalContext().getContext();
  		String path= ctx.getRealPath("/");    
      // write the inputStream to a FileOutputStream
  		System.out.println("pathcopy "+path);
      //OutputStream out = new FileOutputStream(new File(path + destination + fileName));   
      
  		int index=-1;
  		for (int i=0;i<3;i++){
  			index = path.indexOf("/",index+1); 
  		}   
       
      String pathbase=path.substring(0, index);
      String pathcompleto=pathbase+destination+fileName;
      System.out.println("pathcompleto"+pathcompleto);
  		
  		
  		
  		OutputStream out = new FileOutputStream(new File(pathcompleto));   
      
      
      
      int read = 0;
      byte[] bytes = new byte[1024];    
      while ((read = in.read(bytes)) != -1) {
        out.write(bytes, 0, read);
      }    
      in.close();
      out.flush();
      out.close();    
      System.out.println("Exito se creo un nuevo archivo!");
  	} catch (IOException e) {
     	System.out.println(e.getMessage());
    }
  } 	
  
  //*************Ficha personal*************//
  
  public void agregarEducacion(){
  	this.funcionarioAdd.getEducaciones().add(educacion);
  	this.educacion=new Educacion();
  }
  
  public void borrarEducacion(Educacion edu){
  	this.funcionarioAdd.getEducaciones().remove(edu);
  }
  
  public void agregarActividadAnterior(){  	
  	this.funcionarioAdd.getActividadAnteriores().add(actividadAnterior);
  	actividadAnterior=new ActividadAnterior();
  }
  
  public void borrarActividad(ActividadAnterior aa){
  	this.funcionarioAdd.getActividadAnteriores().remove(aa);
  }
  
  public void agregarCapacitacion(){
  	this.funcionarioAdd.getCapacitaciones().add(this.capacitacion);
  	capacitacion=new Capacitacion();
  }
  
  public void borrarCapacitacion(Capacitacion cap){
  	this.funcionarioAdd.getCapacitaciones().remove(cap);
  }
     
  public void save() {        
      FacesMessage msg = new FacesMessage("Successful", "Welcome ");
      FacesContext.getCurrentInstance().addMessage(null, msg);
  }
   
  public boolean isSkip() {
      return skip;
  }

  public void setSkip(boolean skip) {
      this.skip = skip;
  }
  public String onFlowProcess(FlowEvent event) {
    if(skip) {
        skip = false;   //reset in case user goes back
        return "confirm";
    }
    else {
        return event.getNewStep();
    }
  }
  
	public String urlImprimirFun(){
		//ExportarOTPDF.ExportarPDF(ot, "");
		long funID=0;
		if(this.funSelected!=null){
			funID =this.funSelected.getFuncionarioID();
		} 
		 this.urlImpresion=  "/ertec/exportarpdf?faces-redirect=true"+"&id="+funID+"&tipo=fun";
		 System.out.println("url impresion ficha "+this.urlImpresion);
		 return this.urlImpresion;
	}
	
	public String add(){
		String salida=null;		
//    funcionarioAdd.setCapacitaciones(listaCapacitacion);
//    funcionarioAdd.setActividadAnteriores(listaActividadAnterior);
//    funcionarioAdd.setEducaciones(listaEducacion);
    if (dao.add(funcionarioAdd)){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Agregado", "Se agrego "+funcionarioAdd.getNombre());
	    FacesContext.getCurrentInstance().addMessage(null, message);
	    lista.add(funcionarioAdd); 
	    funcionarioAdd=new Funcionario();
	    //ver si se puede borrar
	    RequestContext context = RequestContext.getCurrentInstance();
	    context.reset("formaddfuncionario");	    
			salida= "/paginas/funcionarios.xhtml?faces-redirect=true";
		}
		else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo agregar"+funcionarioAdd.getNombre());
	    FacesContext.getCurrentInstance().addMessage(null, message);
		}
		System.out.println(">>AGREGAR"+funcionarioAdd.getFuncionarioID());
		//recargarLista ();
		return salida;
	}
	 
	public void onCellEdit(CellEditEvent event) {
     Object oldValue = event.getOldValue();
     Object newValue = event.getNewValue();
      
     if(newValue != null && !newValue.equals(oldValue)) {
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
         FacesContext.getCurrentInstance().addMessage(null, msg);
     }
	} 
	 
	//el usuario selected se usa para cargar en funcionarioAdd los datos de
	//la ficha completa
	public void updateFichaSelected (){
		dao.updateFichaSelected(this.funcionarioAdd);
		int ind=lista.indexOf(funSelected);
		lista.remove(funSelected);
		lista.add(ind,funcionarioAdd);
		this.funcionarioAdd=new Funcionario();		
	}
	  
	 
	public void onRowEdit(RowEditEvent event) {
		Funcionario f= (Funcionario) event.getObject();
		System.out.println("entro en funcionario update");
   	if(dao.update(f)){    		
   		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizado", "Se actualizo el funcionario "+f.getNombre());
      FacesContext.getCurrentInstance().addMessage(null, message); 
            //recargarLista ();
  	}
   	else{
   		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo actualizar");
      FacesContext.getCurrentInstance().addMessage(null, message);           
   	}        
  }
	
	public void delete(Funcionario f){ 
		if (dao.delete(f) ){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Borrado", "Se elimino el funcionario "+f.getNombre());
	    FacesContext.getCurrentInstance().addMessage(null, message);	
	    this.lista.remove(f);
	    DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
          .findComponent("formfuncionarios:datatablefuncionario");
      if (dataTable != null) {
      	dataTable.reset();
      }
		}
		else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error no se elimino el funcionario "+f.getNombre()+" porque esta en uso en otras relaciones");
	        FacesContext.getCurrentInstance().addMessage(null, message);			
		}
		//return "/paginas/funcionarios.xhtml?faces-redirect=true";
    
	}

	//funciones extra
	
	public void recargarLista (){
		this.lista=dao.getListaFuncionarios();
	}

	public void initDetalleFicha (){
		this.habEdiFun=false;
		System.out.println("initDetalleFicha: "+funSelected.getFuncionarioID());
		this.funcionarioAdd=dao.getFichaCompleta(funSelected.getFuncionarioID());
		
	}
	
	public void initAddFun(){
		System.out.println("entro en initaddfun");
		this.funcionarioAdd=new Funcionario();
		Wizard wizard = (Wizard) FacesContext.getCurrentInstance().getViewRoot().findComponent("formaddfuncionario:wiz");
    wizard.setStep("personal");
    RequestContext.getCurrentInstance().update("formaddfuncionario:wiz");
    System.out.println("initaddfun "+wizard+ " "+funcionarioAdd.getNombre());
	}
	//Getter y setter
	
	public ArrayList<Funcionario> getFuncionariosHallados() { 
		return funcionariosHallados;
	}

	public void setFuncionariosHallados(ArrayList<Funcionario> funcionariosHallados) {
		this.funcionariosHallados = funcionariosHallados;
	}

	public ArrayList<Funcionario> getLista() {
		return lista;
	}

	public void setLista(ArrayList<Funcionario> lista) {
		this.lista = lista;
	}

	public Funcionario getFuncionarioOBJ() {
		return funcionarioOBJ;
	}

	public void setFuncionarioOBJ(Funcionario funcionarioOBJ) {
		this.funcionarioOBJ = funcionarioOBJ;
	}

	public ArrayList<Funcionario> getListaFuncionariosOBJ() {
		return listaFuncionariosOBJ;
	}

	public void setListaFuncionariosOBJ(ArrayList<Funcionario> listaFuncionariosOBJ) {
		this.listaFuncionariosOBJ = listaFuncionariosOBJ;
	}

	public Funcionario getFunSelected() {
		return funSelected;
	}

	public void setFunSelected(Funcionario funSelected) {
		this.funSelected = funSelected;
	}

	public String getUrlImpresion() {
		return urlImpresion;
	}

	public void setUrlImpresion(String urlImpresion) {
		this.urlImpresion = urlImpresion;
	}
 

	
//	public void tecleo(){
//	String aux=this.getNombre();
//	System.out.println("Teclou: " + aux);
//	this.limpiarVariables();
//	this.setNombre(aux);
//	this.findFuncionario();
//}


////se seleccionan el valor para hacer update	
//public void selecToUpdateFuncionario(Funcionario f){		
//	//agregar las variables
//	getVariables(f);		
//	//return "/paginas/updatefuncionario.xhtml?faces-redirect=true";
//}
//
////se efectua el update
//public void updateFuncionario(){
//	Funcionario f = setVariables();
//	dao.update(f);
//	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizado", "Se actualizo el funcionario "+f.getNombre());
//    FacesContext.getCurrentInstance().addMessage(null, message);
//    this.findFuncionario();
//    
//	recargarListaFuncionarios();
//	//return "/paginas/funcionarios.xhtml?faces-redirect=true";
//}
//
	
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}



	public FileUploadEvent getEventUpload() {
		return eventUpload;
	}

	public void setEventUpload(FileUploadEvent eventUpload) {
		this.eventUpload = eventUpload;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	} 

	public Educacion getEducacion() {
		return educacion;
	}

	public void setEducacion(Educacion educacion) {
		this.educacion = educacion;
	}

	public Capacitacion getCapacitacion() {
		return capacitacion;
	}

	public void setCapacitacion(Capacitacion capacitacion) {
		this.capacitacion = capacitacion;
	}

	public ActividadAnterior getActividadAnterior() {
		return actividadAnterior;
	}

	public void setActividadAnterior(ActividadAnterior actividadAnterior) {
		this.actividadAnterior = actividadAnterior;
	}

	public boolean isHabEdiFun() {
		return habEdiFun;
	}

	public void setHabEdiFun(boolean habEdiFun) {
		this.habEdiFun = habEdiFun;
	} 
	 public UploadedFile getFile() {
     return file;
 }

 public void setFile(UploadedFile file) {
     this.file = file;
 }

public Funcionario getFuncionarioAdd() {
	return funcionarioAdd;
}

public void setFuncionarioAdd(Funcionario funcionarioAdd) {
	this.funcionarioAdd = funcionarioAdd;
}
	
	
	
}



