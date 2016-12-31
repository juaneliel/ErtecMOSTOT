package model;

import java.io.Serializable;
import javax.persistence.*;
 
@Entity
@Table(name="CuentasCorrientes")
@NamedQuery(name="CuentasCorriente.findAll", query="SELECT c FROM CuentasCorriente c")

 
@SequenceGenerator(name = "CC_SEQ", sequenceName = "CuentaCorriente_SEQ", initialValue = 1349, allocationSize = 1)



public class CuentasCorriente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CC_SEQ")	
	@Column(name="Id")
	private int id;

	@Column(name="ClienteID")
	private int clienteID;

	@Column(name="CuentaID")
	private int cuentaID;

	public CuentasCorriente() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getClienteID() {
		return this.clienteID;
	}

	public void setClienteID(int clienteID) {
		this.clienteID = clienteID;
	}

	public int getCuentaID() {
		return this.cuentaID;
	}

	public void setCuentaID(int cuentaID) {
		this.cuentaID = cuentaID;
	}

}