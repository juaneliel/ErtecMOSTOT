package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "AA_SEQ", sequenceName = "AA_SEQ", initialValue = 1, allocationSize = 1)
public class ActividadAnterior implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AA_SEQ") 
	private int actividadAnteriorID;	
	@Temporal(TemporalType.DATE) 
	private Date desde;	
	@Temporal(TemporalType.DATE) 
	private Date hasta;	
	private String firma;
	private String puesto;
	private String causaRetiro;
	private String sueldo;
	private String detalle;
	private String otros;
	private int funcionarioID;
	public ActividadAnterior() {
		super();
	}
	
	public int getFuncionarioID() {
		return funcionarioID;
	}

	public void setFuncionarioID(int funcionarioID) {
		this.funcionarioID = funcionarioID;
	}
 
	public int getActividadAnteriorID() {
		return actividadAnteriorID;
	}

	public void setActividadAnteriorID(int actividadAnteriorID) {
		this.actividadAnteriorID = actividadAnteriorID;
	}

	public Date getDesde() {
		return desde;
	}
	public void setDesde(Date desde) {
		this.desde = desde;
	}
	public Date getHasta() {
		return hasta;
	}
	public void setHasta(Date hasta) {
		this.hasta = hasta;
	}
	public String getFirma() {
		return firma;
	}
	public void setFirma(String firma) {
		this.firma = firma;
	}
	public String getPuesto() {
		return puesto;
	}
	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}
	public String getCausaRetiro() {
		return causaRetiro;
	}
	public void setCausaRetiro(String causaRetiro) {
		this.causaRetiro = causaRetiro;
	}
	public String getSueldo() {
		return sueldo;
	}
	public void setSueldo(String sueldo) {
		this.sueldo = sueldo;
	}
	public String getDetalle() {
		return detalle;
	}
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	public String getOtros() {
		return otros;
	}
	public void setOtros(String otros) {
		this.otros = otros;
	}
   
}
