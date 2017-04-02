package model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import model.DAO.DAO_Movimiento;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name="Movimientos")
@NamedQuery(name="Movimiento.findAll", query="SELECT m FROM Movimiento m ORDER BY m.movimientoID DESC")

@SequenceGenerator(name = "MOV_SEQ", sequenceName = "MOVIMIENTO_SEQ", initialValue = 163000, allocationSize = 1)

public class Movimiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MOV_SEQ")	
	@Column(name="MovimientoID")
	private int movimientoID;

 

	@OneToMany (fetch=FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval=true)
	@JoinColumn(name="MovimientoID", referencedColumnName="MovimientoID") 
	private List<NexoMovimiento> nexos;
	
	@OneToOne(fetch=FetchType.EAGER,optional=true) 
	@JoinColumn(name="ClienteID" )
	private Cliente cliente;
		
	@OneToOne(fetch=FetchType.EAGER,optional=true) 
	@JoinColumn(name="ContratoID")
	private Contrato contrato;
	
	
	@Column(name="CodigoMovimientoID")
	private int codigoMovimientoID;

	@Column(name="Comprobante")
	private int comprobante;

 

	@Column(name="Cotizacion")
	private BigDecimal cotizacion;

	@Temporal(TemporalType.DATE)
	@Column(name="Fecha")
	private Date fecha;

	@Column(name="NombreCliente")
	private String nombreCliente;

	@Column(name="NroMovimiento")
	private int nroMovimiento;

	@Column(name="Referencia")
	private int referencia;

	@Column(name="TipoOT")
	private String tipoOT;

	@Column(name="TipoReferencia")
	private String tipoReferencia;

	public Movimiento() {
	}

	public int getMovimientoID() {
		return this.movimientoID;
	}

	public void setMovimientoID(int movimientoID) {
		this.movimientoID = movimientoID;
	}

	public int getCodigoMovimientoID() {
		return this.codigoMovimientoID;
	}

	public void setCodigoMovimientoID(int codigoMovimientoID) {
		this.codigoMovimientoID = codigoMovimientoID;
	}

	public int getComprobante() {
		return this.comprobante;
	}

	public void setComprobante(int comprobante) {
		this.comprobante = comprobante;
	}
 

 
	public BigDecimal getCotizacion() {
    return cotizacion;
  }

  public void setCotizacion(BigDecimal cotizacion) {
    this.cotizacion = cotizacion;
  }

  public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getNombreCliente() {
		return this.nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public int getNroMovimiento() {
		return this.nroMovimiento;
	}

	public void setNroMovimiento(int nroMovimiento) {
		this.nroMovimiento = nroMovimiento;
	}

	public int getReferencia() {
		return this.referencia;
	}

	public void setReferencia(int referencia) {
		this.referencia = referencia;
	}

	public String getTipoOT() {
		return this.tipoOT;
	}

	public void setTipoOT(String tipoOT) {
		this.tipoOT = tipoOT;
	}

	public String getTipoReferencia() {
		return this.tipoReferencia;
	}

	public void setTipoReferencia(String tipoReferencia) {
		this.tipoReferencia = tipoReferencia;
	}

	public List<NexoMovimiento> getNexos() {
		return nexos;
	}

	public void setNexos(List<NexoMovimiento> nexos) {
		this.nexos = nexos;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getCodigoString(){
	  return DAO_Movimiento.getCodigoString(this.getCodigoMovimientoID());  
	}
	 

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}
	
	
}