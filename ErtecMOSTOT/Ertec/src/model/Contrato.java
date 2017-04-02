package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.math.BigDecimal;

@Entity
@Table(name="Contratos")
@NamedQuery(name="Contrato.findAll", query="SELECT c FROM Contrato c")

@SequenceGenerator(name = "CON_SEQ", sequenceName = "Contrato_SEQ", initialValue = 1100, allocationSize = 1)



public class Contrato implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CON_SEQ")
  @Column(name="Id")
  private int id;


	
	//hay error en los datos de contratos
	@OneToOne(fetch=FetchType.EAGER,optional=true) 
  @JoinColumn(name="ClienteID")
  private Cliente cliente;

  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CLI_SEQ")  
	@Column(name="ContratoID")
	private int contratoID;

	@Column(name="CorredorID")
	private int corredorID;

	@Column(name="Cuota")
	private BigDecimal cuota;

	@Column(name="Direccion")
	private String direccion;

	@Column(name="Equipo")
	private String equipo;

	@Temporal(TemporalType.DATE)
	@Column(name="FechaFin")
	private Date fechaFin;

	@Temporal(TemporalType.DATE)
	@Column(name="FechaInicio")
	private Date fechaInicio;

	@Column(name="Frecuencia")
	private String frecuencia;

	@Column(name="Localidad")
	private String localidad;

	@Column(name="Moneda")
	private String moneda;

	@Temporal(TemporalType.DATE)
	@Column(name="NoAumentar")
	private Date noAumentar;

	@Temporal(TemporalType.DATE)
	@Column(name="NoFacturar")
	private Date noFacturar;

	@Column(name="Retirado")
	private String retirado;

	@Column(name="Tipo")
	private String tipo;

	@Column(name="TopeMesesVisita")
	private int topeMesesVisita;

	@Temporal(TemporalType.DATE)
	@Column(name="UltimoAjuste")
	private Date ultimoAjuste;

	@Column(name="Zona")
	private String zona;

	public Contrato() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}



	public int getContratoID() {
		return this.contratoID;
	}

	public void setContratoID(int contratoID) {
		this.contratoID = contratoID;
	}

	public int getCorredorID() {
		return this.corredorID;
	}

	public void setCorredorID(int corredorID) {
		this.corredorID = corredorID;
	}

	public BigDecimal getCuota() {
		return this.cuota;
	}

	public void setCuota(BigDecimal cuota) {
		this.cuota = cuota;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEquipo() {
		return this.equipo;
	}

	public void setEquipo(String equipo) {
		this.equipo = equipo;
	}

	public Date getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFrecuencia() {
		return this.frecuencia;
	}

	public void setFrecuencia(String frecuencia) {
		this.frecuencia = frecuencia;
	}

	public String getLocalidad() {
		return this.localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getMoneda() {
		return this.moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public Date getNoAumentar() {
		return this.noAumentar;
	}

	public void setNoAumentar(Date noAumentar) {
		this.noAumentar = noAumentar;
	}

	public Date getNoFacturar() {
		return this.noFacturar;
	}

	public void setNoFacturar(Date noFacturar) {
		this.noFacturar = noFacturar;
	}

	public String getRetirado() {
		return this.retirado;
	}

	public void setRetirado(String retirado) {
		this.retirado = retirado;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getTopeMesesVisita() {
		return this.topeMesesVisita;
	}

	public void setTopeMesesVisita(int topeMesesVisita) {
		this.topeMesesVisita = topeMesesVisita;
	}

	public Date getUltimoAjuste() {
		return this.ultimoAjuste;
	}

	public void setUltimoAjuste(Date ultimoAjuste) {
		this.ultimoAjuste = ultimoAjuste;
	}

	public String getZona() {
		return this.zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}