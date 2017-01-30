package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the DatosGlobales database table.
 * 
 */
@Entity
@Table(name="DatosGlobales")
@NamedQuery(name="DatosGlobale.findAll", query="SELECT d FROM DatosGlobale d")
public class DatosGlobale implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name="Arrendamiento_Seq")
	private int arrendamiento_Seq;

	@Column(name="Mantenimiento_Seq")
	private int mantenimiento_Seq;

	public DatosGlobale() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getArrendamiento_Seq() {
		return this.arrendamiento_Seq;
	}

	public void setArrendamiento_Seq(int arrendamiento_Seq) {
		this.arrendamiento_Seq = arrendamiento_Seq;
	}

	public int getMantenimiento_Seq() {
		return this.mantenimiento_Seq;
	}

	public void setMantenimiento_Seq(int mantenimiento_Seq) {
		this.mantenimiento_Seq = mantenimiento_Seq;
	}

}