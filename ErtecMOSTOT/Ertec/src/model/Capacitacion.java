package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "CAP_SEQ", sequenceName = "CAP_SEQ", initialValue = 1, allocationSize = 1)
public class Capacitacion implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CAP_SEQ") 
	private int capacitacionID;	
	@Temporal(TemporalType.DATE) 
	private Date fecha;	
	private String actividad;
	private String capacitacion;
	private String observaciones;
	private String pathArchivo;
	private int funcionarioID;
	public Capacitacion() {
		super();
	}
	 
	public int getCapacitacionID() {
		return capacitacionID;
	}

	public void setCapacitacionID(int capacitacionID) {
		this.capacitacionID = capacitacionID;
	}

	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getActividad() {
		return actividad;
	}
	public void setActividad(String actividad) {
		this.actividad = actividad;
	}
	public String getCapacitacion() {
		return capacitacion;
	}
	public void setCapacitacion(String capacitacion) {
		this.capacitacion = capacitacion;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public String getPathArchivo() {
		return pathArchivo;
	}
	public void setPathArchivo(String pathArchivo) {
		this.pathArchivo = pathArchivo;
	}
	public int getFuncionarioID() {
		return funcionarioID;
	}
	public void setFuncionarioID(int funcionarioID) {
		this.funcionarioID = funcionarioID;
	}	
}
