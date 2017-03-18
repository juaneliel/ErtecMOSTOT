package model;

import java.io.Serializable;
import javax.persistence.*;


 
@Entity
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

  public Usuario() {
		super();
	}
	
	@Id 
	private String nombre;

	private String clave;
	
	private String accesoAdmin;

	private String accesoArticulos;

	private String accesoClientes;

	private String email;

	private String accesoFuncionarios;

	private String accesoManodeobra;

	private String accesoMovimientos;

	private String accesoOt;

	private String accesoProveedores;

	private String accesoReclamos;

	private String accesoFichaPersonal;
 
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getAccesoAdmin() {
		return accesoAdmin;
	}

	public void setAccesoAdmin(String accesoAdmin) {
		this.accesoAdmin = accesoAdmin;
	}

	public String getAccesoArticulos() {
		return accesoArticulos;
	}

	public void setAccesoArticulos(String accesoArticulos) {
		this.accesoArticulos = accesoArticulos;
	}

	public String getAccesoClientes() {
		return accesoClientes;
	}

	public void setAccesoClientes(String accesoClientes) {
		this.accesoClientes = accesoClientes;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAccesoFuncionarios() {
		return accesoFuncionarios;
	}

	public void setAccesoFuncionarios(String accesoFuncionarios) {
		this.accesoFuncionarios = accesoFuncionarios;
	}

	public String getAccesoManodeobra() {
		return accesoManodeobra;
	}

	public void setAccesoManodeobra(String accesoManodeobra) {
		this.accesoManodeobra = accesoManodeobra;
	}

	public String getAccesoMovimientos() {
		return accesoMovimientos;
	}

	public void setAccesoMovimientos(String accesoMovimientos) {
		this.accesoMovimientos = accesoMovimientos;
	}	

	public String getAccesoOt() {
		return accesoOt;
	}

	public void setAccesoOt(String accesoOt) {
		this.accesoOt = accesoOt;
	}

	public String getAccesoProveedores() {
		return accesoProveedores;
	}

	public void setAccesoProveedores(String accesoProveedores) {
		this.accesoProveedores = accesoProveedores;
	}

	public String getAccesoReclamos() {
		return accesoReclamos;
	}

	public void setAccesoReclamos(String accesoReclamos) {
		this.accesoReclamos = accesoReclamos;
	}

	public String getAccesoFichaPersonal() {
		return accesoFichaPersonal;
	}

	public void setAccesoFichaPersonal(String accesoFichaPersonal) {
		this.accesoFichaPersonal = accesoFichaPersonal;
	}    
 

}