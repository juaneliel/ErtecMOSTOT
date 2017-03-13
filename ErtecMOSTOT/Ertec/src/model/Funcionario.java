package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import model.FichaPersonal;

import javax.persistence.*;

@Entity
@Table(name="Funcionarios")
@NamedQuery(name="Funcionario.findAll", query="SELECT f FROM Funcionario f")

@SequenceGenerator(name = "FUN_SEQ", sequenceName = "FUNCIONARIO_SEQ", initialValue = 101, allocationSize = 1)

public class Funcionario implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name="FuncionarioID")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FUN_SEQ") 
  private int funcionarioID;
 
	@Lob()
	private byte[] foto;
  
	@OneToOne(fetch=FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinColumn(name="fichaID" , insertable = false, updatable = false)
	private FichaPersonal ficha;
	
	@OneToMany (fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name="funcionarioID", referencedColumnName="funcionarioID")
	private List<ActividadAnterior> actividadAnteriores;
	
	@OneToMany (fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name="funcionarioID", referencedColumnName="funcionarioID")
	private List<Capacitacion> capacitaciones;
	
	@OneToMany (fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name="funcionarioID", referencedColumnName="funcionarioID")
	private List<Educacion> educaciones;
  
  private int fichaID; 
	public List<ActividadAnterior> getActividadAnteriores() {
		return actividadAnteriores;
	}

	public void setActividadAnteriores(List<ActividadAnterior> actividadAnteriores) {
		this.actividadAnteriores = actividadAnteriores;
	}

	public List<Capacitacion> getCapacitaciones() {
		return capacitaciones;
	}

	public void setCapacitaciones(List<Capacitacion> capacitaciones) {
		this.capacitaciones = capacitaciones;
	}

	public List<Educacion> getEducaciones() {
		return educaciones;
	}

	public void setEducaciones(List<Educacion> educaciones) {
		this.educaciones = educaciones;
	}
 
  
  @Column(name="Cedula")
  private String cedula;  
  @Temporal(TemporalType.DATE)
  @Column(name="VencimientoCedula")
  private Date vencimientoCedula;
  @Column(name="LibretaCat")
  private String libretaCat;   
  @Temporal(TemporalType.DATE)
  @Column(name="VencimientoLibreta")
  private Date vencimientoLibreta;
  private String activo;
  private String alias;
  private String area;
  private String cat;
  private String direccion;
  private String email;
  private int idEnService;
  private double lat;
  private double lng;
  private String nombre;
  private String telefono;
  
  public Funcionario() {
  	
  }

  @Temporal(TemporalType.DATE)
  @Column(name="CarneSalud")
  private Date carneSalud;
  
  @Temporal(TemporalType.DATE)
  @Column(name="Nacimiento")
  private Date nacimiento;
  
  public int getFuncionarioID() {
    return this.funcionarioID;
  }

  public void setFuncionarioID(int funcionarioID) {
    this.funcionarioID = funcionarioID;
  }

  public String getActivo() {
    return this.activo;
  }

  public void setActivo(String activo) {
    this.activo = activo;
  }

  public String getAlias() {
    return this.alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public String getArea() {
    return this.area;
  }

 

	public int getFichaID() {
		return fichaID;
	}

	public void setFichaID(int fichaID) {
		this.fichaID = fichaID;
	}

	public void setArea(String area) {
    this.area = area;
  }

  public String getCat() {
    return this.cat;
  }

  public void setCat(String cat) {
    this.cat = cat;
  }

  public String getDireccion() {
    return this.direccion;
  }

  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getIdEnService() {
    return this.idEnService;
  }

  public void setIdEnService(int idEnService) {
    this.idEnService = idEnService;
  }

  public double getLat() {
    return this.lat;
  }

  public void setLat(double lat) {
    this.lat = lat;
  }

  public double getLng() {
    return this.lng;
  }

  public void setLng(double lng) {
    this.lng = lng;
  }

  public String getNombre() {
    return this.nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getTelefono() {
    return this.telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public Date getCarneSalud() {
    return carneSalud;
  }

  public void setCarneSalud(Date carneSalud) {
    this.carneSalud = carneSalud;
  }

  public Date getNacimiento() {
    return nacimiento;
  }

  public void setNacimiento(Date nacimiento) {
    this.nacimiento = nacimiento;
  }

  public String getCedula() {
    return cedula;
  }

  public void setCedula(String cedula) {
    this.cedula = cedula;
  }

  public Date getVencimientoCedula() {
    return vencimientoCedula;
  }

  public void setVencimientoCedula(Date vencimientoCedula) {
    this.vencimientoCedula = vencimientoCedula;
  }

  public String getLibretaCat() {
    return libretaCat;
  }

  public void setLibretaCat(String libretaCat) {
    this.libretaCat = libretaCat;
  }

  public Date getVencimientoLibreta() {
    return vencimientoLibreta;
  }

  public void setVencimientoLibreta(Date vencimientoLibreta) {
    this.vencimientoLibreta = vencimientoLibreta;
  }

	public FichaPersonal getFicha() {
		return ficha;
	}

	public void setFicha(FichaPersonal ficha) {
		this.ficha = ficha;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

}





 


 