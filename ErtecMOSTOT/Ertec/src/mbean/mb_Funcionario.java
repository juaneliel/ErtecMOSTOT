package mbean;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream; 
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext; 
import org.primefaces.event.FileUploadEvent;



import java.util.ArrayList;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean; 
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.servlet.ServletContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.UploadedFile;

import model.Funcionario;
import model.DAO.DAO_Funcionario;


@ManagedBean(name="mb_Funcionario")
@SessionScoped
public class mb_Funcionario {

	private DAO_Funcionario dao=new DAO_Funcionario();

  private int funcionarioID;
  private String activo;
  private String alias;
  private String area;
  private String cat;
  private String direccion;
  private String email;
  private int idEnService;
  private double lat;
  private double lng;
  private String nombre;
  private String telefono;
  private Date carneSalud;
  private Date nacimiento; 
  private String cedula; 
  private Date vencimientoCedula; 
  private String libretaCat;  
  private Date vencimientoLibreta;
	private ArrayList <Funcionario> lista;
	private ArrayList<Funcionario> funcionariosHallados=new ArrayList<Funcionario>();
	
	private Funcionario funcionarioOBJ;
	private ArrayList<Funcionario> listaFuncionariosOBJ=new ArrayList<Funcionario>(); 
	
	private Funcionario funcionarioSelected;
	
	@PostConstruct
	public void init(){
		this.recargarLista ();
		//revisarVencimiento();
	}
	
	private String destination="/resources/fotos/";
	 
  public void upload(FileUploadEvent event) {  
      FacesMessage msg = new FacesMessage("Success! ", event.getFile().getFileName() + " is uploaded.");  
      FacesContext.getCurrentInstance().addMessage(null, msg);
      // Do what you want with the file        
      try {
          copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
      } catch (IOException e) {
          e.printStackTrace();
      }

  }  

	public String urlImprimirFun(int funID){
		//ExportarOTPDF.ExportarPDF(ot, "");
		 String urlImpresion=  "/Ertec/exportarpdf?faces-redirect=true"+"&id="+funID+"&tipo=fun";
		 System.out.println("url impresion ficha "+urlImpresion);
		 return urlImpresion;
	}
	
  
  
  
  public void copyFile(String fileName, InputStream in) {
         try {
            
        	 ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
               .getExternalContext().getContext();
             String path= ctx.getRealPath("/");
            
              // write the inputStream to a FileOutputStream
              OutputStream out = new FileOutputStream(new File(path + destination + fileName));
            
              int read = 0;
              byte[] bytes = new byte[1024];
            
              while ((read = in.read(bytes)) != -1) {
                  out.write(bytes, 0, read);
              }
            
              in.close();
              out.flush();
              out.close();
            
              System.out.println("New file created!");
              } catch (IOException e) {
              System.out.println(e.getMessage());
              }
  } 	
	
	public String add(){
		String salida=null;
    Funcionario f=new Funcionario(); 
    f.setNombre(nombre);
    f.setEmail(email);
    f.setTelefono(telefono);
    f.setActivo(activo);
    f.setAlias(alias);
    f.setArea(area);
    f.setCat(cat);
    f.setDireccion(direccion); 
    f.setNacimiento(nacimiento);
    f.setCarneSalud(carneSalud);
    f.setCedula(cedula);
    f.setVencimientoCedula(vencimientoCedula);
    f.setLibretaCat(libretaCat);
    f.setVencimientoLibreta(vencimientoLibreta);
    
    
		if (dao.add(f)){
			salida= "/paginas/funcionarios.xhtml?faces-redirect=true";
		}
		else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Agregado", "El nombre ya existe"+f.getNombre());
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}
		System.out.println(">>AGREGAR"+f.getFuncionarioID());
		recargarLista ();
		return salida;
	}
	
	public void onRowSelect(){
		
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
		}
		else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error no se elimino el funcionario "+f.getNombre());
	        FacesContext.getCurrentInstance().addMessage(null, message);			
		}
		//return "/paginas/funcionarios.xhtml?faces-redirect=true";
    this.lista.remove(f);
	}
 
	 
	
	
	public void limpiarVariables(){
		this.setNombre("");
		this.setEmail("");
		this.setTelefono("");
		this.setFuncionarioID(0);	
	}
	
	
	
	public void findFuncionario(){
		Funcionario f=this.setVariables();	
		this.funcionariosHallados = dao.findFuncionario(f);
		System.out.println("Funcionarios hallados" + this.funcionariosHallados.size() );
	}

	
	//funciones extra
	
	//se le se setean los valores al objeto funcionario
	private Funcionario setVariables(){
		Funcionario f=new Funcionario();
		f.setFuncionarioID(funcionarioID);
		f.setNombre(nombre);
		f.setEmail(email);
		f.setTelefono(telefono);
		return f;
	}
	
	//se toman los valores del objeto funcionario a las variables del mb
