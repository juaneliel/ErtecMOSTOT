package model;

public class DTO_Renglon {

	private int movimientoID;
	private int articuloID;
	private int cantidad;
	private long costo;
	private java.util.Date fecha;
	private long saldo;
	
		
	
	public int getMovimientoID() {
		return movimientoID;
	}
	public void setMovimientoID(int movimientoID) {
		this.movimientoID = movimientoID;
	}
	public int getArticuloID() {
		return articuloID;
	}
	public void setArticuloID(int articuloID) {
		this.articuloID = articuloID;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public long getCosto() {
		return costo;
	}
	public void setCosto(long costo) {
		this.costo = costo;
	}
	public java.util.Date getFecha() {
		return fecha;
	}
	public void setFecha(java.util.Date fecha) {
		this.fecha = fecha;
	}
	public long getSaldo() {
		return saldo;
	}
	public void setSaldo(long saldo) {
		this.saldo = saldo;
	}
}
