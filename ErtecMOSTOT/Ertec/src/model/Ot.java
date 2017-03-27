
package model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name="OT")

@NamedQueries({
	@NamedQuery(name="Ot.findAll", query="SELECT o FROM Ot o ORDER BY o.id DESC") 

	//@NamedQuery(name="Ot.getMovimientos", query="SELECT m FROM Movimiento m, Ot ot WHERE m.movimientoID= Ot.referencia and Ot.tipoOT <> NULL  ORDER BY m.movimientoID DESC") 
}) 





@SequenceGenerator(name = "OT_SEQ", sequenceName = "OT_SEQ", initialValue = 969697, allocationSize = 1)


public class Ot implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OT_SEQ")		
	@Column(name="Id")
	private int id;
 
//	private ArrayList<Movimiento> movimientos;
//	
//	public ArrayList<Movimiento> getMovimientos() {
//		return movimientos;
//	}
//
//	public void setMovimientos(ArrayList<Movimiento> movimientos) {
//		this.movimientos = movimientos;
//	}

	@OneToMany (fetch=FetchType.LAZY)
	@JoinColumn(name="OTID", referencedColumnName="Id" , insertable = false, updatable = false)//columna adicional, columna ot
	private List<Adicional> adicionales;
	
	@OneToMany (fetch=FetchType.LAZY)
	@JoinColumn(name="OTID", referencedColumnName="Id" , insertable = false, updatable = false)
	private List<ComprasExternasOT> comprasExternas;
	
	@OneToOne(fetch=FetchType.EAGER )
	@JoinColumn(name="NroCliente", referencedColumnName="ClienteID" , insertable = false, updatable = false)
	private Cliente cliente;
	
	
	@Column(name="Cliente")
	private String clienteNombre;

	@Column(name="AreaID")
	private int areaID;

	@Column(name="Arrendamiento")
	private int arrendamiento;

	@Column(name="C")
	private int c;

	@Column(name="C_Corriente")
	private int c_Corriente;
 

	@Column(name="DireccionObra")
	private String direccionObra;

	@Column(name="Factura")
	private BigDecimal factura;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FechaFacturada")
	private Date fechaFacturada;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FechaInicio")
	private Date fechaInicio;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FechaTerminada")
	private Date fechaTerminada;

	@Column(name="Mantenimiento")
	private int mantenimiento;

	@Column(name="NroCliente")
	private int nroCliente;

	@Column(name="O_C")
	private int oC;

	@Column(name="Pedido")
	private int pedido;

	@Column(name="Presupuesto")
	private BigDecimal presupuesto;

	@Column(name="Proceso")
	private String proceso;

	@Column(name="R")
	private int r;

	@Column(name="TelObra")
	private String telObra;

	@Column(name="TipoID")
	private int tipoID;

	@Column(name="Trabajo")
	private String trabajo;

	@Column(name="VerifAdm")
	private int verifAdm;

	public Ot() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAreaID() {
		return this.areaID;
	}

	public void setAreaID(int areaID) {
		this.areaID = areaID;
	}

	public int getArrendamiento() {
		return this.arrendamiento;
	}

	public void setArrendamiento(int arrendamiento) {
		this.arrendamiento = arrendamiento;
	}

	public int getC() {
		return this.c;
	}

	public void setC(int c) {
		this.c = c;
	}

	public int getC_Corriente() {
		return this.c_Corriente;
	}

	public void setC_Corriente(int c_Corriente) {
		this.c_Corriente = c_Corriente;
	}

	 

	public List<Adicional> getAdicionales() {
		return adicionales;
	}

	public void setAdicionales(List<Adicional> adicionales) {
		this.adicionales = adicionales;
	}

	public List<ComprasExternasOT> getComprasExternas() {
		return comprasExternas;
	}

	public void setComprasExternas(List<ComprasExternasOT> comprasExternas) {
		this.comprasExternas = comprasExternas;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getClienteNombre() {
		return clienteNombre;
	}

	public void setClienteNombre(String clienteNombre) {
		this.clienteNombre = clienteNombre;
	}

	

	public String getDireccionObra() {
		return this.direccionObra;
	}

	public void setDireccionObra(String direccionObra) {
		this.direccionObra = direccionObra;
	}

	public BigDecimal getFactura() {
		return this.factura;
	}

	public void setFactura(BigDecimal factura) {
		this.factura = factura;
	}

	public Date getFechaFacturada() {
		return this.fechaFacturada;
	}

	public void setFechaFacturada(Date fechaFacturada) {
		this.fechaFacturada = fechaFacturada;
	}

	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaTerminada() {
		return this.fechaTerminada;
	}

	public void setFechaTerminada(Date fechaTerminada) {
		this.fechaTerminada = fechaTerminada;
	}

	public int getMantenimiento() {
		return this.mantenimiento;
	}

	public void setMantenimiento(int mantenimiento) {
		this.mantenimiento = mantenimiento;
	}

	public int getNroCliente() {
		return this.nroCliente;
	}

	public void setNroCliente(int nroCliente) {
		this.nroCliente = nroCliente;
	}

	public int getOC() {
		return this.oC;
	}

	public void setOC(int oC) {
		this.oC = oC;
	}

	public int getPedido() {
		return this.pedido;
	}

	public void setPedido(int pedido) {
		this.pedido = pedido;
	}

	public BigDecimal getPresupuesto() {
		return this.presupuesto;
	}

	public void setPresupuesto(BigDecimal presupuesto) {
		this.presupuesto = presupuesto;
	}

	public String getProceso() {
		return this.proceso;
	}

	public void setProceso(String proceso) {
		this.proceso = proceso;
	}

	public int getR() {
		return this.r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public String getTelObra() {
		return this.telObra;
	}

	public void setTelObra(String telObra) {
		this.telObra = telObra;
	}

	public int getTipoID() {
		return this.tipoID;
	}

	public int getoC() {
		return oC;
	}

	public void setoC(int oC) {
		this.oC = oC;
	}

	public void setTipoID(int tipoID) {
		this.tipoID = tipoID;
	}

	public String getTrabajo() {
		return this.trabajo;
	}

	public void setTrabajo(String trabajo) {
		this.trabajo = trabajo;
	}

	public int getVerifAdm() {
		return this.verifAdm;
	}

	public void setVerifAdm(int verifAdm) {
		this.verifAdm = verifAdm;
	}

}