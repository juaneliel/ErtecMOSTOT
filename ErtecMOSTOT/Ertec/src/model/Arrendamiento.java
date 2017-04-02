package model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the Arrendamientos database table.
 * 
 */
@Entity
@Table(name="Arrendamientos")
@NamedQuery(name="Arrendamiento.findAll", query="SELECT a FROM Arrendamiento a")
public class Arrendamiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="ArticuloID")
  private Articulo articulo;


	@Column(name="Cantidad")
	private int cantidad;

	@Column(name="Costo")
	private BigDecimal costo;

	@Temporal(TemporalType.DATE)
	@Column(name="Fecha")
	private Date fecha;

	@Column(name="MovimientoID")
	private int movimientoID;

	@Column(name="Saldo")
	private int saldo;

	public Arrendamiento() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(int cantidad) {
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

	public int getSaldo() {
		return this.saldo;
	}

	public void setSaldo(int saldo) {
		this.saldo = saldo;
	}

  public Articulo getArticulo() {
    return articulo;
  }

  public void setArticulo(Articulo articulo) {
    this.articulo = articulo;
  }

}