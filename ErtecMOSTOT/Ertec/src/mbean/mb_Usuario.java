package mbean;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.Column;
import javax.persistence.EntityManager;

import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;

import model.Usuario;
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
	
	private  List<Boolean> viewFuncionarios;
	
	
  @PostConstruct  
  public void init(){
    initViewFuncionarios();
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
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
	

  public String loguear(){
		System.out.println("login>"+nombre);
		
		
		try{
			EntityManager em=JpaUtil.getEntityManager(); 
			Usuario u =em.find(Usuario.class,nombre );
			if (u!=null){
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
				
				
				
				return "/paginas/reclamos.xhtml?faces-redirect=true";
			}
			else{
				
				FacesContext context = FacesContext.getCurrentInstance();
				FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error Ingreso","Corrija la contraseña o el usuario ");
				context.addMessage(null,mensaje);
			}
		}
		catch (Exception e){
			e.printStackTrace();
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

	public String logout (){
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		
		return "/paginas/login.xhtml?faces-redirect=true";
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
	
	
	
	
	
	
	
	
	
	
	
}
