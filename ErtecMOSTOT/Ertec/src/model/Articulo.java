package model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the Articulos database table.
 * 
 */
@Entity
@Table(name="Articulos")
@NamedQuery(name="Articulo.findAll", query="SELECT a FROM Articulo a ORDER BY a.articuloID DESC")

@SequenceGenerator(name = "ART_SEQ", sequenceName = "ARTICULO_SEQ", initialValue = 533292, allocationSize = 1)

public class Articulo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ART_SEQ")//se ingresan a mano
	@Column(name="ArticuloID")
	private int articuloID;

	@Column(name="Calidad")
	private String calidad;

	@Column(name="CostoDolares")
	private BigDecimal costoDolares;

	@Column(name="CostoPesos")
	private BigDecimal costoPesos;

	@Column(name="Descripcion")
	private String descripcion;

	@Column(name="Medida")
	private int medida;

	@Column(name="Observacion")
	private String observacion;

	@Column(name="PrecioVenta")
	private BigDecimal precioVenta;

	@Column(name="Stock")
	private BigDecimal stock;

	@Column(name="StockMinimo")
	private BigDecimal stockMinimo;

	@Column(name="UltimoCostoDolares")
	private BigDecimal ultimoCostoDolares;

	@Column(name="UltimoCostoPesos")
	private BigDecimal ultimoCostoPesos;

	public Articulo() {
	}

	public int getArticuloID() {
		return this.articuloID;
	}

	public void setArticuloID(int articuloID) {
		this.articuloID = articuloID;
	}

	public String getCalidad() {
		return this.calidad;
	}

	public void setCalidad(String calidad) {
		this.calidad = calidad;
	}

	public BigDecimal getCostoDolares() {
		return this.costoDolares;
	}

	public void setCostoDolares(BigDecimal costoDolares) {
		this.costoDolares = costoDolares;
	}

	public BigDecimal getCostoPesos() {
		return this.costoPesos;
	}

	public void setCostoPesos(BigDecimal costoPesos) {
		this.costoPesos = costoPesos;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getMedida() {
		return this.medida;
	}

	public void setMedida(int medida) {
		this.medida = medida;
	}

	public String getObservacion() {
		return this.observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public BigDecimal getPrecioVenta() {
		return this.precioVenta;
	}

	public void setPrecioVenta(BigDecimal precioVenta) {
		this.precioVenta = precioVenta;
	}

	public BigDecimal getStock() {
		return this.stock;
	}

	public void setStock(BigDecimal stock) {
		this.stock = stock;
	}

	public BigDecimal getStockMinimo() {
		return this.stockMinimo;
	}

	public void setStockMinimo(BigDecimal stockMinimo) {
		this.stockMinimo = stockMinimo;
	}

	public BigDecimal getUltimoCostoDolares() {
		return this.ultimoCostoDolares;
	}

	public void setUltimoCostoDolares(BigDecimal ultimoCostoDolares) {
		this.ultimoCostoDolares = ultimoCostoDolares;
	}

	public BigDecimal getUltimoCostoPesos() {
		return this.ultimoCostoPesos;
	}

	public void setUltimoCostoPesos(BigDecimal ultimoCostoPesos) {
		this.ultimoCostoPesos = ultimoCostoPesos;
	}

}