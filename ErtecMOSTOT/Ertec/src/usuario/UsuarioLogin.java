package usuario;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.Column;

@Named
@SessionScoped
public class UsuarioLogin implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nombre;  
	private byte puedeActualizar;  
	private byte puedeBorrar;
	
	public boolean estaLogueado(){
		return this.nombre!=null;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public byte getPuedeActualizar() {
		return puedeActualizar;
	}

	public void setPuedeActualizar(byte puedeActualizar) {
		this.puedeActualizar = puedeActualizar;
	}

	public byte getPuedeBorrar() {
		return puedeBorrar;
	}

	public void setPuedeBorrar(byte puedeBorrar) {
		this.puedeBorrar = puedeBorrar;
	}

	 
	
	
	
	
}
