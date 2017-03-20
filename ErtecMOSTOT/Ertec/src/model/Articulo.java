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
@NamedQuery(name="Articulo.findAll", query="SELECT a FROM Articulo a")
public class Articulo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ArticuloID",columnDefinition="Decimal(10,2) default '0.00'")
	private int articuloID;

	@Column(name="Calidad")
	private String calidad;

	@Column(name="CostoDolares",columnDefinition="Decimal(10,2) default '0.00'")
	private BigDecimal costoDolares;

	@Column(name="CostoPesos",columnDefinition="Decimal(10,2) default '0.00'")
	private BigDecimal costoPesos;

	@Column(name="Descripcion")
	private String descripcion;

	@Column(name="Medida",columnDefinition="Decimal(10,2) default '0.00'")
	private BigDecimal medida;

	@Column(name="Observacion")
	private String observacion;

	@Column(name="PrecioVenta",columnDefinition="Decimal(10,2) default '0.00'")
	private BigDecimal precioVenta;

	@Column(name="Stock")
	private BigDecimal stock;

	@Column(name="StockMinimo")
	private BigDecimal stockMinimo;

	@Column(name="UltimoCostoDolares",columnDefinition="Decimal(10,2) default '0.00'")
	private BigDecimal ultimoCostoDolares;

	@Column(name="UltimoCostoPesos",columnDefinition="Decimal(10,2) default '0.00'")
	private BigDecimal ultimoCostoPesos;

	public Articulo() {
		super();
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

	public BigDecimal getMedida() {
		return this.medida;
	}

	public void setMedida(BigDecimal medida) {
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