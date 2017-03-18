package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "FP_SEQ", sequenceName = "FP_SEQ", initialValue = 1, allocationSize = 1)
public class FichaPersonal implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FP_SEQ") 
	private int fichaID;	
	private String primerNombre;
	private String segundoNombre;
	private String primerApellido;
	private String segundoApellido;
	private String ciudadania;	
	@Temporal(TemporalType.DATE) 
	private Date nacimiento;
	@Temporal(TemporalType.DATE) 
	private Date ingreso;	
	private String cedula;
	private String credencialCivica;
	private String nombrePadre;
	private String nombreMadre;
	private String nombreConyuge;
	private String direccionMadre;
	private String direccionPadre;
	private String direccionConyuge;
	private String telefonoMadre;
	private String telefonoPadre;
	private String telefonoConyuge;
	@Temporal(TemporalType.DATE) 
	private Date fechaEgreso;	 
	private String motivoEgreso;
	private String nombreAvisarUrgencia;
	private String direccionAvisarUrgencia;
	private String telefonoAvisarUrgencia;
	private String pathCedula1;
	private String pathCedula2;
	private String pathCredencial1;
	private String pathCredencial2;
	private String pathCarneSalud1;
	private String pathCarneSalud2;
	private String pathLibretaConducir1;
	private String pathLibretaConducir2;	
	private String pathFoto;

	
 
	
	public String getPathFoto() {
		return pathFoto;
	}
	public void setPathFoto(String pathFoto) {
		this.pathFoto = pathFoto;
	}
	private int funcionarioID;
		
	public int getFuncionarioID() {
		return funcionarioID;
	}
	public void setFuncionarioID(int funcionarioID) {
		this.funcionarioID = funcionarioID;
	}
	public FichaPersonal() {
		super();
	}

	public int getFichaID() {
		return fichaID;
	}
	public void setFichaID(int fichaID) {
		this.fichaID = fichaID;
	}
	public String getPrimerNombre() {
		return primerNombre;
	}
	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}
	public String getSegundoNombre() {
		return segundoNombre;
	}
	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}
	public String getPrimerApellido() {
		return primerApellido;
	}
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}
	public String getSegundoApellido() {
		return segundoApellido;
	}
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}
	public String getCiudadania() {
		return ciudadania;
	}
	public void setCiudadania(String ciudadania) {
		this.ciudadania = ciudadania;
	}
	public Date getNacimiento() {
		return nacimiento;
	}
	public void setNacimiento(Date nacimiento) {
		this.nacimiento = nacimiento;
	}
	public Date getIngreso() {
		return ingreso;
	}
	public void setIngreso(Date ingreso) {
		this.ingreso = ingreso;
	}
	public String getCedula() {
		return cedula;
	}
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
	public String getCredencialCivica() {
		return credencialCivica;
	}
	public void setCredencialCivica(String credencialCivica) {
		this.credencialCivica = credencialCivica;
	}
	public String getNombrePadre() {
		return nombrePadre;
	}
	public void setNombrePadre(String nombrePadre) {
		this.nombrePadre = nombrePadre;
	}
	public String getNombreMadre() {
		return nombreMadre;
	}
	public void setNombreMadre(String nombreMadre) {
		this.nombreMadre = nombreMadre;
	}
	public String getNombreConyuge() {
		return nombreConyuge;
	}
	public void setNombreConyuge(String nombreConyuge) {
		this.nombreConyuge = nombreConyuge;
	}
	public String getDireccionMadre() {
		return direccionMadre;
	}
	public void setDireccionMadre(String direccionMadre) {
		this.direccionMadre = direccionMadre;
	}
	public String getDireccionPadre() {
		return direccionPadre;
	}
	public void setDireccionPadre(String direccionPadre) {
		this.direccionPadre = direccionPadre;
	}
	public String getDireccionConyuge() {
		return direccionConyuge;
	}
	public void setDireccionConyuge(String direccionConyuge) {
		this.direccionConyuge = direccionConyuge;
	}
	public String getTelefonoMadre() {
		return telefonoMadre;
	}
	public void setTelefonoMadre(String telefonoMadre) {
		this.telefonoMadre = telefonoMadre;
	}
	public String getTelefonoPadre() {
		return telefonoPadre;
	}
	public void setTelefonoPadre(String telefonoPadre) {
		this.telefonoPadre = telefonoPadre;
	}
	public String getTelefonoConyuge() {
		return telefonoConyuge;
	}
	public void setTelefonoConyuge(String telefonoConyuge) {
		this.telefonoConyuge = telefonoConyuge;
	}
 
	public Date getFechaEgreso() {
		return fechaEgreso;
	}
	public void setFechaEgreso(Date fechaEgreso) {
		this.fechaEgreso = fechaEgreso;
	}
	public String getMotivoEgreso() {
		return motivoEgreso;
	}
	public void setMotivoEgreso(String motivoEgreso) {
		this.motivoEgreso = motivoEgreso;
	}
	public String getNombreAvisarUrgencia() {
		return nombreAvisarUrgencia;
	}
	public void setNombreAvisarUrgencia(String nombreAvisarUrgencia) {
		this.nombreAvisarUrgencia = nombreAvisarUrgencia;
	}
	public String getDireccionAvisarUrgencia() {
		return direccionAvisarUrgencia;
	}
	public void setDireccionAvisarUrgencia(String direccionAvisarUrgencia) {
		this.direccionAvisarUrgencia = direccionAvisarUrgencia;
	}
	public String getTelefonoAvisarUrgencia() {
		return telefonoAvisarUrgencia;
	}
	public void setTelefonoAvisarUrgencia(String telefonoAvisarUrgencia) {
		this.telefonoAvisarUrgencia = telefonoAvisarUrgencia;
	}
	public String getPathCedula1() {
		return pathCedula1;
	}
	public void setPathCedula1(String pathCedula1) {
		this.pathCedula1 = pathCedula1;
	}
	public String getPathCedula2() {
		return pathCedula2;
	}
	public void setPathCedula2(String pathCedula2) {
		this.pathCedula2 = pathCedula2;
	}
	public String getPathCredencial1() {
		return pathCredencial1;
	}
	public void setPathCredencial1(String pathCredencial1) {
		this.pathCredencial1 = pathCredencial1;
	}
	public String getPathCredencial2() {
		return pathCredencial2;
	}
	public void setPathCredencial2(String pathCredencial2) {
		this.pathCredencial2 = pathCredencial2;
	}
	public String getPathCarneSalud1() {
		return pathCarneSalud1;
	}
	public void setPathCarneSalud1(String pathCarneSalud1) {
		this.pathCarneSalud1 = pathCarneSalud1;
	}
	public String getPathCarneSalud2() {
		return pathCarneSalud2;
	}
	public void setPathCarneSalud2(String pathCarneSalud2) {
		this.pathCarneSalud2 = pathCarneSalud2;
	}
	public String getPathLibretaConducir1() {
		return pathLibretaConducir1;
	}
	public void setPathLibretaConducir1(String pathLibretaConducir1) {
		this.pathLibretaConducir1 = pathLibretaConducir1;
	}
	public String getPathLibretaConducir2() {
		return pathLibretaConducir2;
	}
	public void setPathLibretaConducir2(String pathLibretaConducir2) {
		this.pathLibretaConducir2 = pathLibretaConducir2;
	}
 
   
}
