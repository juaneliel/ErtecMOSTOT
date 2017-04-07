package model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;




@Entity
@NamedQuery(name="ComprasExternasOT.findAll", query="SELECT c FROM ComprasExternasOT c")

@SequenceGenerator(name = "CE_SEQ", sequenceName = "CE_SEQ", initialValue = 1, allocationSize = 1)


public class ComprasExternasOT implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CE_SEQ") 
	private int id;

	@Column(name="NombreArticulo")
	private String nombreArticulo;
	
	@Column(name="ArticuloID")
	private int articuloID;

//	@OneToOne(fetch=FetchType.EAGER)
//	@JoinColumn(name="ArticuloID" , insertable = false, updatable = false)
//	private Articulo articulo;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="ProveedorID")
	private Proveedores proveedor;
	
	
	
	@Column(name="Cantidad")
	private int cantidad;

	@Temporal(TemporalType.DATE)
	@Column(name="Fecha")
	private Date fecha;

	@Column(name="Moneda")
	private String moneda;

	@Column(name="Precio_Unitario")
	private BigDecimal precio_Unitario;

	public ComprasExternasOT() {
	}


	public int getArticuloID() {
		return this.articuloID;
	}

	public void setArticuloID(int articuloID) {
		this.articuloID = articuloID;
	}

	public int getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getMoneda() {
		return this.moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public BigDecimal getPrecio_Unitario() {
		return this.precio_Unitario;
	}

	public void setPrecio_Unitario(BigDecimal precio_Unitario) {
		this.precio_Unitario = precio_Unitario;
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	public Proveedores getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedores proveedor) {
		this.proveedor = proveedor;
	}

	public String getNombreArticulo() {
		return nombreArticulo;
	}

	public void setNombreArticulo(String nombreArticulo) {
		this.nombreArticulo = nombreArticulo;
	}
}