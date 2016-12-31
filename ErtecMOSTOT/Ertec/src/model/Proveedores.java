package model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the Proveedores database table.
 * 
 */
@Entity
@NamedQuery(name="Proveedores.findAll", query="SELECT p FROM Proveedores p")

@SequenceGenerator(name = "PRO_SEQ", sequenceName = "PROVEEDOR_SEQ", initialValue = 97, allocationSize = 1)


public class Proveedores implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRO_SEQ")	
	@Column(name="ProveedorID")
	private int proveedorID;

	@Column(name="Direccion")
	private String direccion;

	@Column(name="Email")
	private String email;

	@Column(name="Fax")
	private int fax;

	@Column(name="Nombre")
	private String nombre;

	@Column(name="Observacion")
	private String observacion;

	@Column(name="RUC")
	private BigDecimal ruc;

	@Column(name="Telefono")
	private int telefono;

	public Proveedores() {
	}

	public int getProveedorID() {
		return this.proveedorID;
	}

	public void setProveedorID(int proveedorID) {
		this.proveedorID = proveedorID;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getFax() {
		return this.fax;
	}

	public void setFax(int fax) {
		this.fax = fax;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getObservacion() {
		return this.observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public BigDecimal getRuc() {
		return this.ruc;
	}

	public void setRuc(BigDecimal ruc) {
		this.ruc = ruc;
	}

	public int getTelefono() {
		return this.telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

}