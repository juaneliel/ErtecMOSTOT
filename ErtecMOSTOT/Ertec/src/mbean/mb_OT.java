package mbean;
 
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.ExportarOTPDF;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter; 
import javafx.scene.transform.Rotate;
import javax.faces.bean.ManagedBean; 
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import model.Adicional; 
import model.Articulo;
import model.Cliente;
import model.ComprasExternasOT; 
import model.Funcionario;
import model.ManoObra;
import model.Movimiento;
import model.Ot;
import model.Proveedores;
import model.TipoOT;
import model.DAO.DAO_Cliente;
import model.DAO.DAO_ManoObra;
import model.DAO.DAO_Movimiento;
import model.DAO.DAO_OT;  


@ManagedBean(name="mb_OT", eager = true)
@ViewScoped
public class mb_OT {

	private DAO_OT dao=new DAO_OT(); 
 	private int numeroID=50; 
	private int areaID; 
	private int arrendamiento; 
	private int c; 
	private int c_Corriente; 
	private String cliente; 
	private BigDecimal factura=BigDecimal.ZERO; 
	private Date fechaFacturada; 
	private Date fechaInicio; 
	private Date fechaTerminada; 
	private int mantenimiento; 
	private int nro_Cliente; 
	private int oC; 
	private int pedido; 
	private BigDecimal presupuesto=BigDecimal.ZERO; 
	private String proceso; 
	private int r; 
	private int tipoID; 
	private String trabajo;	
	private String direccionObra;
	private int verifAdm;
	private String telObra;
	private String nombreArticulo;
	private int CExternaID; 
	private int articuloID; 
	private int cantidad; 
	private Date fecha; 
	private String moneda; 
	private BigDecimal precio_Unitario=BigDecimal.ZERO; 
	private int proveedorID;	
	private int adicionalOTID;
	private String especificacionAdicional;
	private int adicionalID;	
 	private Articulo articuloOBJ;
//	private ArrayList<Articulo> listaArticulosOBJ=new ArrayList<Articulo>(); 
	private Ot otSelected;
	private Adicional adiSelected;
	private Movimiento movSelected;
	private ComprasExternasOT ceSelected;
	private ManoObra moSelected;	
	private Proveedores proveedorOBJ;
	private ArrayList<Proveedores> listaProveedoresOBJ; 	
	private ArrayList<TipoOT>  listaTiposOT;	
	private Funcionario funcionarioOBJ;
	private Cliente clienteOBJ;	
	private String urlImpresion;
	private ArrayList<Ot> lista=new ArrayList<Ot>();
	private ArrayList<Movimiento> listaMovimientos=new ArrayList<Movimiento>();
	private ArrayList<ManoObra> listaManoObra=new ArrayList<ManoObra>();
	private ArrayList<Adicional> listaAdicionales=new ArrayList<Adicional>();
	private ArrayList<ComprasExternasOT> listaComprasExternas=new ArrayList<ComprasExternasOT>();
	private ComprasExternasOT compraexternaAdd=new ComprasExternasOT();
	private Movimiento movimientoAdd = new Movimiento();
	private ManoObra manodeobraAdd = new ManoObra();
	private Adicional adicionalAdd=new Adicional();
	private Date fechaIni;
	private Date fechaFin;
	
	
	public Cliente getClienteOBJ() {
		return clienteOBJ;
	}

	public void setClienteOBJ(Cliente clienteOBJ) {
		this.clienteOBJ = clienteOBJ;
	}
	
	
	public String urlImprimirOT(int otID){
		HttpServletRequest servletContext = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
  	String realPath=(String) servletContext.getServletPath(); 
  	System.out.println("contexto addnexo: "+realPath+" "+servletContext+" "+servletContext.getContextPath()+" "+servletContext.getRequestURI());
		
		
		//ExportarOTPDF.ExportarPDF(ot, "");
	  this.urlImpresion=  "/ertec/exportarpdf?"+"&id="+otID+"&tipo=ot";
	  return urlImpresion;
	}
	
	public String urlImprimirMov(int otID){
		//ExportarOTPDF.ExportarPDF(ot, "");
		 this.urlImpresion=  "/ertec/exportarpdf?faces-redirect=true"+"&id="+otID+"&tipo=mov";
		 System.out.println("url impresion mov "+this.urlImpresion);
		 return urlImpresion;
	}
	
