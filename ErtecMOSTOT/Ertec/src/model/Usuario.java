package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Usuario database table.
 * 
 */
@Entity
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="Nombre")
	private String nombre;

	private String admin;

	private String articulos;

	@Column(name="Clave")
	private String clave;

	private String clientes;

	private String email;

	private String funcionarios;

	private String manodeobra;

	private String movimientos;

	private String ot;

	private String proveedores;

	@Column(name="PuedeActualizar")
	private int puedeActualizar;

	@Column(name="PuedeBorrar")
	private int puedeBorrar;

	private String reclamos;

	private int verFichaPersonal;

	public Usuario() {
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getAdmin() {
		return this.admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public String getArticulos() {
		return this.articulos;
	}

	public void setArticulos(String articulos) {
		this.articulos = articulos;
	}

	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getClientes() {
		return this.clientes;
	}

	public void setClientes(String clientes) {
		this.clientes = clientes;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFuncionarios() {
		return this.funcionarios;
	}

	public void setFuncionarios(String funcionarios) {
		this.funcionarios = funcionarios;
	}

	public String getManodeobra() {
		return this.manodeobra;
	}

	public void setManodeobra(String manodeobra) {
		this.manodeobra = manodeobra;
	}

	public String getMovimientos() {
		return this.movimientos;
	}

	public void setMovimientos(String movimientos) {
		this.movimientos = movimientos;
	}

	public String getOt() {
		return this.ot;
	}

	public void setOt(String ot) {
		this.ot = ot;
	}

	public String getProveedores() {
		return this.proveedores;
	}

	public void setProveedores(String proveedores) {
		this.proveedores = proveedores;
	}

	public int getPuedeActualizar() {
		return this.puedeActualizar;
	}

	public void setPuedeActualizar(int puedeActualizar) {
		this.puedeActualizar = puedeActualizar;
	}

	public int getPuedeBorrar() {
		return this.puedeBorrar;
	}

	public void setPuedeBorrar(int puedeBorrar) {
		this.puedeBorrar = puedeBorrar;
	}

	public String getReclamos() {
		return this.reclamos;
	}

	public void setReclamos(String reclamos) {
		this.reclamos = reclamos;
	}

	public int getVerFichaPersonal() {
		return this.verFichaPersonal;
	}

	public void setVerFichaPersonal(int verFichaPersonal) {
		this.verFichaPersonal = verFichaPersonal;
	}

}