package util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.servlet.ServletOutputStream;

import java.io.FileNotFoundException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Paragraph;
import java.io.FileOutputStream;

import java.io.FileOutputStream;
import com.itextpdf.text.pdf.PdfWriter;

import model.ActividadAnterior;
import model.Capacitacion;
import model.Educacion;
import model.FichaPersonal;
import model.Funcionario;
import model.Ot;
import model.DAO.DAO_Funcionario;
import model.DAO.DAO_OT;

import java.math.BigDecimal;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;



public class ExportarFichaFuncionario {
	public static final String RESOURCE = "http://localhost:8080/ertec/resources/images/1964.jpg";	
	//public static final String RESULT = "/home/juan/Escritorio/hola.pdf";
	public static final String FOTO = "http://localhost:8080/ertec/resources/fotos/foto.jpg";
  public static final String RESULT  = "/home/juan/Escritorio/foobar_film_festival.pdf";
	public static void main(String[] args)
      throws IOException, DocumentException {
		ExportarFichaFuncionario prueba=new ExportarFichaFuncionario();
		prueba.ExportarPDF(206, null);
	}
	
  int vposition=0;
	
	public boolean resPosYCamPagina(int resta){
		vposition -=resta;
		if(vposition < 0){
			vposition=770 + vposition;
			return true;
		} 
		return false;
	}
	
