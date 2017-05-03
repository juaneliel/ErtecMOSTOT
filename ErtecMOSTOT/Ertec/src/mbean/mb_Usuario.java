package mbean;


import com.lowagie.text.BadElementException;





import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;  

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.primefaces.component.export.PDFOptions;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;

//import com.itextpdf.text.Document;
//import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import enumerados.EnumAccesoPagina;
import model.Articulo;
import model.Cliente;
import model.Cod_Movimiento;
import model.Contrato;
import model.Funcionario;
import model.Proveedores;
import model.Usuario;
import model.UsuarioLogin;
import model.VentaContado;
import model.DAO.DAO_Articulo;
import model.DAO.DAO_Cliente;
import model.DAO.DAO_Funcionario;
import model.DAO.DAO_Movimiento;
import model.DAO.DAO_OT;
import model.DAO.DAO_Reclamo;
import model.DAO.DAO_Usuario;

import javax.faces.bean.SessionScoped;
import util.JpaUtil;

@ManagedBean(name="mb_Usuario")
@SessionScoped
public class mb_Usuario {

	@Inject
	private UsuarioLogin usuarioLogueado;
	
	private String nombre;
	private String pass;
	private EnumAccesoPagina articulos; 
	private EnumAccesoPagina clientes; 
	private EnumAccesoPagina funcionarios;
	private EnumAccesoPagina movimientos;
	private EnumAccesoPagina ot;
	private EnumAccesoPagina proveedores;
	private EnumAccesoPagina reclamos;
	private EnumAccesoPagina manodeobra;
	private EnumAccesoPagina admin; 
	private String email;
	private String claveVieja;
	private String claveNueva;
	private Usuario auxUser=new Usuario();
	private DAO_Usuario dao=new DAO_Usuario();	
	private List<Boolean> viewFuncionarios;
	private ArrayList<Usuario> lista;
	private ArrayList<Funcionario> listaFuncionariosOBJ;
	private ArrayList<Articulo> listaArticulosOBJ;
	private ArrayList<Cliente> listaClientesOBJ;
	private ArrayList<Proveedores> listaProveedoresOBJ;
	private ArrayList<Contrato> listaContratosOBJ;
	private ArrayList<VentaContado> listaVCOBJ;
	private Map<Integer,Cod_Movimiento> mapaCodMov = new HashMap<Integer, Cod_Movimiento>(); 
	

	 
	
/***************Seccion  logueado********************/
	
	//se usa cuando el usuario se loguea o cuando se modifican sus accesos
	private void actualizarAccesosLogueado(Usuario u){
		 email=u.getEmail();
		 usuarioLogueado.actualizarAccesos(u);
	}
	
	public boolean  acceso(String nivelRequerido,String recurso){
		return this.usuarioLogueado.acceso(nivelRequerido,recurso); 
	} 
	
	public String loguear(){
		System.out.println("login>"+nombre);
		Usuario u=DAO_Usuario.login(nombre, pass);		
		if (u!=null){
			RequestContext context = RequestContext.getCurrentInstance();
			context.addCallbackParam("loggedIn", true);
			actualizarAccesosLogueado(u);
			return this.usuarioLogueado.getPaginaReingreso();
			//return "/paginas/reclamos.xhtml?faces-redirect=true";//cambiar a una pagina standar
		}
		else{			
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error Ingreso","Corrija la contraseña o el usuario ");
				context.addMessage(null,mensaje);
		}
		return null;		
	}
	  
