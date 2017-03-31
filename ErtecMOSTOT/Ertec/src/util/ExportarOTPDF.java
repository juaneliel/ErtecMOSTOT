package util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;

import java.io.FileNotFoundException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Paragraph;
import java.io.FileOutputStream;

import java.io.FileOutputStream;
import com.itextpdf.text.pdf.PdfWriter;

import model.Ot;
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



public class ExportarOTPDF {
	
	public static final String RESOURCE = "http://localhost:8080/ertec/resources/images/1964.jpg";
	//public static final String RESULT = "/home/juan/Escritorio/hola.pdf";
	
    	
  public static void ExportarPDF(int idOT, ServletOutputStream sOS  )   {
  
  	//String RESULT = "/home/juan/Escritorio/OT.pdf";
  	
  	//FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/1964.pdf");
  	
  	DAO_OT dao= new DAO_OT();
  	Ot ot = dao.getOT(idOT);
  	if(ot==null){
  		return;
  	}
  	String cliente = ot.getClienteNombre();
    String direccion = "Sin datos";
    String telCliente =  "Sin datos";
    int ccte=0;
    int c=0;
    int arr=ot.getArrendamiento();
    int mant=ot.getMantenimiento();
    
  	if(ot.getNroCliente()!=0){
  	  cliente = ot.getCliente().getNombre();
  	  direccion = ot.getCliente().getDirCliente();
      telCliente =  ot.getCliente().getTelCliente();
//      if(ot.getCliente()!=null && ot.getCliente().getCuentacorriente()!=null ){
//        ccte=ot.getCliente().getCuentacorriente().getCuentaID();  
//      }  
       ccte=ot.getCliente().getCuentaCorriente(); 
      
      
  	}
    int numeroCliente=ot.getNroCliente();    
    String lugarDeObra= ot.getDireccionObra();
    String telObra = ot.getTelObra(); 
    int verifadm=ot.getVerifAdm();
   // int idOT=ot.getId();	
  	BigDecimal pres= ot.getPresupuesto();
  	int oc= ot.getOC();
  	int ped = ot.getPedido();  	
  	int r=ot.getR();
  	String trabajo = ot.getTrabajo();
  	
   try {
  	 
  	 Document document = new Document(PageSize.A4, 30, 30, 30, 30); 
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
      	 System.out.println("CL "+cl);
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
       canvas.saveState();
       canvas.beginText();
       canvas.moveText(420,780);
       canvas.setFontAndSize(bf, 10);
       canvas.showText("ORDEN DE TRABAJO");
       canvas.endText();
       canvas.restoreState();
       
       canvas.saveState();
       canvas.beginText();
       canvas.moveText(440,750);
       canvas.setFontAndSize(bf, 12);
       canvas.showText("Nro: "+Integer.toString(idOT));
       canvas.endText();
       canvas.restoreState();
       

       
       // Creacion de tabla 1         
       PdfPTable tabla1 = new PdfPTable(2);    
       tabla1.setTotalWidth(350);  
       PdfPCell cell =new PdfPCell(new Paragraph("CLIENTE: "+cliente, new Font(FontFamily.HELVETICA, 10)));
       cell.setMinimumHeight(20);
       
       tabla1.addCell( cell );
       tabla1.addCell( new PdfPCell(new Paragraph("ID: "+ numeroCliente , new Font(FontFamily.HELVETICA, 10))));         
       
       cell =new PdfPCell(new Paragraph("DIRECCION: " +direccion , new Font(FontFamily.HELVETICA, 10)));
       cell.setMinimumHeight(20);         
       tabla1.addCell( cell );         
       tabla1.addCell( new PdfPCell(new Paragraph( "TEL: "+telCliente, new Font(FontFamily.HELVETICA, 10))));
       
       cell =new PdfPCell(new Paragraph("LUGAR DE OBRA: "+ lugarDeObra , new Font(FontFamily.HELVETICA, 10)));
       cell.setMinimumHeight(20);         
       tabla1.addCell( cell );   
       tabla1.addCell( new PdfPCell(new Paragraph("TEL " + telObra, new Font(FontFamily.HELVETICA, 10))));
       tabla1.setWidths( new int[]{ 10,5 } );
       tabla1.writeSelectedRows(0, -1, 20, 710, canvas);
                  
       
       
       PdfPTable tabla2 = new PdfPTable(1); 
       tabla2.setTotalWidth(90);         
       tabla2.addCell( new PdfPCell(new Paragraph("C.CTE.: "+ccte, new Font(FontFamily.HELVETICA, 8))));
       tabla2.addCell( new PdfPCell(new Paragraph("ARR.: "+ arr, new Font(FontFamily.HELVETICA, 8))));
       tabla2.addCell( new PdfPCell(new Paragraph("MANT.: " +mant  , new Font(FontFamily.HELVETICA, 8))));
       tabla2.addCell( new PdfPCell(new Paragraph("VERIF ADM.: "+verifadm, new Font(FontFamily.HELVETICA, 8)))); 
       tabla2.writeSelectedRows(0, -1, 380, 710, canvas);
       
      
       
       PdfPTable tabla3 = new PdfPTable(2); 
       tabla3.setTotalWidth(90);  
       PdfPCell celdaFinal = new PdfPCell(new Paragraph("PRES.: "+pres, new Font(FontFamily.HELVETICA, 8)));
       celdaFinal.setColspan(2);  
       tabla3.addCell( celdaFinal);
       celdaFinal = new PdfPCell(new Paragraph("O.C.: "+ oc, new Font(FontFamily.HELVETICA, 8)));
       celdaFinal.setColspan(2);  
       tabla3.addCell( celdaFinal);
       celdaFinal = new PdfPCell(new Paragraph("PED.: " +ped, new Font(FontFamily.HELVETICA, 8)));
       celdaFinal.setColspan(2);  
       tabla3.addCell( celdaFinal);
       tabla3.addCell( new PdfPCell(new Paragraph("C.: "+c, new Font(FontFamily.HELVETICA, 8)))); 
       tabla3.addCell( new PdfPCell(new Paragraph("R.: "+r, new Font(FontFamily.HELVETICA, 8)))); 
       tabla3.writeSelectedRows(0, -1, 480, 710, canvas);
       
       
       
       canvas.saveState();
       canvas.beginText();
       canvas.moveText(20,635);
       canvas.setFontAndSize(bf, 10);
       canvas.showText("TRABAJO A EFECTUAR:");
       canvas.endText();
       canvas.restoreState();
       
       //trabajo a efectuar ver como se define  
       PdfPTable tabla4 = new PdfPTable(1); 
       tabla4.setTotalWidth(550);   
       
       cell = new PdfPCell(new Paragraph(trabajo, new Font(FontFamily.HELVETICA, 12)));
  	 cell.setMinimumHeight(20);
       tabla4.addCell(cell); 
       
       for (int i=1; i<=16;i++){ 
      	 cell = new PdfPCell(new Paragraph("", new Font(FontFamily.HELVETICA, 12)));
      	 cell.setMinimumHeight(20);
         tabla4.addCell(cell);  
       }         
       tabla4.writeSelectedRows(0, -1, 20, 630, canvas);
       
       
       
       PdfPTable tabla5 = new PdfPTable(4); 
       tabla5.setTotalWidth(550); 
       PdfPCell cabeceraAdicionales = new PdfPCell(new Paragraph("ADICIONALES (No se realizara ningun trabajo o suministro adicional que no este debidamente autorizado por ERTEC)", new Font(FontFamily.HELVETICA, 10)));
       cabeceraAdicionales.setColspan(2);  
       tabla5.addCell( cabeceraAdicionales );   
       
       
       tabla5.addCell( new PdfPCell(new Paragraph("CLIENTE", new Font(FontFamily.HELVETICA,10)))); 
       tabla5.addCell( new PdfPCell(new Paragraph("ERTEC", new Font(FontFamily.HELVETICA, 10)))); 
       for (int i=1; i<=6;i++){
         tabla5.addCell( new PdfPCell(new Paragraph(Integer.toString(i), new Font(FontFamily.HELVETICA, 10)))); 
         tabla5.addCell( new PdfPCell(new Paragraph("", new Font(FontFamily.HELVETICA, 12)))); 
         tabla5.addCell( new PdfPCell(new Paragraph("", new Font(FontFamily.HELVETICA, 12)))); 
         tabla5.addCell( new PdfPCell(new Paragraph("", new Font(FontFamily.HELVETICA, 12)))); 
       }         
       tabla5.setWidths( new int[]{ 1,13,3,3 } ); 
       tabla5.writeSelectedRows(0, -1, 20, 275, canvas);
       
       
       
       
       PdfPTable tabla6 = new PdfPTable(5);    
       tabla6.setTotalWidth(260);         
       tabla6.addCell( new PdfPCell(new Paragraph("", new Font(FontFamily.HELVETICA, 8))));
       tabla6.addCell( new PdfPCell(new Paragraph("FIRMA" , new Font(FontFamily.HELVETICA, 8))));         
       tabla6.addCell( new PdfPCell(new Paragraph("DIA" , new Font(FontFamily.HELVETICA, 8))));         
       tabla6.addCell( new PdfPCell(new Paragraph("MES", new Font(FontFamily.HELVETICA, 8))));
       tabla6.addCell( new PdfPCell(new Paragraph("AÑO" , new Font(FontFamily.HELVETICA, 8))));  
       tabla6.addCell( new PdfPCell(new Paragraph("AUTORIZADA", new Font(FontFamily.HELVETICA, 8))));
       for (int i=0; i<4;i++){
           tabla6.addCell( new PdfPCell(new Paragraph("", new Font(FontFamily.HELVETICA, 8)))); 
       }         
       tabla6.addCell( new PdfPCell(new Paragraph("INICIADA" , new Font(FontFamily.HELVETICA, 8))));
       for (int i=0; i<4;i++){
           tabla6.addCell( new PdfPCell(new Paragraph("", new Font(FontFamily.HELVETICA, 8)))); 
       } 
       tabla6.addCell( new PdfPCell(new Paragraph("TERMINADA" , new Font(FontFamily.HELVETICA, 8)))); 
       for (int i=0; i<4;i++){
           tabla6.addCell( new PdfPCell(new Paragraph("", new Font(FontFamily.HELVETICA, 8)))); 
       }
       tabla6.addCell( new PdfPCell(new Paragraph( "Vo Bo Cliente", new Font(FontFamily.HELVETICA,8)))); 
       for (int i=0; i<4;i++){
           tabla6.addCell( new PdfPCell(new Paragraph("", new Font(FontFamily.HELVETICA, 8)))); 
       }
       tabla6.addCell( new PdfPCell(new Paragraph( "ACLARACION DE FIRMA", new Font(FontFamily.HELVETICA,7)))); 
       PdfPCell aclaracionFirma = new PdfPCell(new Paragraph("", new Font(FontFamily.HELVETICA, 7)));
       aclaracionFirma.setColspan(4);  
       tabla6.addCell( aclaracionFirma );  
       tabla6.setWidths( new int[]{3,5,1,1,1 } );
       tabla6.writeSelectedRows(0, -1, 20, 150, canvas);
       
       
       
            
       PdfPTable tabla7 = new PdfPTable(5);    
       tabla7.setTotalWidth(260);         
       tabla7.addCell( new PdfPCell(new Paragraph("", new Font(FontFamily.HELVETICA, 8))));
       tabla7.addCell( new PdfPCell(new Paragraph("FIRMA" , new Font(FontFamily.HELVETICA, 8))));         
       tabla7.addCell( new PdfPCell(new Paragraph("DIA" , new Font(FontFamily.HELVETICA, 8))));         
       tabla7.addCell( new PdfPCell(new Paragraph("MES", new Font(FontFamily.HELVETICA, 8))));
       tabla7.addCell( new PdfPCell(new Paragraph("AÑO" , new Font(FontFamily.HELVETICA, 8))));  
       tabla7.addCell( new PdfPCell(new Paragraph("MATERIAL DEVUELTO", new Font(FontFamily.HELVETICA, 8))));
       for (int i=0; i<4;i++){
      	 tabla7.addCell( new PdfPCell(new Paragraph("", new Font(FontFamily.HELVETICA, 8)))); 
       }        
       tabla7.addCell( new PdfPCell(new Paragraph("CONTROL DE OBRA" , new Font(FontFamily.HELVETICA, 8)))); 
       for (int i=0; i<4;i++){
      	 tabla7.addCell( new PdfPCell(new Paragraph("", new Font(FontFamily.HELVETICA, 8)))); 
       }          
       tabla7.addCell( new PdfPCell(new Paragraph("COSTOS Y MANO DE OBRA" , new Font(FontFamily.HELVETICA, 8))));
       for (int i=0; i<4;i++){
      	 tabla7.addCell( new PdfPCell(new Paragraph("", new Font(FontFamily.HELVETICA, 8)))); 
       } 
       tabla7.addCell( new PdfPCell(new Paragraph( "LIQUIDACION", new Font(FontFamily.HELVETICA,8))));
       for (int i=0; i<4;i++){
      	 tabla7.addCell( new PdfPCell(new Paragraph("", new Font(FontFamily.HELVETICA, 8)))); 
       } 
       tabla7.addCell( new PdfPCell(new Paragraph( "FACTURACION", new Font(FontFamily.HELVETICA,8)))); 
       
       tabla7.setWidths( new int[]{3,5,1,1,1 } );
       tabla7.writeSelectedRows(0, -1, 290, 150, canvas);
       
       
       
       
       
       //nueva pagina
       
      
       document.newPage();
       
       
       //facturacion
       PdfPTable tabla8 = new PdfPTable(9); 
       tabla8.setTotalWidth(550); 
       
       PdfPCell detalleFActuras = new PdfPCell(new Paragraph("DETALLE DE FACTURA", new Font(FontFamily.HELVETICA, 8)));
  	 detalleFActuras.setColspan(6);  
       tabla8.addCell( detalleFActuras ); 
       tabla8.addCell( new PdfPCell(new Paragraph("PRECIO UNITARIO" , new Font(FontFamily.HELVETICA, 8)))); 
       detalleFActuras = new PdfPCell(new Paragraph("IMPORTE TOTAL", new Font(FontFamily.HELVETICA, 8)));
  	 detalleFActuras.setColspan(2);  
       tabla8.addCell( detalleFActuras ); 
       
       for (int i=1; i<=24;i++){ 
      	 cell = new PdfPCell(new Paragraph("", new Font(FontFamily.HELVETICA, 10)));
      	 cell.setMinimumHeight(15);
      	 cell.setColspan(6);  
           tabla8.addCell( cell ); 
           tabla8.addCell( new PdfPCell(new Paragraph("" , new Font(FontFamily.HELVETICA, 10))));   
           tabla8.addCell( new PdfPCell(new Paragraph("" , new Font(FontFamily.HELVETICA, 10))));  
           tabla8.addCell( new PdfPCell(new Paragraph("" , new Font(FontFamily.HELVETICA, 10))));  
           
       }
       

     cell = new PdfPCell(new Paragraph("CONDICIONES DE PAGO", new Font(FontFamily.HELVETICA, 10)));
     cell.setColspan(5);  
     tabla8.addCell( cell );    
     
     tabla8.addCell( new PdfPCell(new Paragraph("CONTROLADO" , new Font(FontFamily.HELVETICA, 10)))); 
     
     detalleFActuras = new PdfPCell(new Paragraph("SUB - TOTAL", new Font(FontFamily.HELVETICA, 10))); 
     tabla8.addCell( detalleFActuras );      
     
     detalleFActuras = new PdfPCell(new Paragraph("1", new Font(FontFamily.HELVETICA, 10))); 
     tabla8.addCell( detalleFActuras ); 
     
     detalleFActuras = new PdfPCell(new Paragraph("2", new Font(FontFamily.HELVETICA, 10))); 
     tabla8.addCell( detalleFActuras ); 
      
     
     
     detalleFActuras = new PdfPCell(new Paragraph("3", new Font(FontFamily.HELVETICA, 10)));
     detalleFActuras.setColspan(5);       
     tabla8.addCell( detalleFActuras ); 
     
     detalleFActuras = new PdfPCell(new Paragraph("4", new Font(FontFamily.HELVETICA, 10)));
     tabla8.addCell( detalleFActuras ); 
     
     
     detalleFActuras = new PdfPCell(new Paragraph("IVA", new Font(FontFamily.HELVETICA, 10)));  
     tabla8.addCell( detalleFActuras ); 
     detalleFActuras = new PdfPCell(new Paragraph("5", new Font(FontFamily.HELVETICA, 10))); 
     tabla8.addCell( detalleFActuras ); 
     detalleFActuras = new PdfPCell(new Paragraph("6", new Font(FontFamily.HELVETICA, 10)));  
     tabla8.addCell( detalleFActuras ); 
    
      
     
     
    tabla8.addCell( new PdfPCell(new Paragraph("FACTURA - V.C." , new Font(FontFamily.HELVETICA, 10)))); 
    
    detalleFActuras = new PdfPCell(new Paragraph("FECHA", new Font(FontFamily.HELVETICA, 10)));
    detalleFActuras.setColspan(3);  
    tabla8.addCell( detalleFActuras ); 
    
    tabla8.addCell( new PdfPCell(new Paragraph("PROCESO" , new Font(FontFamily.HELVETICA, 10)))); 
    
    
    tabla8.addCell( new PdfPCell(new Paragraph("e" , new Font(FontFamily.HELVETICA, 10)))); 
     
    
    detalleFActuras = new PdfPCell(new Paragraph("TOTAL", new Font(FontFamily.HELVETICA, 10))); 
    tabla8.addCell( detalleFActuras ); 
    
    
    tabla8.addCell( new PdfPCell(new Paragraph("7" , new Font(FontFamily.HELVETICA, 10)))); 
    tabla8.addCell( new PdfPCell(new Paragraph("8" , new Font(FontFamily.HELVETICA, 10))));
    
    
    tabla8.addCell( new PdfPCell(new Paragraph("9" , new Font(FontFamily.HELVETICA, 10)))); 
    tabla8.addCell( new PdfPCell(new Paragraph("10" , new Font(FontFamily.HELVETICA, 10)))); 
    tabla8.addCell( new PdfPCell(new Paragraph("11" , new Font(FontFamily.HELVETICA, 10))));     
    tabla8.addCell( new PdfPCell(new Paragraph("12" , new Font(FontFamily.HELVETICA, 10)))); 
    tabla8.addCell( new PdfPCell(new Paragraph("13" , new Font(FontFamily.HELVETICA, 10)))); 
    tabla8.addCell( new PdfPCell(new Paragraph("14" , new Font(FontFamily.HELVETICA, 10)))); 
    tabla8.addCell( new PdfPCell(new Paragraph("15" , new Font(FontFamily.HELVETICA, 10))));     
    tabla8.addCell( new PdfPCell(new Paragraph("16" , new Font(FontFamily.HELVETICA, 10)))); 
    tabla8.addCell( new PdfPCell(new Paragraph("17" , new Font(FontFamily.HELVETICA, 10)))); 
    tabla8.addCell( new PdfPCell(new Paragraph("18" , new Font(FontFamily.HELVETICA, 10)))); 
    
    
    
    
     
    // 
    // 
    // 
    // 
    // 
    // 
    // 
    // tabla8.addCell( new PdfPCell(new Paragraph("IVA" , new Font(FontFamily.HELVETICA, 10)))); 
    // tabla8.addCell( new PdfPCell(new Paragraph("" , new Font(FontFamily.HELVETICA, 10)))); 
    // tabla8.addCell( new PdfPCell(new Paragraph("" , new Font(FontFamily.HELVETICA, 10)))); 
    // tabla8.addCell( new PdfPCell(new Paragraph("" , new Font(FontFamily.HELVETICA, 10)))); 
    // 
    // 
    // 
    // tabla8.addCell( new PdfPCell(new Paragraph("FACTURA - V.C." , new Font(FontFamily.HELVETICA, 10)))); 
    //  
    // detalleFActuras = new PdfPCell(new Paragraph("FECHA", new Font(FontFamily.HELVETICA, 10)));
    // detalleFActuras.setColspan(3);  
    // tabla8.addCell( detalleFActuras ); 
    // 
    // tabla8.addCell( new PdfPCell(new Paragraph("PROCESO" , new Font(FontFamily.HELVETICA, 10)))); 
    // tabla8.addCell( new PdfPCell(new Paragraph("TOTAL" , new Font(FontFamily.HELVETICA, 10)))); 
    // tabla8.addCell( new PdfPCell(new Paragraph("" , new Font(FontFamily.HELVETICA, 10)))); 
    // tabla8.addCell( new PdfPCell(new Paragraph("" , new Font(FontFamily.HELVETICA, 10)))); 
    //// 
    //
       
       
       
    tabla8.setWidths( new int[]{6,2,2,2,4,4,4,4,1 } );
    tabla8.writeSelectedRows(0, -1, 20, 800, canvas);
    
      
       
       
       PdfPTable tabla9 = new PdfPTable(1);    
       tabla9.setTotalWidth(550);
       tabla9.addCell( new PdfPCell(new Paragraph("OBSERVACIONES DE OT: Detalle cualquier evento que incidiera en la realizacion de la OT (Materiales Mano de Obra, tiempo perdido, suministros, herramientas, etc.)", new Font(FontFamily.HELVETICA, 8)))); 
       for (int i=0; i<6;i++){
      	 cell = new PdfPCell(new Paragraph("", new Font(FontFamily.HELVETICA, 8)));
      	 cell.setMinimumHeight(15);
      	 tabla9.addCell( cell ); 
       }                
       tabla9.writeSelectedRows(0, -1, 20, 320, canvas);
       
       
       
        
       
       PdfPTable tabla10 = new PdfPTable(4);    
       tabla10.setTotalWidth(260);   
       
       aclaracionFirma = new PdfPCell(new Paragraph("OPERARIO", new Font(FontFamily.HELVETICA, 7)));
       aclaracionFirma.setRowspan(2);;  
       tabla10.addCell( aclaracionFirma ); 
       
       
       cell = new PdfPCell(new Paragraph("Nro", new Font(FontFamily.HELVETICA, 7)));
       cell.setRowspan(2);
       tabla10.addCell( cell ); 
       
       aclaracionFirma = new PdfPCell(new Paragraph("HORAS", new Font(FontFamily.HELVETICA, 7)));
       aclaracionFirma.setColspan(2);
       tabla10.addCell( aclaracionFirma ); 
       
       tabla10.addCell( new PdfPCell(new Paragraph("SIMPLES", new Font(FontFamily.HELVETICA, 8)))); 
       tabla10.addCell( new PdfPCell(new Paragraph("DOBLES", new Font(FontFamily.HELVETICA, 8)))); 
       
       for (int i=0; i<36;i++){
      	 tabla10.addCell( new PdfPCell(new Paragraph(".", new Font(FontFamily.HELVETICA, 8))));             
       }  
       
       tabla10.addCell( new PdfPCell(new Paragraph("TOTAL", new Font(FontFamily.HELVETICA, 8))));          
       for (int i=0; i<3;i++){
      	 tabla10.addCell( new PdfPCell(new Paragraph(".", new Font(FontFamily.HELVETICA, 8))));             
         }
         
 
         tabla10.setWidths( new int[]{2,1,1,1 } );
         tabla10.writeSelectedRows(0, -1,30, 190, canvas);
         
         
          
         
         
         PdfPTable tabla11 = new PdfPTable(5);    
         tabla11.setTotalWidth(260);   
         
         aclaracionFirma = new PdfPCell(new Paragraph("ARRENDAMIENTO", new Font(FontFamily.HELVETICA, 7)));
       aclaracionFirma.setColspan(5);  
       tabla11.addCell( aclaracionFirma ); 
       
       tabla11.addCell( new PdfPCell(new Paragraph("ACCION", new Font(FontFamily.HELVETICA, 8))));
       tabla11.addCell( new PdfPCell(new Paragraph("IMPORTE", new Font(FontFamily.HELVETICA, 8))));
       aclaracionFirma = new PdfPCell(new Paragraph("FECHA", new Font(FontFamily.HELVETICA, 7)));
       aclaracionFirma.setColspan(3);  
       tabla11.addCell( aclaracionFirma );          
        
       tabla11.addCell( new PdfPCell(new Paragraph("INICIA FACTURACION", new Font(FontFamily.HELVETICA, 8))));
       for (int i=0; i<4;i++){
           tabla11.addCell( new PdfPCell(new Paragraph("", new Font(FontFamily.HELVETICA, 8)))); 
       }         
       tabla11.addCell( new PdfPCell(new Paragraph("AUMENTA CUOTA" , new Font(FontFamily.HELVETICA, 8))));
       for (int i=0; i<4;i++){
           tabla11.addCell( new PdfPCell(new Paragraph("", new Font(FontFamily.HELVETICA, 8)))); 
       } 
       tabla11.addCell( new PdfPCell(new Paragraph("SUSPENDER FACTURACION" , new Font(FontFamily.HELVETICA, 8)))); 
       for (int i=0; i<4;i++){
           tabla11.addCell( new PdfPCell(new Paragraph("", new Font(FontFamily.HELVETICA, 8)))); 
       }
       tabla11.addCell( new PdfPCell(new Paragraph( "REDUCE CUOTA", new Font(FontFamily.HELVETICA,8)))); 
       for (int i=0; i<4;i++){
           tabla11.addCell( new PdfPCell(new Paragraph("", new Font(FontFamily.HELVETICA, 8)))); 
       }
       tabla11.addCell( new PdfPCell(new Paragraph( "PROCESO", new Font(FontFamily.HELVETICA,8)))); 
       for (int i=0; i<4;i++){
           tabla11.addCell( new PdfPCell(new Paragraph("", new Font(FontFamily.HELVETICA, 8)))); 
         }
 
         tabla11.setWidths( new int[]{4,2,1,1,1 } );
         tabla11.writeSelectedRows(0, -1,300, 190, canvas);
         
         
         
         
         
         PdfPTable tabla12 = new PdfPTable(3); 
         tabla12.setTotalWidth(220);  
         
         tabla12.addCell( new PdfPCell(new Paragraph("SECCION", new Font(FontFamily.HELVETICA, 8))));
       aclaracionFirma = new PdfPCell(new Paragraph("IMPORTE", new Font(FontFamily.HELVETICA, 7)));
       aclaracionFirma.setColspan(2);  
       tabla12.addCell( aclaracionFirma ); 
       
       for (int i=0; i<9;i++){
           tabla12.addCell( new PdfPCell(new Paragraph(".", new Font(FontFamily.HELVETICA, 8)))); 
       }
       
       tabla12.addCell( new PdfPCell(new Paragraph("TOTAL", new Font(FontFamily.HELVETICA, 8))));
       tabla12.addCell( new PdfPCell(new Paragraph("", new Font(FontFamily.HELVETICA, 8))));
       tabla12.addCell( new PdfPCell(new Paragraph("", new Font(FontFamily.HELVETICA, 8))));

       
       
       tabla12.setWidths( new int[]{5,5,1 } );
       tabla12.writeSelectedRows(0, -1, 330, 100, canvas);
       
       
       
      
       
       
       
       
       
       document.close();
       
       sOS.flush();
       sOS.close();
       System.out.println("Todo Bien"); 
        
     }
     catch(Exception e){
    	 e.printStackTrace();
     } 
   }
    
}