	@PostConstruct	
	public void init(){
		this.recargar();
		this.listaTiposOT=dao.getTiposOT();
	}
	
	private void recargar(){
		this.lista=dao.getLista();		
	}
	
	
	public void filtrarPorFechasManoObraOT(){		
		this.listaManoObra=DAO_ManoObra.getFiltradaPorFechasYOT(fechaIni, fechaFin, this.otSelected.getId());
	}
	
	
	//modificar al hacer onetomany
	public void recargarListaMovimientos(int idOT){
		System.out.println("<<< oiitd "+idOT);
		this.listaMovimientos = dao.getListaMovimientosOT(idOT);
		System.out.println("<<< listamovimientos size= "+this.listaMovimientos.size());
		
//		 Map<String,Object> options = new HashMap<String, Object>();
//	        options.put("resizable", false);
//	        options.put("draggable", false);
//	        options.put("modal", true);
		
   }
	
	
	
	
	
	//modificar al hacer onetomany
	public void recargarListaManoObra(int idOT){
		System.out.println("<<< oiitd "+idOT);
		this.listaManoObra = dao.getListaManoObraOT(idOT);
		System.out.println("<<< lista mano de obra size= "+this.listaManoObra.size());
	}
	
	public void recargarAdiocionalSelected(){
		this.numeroID=otSelected.getId();
		this.adicionalAdd.setOtid(numeroID);
		this.listaAdicionales = dao.getAdicionales(this.otSelected.getId());
	}
	
	public void recargarCESelected(){
		this.numeroID=otSelected.getId();
		this.compraexternaAdd.setOtid(numeroID);
		this.listaComprasExternas=dao.getComprasExternas(numeroID);
	}

	
	public void recargarMovimientoSelected(){
		this.numeroID=otSelected.getId();
		this.movimientoAdd.setReferencia(numeroID);
		this.listaMovimientos=dao.getListaMovimientosOT(this.otSelected.getId());
	}
	
	
//	
//	public void recargarListaAdicionales(int idOT){
//		System.out.println("<<< oiitd "+idOT);
//		this.listaAdicionales = dao.getAdicionales(idOT);
//		System.out.println("<<< lista adicionales size= "+this.listaAdicionales.size());
//	}
	
