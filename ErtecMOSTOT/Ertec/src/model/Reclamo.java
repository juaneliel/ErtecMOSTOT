package model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;
import java.util.Date;



@Entity
@Table(name="Reclamos")
@NamedQuery(name="Reclamo.findAll", query="SELECT r FROM Reclamo r  ORDER BY r.id DESC")
 
@SequenceGenerator(name = "REC_SEQ", sequenceName = "RECLAMO_SEQ", initialValue = 55300, allocationSize = 1)


public class Reclamo implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REC_SEQ")
  private int id;  
 
  @OneToOne(fetch=FetchType.EAGER,optional=true)  
  private Funcionario funcionario;
  
  @OneToOne(fetch=FetchType.EAGER,optional=true ) 
  private Cliente cliente;
  
  @OneToOne(fetch=FetchType.EAGER,optional=true) 
  private Contrato contrato;

  @OneToOne(fetch=FetchType.EAGER,optional=true) 
  private VentaContado ventaContado;
  
	private short antel; 
	private String codigo="";
	private short conmutador;
	private String contacto="";
	private short energia;
	private String estado="";
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_reclamado")
	private Date fechaReclamado;
	@Temporal(TemporalType.DATE)
	@Column(name="fecha_visita")
	private Date fechaVisita;
	@Column(name="hora_reclamado")
	private Time horaReclamado;
	private String myr;
	@Column(name="myr_estado")
	private String myrEstado="";
	private String nombreCliente="";
	private String observaciones="";
	private String ordenreparacion="";
	private String reclamo="";
	private short red;
	private short telefonos;
	private String urgente="";
	private String equipo="";
	
	public Reclamo() { 
		super();
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public short getAntel() {
		return this.antel;
	}

	public void setAntel(short antel) {
		this.antel = antel;
	}
 
	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public short getConmutador() {
		return this.conmutador;
	}

	public void setConmutador(short conmutador) {
		this.conmutador = conmutador;
	}

	public String getContacto() {
		return this.contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public short getEnergia() {
		return this.energia;
	}

	public void setEnergia(short energia) {
		this.energia = energia;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaReclamado() {
		return this.fechaReclamado;
	}

	public void setFechaReclamado(Date fechaReclamado) {
		this.fechaReclamado = fechaReclamado;
	}

	public Date getFechaVisita() {
		return this.fechaVisita;
	}

	public void setFechaVisita(Date fechaVisita) {
		this.fechaVisita = fechaVisita;
	}



	public Time getHoraReclamado() {
		return this.horaReclamado;
	}

	public void setHoraReclamado(Time horaReclamado) {
		this.horaReclamado = horaReclamado;
	}

	public String getMyr() {
		return this.myr;
	}

	public void setMyr(String myr) {
		this.myr = myr;
	}

	public String getMyrEstado() {
		return this.myrEstado;
	}

	public void setMyrEstado(String myrEstado) {
		this.myrEstado = myrEstado;
	}

	public String getNombreCliente() {
		return this.nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getOrdenreparacion() {
		return this.ordenreparacion;
	}

	public void setOrdenreparacion(String ordenreparacion) {
		this.ordenreparacion = ordenreparacion;
	}

	public String getReclamo() {
		return this.reclamo;
	}

	public void setReclamo(String reclamo) {
		this.reclamo = reclamo;
	}

	public short getRed() {
		return this.red;
	}

	public void setRed(short red) {
		this.red = red;
	}

	public short getTelefonos() {
		return this.telefonos;
	}

	public void setTelefonos(short telefonos) {
		this.telefonos = telefonos;
	}

	public String getUrgente() {
		return this.urgente;
	}

	public void setUrgente(String urgente) {
		this.urgente = urgente;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	public VentaContado getVentaContado() {
		return ventaContado;
	}

	public void setVentaContado(VentaContado ventaContado) {
		this.ventaContado = ventaContado;
	}

	public String getEquipo() {
		return equipo;
	}

	public void setEquipo(String equipo) {
		this.equipo = equipo;
	}

}