//
//
//
//
//
//
//
//
//
//
//
//
//
//
//detalleFActuras = new PdfPCell(new Paragraph("CONDICIONES DE PAGO", new Font(FontFamily.HELVETICA, 10)));
// detalleFActuras.setColspan(5);  
// tabla8.addCell( detalleFActuras );          
// tabla8.addCell( new PdfPCell(new Paragraph("CONTROLADO" , new Font(FontFamily.HELVETICA, 10)))); 
// 
// detalleFActuras = new PdfPCell(new Paragraph("SUB - TOTAL", new Font(FontFamily.HELVETICA, 10)));
// detalleFActuras.setRowspan(2);
// tabla8.addCell( detalleFActuras );      
// 
// detalleFActuras = new PdfPCell(new Paragraph("", new Font(FontFamily.HELVETICA, 10)));
// detalleFActuras.setRowspan(2);  
// tabla8.addCell( detalleFActuras ); 
// 
// detalleFActuras = new PdfPCell(new Paragraph("", new Font(FontFamily.HELVETICA, 10)));
// detalleFActuras.setRowspan(2); 
// tabla8.addCell( detalleFActuras ); 
// 
// 
//
// 
// detalleFActuras = new PdfPCell(new Paragraph(".", new Font(FontFamily.HELVETICA, 10)));
// detalleFActuras.setColspan(5); 
// detalleFActuras.setRowspan(2);
// tabla8.addCell( detalleFActuras ); 
// 
// detalleFActuras = new PdfPCell(new Paragraph(".", new Font(FontFamily.HELVETICA, 10)));
// detalleFActuras.setRowspan(5);
// tabla8.addCell( detalleFActuras ); 
// 
// 
// 
// detalleFActuras = new PdfPCell(new Paragraph("IVA", new Font(FontFamily.HELVETICA, 10))); 
// detalleFActuras.setRowspan(2);
// tabla8.addCell( detalleFActuras ); 
// detalleFActuras = new PdfPCell(new Paragraph("", new Font(FontFamily.HELVETICA, 10))); 
// detalleFActuras.setRowspan(2);
// tabla8.addCell( detalleFActuras ); 
// detalleFActuras = new PdfPCell(new Paragraph("", new Font(FontFamily.HELVETICA, 10))); 
// detalleFActuras.setRowspan(2);
// tabla8.addCell( detalleFActuras ); 
// 
// 
//tabla8.addCell( new PdfPCell(new Paragraph("FACTURA - V.C." , new Font(FontFamily.HELVETICA, 10)))); 
//
//detalleFActuras = new PdfPCell(new Paragraph("FECHA", new Font(FontFamily.HELVETICA, 10)));
//detalleFActuras.setColspan(3);  
//tabla8.addCell( detalleFActuras ); 
//
//tabla8.addCell( new PdfPCell(new Paragraph("PROCESO" , new Font(FontFamily.HELVETICA, 10)))); 
//tabla8.addCell( new PdfPCell(new Paragraph("TOTAL" , new Font(FontFamily.HELVETICA, 10)))); 
//tabla8.addCell( new PdfPCell(new Paragraph("" , new Font(FontFamily.HELVETICA, 10)))); 
//tabla8.addCell( new PdfPCell(new Paragraph("" , new Font(FontFamily.HELVETICA, 10))));        
// 
// 
//// 
//// 
//// 
//// 
//// 
//// 
//// 
//// tabla8.addCell( new PdfPCell(new Paragraph("IVA" , new Font(FontFamily.HELVETICA, 10)))); 
//// tabla8.addCell( new PdfPCell(new Paragraph("" , new Font(FontFamily.HELVETICA, 10)))); 
//// tabla8.addCell( new PdfPCell(new Paragraph("" , new Font(FontFamily.HELVETICA, 10)))); 
//// tabla8.addCell( new PdfPCell(new Paragraph("" , new Font(FontFamily.HELVETICA, 10)))); 
//// 
//// 
//// 
//// tabla8.addCell( new PdfPCell(new Paragraph("FACTURA - V.C." , new Font(FontFamily.HELVETICA, 10)))); 
////  
//// detalleFActuras = new PdfPCell(new Paragraph("FECHA", new Font(FontFamily.HELVETICA, 10)));
//// detalleFActuras.setColspan(3);  
//// tabla8.addCell( detalleFActuras ); 
//// 
//// tabla8.addCell( new PdfPCell(new Paragraph("PROCESO" , new Font(FontFamily.HELVETICA, 10)))); 
//// tabla8.addCell( new PdfPCell(new Paragraph("TOTAL" , new Font(FontFamily.HELVETICA, 10)))); 
//// tabla8.addCell( new PdfPCell(new Paragraph("" , new Font(FontFamily.HELVETICA, 10)))); 
//// tabla8.addCell( new PdfPCell(new Paragraph("" , new Font(FontFamily.HELVETICA, 10)))); 
//// 
//


