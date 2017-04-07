package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Adicionales database table.
 * 
 */
@Entity
@Table(name="Adicionales")
@NamedQuery(name="Adicional.findAll", query="SELECT a FROM Adicional a")

@SequenceGenerator(name = "ADI_SEQ", sequenceName = "ADI_SEQ", initialValue = 1, allocationSize = 1)


public class Adicional implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADI_SEQ")
	@Column(name="AdicionalID")
	private int adicionalID;

	@Column(name="Especificacion")
	private String especificacion;
 
	public Adicional() {
	}

	public int getAdicionalID() {
		return this.adicionalID;
	}

	public void setAdicionalID(int adicionalID) {
		this.adicionalID = adicionalID;
	}

	public String getEspecificacion() {
		return this.especificacion;
	}

	public void setEspecificacion(String especificacion) {
		this.especificacion = especificacion;
	} 

}