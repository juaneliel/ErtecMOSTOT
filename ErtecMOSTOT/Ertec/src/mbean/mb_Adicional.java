package mbean;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.Column;

import model.Adicional;
import model.Funcionario;
import model.Ot ;
import model.DAO.DAO_Adicional;

public class mb_Adicional {

	private int adicionalID; 
	private String especificacion; 
	private DAO_Adicional daoAdi = new DAO_Adicional();
	
	public String addAdicional (Adicional a, Ot ot){
		daoAdi.addAdicional(a, ot);
		return null;
	}	
	public void deleteAdicional(Adicional a){
		System.out.println("borrar>>>"+a.getAdicionalID());
		daoAdi.deleteAdicional(a);
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Borrado", "Se elimino el funcionario "+a.getAdicionalID());
    FacesContext.getCurrentInstance().addMessage(null, message);
	}	
	public void findAdicional (){
		
	}
	public int getAdicionalID() {
		return adicionalID;
	}
	public void setAdicionalID(int adicionalID) {
		this.adicionalID = adicionalID;
	}
	public String getEspecificacion() {
		return especificacion;
	}
	public void setEspecificacion(String especificacion) {
		this.especificacion = especificacion;
	}	
}