	public void recargarListaComprasExternas(int idOT){
		System.out.println("<<< oiitd "+idOT);
		this.listaComprasExternas  = dao.getComprasExternas(idOT);
		System.out.println("<<< lista compras externas size= "+this.listaComprasExternas.size());
	}
	
//	public void recargarCombo(int otID){
//		this.recargarListaManoObra(otID);
//		this.recargarListaMovimientos(otID);
//		this.recargarListaAdicionales(otID);
//		this.recargarListaComprasExternas(otID);
//	}

	
	public void onRowEdit(RowEditEvent event) {
		
//		Funcionario f= (Funcionario) event.getObject();
//		
//		
//    	if(dao.update(f)){    		
//    		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizado", "Se actualizo el funcionario "+f.getNombre());
//            FacesContext.getCurrentInstance().addMessage(null, message); 
//            recargarListaFuncionarios();
//    	}
//    	else{
//    		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo actualizar");
//            FacesContext.getCurrentInstance().addMessage(null, message);           
//    	}  	
        
    }
	
	
	public String add(){
		String salida=null;
		Ot ot = new Ot();
		
		ot.setC(this.c);
		
		if(clienteOBJ!=null){
			ot.setClienteNombre(this.clienteOBJ.getNombre());
			ot.setNroCliente(clienteOBJ.getClienteID());
		}
		
		ot.setDireccionObra(this.direccionObra);
		ot.setTelObra(telObra);
		ot.setFactura(factura);
		ot.setFechaFacturada(fechaFacturada);
		ot.setFechaInicio(fechaInicio);
		ot.setFechaTerminada(fechaTerminada);
		ot.setMantenimiento(mantenimiento);
		ot.setOC(oC);
		ot.setPedido(pedido);
		ot.setPresupuesto(presupuesto);
		ot.setProceso(proceso);
		ot.setTipoID(tipoID);
		ot.setTrabajo(trabajo);
		ot.setVerifAdm(verifAdm);
		
		
		
		if (dao.add (ot)){
			salida= "/paginas/listot.xhtml?faces-redirect=true";
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Agregado", "La Ot se agrego exitosamente");
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}
		else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Agregado", "Error al agregar OT");
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}
		System.out.println(">>AGREGAR"+ot.getId() );
		recargar ();
		return salida;
	}
	
	public void delete(Ot f){ 
		if (dao.delete(f) ){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Borrado", "Se elimino la OT "+f.getId());
      FacesContext.getCurrentInstance().addMessage(null, message);	
      this.lista.remove(f);
      DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
          .findComponent("formordendetrabajo:datatableordendetrabajo");
      if (dataTable != null) {
      	dataTable.reset();
      }   
		}
		else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error no se elimino la OT "+f.getId());
	        FacesContext.getCurrentInstance().addMessage(null, message);			
		}
		//return "/paginas/funcionarios.xhtml?faces-redirect=true";
        
	}
 
 
	
	
	
	
	
	
	
	
	
	
	
	
	public String addAdicional (){
		String salida=null;
		Adicional a=new Adicional();
		a.setEspecificacion(especificacionAdicional);
		a.setOtid(otSelected.getId());
		
		System.out.println("entro en addadicional");
		if(dao.addAdicionalOT(a)){
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Agregado","Se agrego el adicional a la OT");
			context.addMessage(null,mensaje);
			//this.recargarListaAdicionales(numeroID);
			this.listaAdicionales.add(a);
		}
		else{
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error","No se pudo agregar el adicional");
			context.addMessage(null,mensaje);
		}
		return salida;
	}
	
	public void editAdicional (RowEditEvent event) {
		
		Adicional adi= (Adicional) event.getObject();
		
		
    	if(dao.updateAdicional(adi)){    		
    		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizado", "Se actualizo el adicional "+adi.getAdicionalID());
            FacesContext.getCurrentInstance().addMessage(null, message); 
            //this.recargarListaAdicionales(numeroID);
    	}
    	else{
    		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo actualizar");
            FacesContext.getCurrentInstance().addMessage(null, message);           
    	}  	
        
    }
	
	public void editCompraExterna (RowEditEvent event) {
		
		ComprasExternasOT ce= (ComprasExternasOT) event.getObject();
		
		
    	if(dao.updateCompraExterna(ce)){    		
    		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizado", "Se actualizo la compra externa "+ ce.getId()  );
            FacesContext.getCurrentInstance().addMessage(null, message); 
            //this.recargarListaComprasExternas(numeroID);
    	}
    	else{
    		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo actualizar la compra externa");
            FacesContext.getCurrentInstance().addMessage(null, message);           
    	}  	
        
    }
	
	
	
	public void deleteAdicional(Adicional adi){
		
		if (dao.deleteAdicional(adi) ){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Borrado", "Se elimino el adicional  "+adi.getAdicionalID());
	        FacesContext.getCurrentInstance().addMessage(null, message);	
	    this.listaAdicionales.remove(adi);
		}
		else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error no se pudo eliminar "+adi.getAdicionalID());
	        FacesContext.getCurrentInstance().addMessage(null, message);			
		}
		//return "/paginas/funcionarios.xhtml?faces-redirect=true";
        //this.recargarListaAdicionales(numeroID);;
        System.out.println("entro en delete adicional");		
	}
	
