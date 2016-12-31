package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Areas database table.
 * 
 */
@Entity
@Table(name="Areas")
@NamedQuery(name="Area.findAll", query="SELECT a FROM Area a")
public class Area implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="AreaID")
	private int areaID;

	@Column(name="Descripcion")
	private String descripcion;

	public Area() {
	}

	public int getAreaID() {
		return this.areaID;
	}

	public void setAreaID(int areaID) {
		this.areaID = areaID;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}