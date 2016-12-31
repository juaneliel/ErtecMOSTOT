package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the CodigosMO database table.
 * 
 */
@Entity
@NamedQuery(name="CodigosMO.findAll", query="SELECT c FROM CodigosMO c")
public class CodigosMO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="Codigo")
	private int codigo;

	@Column(name="Descripcion")
	private String descripcion;

	@Column(name="Tipo")
	private int tipo;

	private int verCliente;

	private int verMYR;

	private int verNContrato;

	private int verReferencia;

	private int verTContrato;

	private int verTipo;

	public CodigosMO() {
	}

	public int getCodigo() {
		return this.codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getTipo() {
		return this.tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public int getVerCliente() {
		return this.verCliente;
	}

	public void setVerCliente(int verCliente) {
		this.verCliente = verCliente;
	}

	public int getVerMYR() {
		return this.verMYR;
	}

	public void setVerMYR(int verMYR) {
		this.verMYR = verMYR;
	}

	public int getVerNContrato() {
		return this.verNContrato;
	}

	public void setVerNContrato(int verNContrato) {
		this.verNContrato = verNContrato;
	}

	public int getVerReferencia() {
		return this.verReferencia;
	}

	public void setVerReferencia(int verReferencia) {
		this.verReferencia = verReferencia;
	}

	public int getVerTContrato() {
		return this.verTContrato;
	}

	public void setVerTContrato(int verTContrato) {
		this.verTContrato = verTContrato;
	}

	public int getVerTipo() {
		return this.verTipo;
	}

	public void setVerTipo(int verTipo) {
		this.verTipo = verTipo;
	}

}