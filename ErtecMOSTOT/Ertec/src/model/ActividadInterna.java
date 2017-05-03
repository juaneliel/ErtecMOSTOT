package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: ActividadInterna
 *
 */
@Entity
@SequenceGenerator(name = "AI_SEQ", sequenceName = "AI_SEQ", initialValue = 1, allocationSize = 1)

public class ActividadInterna implements Serializable {

	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AI_SEQ") 
	private int actividadInternaID;	
	@Temporal(TemporalType.DATE) 
	private	Date Fecha;
	private String cargo="";
	private String seccion="";
	private String sueldo="";
	private String comision;
	private String otros="";
	private String detalle="";

	
	
	

	public int getActividadInternaID() {
		return actividadInternaID;
	}





	public void setActividadInternaID(int actividadInternaID) {
		this.actividadInternaID = actividadInternaID;
	}





	public Date getFecha() {
		return Fecha;
	}





	public void setFecha(Date fecha) {
		Fecha = fecha;
	}





	public String getCargo() {
		return cargo;
	}





	public void setCargo(String cargo) {
		this.cargo = cargo;
	}





	public String getSeccion() {
		return seccion;
	}





	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}





	public String getSueldo() {
		return sueldo;
	}





	public void setSueldo(String sueldo) {
		this.sueldo = sueldo;
	}





	public String getComision() {
		return comision;
	}





	public void setComision(String comision) {
		this.comision = comision;
	}





	public String getOtros() {
		return otros;
	}





	public void setOtros(String otros) {
		this.otros = otros;
	}





	public String getDetalle() {
		return detalle;
	}





	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}





	public ActividadInterna() {
		super();
	}
   
}
