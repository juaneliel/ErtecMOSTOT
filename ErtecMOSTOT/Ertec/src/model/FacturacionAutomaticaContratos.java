package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;


@Entity 
//@Table(name="FacturacionAutomaticaContratos")
@SequenceGenerator(name = "FAC_SEQ", sequenceName = "FAC_SEQ", initialValue = 1, allocationSize = 1)

public class FacturacionAutomaticaContratos implements Serializable {
	
	private static final long serialVersionUID = 1L;
	 
	@Id
 	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FAC_SEQ") 
	private int numero;	
	
	@ManyToOne(fetch=FetchType.EAGER,optional=true) 
//	@JoinColumn(name="ClienteID")
	private Cliente cliente;
	
	@ManyToOne(fetch=FetchType.EAGER,optional=true)
	//@JoinColumn(name="ContratoID")	
	private Contrato contrato;
	@Temporal(TemporalType.DATE)
	private Date fecha;
	@Column(columnDefinition="Decimal(10,2) default '0.00'")
	private BigDecimal importe;
	@Column(columnDefinition="Decimal(10,2) default '0.00'")
	private BigDecimal iva; 
	@Column(columnDefinition="Decimal(10,2) default '0.00'")
	private BigDecimal total;
	private String descripcion;
	private String descripcionAuxiliar;
	
			//[Tipo],[Moneda],
	
	

	public FacturacionAutomaticaContratos() {
		super();
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	public BigDecimal getIva() {
		return iva;
	}

	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcionAuxiliar() {
		return descripcionAuxiliar;
	}

	public void setDescripcionAuxiliar(String descripcionAuxiliar) {
		this.descripcionAuxiliar = descripcionAuxiliar;
	}
   
}
