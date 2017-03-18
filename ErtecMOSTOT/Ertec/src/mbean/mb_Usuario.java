package mbean;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;

import enumerados.EnumAccesoPagina;
import model.Funcionario;
import model.Usuario;
import model.UsuarioLogin;
import model.DAO.DAO_Funcionario;
import model.DAO.DAO_Usuario;

import javax.faces.bean.SessionScoped;
import util.JpaUtil;

@ManagedBean
@SessionScoped
public class mb_Usuario {

	@Inject
	private UsuarioLogin usuarioLogueado;
	
	private String nombre;
	private String pass;
	private EnumAccesoPagina articulos; 
	private EnumAccesoPagina clientes; 
	private EnumAccesoPagina funcionarios;
	private EnumAccesoPagina movimientos;
	private EnumAccesoPagina ot;
	private EnumAccesoPagina proveedores;
	private EnumAccesoPagina reclamos;
	private EnumAccesoPagina manodeobra;
	private EnumAccesoPagina admin; 
	private String email;
	private String claveVieja;
	private String claveNueva;
	private Usuario auxUser=new Usuario();
	
/***************Seccion  logueado********************/
	
	//se usa cuando el usuario se loguea o cuando se modifican sus accesos
	private void actualizarAccesosLogueado(Usuario u){
		 email=u.getEmail();
		 usuarioLogueado.actualizarAccesos(u);
	}
	
	public boolean  acceso(String nivelRequerido,String recurso){
		return this.usuarioLogueado.acceso(nivelRequerido,recurso); 
	} 
	
	public String loguear(){
		System.out.println("login>"+nombre);
		Usuario u=DAO_Usuario.login(nombre, pass);		
		if (u!=null){
			RequestContext context = RequestContext.getCurrentInstance();
			context.addCallbackParam("loggedIn", true);
			actualizarAccesosLogueado(u);
			return this.usuarioLogueado.getPaginaReingreso();
			//return "/paginas/reclamos.xhtml?faces-redirect=true";//cambiar a una pagina standar
		}
		else{			
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error Ingreso","Corrija la contraseña o el usuario ");
				context.addMessage(null,mensaje);
		}
		return null;		
	}
	  
	public String logout (){
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		
//			HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
//				.getExternalContext().getSession(false);
//				session.invalidate();
		this.usuarioLogueado.setNombre(null);
		return "/login.xhtml?faces-redirect=true";
	}
	
	
	
	
	
	
	
	
	private DAO_Usuario dao=new DAO_Usuario();
	
	private  List<Boolean> viewFuncionarios;
	private ArrayList<Usuario> lista;

	private ArrayList<Funcionario> listaFuncionariosOBJ;
	
  @PostConstruct  
  public void init(){
    initViewFuncionarios();
    recargarLista();
  }
	