  public void ExportarPDF(int idFuncionario, ServletOutputStream sOS  )   {
  
  	 
  	
  	//FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/1964.pdf");
  	
  	DAO_Funcionario dao= new DAO_Funcionario();
  	Funcionario f = dao.getFuncionario(idFuncionario);
  	
  	
  	//Funcionario f = this.getFunPrueba();
  	
  	if (f==null){
  		return;
  	}
  	
    String nombre=f.getNombre();
    String email=f.getEmail();
    String telefono=f.getTelefono();
    String activo=f.getActivo();
    String alias=f.getAlias();
    String area=f.getArea();
    String cat=f.getCat();
    String direccion=f.getDireccion(); 
    Date nacimiento=f.getNacimiento();
    Date carneSalud=f.getCarneSalud();
		String cedula=f.getCedula();
		Date vencimientoCedula=f.getVencimientoCedula();
		String libretaCat=f.getLibretaCat();
		Date vencimientoLibreta=f.getVencimientoLibreta();
    String primerNombre="";
  	 String segundoNombre="";
  	 String primerApellido="";
  	 String segundoApellido="";
  	 String ciudadania="";	
  	 Date ingreso=null;	
  	 String credencialCivica="";
    
    
		FichaPersonal ficha= f.getFicha();
		if(ficha!=null){
			primerNombre=ficha.getPrimerNombre();
			segundoNombre=ficha.getSegundoNombre();
			primerApellido=ficha.getPrimerApellido();
			segundoApellido=ficha.getSegundoApellido();
			ciudadania=ficha.getCiudadania();
			credencialCivica=ficha.getCredencialCivica();
			ingreso=ficha.getIngreso();
			//FOTO=ficha.getPathFoto();
			
		}
  	
   try {
  	 
  	 Document document = new Document(PageSize.A4,30,30,30,30); 
  	 //basta cambiar esto para pruebas con main
       //PdfWriter writer= PdfWriter.getInstance(document, new FileOutputStream(RESULT)); 
       PdfWriter writer=PdfWriter.getInstance(document, sOS);
        
       
       document.open();      

       
       Paragraph ErtecDireccion
       = new Paragraph("Mercedes 1787\nTel: 2400 9136\nFax: 24085721\nEmail: ertec@ertec.com.uy\nWeb: ertec.com.uy\nMontevideo - Uruguay", new Font(FontFamily.HELVETICA, 8));
       ErtecDireccion.setAlignment(Element.ALIGN_CENTER);
       document.add(ErtecDireccion);
       
 
       
       // Imagen 
       try{       
      	 URL cl=ClassLoader.getSystemResource(RESOURCE);
      	 
	       Image img = Image.getInstance(RESOURCE);
	       img.setAbsolutePosition(20,750);
	       img.scalePercent(50);
	       document.add(img);
       }
       catch(Exception e){
      	 System.err.println("no se pudo cargar la imagen");
       }
       
       
       PdfContentByte canvas = writer.getDirectContent();
       
       BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);


       
       // foto
       try{       
      	 this.vposition=750;
      	 
//      	 URL cl=ClassLoader.getSystemResource(FOTO); 
//	       Image img = Image.getInstance(FOTO);
	       Image img = Image.getInstance(f.getFoto());
	    //   System.out.println("CL "+cl);
	       img.setAbsolutePosition(420,730);
	       img.scaleAbsoluteHeight(90);
	       img.scaleAbsoluteWidth(90);
	       document.add(img);
       }
       catch(Exception e){
      	 System.err.println("no se pudo cargar la imagen");
       }
       
       
       canvas.saveState();
       canvas.beginText();
       this.resPosYCamPagina(20);//730
       canvas.moveText(440,710);
       canvas.setFontAndSize(bf, 12);
       canvas.showText("ID: "+Integer.toString(idFuncionario));
       canvas.endText();
       canvas.restoreState();
        
       document.add(new Phrase("\nDatos Personales:\n"));
       
       // Creacion de tabla 1         
       PdfPTable tabla1 = new PdfPTable(2);    
       tabla1.setTotalWidth(550);  
       
       PdfPCell cell =new PdfPCell(new Paragraph("Apellidos: "+primerApellido+" "+segundoApellido, new Font(FontFamily.HELVETICA, 10)));
       cell.setMinimumHeight(20);       
       tabla1.addCell( cell );       
       tabla1.addCell( new PdfPCell(new Paragraph("Nombres: "+ primerNombre+" "+segundoNombre , new Font(FontFamily.HELVETICA, 10))));         
       
       cell =new PdfPCell(new Paragraph("Ciudadania: " +ciudadania , new Font(FontFamily.HELVETICA, 10)));
       cell.setMinimumHeight(20);         
       tabla1.addCell( cell );    
       if(nacimiento!=null){
      	 tabla1.addCell( new PdfPCell(new Paragraph( "F. Nacimiento: "+nacimiento, new Font(FontFamily.HELVETICA, 10))));
       }
       else{
      	 tabla1.addCell( new PdfPCell(new Paragraph( "F. Nacimiento:", new Font(FontFamily.HELVETICA, 10))));
       }
       
       cell =new PdfPCell(new Paragraph("Cedula: "+ cedula , new Font(FontFamily.HELVETICA, 10)));
       cell.setMinimumHeight(20);         
       tabla1.addCell( cell );   
       tabla1.addCell( new PdfPCell(new Paragraph("C.Civica: " + credencialCivica, new Font(FontFamily.HELVETICA, 10))));
       
       cell =new PdfPCell(new Paragraph("C.Conducir: "+ libretaCat , new Font(FontFamily.HELVETICA, 10)));
       cell.setMinimumHeight(20);         
       tabla1.addCell( cell );   
       if(ingreso!=null){
      	 tabla1.addCell( new PdfPCell(new Paragraph("F.ingreso: " + ingreso, new Font(FontFamily.HELVETICA, 10))));
       }
       else{
      	 tabla1.addCell( new PdfPCell(new Paragraph("F.ingreso: " + "", new Font(FontFamily.HELVETICA, 10))));
       }
       
       cell =new PdfPCell(new Paragraph("Direccion: "+ direccion , new Font(FontFamily.HELVETICA, 10)));
       cell.setMinimumHeight(20);         
       tabla1.addCell( cell );   
       tabla1.addCell( new PdfPCell(new Paragraph("Telefono: " + telefono, new Font(FontFamily.HELVETICA, 10))));
       tabla1.setWidthPercentage(100);
       tabla1.setSpacingBefore(0f);
       tabla1.setSpacingAfter(0f);
       
       tabla1.setWidths( new int[]{ 1,1 } );
     //  tabla1.writeSelectedRows(0, -1, 20, 710, canvas);
       tabla1.setKeepTogether(true);
       document.add(tabla1);         
        
       document.add(new Phrase("Educación:\n"));
       
       //Educaciones  
       PdfPTable tabla2 = new PdfPTable(3); 
       tabla2.setTotalWidth(550);   
       
       cell = new PdfPCell(new Paragraph("Educación/Instituto", new Font(FontFamily.HELVETICA, 12)));
       cell.setMinimumHeight(20);
       tabla2.addCell(cell); 
       
       cell = new PdfPCell(new Paragraph("Nro de años", new Font(FontFamily.HELVETICA, 12)));
       cell.setMinimumHeight(20);
       tabla2.addCell(cell);
       
       cell = new PdfPCell(new Paragraph("Titulo", new Font(FontFamily.HELVETICA, 12)));
       cell.setMinimumHeight(20);
       tabla2.addCell(cell);
       
       List<Educacion> educaciones= f.getEducaciones();
       int auxEspacios=40;
       for (Educacion edu : educaciones){ 
      	 auxEspacios+=40;
      	 cell = new PdfPCell(new Paragraph(edu.getInstituto(), new Font(FontFamily.HELVETICA, 10)));
      	 cell.setMinimumHeight(20);
         tabla2.addCell(cell); 
         cell = new PdfPCell(new Paragraph(edu.getAnios(), new Font(FontFamily.HELVETICA, 10)));
      	 cell.setMinimumHeight(20);
         tabla2.addCell(cell); 
         cell = new PdfPCell(new Paragraph(edu.getTitulo(), new Font(FontFamily.HELVETICA, 10)));
      	 cell.setMinimumHeight(20);
         tabla2.addCell(cell); 
       }         
       this.resPosYCamPagina(10);//580     
       tabla2.setWidths( new int[]{ 3,1,3 } );
     //  tabla2.writeSelectedRows(0, -1, 20, this.vposition, canvas); 
       tabla2.setKeepTogether(true);
       tabla2.setWidthPercentage(100);
       tabla2.setSpacingBefore(0f);
       tabla2.setSpacingAfter(0f);
       document.add(tabla2);
       this.resPosYCamPagina(auxEspacios);
       
       //Actividades anteriores
       document.add(new Phrase("Actividad Anterior:\n"));

       PdfPTable tabla3 = new PdfPTable(5); 
       tabla3.setTotalWidth(550);   
       
       cell = new PdfPCell(new Paragraph("Desde", new Font(FontFamily.HELVETICA, 12)));
       cell.setMinimumHeight(20);
       tabla3.addCell(cell); 
       
       cell = new PdfPCell(new Paragraph("Hasta", new Font(FontFamily.HELVETICA, 12)));
       cell.setMinimumHeight(20);
       tabla3.addCell(cell);
       
       cell = new PdfPCell(new Paragraph("Firma", new Font(FontFamily.HELVETICA, 12)));
       cell.setMinimumHeight(20);
       tabla3.addCell(cell);
       
       cell = new PdfPCell(new Paragraph("Puesto", new Font(FontFamily.HELVETICA, 12)));
       cell.setMinimumHeight(20);
       tabla3.addCell(cell);
       
       cell = new PdfPCell(new Paragraph("Causa retiro", new Font(FontFamily.HELVETICA, 12)));
       cell.setMinimumHeight(20);
       tabla3.addCell(cell);
       
       List<ActividadAnterior> actividades= f.getActividadAnteriores();
       auxEspacios=40;
       for (ActividadAnterior aa : actividades){ 
      	 auxEspacios+=20;
      	 if(aa.getDesde()!=null){
      		 cell = new PdfPCell(new Paragraph(aa.getDesde().toString() , new Font(FontFamily.HELVETICA, 10)));
      	 }
      	 else{
      		 cell = new PdfPCell(new Paragraph("" , new Font(FontFamily.HELVETICA, 10)));           
      	 }
      	 cell.setMinimumHeight(20);
         tabla3.addCell(cell); 
         if(aa.getHasta()!=null){
        	 cell = new PdfPCell(new Paragraph(aa.getHasta().toString(), new Font(FontFamily.HELVETICA, 10)));
         }
         else{
        	 cell = new PdfPCell(new Paragraph("", new Font(FontFamily.HELVETICA, 10)));
         }
      	 cell.setMinimumHeight(20);
         tabla3.addCell(cell); 
      	 cell = new PdfPCell(new Paragraph( aa.getFirma(), new Font(FontFamily.HELVETICA, 10)));
      	 cell.setMinimumHeight(20);
         tabla3.addCell(cell); 
         cell = new PdfPCell(new Paragraph( aa.getPuesto(), new Font(FontFamily.HELVETICA, 10)));
      	 cell.setMinimumHeight(20);
         tabla3.addCell(cell); 
      	 cell = new PdfPCell(new Paragraph( aa.getCausaRetiro(), new Font(FontFamily.HELVETICA, 10)));
      	 cell.setMinimumHeight(20);
         tabla3.addCell(cell);  
       }         
       this.resPosYCamPagina(10); 
       tabla3.setWidths( new int[]{1,1,2,2,2} );
     //  tabla3.writeSelectedRows(0, -1, 20,this.vposition, canvas);
       tabla3.setWidthPercentage(100);
       tabla3.setSpacingBefore(0f);
       tabla3.setSpacingAfter(0f);
       tabla3.setKeepTogether(true);
       document.add(tabla3);
       
       
       //capacitaciones
       document.add(new Phrase("Capacitación:\n"));
        
       PdfPTable tabla4 = new PdfPTable(4); 
       tabla4.setTotalWidth(550);   
       
       cell = new PdfPCell(new Paragraph("Fecha", new Font(FontFamily.HELVETICA, 12)));
       cell.setMinimumHeight(20);
       tabla4.addCell(cell); 
       
       cell = new PdfPCell(new Paragraph("Actividad", new Font(FontFamily.HELVETICA, 12)));
       cell.setMinimumHeight(20);
       tabla4.addCell(cell);
       
       cell = new PdfPCell(new Paragraph("Capacitación", new Font(FontFamily.HELVETICA, 12)));
       cell.setMinimumHeight(20);
       tabla4.addCell(cell);
       
       cell = new PdfPCell(new Paragraph("Observaciones", new Font(FontFamily.HELVETICA, 12)));
       cell.setMinimumHeight(20);
       tabla4.addCell(cell);
       
       List<Capacitacion> capacitaciones= f.getCapacitaciones();
       auxEspacios=40;
       for (Capacitacion c : capacitaciones){ 
      	 auxEspacios+=40;
      	 if(c.getFecha()!=null){
      		 cell = new PdfPCell(new Paragraph(c.getFecha().toString() , new Font(FontFamily.HELVETICA, 10)));
      	 }
      	 else{
      		 cell = new PdfPCell(new Paragraph("" , new Font(FontFamily.HELVETICA, 10)));           
      	 }
      	 cell.setMinimumHeight(20);
         tabla4.addCell(cell); 
         cell = new PdfPCell(new Paragraph(c.getActividad(), new Font(FontFamily.HELVETICA, 10)));         
      	 cell.setMinimumHeight(20);
         tabla4.addCell(cell); 
      	 cell = new PdfPCell(new Paragraph(c.getCapacitacion(), new Font(FontFamily.HELVETICA, 10)));
      	 cell.setMinimumHeight(20);
         tabla4.addCell(cell); 
         cell = new PdfPCell(new Paragraph(c.getObservaciones(), new Font(FontFamily.HELVETICA, 10)));
      	 cell.setMinimumHeight(20);
         tabla4.addCell(cell);  
       }         
       this.resPosYCamPagina(10); 
       tabla4.setWidths( new int[]{1,2,2,2} );
       //tabla4.writeSelectedRows(0, -1, 20,this.vposition, canvas);
       tabla4.setWidthPercentage(100);
       tabla4.setSpacingBefore(0f);
       tabla4.setSpacingAfter(0f);
       tabla4.setKeepTogether(true);       
       document.add(tabla4);
       this.resPosYCamPagina(auxEspacios+20);
       
       
       //Datos familiares
       document.add(new Phrase("Datos familiares:\n"));
      
       
       PdfPTable tabla5 = new PdfPTable(4); 
       tabla5.setTotalWidth(550);   
       
       cell = new PdfPCell(new Paragraph("", new Font(FontFamily.HELVETICA, 12)));
       cell.setMinimumHeight(20);
       tabla5.addCell(cell); 
       
       cell = new PdfPCell(new Paragraph("Nombre", new Font(FontFamily.HELVETICA, 12)));
       cell.setMinimumHeight(20);
       tabla5.addCell(cell);
       
       cell = new PdfPCell(new Paragraph("Dirección", new Font(FontFamily.HELVETICA, 12)));
       cell.setMinimumHeight(20);
       tabla5.addCell(cell);
       
       cell = new PdfPCell(new Paragraph("Teléfono", new Font(FontFamily.HELVETICA, 12)));
       cell.setMinimumHeight(20);
       tabla5.addCell(cell);
       
       cell = new PdfPCell(new Paragraph("Padre", new Font(FontFamily.HELVETICA, 12)));
       cell.setMinimumHeight(20);
       tabla5.addCell(cell);
       cell = new PdfPCell(new Paragraph(f.getFicha().getNombrePadre(), new Font(FontFamily.HELVETICA, 12)));
       cell.setMinimumHeight(20);
       tabla5.addCell(cell);
       cell = new PdfPCell(new Paragraph(f.getFicha().getDireccionPadre(), new Font(FontFamily.HELVETICA, 12)));
       cell.setMinimumHeight(20);
       tabla5.addCell(cell);
       cell = new PdfPCell(new Paragraph(f.getFicha().getTelefonoPadre(), new Font(FontFamily.HELVETICA, 12)));
       cell.setMinimumHeight(20);
       tabla5.addCell(cell);
       
       cell = new PdfPCell(new Paragraph("Madre", new Font(FontFamily.HELVETICA, 12)));
       cell.setMinimumHeight(20);
       tabla5.addCell(cell);
       cell = new PdfPCell(new Paragraph(f.getFicha().getNombreMadre(), new Font(FontFamily.HELVETICA, 12)));
       cell.setMinimumHeight(20);
       tabla5.addCell(cell);
       cell = new PdfPCell(new Paragraph(f.getFicha().getDireccionMadre(), new Font(FontFamily.HELVETICA, 12)));
       cell.setMinimumHeight(20);
       tabla5.addCell(cell);
       cell = new PdfPCell(new Paragraph(f.getFicha().getTelefonoMadre(), new Font(FontFamily.HELVETICA, 12)));
       cell.setMinimumHeight(20);
       tabla5.addCell(cell);
       
       cell = new PdfPCell(new Paragraph("Conyuge", new Font(FontFamily.HELVETICA, 12)));
       cell.setMinimumHeight(20);
       tabla5.addCell(cell);
       cell = new PdfPCell(new Paragraph(f.getFicha().getNombreConyuge(), new Font(FontFamily.HELVETICA, 12)));
       cell.setMinimumHeight(20);
       tabla5.addCell(cell);
       cell = new PdfPCell(new Paragraph(f.getFicha().getDireccionConyuge(), new Font(FontFamily.HELVETICA, 12)));
       cell.setMinimumHeight(20);
       tabla5.addCell(cell);
       cell = new PdfPCell(new Paragraph(f.getFicha().getTelefonoConyuge(), new Font(FontFamily.HELVETICA, 12)));
       cell.setMinimumHeight(20);
       tabla5.addCell(cell);
       
       cell = new PdfPCell(new Paragraph("Urgencias", new Font(FontFamily.HELVETICA, 12)));
       cell.setMinimumHeight(20);
       tabla5.addCell(cell);
       cell = new PdfPCell(new Paragraph(f.getFicha().getNombreAvisarUrgencia(), new Font(FontFamily.HELVETICA, 12)));
       cell.setMinimumHeight(20);
       tabla5.addCell(cell);
       cell = new PdfPCell(new Paragraph(f.getFicha().getDireccionAvisarUrgencia(), new Font(FontFamily.HELVETICA, 12)));
       cell.setMinimumHeight(20);
       tabla5.addCell(cell);
       cell = new PdfPCell(new Paragraph(f.getFicha().getTelefonoAvisarUrgencia(), new Font(FontFamily.HELVETICA, 12)));
       cell.setMinimumHeight(20);
       tabla5.addCell(cell);     
       
        
       this.resPosYCamPagina(10); 
       tabla5.setWidths( new int[]{1,2,2,2} );
     //  tabla5.writeSelectedRows(0, -1, 20,this.vposition, canvas);
       tabla5.setWidthPercentage(100);
       tabla5.setSpacingBefore(0f);
       tabla5.setSpacingAfter(0f);
       tabla5.setKeepTogether(true); 
       document.add(tabla5);
       this.resPosYCamPagina(120);
       
       
       
       
       
      
       
       
       //nueva pagina
       
      
       
       
       

      
       
       
       
       
       
       document.close();
       
       sOS.flush();
       sOS.close();
       System.out.println("Todo Bien"); 
        
     }
     catch(Exception e){
    	 e.printStackTrace();
     } 
   }
  
  public Funcionario getFunPrueba(){
  	Funcionario f = new Funcionario();
  	Educacion educ=new Educacion();
  	educ.setAnios("1");
  	educ.setTitulo("algo");
  	educ.setInstituto("dd");
  	f.getEducaciones().add(educ);
  	educ=new Educacion();
  	educ.setAnios("2");
  	educ.setTitulo("algo2");
  	educ.setInstituto("dd2");
  	f.getEducaciones().add(educ);
  	educ=new Educacion();
  	educ.setAnios("1");
  	educ.setTitulo("algo");
  	educ.setInstituto("dd");
  	f.getEducaciones().add(educ);
  	educ=new Educacion();
  	educ.setAnios("2");
  	educ.setTitulo("algo2");
  	educ.setInstituto("dd2");
  	f.getEducaciones().add(educ);
  	educ=new Educacion();
  	educ.setAnios("1");
  	educ.setTitulo("algo");
  	educ.setInstituto("dd");
  	f.getEducaciones().add(educ);
  	educ=new Educacion();
  	educ.setAnios("2");
  	educ.setTitulo("algo2");
  	educ.setInstituto("dd2");
  	f.getEducaciones().add(educ);
  	
  	ActividadAnterior aa =new ActividadAnterior();
  	aa.setCausaRetiro("se fue");
  	f.getActividadAnteriores().add(aa);
  	aa =new ActividadAnterior();
  	aa.setCausaRetiro("se fue");
  	f.getActividadAnteriores().add(aa);
  	aa =new ActividadAnterior();
  	aa.setCausaRetiro("se fue");
  	aa.setDesde(new Date());
  	f.getActividadAnteriores().add(aa);
  	aa =new ActividadAnterior();
  	aa.setCausaRetiro("se fue");
  	f.getActividadAnteriores().add(aa);
  	aa =new ActividadAnterior();
  	aa.setCausaRetiro("se fue");
  	f.getActividadAnteriores().add(aa);
  	aa =new ActividadAnterior();
  	aa.setCausaRetiro("se fue");
  	aa.setDesde(new Date());
  	f.getActividadAnteriores().add(aa);
  	aa =new ActividadAnterior();
  	aa.setCausaRetiro("se fue");
  	f.getActividadAnteriores().add(aa);
  	aa =new ActividadAnterior();
  	aa.setCausaRetiro("se fue");
  	aa.setDesde(new Date());
  	f.getActividadAnteriores().add(aa);
  	
  	Capacitacion c= new Capacitacion();
  	c.setActividad("nada");
  	f.getCapacitaciones().add(c);
  	c= new Capacitacion();
  	c.setActividad("nada");
  	f.getCapacitaciones().add(c);
  	c= new Capacitacion();
  	c.setActividad("nada");
  	f.getCapacitaciones().add(c);
  	
  	return f;
  	
  }

    
}
