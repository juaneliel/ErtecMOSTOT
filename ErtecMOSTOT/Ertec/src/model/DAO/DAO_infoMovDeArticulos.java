package model.DAO;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class DAO_infoMovDeArticulos {

	private long movimientoID;
	private int codigoMovimientoID;
	private int referencia;
	private BigDecimal cantidad; 
	private BigDecimal costo;  
	private String nombreCliente;
	private Date fecha;
	 
	public long getMovimientoID() {
		return movimientoID;
	}
	public void setMovimientoID(long movimientoID) {
		this.movimientoID = movimientoID;
	}
	public int getCodigoMovimientoID() {
		return codigoMovimientoID;
	}
	public void setCodigoMovimientoID(int codigoMovimientoID) {
		this.codigoMovimientoID = codigoMovimientoID;
	}
	public int getReferencia() {
		return referencia;
	}
	public void setReferencia(int referencia) {
		this.referencia = referencia;
	}
	public BigDecimal getCantidad() {
		return cantidad;
	}
	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}
	public BigDecimal getCosto() {
		return costo;
	}
	public void setCosto(BigDecimal costo) {
		this.costo = costo;
	}
	public String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	} 

	
}