//	public void deleteManoObra(ManoObra mo){
//		
//		if (dao.deleteManoObra(mo) ){
//			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Borrado", "Se elimino la mano de obra  "+mo.getManoObraID());
//	        FacesContext.getCurrentInstance().addMessage(null, message);			
//		}
//		else{
//			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error no se pudo eliminar "+mo.getManoObraID());
//	        FacesContext.getCurrentInstance().addMessage(null, message);			
//		}
//		//return "/paginas/funcionarios.xhtml?faces-redirect=true";
//        this.recargarListaAdicionales(numeroID);;
//        System.out.println("entro en delete adicional");
//	}
	
	
	
	
	//private int idOTCargar=5794;
	
	
	public void cargarOT(){
		System.out.println(dao.getOT( numeroID ));
		Ot ot = dao.getOT( numeroID);
		if (ot==null){
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error la ot no existe","La OT no existe en el sistema");
			context.addMessage(null,mensaje);
			
			limpiarCampos();
			//setFactura("Error la ot no existe");
			return;
		}
		
		
		//setCliente(ot.getCliente());
		setFactura(ot.getFactura());
		setFechaFacturada(ot.getFechaFacturada()); 
		if (this.fechaFacturada.equals("1900-01-01")){
			setFechaFacturada(null);
		}
		setFactura(ot.getFactura());
		setFechaInicio(ot.getFechaInicio());
		if (fechaInicio.equals("1900-01-01 00:00:00.0")){
			setFechaInicio(null);
		}
		setFechaTerminada(ot.getFechaTerminada() );
		if (this.fechaTerminada.equals("1900-01-01 00:00:00.0")){
			setFechaTerminada(null);
		}
		setNumeroID(ot.getId());
		setProceso(ot.getProceso());
		if(proceso.equals("E")){
			setProceso("Ejecucion");
		}
		setTrabajo(ot.getTrabajo());
		
		
		DAO_Movimiento dm= new DAO_Movimiento();	 
		
		
	}
		

	
	 
	
	
	public String getEspecificacionAdicional() {
		return especificacionAdicional;
	}

	public void setEspecificacionAdicional(String especificacionAdicional) {
		this.especificacionAdicional = especificacionAdicional;
	}

	public String addCompraExterna(){
		System.out.println("entro en compra");
		ComprasExternasOT ceot = new ComprasExternasOT();
		ceot.setNombreArticulo(nombreArticulo);
		ceot.setCantidad(cantidad);
		//ceot.setId(CExternaID);
		ceot.setFecha(fecha);
		ceot.setMoneda(moneda);
		ceot.setOtid(otSelected.getId());
		ceot.setPrecio_Unitario(precio_Unitario);
	  if(proveedorOBJ!=null){
			ceot.setProveedorID(proveedorOBJ.getProveedorID());	
		}
		if(articuloOBJ!=null){
			ceot.setArticuloID(articuloOBJ.getArticuloID());
		}		
		if(dao.addCompraExternaOT(ceot)){
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Agregado","Se agrego la compra externa a la OT");
			context.addMessage(null,mensaje);
			this.listaComprasExternas.add(ceot);
			//this.recargarListaComprasExternas(numeroID);
		}
		else{
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error","No se pudo agregar la compra externa");
			context.addMessage(null,mensaje);
		}		
		return null;
	} 

	public void deleteCompraExterna(ComprasExternasOT ce){
		
		if (dao.deleteCompraExterna(ce) ){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Borrado", "Se elimino la compra externa  "+ce.getId());
	        FacesContext.getCurrentInstance().addMessage(null, message);	
	        //this.recargarListaComprasExternas(numeroID);
	        this.listaComprasExternas.remove(ce);
		}
		else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error no se pudo eliminar "+ce.getId());
	        FacesContext.getCurrentInstance().addMessage(null, message);			
		}
		//return "/paginas/funcionarios.xhtml?faces-redirect=true"; 
        System.out.println("entro en delete ce");
		 
	}
	
	
	public void openAgregarCompraExterna(int otID){
		this.numeroID=otID;
		 Map<String,Object> options = new HashMap<String, Object>();
		 options.put("modal", true); 
	        options.put("contentWidth", "100%");
	        options.put("contentHeight", "100%");
	        options.put("headerElement", "customheader");
	         
	        System.out.println("entro en open addce");
		RequestContext.getCurrentInstance().openDialog("/templates/templateaddcompraexterna", options, null);
		System.out.println("paso en open addce");
	}
	
//	public ArrayList <Articulo> completarArticulo(String query){
//		this.listaArticulosOBJ=dao.completarArticulo(query);
//		return listaArticulosOBJ;
//	}
	
