package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "FP_SEQ", sequenceName = "FP_SEQ", initialValue = 1, allocationSize = 1)


public class FichaPersonal implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FP_SEQ") 
	private int id;
	
	private String primerNombre;
	private String segundoNombre;
	private String primerApellido;
	private String segundoApellido;
	private String ciudadania;
	
	@Temporal(TemporalType.DATE) 
	private Date nacimiento;
	@Temporal(TemporalType.DATE) 
	private Date ingreso;
	
	private String cedula;
	private String credencialCivica;
	
	
	
	
	
	
	
	
	
	public FichaPersonal() {
		super();
	}
   
}
