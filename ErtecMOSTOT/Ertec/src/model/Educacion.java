package model;

import java.io.Serializable;
import javax.persistence.*;
 
@Entity
@SequenceGenerator(name = "EDU_SEQ", sequenceName = "EDU_SEQ", initialValue = 1, allocationSize = 1)
public class Educacion implements Serializable {	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EDU_SEQ") 
	private int educacionID;
	private String instituto;
	private String anios;
	private String titulo;
	private String pathArchivo; 
  public Educacion() {
		super();
	}
 
	public int getEducacionID() {
		return educacionID;
	}

	public void setEducacionID(int educacionID) {
		this.educacionID = educacionID;
	}

	 
	public String getInstituto() {
		return instituto;
	}

	public void setInstituto(String instituto) {
		this.instituto = instituto;
	}

	public String getAnios() {
		return anios;
	}

	public void setAnios(String anios) {
		this.anios = anios;
	}

	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getPathArchivo() {
		return pathArchivo;
	}
	public void setPathArchivo(String pathArchivo) {
		this.pathArchivo = pathArchivo;
	}


}
