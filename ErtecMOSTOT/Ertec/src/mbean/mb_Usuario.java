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
import model.DAO.DAO_Usuario;
import usuario.UsuarioLogin;

import javax.faces.bean.SessionScoped;
import util.JpaUtil;

@ManagedBean
@SessionScoped
public class mb_Usuario {

	@Inject
	private UsuarioLogin usuario;
	
	private String nombre;
	private String pass;
	private boolean puedeActualizar;
	private boolean puedeBorrar;
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
	
	private DAO_Usuario dao=new DAO_Usuario();
	
	private  List<Boolean> viewFuncionarios;
	private ArrayList<Usuario> lista;
	
  @PostConstruct  
  public void init(){
    initViewFuncionarios();
    recargarLista();
  }
	
  public String onIdle() {
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
                                    "Sin actividad.", "Deberas loguearte nuevamente"));
    return "/paginas/login.xhtml?faces-redirect=true"; 
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
  
	
	public String add(){
		String salida=null;
		Usuario u=new Usuario(); 
    u.setAdmin(admin.toString());
    u.setArticulos(articulos.toString());
    u.setClave(pass.toString());
    u.setClientes(clientes.toString());
    u.setFuncionarios(funcionarios.toString());
    u.setManodeobra(manodeobra.toString());
    u.setMovimientos(movimientos.toString());
    u.setNombre(nombre);
    u.setOt(ot.toString());
    u.setProveedores(proveedores.toString());
    u.setReclamos(reclamos.toString());
    u.setEmail(email);
    
    
		if (dao.add(u)){
			salida= "/paginas/usuarios.xhtml?faces-redirect=true";
		}
		else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Agregado", "El nombre ya existe"+u.getNombre());
	    FacesContext.getCurrentInstance().addMessage(null, message);
		} 
		recargarLista ();
		return salida;
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
  			this.actualizarAccesos(u); 
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
  
  
 


  public String loguear(){
		System.out.println("login>"+nombre);
		Usuario u=DAO_Usuario.login(nombre, pass);
			
		if (u!=null){
			RequestContext context = RequestContext.getCurrentInstance();
			context.addCallbackParam("loggedIn", true);
			actualizarAccesos(u);
			return "/paginas/reclamos.xhtml?faces-redirect=true";
		}
		else{
			
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error Ingreso","Corrija la contraseña o el usuario ");
			context.addMessage(null,mensaje);
		}
			
		
//		if (nombre.equals("ertec")&& pass.equals("123") ){
//			usuario.setNombre(nombre);
//			return "/paginas/funcionarios.xhtml?faces-redirect=true";
//		}
//		else{
//			
//			FacesContext context = FacesContext.getCurrentInstance();
//			FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error Ingreso","Corrija la contraseña o el usuario ");
//			context.addMessage(null,mensaje);
//		}
		return null;
		
	}
  
  
  private void actualizarAccesos(Usuario u){
  	System.out.println("+++"+u.getPuedeActualizar());
		usuario.setNombre(nombre);
		if (u.getPuedeActualizar()==1){
			puedeActualizar=true;
		}
		else{
			puedeActualizar=false;
		}
		if (u.getPuedeBorrar()==1){
			puedeBorrar=true;
		}
		else{
			puedeBorrar=false;
		} 
		
		articulos = EnumAccesoPagina.valueOf(u.getArticulos());		
		clientes= EnumAccesoPagina.valueOf(u.getClientes()); 
		funcionarios = EnumAccesoPagina.valueOf(u.getFuncionarios());
		movimientos= EnumAccesoPagina.valueOf(u.getMovimientos());
		ot= EnumAccesoPagina.valueOf(u.getOt());
		proveedores= EnumAccesoPagina.valueOf(u.getProveedores());
		admin= EnumAccesoPagina.valueOf(u.getAdmin());			
		manodeobra=EnumAccesoPagina.valueOf(u.getManodeobra());
		reclamos=EnumAccesoPagina.valueOf(u.getReclamos()); 
	}
  
  

	public String logout (){
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		
		
		
//		
//			HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
//				.getExternalContext().getSession(false);
//				session.invalidate();
				this.usuario.setNombre(null);
					return "/login.xhtml?faces-redirect=true";
	}




	public UsuarioLogin getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioLogin usuario) {
		this.usuario = usuario;
	}

	public void setPuedeActualizar(boolean puedeActualizar) {
		this.puedeActualizar = puedeActualizar;
	}

	public void setPuedeBorrar(boolean puedeBorrar) {
		this.puedeBorrar = puedeBorrar;
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

	public boolean getPuedeActualizar() {
		return puedeActualizar;
	}

	public boolean getPuedeBorrar() {
		return puedeBorrar;
	}


	public boolean acceso(String nivel,String acceso) {
		EnumAccesoPagina acc=EnumAccesoPagina.valueOf(acceso);
		EnumAccesoPagina niv=EnumAccesoPagina.valueOf(nivel);	
		int comp=acc.compareTo(niv);
		//System.out.println(">>>acc "+nivel+" "+acceso+" "+comp);
		return comp>=0;
	}


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
	
	
	
	
	
	
	
	
	
	
	
}
