package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the TipoOT database table.
 * 
 */
@Entity
@NamedQuery(name="TipoOT.findAll", query="SELECT t FROM TipoOT t")
public class TipoOT implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="TipoID")
	private int tipoID;

	@Column(name="Descripcion")
	private String descripcion;

	public TipoOT() {
	}

	public int getTipoID() {
		return this.tipoID;
	}

	public void setTipoID(int tipoID) {
		this.tipoID = tipoID;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}