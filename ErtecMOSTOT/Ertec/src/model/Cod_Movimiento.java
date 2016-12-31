package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Cod_Movimientos database table.
 * 
 */
@Entity
@Table(name="Cod_Movimientos")
@NamedQuery(name="Cod_Movimiento.findAll", query="SELECT c FROM Cod_Movimiento c")
public class Cod_Movimiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CodMov_ID")
	private int codMov_ID;

	@Column(name="Descripcion")
	private String descripcion;

	private int verCliente;

	private int verCosto;

	private int verCotizacion;

	private int verEnOT;

	private int verNoOT;

	private int verReferencia;

	public Cod_Movimiento() {
	}

	public int getCodMov_ID() {
		return this.codMov_ID;
	}

	public void setCodMov_ID(int codMov_ID) {
		this.codMov_ID = codMov_ID;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getVerCliente() {
		return this.verCliente;
	}

	public void setVerCliente(int verCliente) {
		this.verCliente = verCliente;
	}

	public int getVerCosto() {
		return this.verCosto;
	}

	public void setVerCosto(int verCosto) {
		this.verCosto = verCosto;
	}

	public int getVerCotizacion() {
		return this.verCotizacion;
	}

	public void setVerCotizacion(int verCotizacion) {
		this.verCotizacion = verCotizacion;
	}

	public int getVerEnOT() {
		return this.verEnOT;
	}

	public void setVerEnOT(int verEnOT) {
		this.verEnOT = verEnOT;
	}

	public int getVerNoOT() {
		return this.verNoOT;
	}

	public void setVerNoOT(int verNoOT) {
		this.verNoOT = verNoOT;
	}

	public int getVerReferencia() {
		return this.verReferencia;
	}

	public void setVerReferencia(int verReferencia) {
		this.verReferencia = verReferencia;
	}

}