package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


@Entity
@Table(name="Clientes")
@NamedQuery(name="Cliente.findAll", query="SELECT c FROM Cliente c ORDER BY c.clienteID DESC") 

@SequenceGenerator(name = "CLI_SEQ", sequenceName = "CLI_SEQ", initialValue = 10000, allocationSize = 1)


public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CLI_SEQ")		
	@Column(name="ClienteID")
	private int clienteID;

	@Column(name="NomCliente")
	private String nombre;
	
	
//	@OneToOne(fetch=FetchType.EAGER)
//	@JoinColumn(name="ClienteID" , insertable = false, updatable = false)
//	private CuentasCorriente cuentacorriente;
	
	@OneToMany (fetch=FetchType.LAZY)
	@JoinColumn(name="ClienteID", referencedColumnName="ClienteID")
	private List<Contrato> contratos;

	@Column(name="Credito")
	private String credito;

	@Column(name="CuentaCorriente")
	private int cuentaCorriente;

	@Column(name="DirCliente")
	private String dirCliente;

	@Column(name="LocCliente")
	private String locCliente;
 

	@Column(name="RucCliente")
	private String rucCliente;

	@Column(name="TelCliente")
	private String telCliente;

	//bi-directional many-to-one association to Reclamo
	@OneToMany(mappedBy="cliente")
	private List<Reclamo> reclamos;

	public Cliente() {
	}

	public int getClienteID() {
		return this.clienteID;
	}

	public void setClienteID(int clienteID) {
		this.clienteID = clienteID;
	}

	public String getCredito() {
		return this.credito;
	}

	public void setCredito(String credito) {
		this.credito = credito;
	}

	public int getCuentaCorriente() {
		return this.cuentaCorriente;
	}

	public void setCuentaCorriente(int cuentaCorriente) {
		this.cuentaCorriente = cuentaCorriente;
	}

	public String getDirCliente() {
		return this.dirCliente;
	}

	public void setDirCliente(String dirCliente) {
		this.dirCliente = dirCliente;
	}

	public String getLocCliente() {
		return this.locCliente;
	}

	public void setLocCliente(String locCliente) {
		this.locCliente = locCliente;
	}



	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Contrato> getContratos() {
		return contratos;
	}

	public void setContratos(List<Contrato> contratos) {
		this.contratos = contratos;
	}

	public String getRucCliente() {
		return this.rucCliente;
	}

	public void setRucCliente(String rucCliente) {
		this.rucCliente = rucCliente;
	}

	public String getTelCliente() {
		return this.telCliente;
	}

	public void setTelCliente(String telCliente) {
		this.telCliente = telCliente;
	}

	public List<Reclamo> getReclamos() {
		return this.reclamos;
	}

	public void setReclamos(List<Reclamo> reclamos) {
		this.reclamos = reclamos;
	}

	public Reclamo addReclamo(Reclamo reclamo) {
		getReclamos().add(reclamo);
		reclamo.setCliente(this);

		return reclamo;
	}

	public Reclamo removeReclamo(Reclamo reclamo) {
		getReclamos().remove(reclamo);
		reclamo.setCliente(null);

		return reclamo;
	}

}