//	private void getVariables(Funcionario f){
//		this.setNombre(f.getNombre());
//		this.setEmail(f.getEmail());
//		this.setTelefono(f.getTelefono());
//		this.setFuncionarioID(f.getFuncionarioID());		
//	}	
	

	
	public void recargarLista (){
		this.lista=dao.getListaFuncionarios();
	}

	

	
	
	public String getPageAdd(){
		this.limpiarVariables();
		return "/paginas/addfuncionarios.xhtml";
	}
	
	public ArrayList <Funcionario> completarFuncionario(String query){
		this.listaFuncionariosOBJ=dao.completarFuncionario(query);
		return listaFuncionariosOBJ;
	}
	
	
	//Getter y setter
	
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getFuncionarioID() {
		return funcionarioID;
	}

	public void setFuncionarioID(int funcionarioID) {
		this.funcionarioID = funcionarioID;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

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


  public String getActivo() {
    return activo;
  }


  public void setActivo(String activo) {
    this.activo = activo;
  }


  public String getAlias() {
    return alias;
  }


  public void setAlias(String alias) {
    this.alias = alias;
  }


  public String getArea() {
    return area;
  }


  public void setArea(String area) {
    this.area = area;
  }


  public String getCat() {
    return cat;
  }


  public void setCat(String cat) {
    this.cat = cat;
  }


  public String getDireccion() {
    return direccion;
  }


  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }


  public int getIdEnService() {
    return idEnService;
  }


  public void setIdEnService(int idEnService) {
    this.idEnService = idEnService;
  }


  public double getLat() {
    return lat;
  }


  public void setLat(double lat) {
    this.lat = lat;
  }


  public double getLng() {
    return lng;
  }


  public void setLng(double lng) {
    this.lng = lng;
  }


  public Date getCarneSalud() {
    return carneSalud;
  }


  public void setCarneSalud(Date carneSalud) {
    this.carneSalud = carneSalud;
  }


  public Date getNacimiento() {
    return nacimiento;
  }


  public void setNacimiento(Date nacimiento) {
    this.nacimiento = nacimiento;
  }


  public String getCedula() {
    return cedula;
  }


  public void setCedula(String cedula) {
    this.cedula = cedula;
  }


  public Date getVencimientoCedula() {
    return vencimientoCedula;
  }


  public void setVencimientoCedula(Date vencimientoCedula) {
    this.vencimientoCedula = vencimientoCedula;
  }


  public String getLibretaCat() {
    return libretaCat;
  }


  public void setLibretaCat(String libretaCat) {
    this.libretaCat = libretaCat;
  }


  public Date getVencimientoLibreta() {
    return vencimientoLibreta;
  }


  public void setVencimientoLibreta(Date vencimientoLibreta) {
    this.vencimientoLibreta = vencimientoLibreta;
  }


	public Funcionario getFuncionarioSelected() {
		return funcionarioSelected;
	}


	public void setFuncionarioSelected(Funcionario funcionarioSelected) {
		this.funcionarioSelected = funcionarioSelected;
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
	
	
	
	
	
}



