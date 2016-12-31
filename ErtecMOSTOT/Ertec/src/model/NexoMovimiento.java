package model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@NamedQuery(name="NexoMovimiento.findAll", query="SELECT n FROM NexoMovimiento n")
public class NexoMovimiento implements Serializable {
	private static final long serialVersionUID = 1L;

	
//	@ManyToOne
//	@JoinColumn(name="MovimientoID")
//	private Movimiento movimiento;
//	   
//	
	
	 
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ArticuloID" , insertable = false, updatable = false)
	private Articulo articulo;
	
	
	@Id
	@Column(name="NexoMovimientoID")
	private int nexoMovimientoID;

	@Column(name="ArticuloID")
	private int articuloID;

	@Column(name="Cantidad")
	private BigDecimal cantidad;

	@Column(name="Costo")
	private BigDecimal costo;

	@Temporal(TemporalType.DATE)
	@Column(name="Fecha")
	private Date fecha;

	@Column(name="MovimientoID")
	private int movimientoID;

	public NexoMovimiento() {
	}

	public int getNexoMovimientoID() {
		return this.nexoMovimientoID;
	}

	public void setNexoMovimientoID(int nexoMovimientoID) {
		this.nexoMovimientoID = nexoMovimientoID;
	}

	public int getArticuloID() {
		return this.articuloID;
	}

	public void setArticuloID(int articuloID) {
		this.articuloID = articuloID;
	}

	public BigDecimal getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getCosto() {
		return this.costo;
	}

	public void setCosto(BigDecimal costo) {
		this.costo = costo;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getMovimientoID() {
		return this.movimientoID;
	}

	public void setMovimientoID(int movimientoID) {
		this.movimientoID = movimientoID;
	}

	public Articulo getArticulo() {
		return articulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}

}