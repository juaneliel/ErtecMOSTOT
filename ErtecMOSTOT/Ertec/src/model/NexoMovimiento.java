package model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@NamedQuery(name="NexoMovimiento.findAll", query="SELECT n FROM NexoMovimiento n")

@SequenceGenerator(name = "NEX_SEQ", sequenceName = "NEXO_SEQ", initialValue = 1, allocationSize = 1)

public class NexoMovimiento implements Serializable {
	private static final long serialVersionUID = 1L;

	
//	@ManyToOne
//	@JoinColumn(name="MovimientoID")
//	private Movimiento movimiento;
//	   
//	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NEX_SEQ")	
	@Column(name="NexoMovimientoID")
	private int nexoMovimientoID;
	 
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="ArticuloID" )
	private Articulo articulo;
	
	@Column(name="Cantidad")
	private BigDecimal cantidad=BigDecimal.ZERO;

	@Column(name="Costo")
	private BigDecimal costo=BigDecimal.ZERO;

	@Temporal(TemporalType.DATE)
	@Column(name="Fecha")
	private Date fecha;
 
	public NexoMovimiento() {
	}

	public int getNexoMovimientoID() {
		return this.nexoMovimientoID;
	}

	public void setNexoMovimientoID(int nexoMovimientoID) {
		this.nexoMovimientoID = nexoMovimientoID;
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

	public Articulo getArticulo() {
		return articulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}

}