package model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

 
@Entity
@NamedQuery(name="ManoObra.findAll", query="SELECT m FROM ManoObra m ORDER BY m.manoObraID DESC")
@SequenceGenerator(name = "MO_SEQ", sequenceName = "MANOOBRA_SEQ", initialValue = 91198, allocationSize = 1)

public class ManoObra implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MO_SEQ")
	@Column(name="ManoObraID",insertable=true, updatable=true, unique=true, nullable=false)
	private int manoObraID;

	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="FuncionarioID")
	private Funcionario funcionario;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="ClienteID")
	private Cliente cliente;
	 	
	@Column(name="Cliente")
	private String clienteNombre;
	
	@Column(name="Codigo")
	private int codigo;

	@Temporal(TemporalType.DATE)
	@Column(name="Fecha")
	private Date fecha;
 

	@Column(name="MYR")
	private int myr;

	private int NContrato;

	@Column(name="OrdenTrabajo")
	private int ordenTrabajo;

	private String TContrato;

	@Column(name="Tipo")
	private int tipo;

	@Column(name="TipoHora")
	private String tipoHora;

	@Column(name="TipoMano")
	private String tipoMano;

	@Column(name="TotalHora")
	private BigDecimal totalHora;

	public ManoObra() {
	}

	public int getManoObraID() {
		return this.manoObraID;
	}

	public void setManoObraID(int manoObraID) {
		this.manoObraID = manoObraID;
	}

	public String getClienteNombre() {
		return clienteNombre;
	}

	public void setClienteNombre(String clienteNombre) {
		this.clienteNombre = clienteNombre;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	 
	public int getCodigo() {
		return this.codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	} 

	public int getMyr() {
		return this.myr;
	}

	public void setMyr(int myr) {
		this.myr = myr;
	}

	public int getNContrato() {
		return this.NContrato;
	}

	public void setNContrato(int NContrato) {
		this.NContrato = NContrato;
	}

	public int getOrdenTrabajo() {
		return this.ordenTrabajo;
	}

	public void setOrdenTrabajo(int ordenTrabajo) {
		this.ordenTrabajo = ordenTrabajo;
	}

	public String getTContrato() {
		return this.TContrato;
	}

	public void setTContrato(String TContrato) {
		this.TContrato = TContrato;
	}

	public int getTipo() {
		return this.tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public String getTipoHora() {
		return this.tipoHora;
	}

	public void setTipoHora(String tipoHora) {
		this.tipoHora = tipoHora;
	}

	public String getTipoMano() {
		return this.tipoMano;
	}

	public void setTipoMano(String tipoMano) {
		this.tipoMano = tipoMano;
	}

	public BigDecimal getTotalHora() {
		return this.totalHora;
	}

	public void setTotalHora(BigDecimal totalHora) {
		this.totalHora = totalHora;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Cliente getCliente() {
		return cliente;
	}
	
} 