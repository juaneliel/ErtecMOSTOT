package model;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.Column;

import enumerados.EnumAccesoPagina;

@Named
@SessionScoped
public class UsuarioLogin implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nombre=null;  
	private EnumAccesoPagina accesoArticulos; 
	private EnumAccesoPagina accesoClientes; 
	private EnumAccesoPagina accesoFuncionarios;
	private EnumAccesoPagina accesoMovimientos;
	private EnumAccesoPagina accesoOt;
	private EnumAccesoPagina accesoProveedores;
	private EnumAccesoPagina accesoReclamos;
	private EnumAccesoPagina accesoManodeobra;
	private EnumAccesoPagina accesoAdmin; 
	private EnumAccesoPagina accesoFichaPersonal; 	
	private String paginaReingreso="/paginas/bienvenido.xhtml?faces-redirect=true";
	
	public boolean estaLogueado(){
		return this.nombre!=null;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void actualizarAccesos(Usuario u){
		this.nombre=u.getNombre();
  	System.out.println("entro en actualizaracceso de userlogin"+u.getNombre());		
  	accesoAdmin= EnumAccesoPagina.valueOf(u.getAccesoAdmin());	
  	accesoArticulos = EnumAccesoPagina.valueOf(u.getAccesoArticulos());		
  	accesoClientes= EnumAccesoPagina.valueOf(u.getAccesoClientes()); 
  	accesoFichaPersonal=EnumAccesoPagina.valueOf(u.getAccesoFichaPersonal());
  	accesoFuncionarios = EnumAccesoPagina.valueOf(u.getAccesoFuncionarios());
  	accesoManodeobra=EnumAccesoPagina.valueOf(u.getAccesoManodeobra());
  	accesoMovimientos= EnumAccesoPagina.valueOf(u.getAccesoMovimientos());
  	accesoOt= EnumAccesoPagina.valueOf(u.getAccesoOt());
  	accesoProveedores= EnumAccesoPagina.valueOf(u.getAccesoProveedores());  	
  	accesoReclamos=EnumAccesoPagina.valueOf(u.getAccesoReclamos());  
	}
  
	public boolean acceso(String nivelRequerido,String recurso) {
		EnumAccesoPagina acceso;
		//("acceso recurso usuarioLogin: "+recurso);
     switch (recurso) {
       case "admin":
         acceso = this.getAccesoAdmin();
         break;
         case "articulo":
             acceso = this.getAccesoArticulos();
             break; 
         case "cliente":
           acceso = this.getAccesoClientes();
           break;
         case "fichapersonal":
           acceso = this.getAccesoFichaPersonal();
           break;  
         case "funcionario":
           acceso = this.getAccesoFuncionarios();
           break;
         case "manodeobra":
           acceso = this.getAccesoManodeobra();
           break;
         case "movimiento":
           acceso = this.getAccesoMovimientos();
           break;
         case "ot":
           acceso = this.getAccesoOt();
           break;
         case "proveedor":
           acceso = this.getAccesoProveedores();
           break;
         case "reclamo":
           acceso = this.getAccesoReclamos();
           break;

         default:
             throw new IllegalArgumentException("no encontro el acceso");
     } 
		 
		EnumAccesoPagina niv=EnumAccesoPagina.valueOf(nivelRequerido);	
		int comp=acceso.compareTo(niv);
		//System.out.println(">>>acc "+nivel+" "+acceso+" "+comp);
		return comp>=0;
	}

	public boolean accesoVerUrl(String verUrl){
		String url=verUrl.toLowerCase();
		if(url.contains("admin")){
			return this.acceso("VER", "admin");
		}
		if(url.contains("articulo")){
			return this.acceso("VER", "articulo");
		}
		if(url.contains("cliente")){
			return this.acceso("VER", "cliente");
		}
		if(url.contains("ficha")){
			return this.acceso("VER", "ficha");
		}
		if(url.contains("funcionario")){
			return this.acceso("VER", "funcionario");
		}
		if(url.contains("mano")){
			return this.acceso("VER", "manodeobra");
		}
		if(url.contains("movimiento")){
			return this.acceso("VER", "movimiento");
		}
		if(url.contains("ot")){
			return this.acceso("VER", "ot");
		}
		if(url.contains("proveedor")){
			return this.acceso("VER", "proveedor");
		}
		if(url.contains("reclamo")){
			return this.acceso("VER", "reclamo");
		} 
		return true;
	}
	
	public EnumAccesoPagina getAccesoArticulos() {
		return accesoArticulos;
	}

	public void setAccesoArticulos(EnumAccesoPagina accesoArticulos) {
		this.accesoArticulos = accesoArticulos;
	}

	public EnumAccesoPagina getAccesoClientes() {
		return accesoClientes;
	}

	public void setAccesoClientes(EnumAccesoPagina accesoClientes) {
		this.accesoClientes = accesoClientes;
	}

	public EnumAccesoPagina getAccesoFuncionarios() {
		return accesoFuncionarios;
	}

	public void setAccesoFuncionarios(EnumAccesoPagina accesoFuncionarios) {
		this.accesoFuncionarios = accesoFuncionarios;
	}

	public EnumAccesoPagina getAccesoMovimientos() {
		return accesoMovimientos;
	}

	public void setAccesoMovimientos(EnumAccesoPagina accesoMovimientos) {
		this.accesoMovimientos = accesoMovimientos;
	}

	public EnumAccesoPagina getAccesoOt() {
		return accesoOt;
	}

	public void setAccesoOt(EnumAccesoPagina accesoOt) {
		this.accesoOt = accesoOt;
	}

	public EnumAccesoPagina getAccesoProveedores() {
		return accesoProveedores;
	}

	public void setAccesoProveedores(EnumAccesoPagina accesoProveedores) {
		this.accesoProveedores = accesoProveedores;
	}

	public EnumAccesoPagina getAccesoReclamos() {
		return accesoReclamos;
	}

	public void setAccesoReclamos(EnumAccesoPagina accesoReclamos) {
		this.accesoReclamos = accesoReclamos;
	}

	public EnumAccesoPagina getAccesoManodeobra() {
		return accesoManodeobra;
	}

	public void setAccesoManodeobra(EnumAccesoPagina accesoManodeobra) {
		this.accesoManodeobra = accesoManodeobra;
	}

	public EnumAccesoPagina getAccesoAdmin() {
		return accesoAdmin;
	}

	public void setAccesoAdmin(EnumAccesoPagina accesoAdmin) {
		this.accesoAdmin = accesoAdmin;
	}

	public EnumAccesoPagina getAccesoFichaPersonal() {
		return accesoFichaPersonal;
	}

	public void setAccesoFichaPersonal(EnumAccesoPagina accesoFichaPersonal) {
		this.accesoFichaPersonal = accesoFichaPersonal;
	}

	public String getPaginaReingreso() {
		System.out.println("paginaReingreso get :"+paginaReingreso);
		return paginaReingreso;
	}

	public void setPaginaReingreso(String paginaReingreso) {
		this.paginaReingreso = paginaReingreso;
	}

 



 

	 
	
	
	
	
}
