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

	@Column(name="Clave")
	private String clave;

	@Column(name="PuedeActualizar")
	private byte puedeActualizar;

	@Column(name="PuedeBorrar")
	private byte puedeBorrar;

	public Usuario() {
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public byte getPuedeActualizar() {
		return this.puedeActualizar;
	}

	public void setPuedeActualizar(byte puedeActualizar) {
		this.puedeActualizar = puedeActualizar;
	}

	public byte getPuedeBorrar() {
		return this.puedeBorrar;
	}

	public void setPuedeBorrar(byte puedeBorrar) {
		this.puedeBorrar = puedeBorrar;
	}

}