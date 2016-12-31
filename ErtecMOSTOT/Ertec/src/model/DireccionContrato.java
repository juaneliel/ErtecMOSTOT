package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the DireccionContrato database table.
 * 
 */
@Entity
@NamedQuery(name="DireccionContrato.findAll", query="SELECT d FROM DireccionContrato d")
public class DireccionContrato implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="Id")
	private int id;

	@Column(name="Direccion")
	private String direccion;

	private int idContrato;

	@Column(name="Telefono")
	private String telefono;

	public DireccionContrato() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public int getIdContrato() {
		return this.idContrato;
	}

	public void setIdContrato(int idContrato) {
		this.idContrato = idContrato;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

}