	public String logout (){
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		
//			HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
//				.getExternalContext().getSession(false);
//				session.invalidate();

		RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam("loggedIn", false);
		this.usuarioLogueado.setNombre(null);
		return "/login.xhtml?faces-redirect=true";
	} 
	
	

	 
  @PostConstruct  
  public void init(){
    customizationOptions();
    initViewFuncionarios();
    recargarLista();
    FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("es"));
    cargarCodMov();    
  }
	
  private void cargarCodMov(){
  	 mapaCodMov =DAO_Movimiento.cargarCodMov();
  }
  
  public void onIdle() {
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
                                    "Sin actividad.", "Deberas loguearte nuevamente"));
    return ;//"/paginas/login.xhtml?faces-redirect=true"; 
}

  public String addUsuario(){
  	String salida=null;
  	if (dao.add(this.auxUser)){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Agregado", "Se agrego "+auxUser.getNombre());
	    FacesContext.getCurrentInstance().addMessage(null, message);
			salida=  "/paginas/usuarios.xhtml?faces-redirect=true";
		}
		else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo agregar intente con otro nombre de usuario");
	    FacesContext.getCurrentInstance().addMessage(null, message);
		}
		System.out.println(">>AGREGAR"+auxUser.getNombre());
		recargarLista ();
		auxUser=new Usuario();
		return salida;
  }
  
	public void onActive() {
	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
	                                    "Bienvenido nuevamente", "Logueate por favor"));
	}

	private void initViewFuncionarios(){
	  viewFuncionarios=Arrays.asList(true, true, true, true, false,false, false, false, true, false, false,
	      false, false, false,false   ,false,false);
	}	
	
  public List<Boolean> getViewFuncionarios() {
    return viewFuncionarios;
  }  
  
  public void setViewFuncionarios(List<Boolean> viewFuncionarios) {
    this.viewFuncionarios = viewFuncionarios;
  }
  
  public void onToggleFuncionarios(ToggleEvent e) {
    viewFuncionarios.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
  }  
 
  
	public void delete(Usuario f){ 
		if (dao.delete(f) ){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Borrado", "Se elimino el funcionario "+f.getNombre());
	        FacesContext.getCurrentInstance().addMessage(null, message);			
		}
		else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error no se elimino el funcionario "+f.getNombre());
	        FacesContext.getCurrentInstance().addMessage(null, message);			
		}
    recargarLista ();
	}
  
  public boolean igualNombre(String nom){
  	System.out.println(">>>igualnombre: "+nombre+" "+nom);
  	return nom.toUpperCase().equals(this.nombre.toUpperCase());
  }
  
	public void onRowEdit(RowEditEvent event) {
		
		Usuario u= (Usuario) event.getObject();
		
		System.out.println("entro en usuario update");
  	if(dao.update(u)){    		 
  		if(u.getNombre().toUpperCase().equals(this.nombre.toUpperCase())){
  			this.actualizarAccesosLogueado(u); 
  			System.out.println("verclientOnrowEdit: "+clientes);
  		}
  		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizado", "Se actualizo el Usuario "+u.getNombre());
          FacesContext.getCurrentInstance().addMessage(null, message);           
  	}
  	else{
  		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo actualizar");
          FacesContext.getCurrentInstance().addMessage(null, message);           
  	}  	
      
  }
  
  public void recargarLista(){
  	this.lista=dao.getLista();
  }

  
  
  public String updateClave(){
  	if(dao.updateClave(this.usuarioLogueado, claveVieja, claveNueva,email)){
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizada","Los datos se han modificado");
			context.addMessage(null,mensaje);
			this.nombre="";
			this.claveVieja="";
			this.claveNueva="";
			return "/paginas/usuarios.xhtml?faces-redirect=true";
  	}
  	else{
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error","No se pudo actualizar los datos");
			context.addMessage(null,mensaje);
  	}
  	return null;
  }

 
  
  
  






	public UsuarioLogin getUsuario() {
		return usuarioLogueado;
	}

	public void setUsuario(UsuarioLogin usuario) {
		this.usuarioLogueado = usuario;
	}


	
	

	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getPass() {
		return pass;
	}


	public void setPass(String pass) {
		this.pass = pass;
	}



	

	//se le pasa el nivel que se pretende y luego el acceso que tiene el usuario logueado
	//se puede mejorar usando loginUser como el logueado actual

	


	public EnumAccesoPagina getArticulos() {
		return articulos;
	}


	public void setArticulos(EnumAccesoPagina articulos) {
		this.articulos = articulos;
	}


	public EnumAccesoPagina getClientes() {
		return clientes;
	}


	public void setClientes(EnumAccesoPagina clientes) {
		this.clientes = clientes;
	}


	public EnumAccesoPagina getFuncionarios() {
		return funcionarios;
	}


	public void setFuncionarios(EnumAccesoPagina funcionarios) {
		this.funcionarios = funcionarios;
	}


	public EnumAccesoPagina getMovimientos() {
		return movimientos;
	}


	public void setMovimientos(EnumAccesoPagina movimientos) {
		this.movimientos = movimientos;
	}


	public EnumAccesoPagina getOt() {
		return ot;
	}


	public void setOt(EnumAccesoPagina ot) {
		this.ot = ot;
	}


	public EnumAccesoPagina getProveedores() {
		return proveedores;
	}


	public void setProveedores(EnumAccesoPagina proveedores) {
		this.proveedores = proveedores;
	}


	public EnumAccesoPagina getAdmin() {
		return admin;
	}


	public void setAdmin(EnumAccesoPagina admin) {
		this.admin = admin;
	}


	public EnumAccesoPagina getReclamos() {
		return reclamos;
	}


	public void setReclamos(EnumAccesoPagina reclamos) {
		this.reclamos = reclamos;
	}


 


	public EnumAccesoPagina getManodeobra() {
		return manodeobra;
	}


	public void setManodeobra(EnumAccesoPagina manodeobra) {
		this.manodeobra = manodeobra;
	}


	public ArrayList<Usuario> getLista() {
		return lista;
	}


	public void setLista(ArrayList<Usuario> lista) {
		this.lista = lista;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
	public ArrayList <Articulo> completarArticulo(String query){
		this.listaArticulosOBJ=DAO_Articulo.completarArticulo(query);
		return listaArticulosOBJ;
	}
	
	public ArrayList <Cliente> completarCliente(String query){
		this.listaClientesOBJ = DAO_Cliente.completarCliente(query); 
		return listaClientesOBJ;		
	}
	
	public ArrayList <VentaContado> completarVC(String query){
		this.listaVCOBJ = DAO_Reclamo.completarVC(query); 
		return listaVCOBJ;		
	}
	
	public ArrayList <Funcionario> completarFuncionario(String query){
		this.listaFuncionariosOBJ=DAO_Funcionario.completarFuncionario(query);
		return listaFuncionariosOBJ;
	}

	public ArrayList <Proveedores> completarProveedor(String query){
		this.listaProveedoresOBJ=DAO_OT.completarProveedor(query);
		return listaProveedoresOBJ;
	} 
	
	public ArrayList<Funcionario> getListaFuncionariosOBJ() {
		return listaFuncionariosOBJ;
	}

	public void setListaFuncionariosOBJ(ArrayList<Funcionario> listaFuncionariosOBJ) {
		this.listaFuncionariosOBJ = listaFuncionariosOBJ;
	}

	public Usuario getAuxUser() {
		return auxUser;
	}

	public void setAuxUser(Usuario auxUser) {
		this.auxUser = auxUser;
	}

	public String getClaveVieja() {
		return claveVieja;
	}

	public void setClaveVieja(String claveVieja) {
		this.claveVieja = claveVieja;
	}

	public String getClaveNueva() {
		return claveNueva;
	}

	public void setClaveNueva(String claveNueva) {
		this.claveNueva = claveNueva;
	}

	public ArrayList<Articulo> getListaArticulosOBJ() {
		return listaArticulosOBJ;
	}

	public UsuarioLogin getUsuarioLogueado() {
		return usuarioLogueado;
	}

 

	public void setListaArticulosOBJ(ArrayList<Articulo> listaArticulosOBJ) {
		this.listaArticulosOBJ = listaArticulosOBJ;
	}

	public ArrayList<Cliente> getListaClientesOBJ() {
		return listaClientesOBJ;
	}

	public void setListaClientesOBJ(ArrayList<Cliente> listaClientesOBJ) {
		this.listaClientesOBJ = listaClientesOBJ;
	}

	public void setUsuarioLogueado(UsuarioLogin usuarioLogueado) {
		this.usuarioLogueado = usuarioLogueado;
	}
	
	
	
  public ArrayList<Proveedores> getListaProveedoresOBJ() {
		return listaProveedoresOBJ;
	}

	public void setListaProveedoresOBJ(ArrayList<Proveedores> listaProveedoresOBJ) {
		this.listaProveedoresOBJ = listaProveedoresOBJ;
	}

//	public void imprimir(){
//  	Document documento = new Document(PageSize.A4);
//  	ByteArrayOutputStream baos= new ByteArrayOutputStream();
//  	try{
//  		PdfWriter.getInstance(documento, baos);
//  		documento.open();
//  		documento.add( new Paragraph("Hola") );
//  	}
//  	catch(Exception e){
//  		e.printStackTrace();	  		
//  	}
//  	documento.close();
//  	FacesContext contexto = FacesContext.getCurrentInstance();
//  	Object response = contexto.getExternalContext().getResponse();
//  	if(response instanceof HttpServletResponse){
//  		HttpServletResponse hsr = (HttpServletResponse) response;
//  		hsr.setContentType("application/pdf");
//  		hsr.setHeader("Content-disposition","attachment; filename=listaot.pdf");
//  		hsr.setContentLength(baos.size());
//  		try {
//				ServletOutputStream out = hsr.getOutputStream();
//				baos.writeTo(out);
//				out.flush();					
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//  		contexto.responseComplete();	  		
//  	}	  	
//  }

	public ArrayList<Contrato> getListaContratosOBJ() {
		return listaContratosOBJ;
	}

	public void setListaContratosOBJ(ArrayList<Contrato> listaContratosOBJ) {
		this.listaContratosOBJ = listaContratosOBJ;
	}
	public void recargarListaContratos(int cliID){
		System.out.println("recargarlista");
		this.listaContratosOBJ=DAO_Cliente.getContratosCliente(cliID);		
	}

	public Map<Integer, Cod_Movimiento> getMapaCodMov() {
		return mapaCodMov;
	}

	public void setMapaCodMov(Map<Integer, Cod_Movimiento> mapaCodMov) {
		this.mapaCodMov = mapaCodMov;
	}

	public ArrayList<VentaContado> getListaVCOBJ() {
		return listaVCOBJ;
	}

	public void setListaVCOBJ(ArrayList<VentaContado> listaVCOBJ) {
		this.listaVCOBJ = listaVCOBJ;
	}
	
	public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
	 
			try
			{
			System.out.println("working on pdf");
			Document pdf = (Document) document;
			pdf.setPageSize(PageSize.A4);
			pdf.setMargins(-40,-40,30,30); 

//			Phrase phraseBefore = new Phrase("                       Fecha "+new Date());
//			Phrase phraseAfter = new Phrase("dale");
			
//			HeaderFooter header = new HeaderFooter(phraseBefore, false);
//			HeaderFooter footer = new HeaderFooter(phraseAfter, false);			
//			pdf.setHeader(header);
//			pdf.setFooter(footer);
			pdf.open();

//			pdf.add(phraseBefore);
			
			
			 
			} catch (Exception e)
			{
			System.out.println(e.toString());
			}

			
}
	 private PDFOptions pdfOpt;
	 public void customizationOptions() {       
    pdfOpt = new PDFOptions();
    pdfOpt.setFacetBgColor("#eeeeee");
    pdfOpt.setFacetFontColor("#000000");
    pdfOpt.setFacetFontSize("8");
    pdfOpt.setFacetFontStyle("BOLD");
    pdfOpt.setCellFontSize("7");
}

	public PDFOptions getPdfOpt() {
		return pdfOpt;
	}

	public void setPdfOpt(PDFOptions pdfOpt) {
		this.pdfOpt = pdfOpt;
	}
	
}