  public void onIdle() {
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
                                    "Sin actividad.", "Deberas loguearte nuevamente"));
    return ;//"/paginas/login.xhtml?faces-redirect=true"; 
}

  public String addUsuario(){
  	String salida=null;
  	if (dao.add(this.auxUser)){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Agregado", "Se agrego "+auxUser.getNombre());
	    FacesContext.getCurrentInstance().addMessage(null, message);
			salida=  "/paginas/usuarios.xhtml?faces-redirect=true";
		}
		else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo agregar intente con otro nombre de usuario");
	    FacesContext.getCurrentInstance().addMessage(null, message);
		}
		System.out.println(">>AGREGAR"+auxUser.getNombre());
		recargarLista ();
		auxUser=new Usuario();
		return salida;
  }
  
	public void onActive() {
	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
	                                    "Bienvenido nuevamente", "Logueate por favor"));
	}

	private void initViewFuncionarios(){
	  viewFuncionarios=Arrays.asList(true, true, true, true, false,false, false, false, true, false, false,
	      false, false, false,false   ,false,false);
	}	
	
  public List<Boolean> getViewFuncionarios() {
    return viewFuncionarios;
  }  
  
  public void setViewFuncionarios(List<Boolean> viewFuncionarios) {
    this.viewFuncionarios = viewFuncionarios;
  }
  
  public void onToggleFuncionarios(ToggleEvent e) {
    viewFuncionarios.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
  }  
 
  
	public void delete(Usuario f){ 
		if (dao.delete(f) ){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Borrado", "Se elimino el funcionario "+f.getNombre());
	        FacesContext.getCurrentInstance().addMessage(null, message);			
		}
		else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error no se elimino el funcionario "+f.getNombre());
	        FacesContext.getCurrentInstance().addMessage(null, message);			
		}
    recargarLista ();
	}
  
  public boolean igualNombre(String nom){
  	System.out.println(">>>igualnombre: "+nombre+" "+nom);
  	return nom.toUpperCase().equals(this.nombre.toUpperCase());
  }
  
	public void onRowEdit(RowEditEvent event) {
		
		Usuario u= (Usuario) event.getObject();
		
		System.out.println("entro en usuario update");
  	if(dao.update(u)){    		 
  		if(u.getNombre().toUpperCase().equals(this.nombre.toUpperCase())){
  			this.actualizarAccesosLogueado(u); 
  			System.out.println("verclientOnrowEdit: "+clientes);
  		}
  		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizado", "Se actualizo el Usuario "+u.getNombre());
          FacesContext.getCurrentInstance().addMessage(null, message);           
  	}
  	else{
  		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo actualizar");
          FacesContext.getCurrentInstance().addMessage(null, message);           
  	}  	
      
  }
  
  public void recargarLista(){
  	this.lista=dao.getLista();
  }
 
  public String updateClave(){
  	if(dao.updateClave(this.usuarioLogueado, claveVieja, claveNueva,email)){
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizada","Los datos se han modificado");
			context.addMessage(null,mensaje);
			this.nombre="";
			this.claveVieja="";
			this.claveNueva="";
			return "/paginas/usuarios.xhtml?faces-redirect=true";
  	}
  	else{
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error","No se pudo actualizar los datos");
			context.addMessage(null,mensaje);
  	}
  	return null;
  }

 
  
  
  






	public UsuarioLogin getUsuario() {
		return usuarioLogueado;
	}

	public void setUsuario(UsuarioLogin usuario) {
		this.usuarioLogueado = usuario;
	}


	
	

	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getPass() {
		return pass;
	}


	public void setPass(String pass) {
		this.pass = pass;
	}



	

	//se le pasa el nivel que se pretende y luego el acceso que tiene el usuario logueado
	//se puede mejorar usando loginUser como el logueado actual

	


	public EnumAccesoPagina getArticulos() {
		return articulos;
	}


	public void setArticulos(EnumAccesoPagina articulos) {
		this.articulos = articulos;
	}


	public EnumAccesoPagina getClientes() {
		return clientes;
	}


	public void setClientes(EnumAccesoPagina clientes) {
		this.clientes = clientes;
	}


	public EnumAccesoPagina getFuncionarios() {
		return funcionarios;
	}


	public void setFuncionarios(EnumAccesoPagina funcionarios) {
		this.funcionarios = funcionarios;
	}


	public EnumAccesoPagina getMovimientos() {
		return movimientos;
	}


	public void setMovimientos(EnumAccesoPagina movimientos) {
		this.movimientos = movimientos;
	}


	public EnumAccesoPagina getOt() {
		return ot;
	}


	public void setOt(EnumAccesoPagina ot) {
		this.ot = ot;
	}


	public EnumAccesoPagina getProveedores() {
		return proveedores;
	}


	public void setProveedores(EnumAccesoPagina proveedores) {
		this.proveedores = proveedores;
	}


	public EnumAccesoPagina getAdmin() {
		return admin;
	}


	public void setAdmin(EnumAccesoPagina admin) {
		this.admin = admin;
	}


	public EnumAccesoPagina getReclamos() {
		return reclamos;
	}


	public void setReclamos(EnumAccesoPagina reclamos) {
		this.reclamos = reclamos;
	}


 


	public EnumAccesoPagina getManodeobra() {
		return manodeobra;
	}


	public void setManodeobra(EnumAccesoPagina manodeobra) {
		this.manodeobra = manodeobra;
	}


	public ArrayList<Usuario> getLista() {
		return lista;
	}


	public void setLista(ArrayList<Usuario> lista) {
		this.lista = lista;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	
	public ArrayList <Funcionario> completarFuncionario(String query){
		this.listaFuncionariosOBJ=DAO_Funcionario.completarFuncionario(query);
		return listaFuncionariosOBJ;
	}

	public ArrayList<Funcionario> getListaFuncionariosOBJ() {
		return listaFuncionariosOBJ;
	}

	public void setListaFuncionariosOBJ(ArrayList<Funcionario> listaFuncionariosOBJ) {
		this.listaFuncionariosOBJ = listaFuncionariosOBJ;
	}

	public Usuario getAuxUser() {
		return auxUser;
	}

	public void setAuxUser(Usuario auxUser) {
		this.auxUser = auxUser;
	}

	public String getClaveVieja() {
		return claveVieja;
	}

	public void setClaveVieja(String claveVieja) {
		this.claveVieja = claveVieja;
	}

	public String getClaveNueva() {
		return claveNueva;
	}

	public void setClaveNueva(String claveNueva) {
		this.claveNueva = claveNueva;
	}


	
	
	
	
	
	
}