//	 public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
//     Document pdf = (Document) document;
//     pdf.open();
//     pdf.setPageSize(PageSize.A4.rotate());
// }
	

	
	 public List<String> completeText(String query) {
	        List<String> results = new ArrayList<String>();
	        for(int i = 0; i < 10; i++) {
	            results.add(query + i);
	        }
	         
	        return results;
	    }
	
	
	
	public void limpiarCampos(){
		setFactura(null);
		setCliente(null);  
		setFechaFacturada(null);   
		setFactura(null);  
		setFechaInicio(null);  
		setFechaTerminada(null);   
		setProceso("");  
		setTrabajo("");  
	}
	
    public ArrayList<TipoOT> getTiposOT(){
    	return dao.getTiposOT();
    }
	
	
	public String detailOT(Ot ot){
		this.numeroID = ot.getId();
		cargarOT();
		return "/paginas/detailot.xhtml?faces-redirect=true";
	}
	

	public DAO_OT getDto_Ot() {
		return dao;
	}

	public void setDto_Ot(DAO_OT dto_Ot) {
		this.dao = dto_Ot;
	}



	public int getNumeroID() {
		System.out.println("getnumeroidot:"+numeroID);
		return numeroID;
	}

	public void setNumeroID(int nID) {
		this.numeroID = nID;
		System.out.println("NUMEroid " + numeroID);
		
	}
	
	public void setearIDOT(int nID) {
		this.numeroID = nID;
		System.out.println("seteo NUMEroid " + numeroID);
	}
	

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	

	public String getProceso() {
		return proceso;
	}

	public void setProceso(String proceso) {
		this.proceso = proceso;
	}

	public String getTrabajo() {
		return trabajo;
	}

	public void setTrabajo(String trabajo) {
		this.trabajo = trabajo;
	}

	public ArrayList<Movimiento> getListaMovimientos() {
		return listaMovimientos;
	}

	public void setListaMovimientos(ArrayList<Movimiento> listaMovimientos) {
		this.listaMovimientos = listaMovimientos;
	}

 
	public int getAreaID() {
		return areaID;
	}

	public void setAreaID(int areaID) {
		this.areaID = areaID;
	}

	public int getArrendamiento() {
		return arrendamiento;
	}

	public void setArrendamiento(int arrendamiento) {
		this.arrendamiento = arrendamiento;
	}

	public int getC() {
		return c;
	}

	public void setC(int c) {
		this.c = c;
	}

	public int getC_Corriente() {
		return c_Corriente;
	}

	public void setC_Corriente(int c_Corriente) {
		this.c_Corriente = c_Corriente;
	}

	public BigDecimal getFactura() {
		return factura;
	}

	public void setFactura(BigDecimal factura) {
		this.factura = factura;
	}

	public Date getFechaFacturada() {
		return fechaFacturada;
	}

	public void setFechaFacturada(Date fechaFacturada) {
		this.fechaFacturada = fechaFacturada;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaTerminada() {
		return fechaTerminada;
	}

	public void setFechaTerminada(Date fechaTerminada) {
		this.fechaTerminada = fechaTerminada;
	}

	public int getMantenimiento() {
		return mantenimiento;
	}

	public void setMantenimiento(int mantenimiento) {
		this.mantenimiento = mantenimiento;
	}

	public int getNro_Cliente() {
		return nro_Cliente;
	}

	public void setNro_Cliente(int nro_Cliente) {
		this.nro_Cliente = nro_Cliente;
	}

	public int getoC() {
		return oC;
	}

	public void setoC(int oC) {
		this.oC = oC;
	}

	public int getPedido() {
		return pedido;
	}

	public void setPedido(int pedido) {
		this.pedido = pedido;
	}

	public BigDecimal getPresupuesto() {
		return presupuesto;
	}

	public void setPresupuesto(BigDecimal presupuesto) {
		this.presupuesto = presupuesto;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getTipoID() {
		return tipoID;
	}

	public void setTipoID(int tipoID) {
		this.tipoID = tipoID;
	}

	public ArrayList<Ot> getLista() {
		return lista;
	}

	public void setLista(ArrayList<Ot> lista) {
		this.lista = lista;
	}

	public ArrayList<ManoObra> getListaManoObra() {
		return listaManoObra;
	}

	public void setListaManoObra(ArrayList<ManoObra> listaManoObra) {
		this.listaManoObra = listaManoObra;
	}

	public ArrayList<Adicional> getListaAdicionales() {
		return listaAdicionales;
	}

	public void setListaAdicionales(ArrayList<Adicional> listaAdicionales) {
		this.listaAdicionales = listaAdicionales;
	}

	public int getCExternaID() {
		return CExternaID;
	}

	public void setCExternaID(int cExternaID) {
		CExternaID = cExternaID;
	}



	public int getArticuloID() {
		return articuloID;
	}

	public void setArticuloID(int articuloID) {
		this.articuloID = articuloID;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public BigDecimal getPrecio_Unitario() {
		return precio_Unitario;
	}

	public void setPrecio_Unitario(BigDecimal precio_Unitario) {
		this.precio_Unitario = precio_Unitario;
	}

	public int getProveedor() {
		return proveedorID;
	}

	public void setProveedor(int proveedor) {
		this.proveedorID = proveedor;
	}

	public ArrayList<ComprasExternasOT> getListaComprasExternas() {
		return listaComprasExternas;
	}

	public void setListaComprasExternas(ArrayList<ComprasExternasOT> listaComprasExternas) {
		this.listaComprasExternas = listaComprasExternas;
	}

	public int getProveedorID() {
		return proveedorID;
	}

	public void setProveedorID(int proveedorID) {
		this.proveedorID = proveedorID;
	}

	public Articulo getArticuloOBJ() {
		return articuloOBJ;
	}

	public void setArticuloOBJ(Articulo articuloOBJ) {
		this.articuloOBJ = articuloOBJ;
	}

//	public ArrayList<Articulo> getListaArticulosOBJ() {
//		return listaArticulosOBJ;
//	}
//
//	public void setListaArticulosOBJ(ArrayList<Articulo> listaArticulosOBJ) {
//		this.listaArticulosOBJ = listaArticulosOBJ;
//	}

	public Proveedores getProveedorOBJ() {
		return proveedorOBJ;
	}

	public void setProveedorOBJ(Proveedores proveedorOBJ) {
		this.proveedorOBJ = proveedorOBJ;
	}

	public ArrayList<Proveedores> getListaProveedoresOBJ() {
		return listaProveedoresOBJ;
	}

	public void setListaProveedoresOBJ(ArrayList<Proveedores> listaProveedoresOBJ) {
		this.listaProveedoresOBJ = listaProveedoresOBJ;
	}

	public Funcionario getFuncionarioOBJ() {
		return funcionarioOBJ;
	}

	public void setFuncionarioOBJ(Funcionario funcionarioOBJ) {
		this.funcionarioOBJ = funcionarioOBJ;
	}


	public int getAdicionalOTID() {
		return adicionalOTID;
	}

	public void setAdicionalOTID(int adicionalOTID) {
		this.adicionalOTID = adicionalOTID;
	}

	

	public int getAdicionalID() {
		return adicionalID;
	}

	public void setAdicionalID(int adicionalID) {
		this.adicionalID = adicionalID;
	}

	public String getDireccionObra() {
		return direccionObra;
	}

	public void setDireccionObra(String direccionObra) {
		this.direccionObra = direccionObra;
	}

	public int getVerifAdm() {
		return verifAdm;
	}

	public void setVerifAdm(int verifAdm) {
		this.verifAdm = verifAdm;
	}

	public String getTelObra() {
		return telObra;
	}

	public void setTelObra(String telObra) {
		this.telObra = telObra;
	}

	public ArrayList<TipoOT> getListaTiposOT() {
		return listaTiposOT;
	}

	public void setListaTiposOT(ArrayList<TipoOT> listaTiposOT) {
		this.listaTiposOT = listaTiposOT;
	}

	public Ot getOtSelected() {
		return otSelected;
	}

	public void setOtSelected(Ot otSelected) {
		this.otSelected = otSelected;
	}

	public String getNombreArticulo() {
		return nombreArticulo;
	}

	public void setNombreArticulo(String nombreArticulo) {
		this.nombreArticulo = nombreArticulo;
	}

	public String getUrlImpresion() {
		return urlImpresion;
	}

	public void setUrlImpresion(String urlImpresion) {
		this.urlImpresion = urlImpresion;
	}



//	public ArrayList<DTO_MRA> listaMRA (){
//		//hallar los Movimientos
//		//hallar los renglones de cada movimiento
//		//para cada renglon hallar el articulo
//		
//		ArrayList<DTO_MRA> salida = new ArrayList<DTO_MRA>();
//		
//		DAO_Movimiento dm= new DAO_Movimiento();
//		DAO_Renglon dr = new DAO_Renglon();
//		DAO_Arrendamiento dArr = new DAO_Arrendamiento();
//		DAO_Articulo da = new DAO_Articulo();
//		DTO_MRA mra=new DTO_MRA ();
//		
//		ArrayList<Movimiento>  listaMOT =dm.getMovimientosOT( getNumeroID());
//		
//		for (Movimiento MOT : listaMOT  ){
//			if(MOT.getTipoOT().equals("V")){
//				
//				ArrayList <Renglon> ListaRenglon =dr.getRenglonesMovimiento(MOT.getMovimientoID());				
//				for (Renglon r : ListaRenglon  ){
//					mra=new DTO_MRA ();
//					int idA = r.getId().getArticuloID();
//					Articulo art = da.findArticulo(idA);
//					if(art!=null){
//						mra.setDecripcion(art.getDescripcion());
//						mra.setCodigoMovimientoID(MOT.getCodigoMovimientoID());
//						mra.setFecha(r.getFecha());
//						mra.setTipoOT("Venta");
//						salida.add(mra);
//					}					
//				}
//				
//				
//			}
//			if(MOT.getTipoOT().equals("A")){
//				ArrayList <Arrendamiento> ListaArrendamiento =dArr.getArrendamientosMovimiento(MOT.getMovimientoID());				
//				for (Arrendamiento r : ListaArrendamiento ){
//					mra=new DTO_MRA ();
//					int idA = r.getId().getArticuloID();
//					Articulo art = da.findArticulo(idA);
//					if(art!=null){
//						mra.setDecripcion(art.getDescripcion());
//						mra.setCodigoMovimientoID(MOT.getCodigoMovimientoID());
//						mra.setFecha(r.getId().getFecha());
//						mra.setTipoOT("Arrendamiento");
//						salida.add(mra);
//					}					
//				}
//			}
//			if(MOT.getTipoOT().equals("F")){
//				 
//			}	
//			if(MOT.getTipoOT().equals("S")){
//				
//			}	
//			
//		}
//		return salida;
//	}
	
	
  public void imprimirOT(){
  	Document documento = new Document(PageSize.A4);
  	ByteArrayOutputStream baos= new ByteArrayOutputStream();
  	try{
  		PdfWriter writer=PdfWriter.getInstance(documento, baos);
  		documento.open();
  		documento.add( new Paragraph("Hola") );

      PdfContentByte canvas = writer.getDirectContent();
  	   // Creacion de tabla 1         
      PdfPTable tabla1 = new PdfPTable(15);    
      tabla1.setTotalWidth(550);  
      
      PdfPCell cell;
      
      cell=new PdfPCell(new Paragraph("Id", new Font(FontFamily.HELVETICA, 10)));
      cell.setMinimumHeight(20);      
      tabla1.addCell( cell );
      
      cell=new PdfPCell(new Paragraph("Cliente", new Font(FontFamily.HELVETICA, 10)));
      cell.setMinimumHeight(20);      
      tabla1.addCell( cell );
      
      cell=new PdfPCell(new Paragraph("Lugar", new Font(FontFamily.HELVETICA, 10)));
      cell.setMinimumHeight(20);      
      tabla1.addCell( cell );
      
      cell=new PdfPCell(new Paragraph("Telef", new Font(FontFamily.HELVETICA, 10)));
      cell.setMinimumHeight(20);      
      tabla1.addCell( cell );    
 
      cell=new PdfPCell(new Paragraph("Trabajo", new Font(FontFamily.HELVETICA, 10)));
      cell.setMinimumHeight(20);      
      tabla1.addCell( cell );    
 
      cell=new PdfPCell(new Paragraph("Proceso", new Font(FontFamily.HELVETICA, 10)));
      cell.setMinimumHeight(20);      
      tabla1.addCell( cell ); 
      
      cell=new PdfPCell(new Paragraph("Presupuesto", new Font(FontFamily.HELVETICA, 10)));
      cell.setMinimumHeight(20);      
      tabla1.addCell( cell ); 
      
      cell=new PdfPCell(new Paragraph("Pedido", new Font(FontFamily.HELVETICA, 10)));
      cell.setMinimumHeight(20);      
      tabla1.addCell( cell ); 
      
      cell=new PdfPCell(new Paragraph("Factura", new Font(FontFamily.HELVETICA, 10)));
      cell.setMinimumHeight(20);      
      tabla1.addCell( cell ); 
      
      cell=new PdfPCell(new Paragraph("O.C.", new Font(FontFamily.HELVETICA, 10)));
      cell.setMinimumHeight(20);      
      tabla1.addCell( cell ); 
      
      cell=new PdfPCell(new Paragraph("C.", new Font(FontFamily.HELVETICA, 10)));
      cell.setMinimumHeight(20);      
      tabla1.addCell( cell ); 
      
      cell=new PdfPCell(new Paragraph("R.", new Font(FontFamily.HELVETICA, 10)));
      cell.setMinimumHeight(20);      
      tabla1.addCell( cell );     
      
      cell=new PdfPCell(new Paragraph("F.Inicio", new Font(FontFamily.HELVETICA, 10)));
      cell.setMinimumHeight(20);      
      tabla1.addCell( cell );

      cell=new PdfPCell(new Paragraph("F.Fin", new Font(FontFamily.HELVETICA, 10)));
      cell.setMinimumHeight(20);      
      tabla1.addCell( cell );
      
      cell=new PdfPCell(new Paragraph("F.Facturada", new Font(FontFamily.HELVETICA, 10)));
      cell.setMinimumHeight(20);      
      tabla1.addCell( cell );
      
      
      
      for(Ot l : lista){
      	 cell=new PdfPCell(new Paragraph("2", new Font(FontFamily.HELVETICA, 9)));
         cell.setMinimumHeight(20);      
         tabla1.addCell( cell );

      }
      
     
     
      
      tabla1.setWidths( new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1} );
      tabla1.writeSelectedRows(0, -1, 10, 710, canvas);
                 
      
  		
  		
  		
  		
  		
  		
  		
  		
  		
  		
  	}
  	catch(Exception e){
  		e.printStackTrace();	  		
  	}
  	documento.close();
  	FacesContext contexto = FacesContext.getCurrentInstance();
  	Object response = contexto.getExternalContext().getResponse();
  	if(response instanceof HttpServletResponse){
  		HttpServletResponse hsr = (HttpServletResponse) response;
  		hsr.setContentType("application/pdf");
  		hsr.setHeader("Content-disposition","attachment; filename=listaot.pdf");
  		hsr.setContentLength(baos.size());
  		try {
				ServletOutputStream out = hsr.getOutputStream();
				baos.writeTo(out);
				out.flush();					
				
			} catch (Exception e) {
				e.printStackTrace();
			}
  		contexto.responseComplete();	  		
  	}	  	
  }

	public Adicional getAdiSelected() {
		return adiSelected;
	}

	public void setAdiSelected(Adicional adiSelected) {
		this.adiSelected = adiSelected;
	}

	public Movimiento getMovSelected() {
		return movSelected;
	}

	public void setMovSelected(Movimiento movSelected) {
		this.movSelected = movSelected;
	}

	public ComprasExternasOT getCeSelected() {
		return ceSelected;
	}

	public void setCeSelected(ComprasExternasOT ceSelected) {
		this.ceSelected = ceSelected;
	}

	public ManoObra getMoSelected() {
		return moSelected;
	}

	public void setMoSelected(ManoObra moSelected) {
		this.moSelected = moSelected;
	}

	public ComprasExternasOT getCompraexternaAdd() {
		return compraexternaAdd;
	}

	public void setCompraexternaAdd(ComprasExternasOT compraexternaAdd) {
		this.compraexternaAdd = compraexternaAdd;
	}

	public Movimiento getMovimientoAdd() {
		return movimientoAdd;
	}

	public void setMovimientoAdd(Movimiento movimientoAdd) {
		this.movimientoAdd = movimientoAdd;
	}

	public ManoObra getManodeobraAdd() {
		return manodeobraAdd;
	}

	public void setManodeobraAdd(ManoObra manodeobraAdd) {
		this.manodeobraAdd = manodeobraAdd;
	}

	public Adicional getAdicionalAdd() {
		return adicionalAdd;
	}

	public void setAdicionalAdd(Adicional adicionalAdd) {
		this.adicionalAdd = adicionalAdd;
	}

	public Date getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(Date fechaIni) {
		this.fechaIni = fechaIni;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	


	
}
