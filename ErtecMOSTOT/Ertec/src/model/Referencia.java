package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Referencias database table.
 * 
 */
@Entity
@Table(name="Referencias")
@NamedQuery(name="Referencia.findAll", query="SELECT r FROM Referencia r")
public class Referencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ReferenciaID")
	private int referenciaID;

	@Column(name="CodigoMovimientoID")
	private int codigoMovimientoID;

	@Column(name="Descripcion")
	private String descripcion;

	public Referencia() {
	}

	public int getReferenciaID() {
		return this.referenciaID;
	}

	public void setReferenciaID(int referenciaID) {
		this.referenciaID = referenciaID;
	}

	public int getCodigoMovimientoID() {
		return this.codigoMovimientoID;
	}

	public void setCodigoMovimientoID(int codigoMovimientoID) {
		this.codigoMovimientoID = codigoMovimientoID;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}