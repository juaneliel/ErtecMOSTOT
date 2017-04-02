package util;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javafx.util.converter.BigDecimalStringConverter;
import model.Articulo;
import model.ComprasExternasOT;
import model.Movimiento;
import model.Ot;
import model.DAO.DAO_Articulo;
import model.DAO.DAO_Movimiento;
import model.DAO.DAO_OT;
import model.NexoMovimiento;


public class ExportMovimientoPDF {
  
  
  
	HttpServletRequest servletContext = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	String realPath=(String) servletContext.getServletPath(); 
	//System.out.println("contexto addnexo: ");
	
	

  public static final String RESOURCE = "http://localhost:8080/ertec/resources/images/1964.jpg";
  public static final String RESULT = "/home/juan/Escritorio/movimiento.pdf";
  
  public static void main(String[] args) {
    ExportMovimientoPDF.ExportarPDF(0,null);
  }    
  
  
  
  public static void ExportarPDF(int idOT, ServletOutputStream sOS  )   {
  
    //String RESULT = "/home/juan/Escritorio/OT.pdf";
    
    //FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/1964.pdf");
    
    BigDecimal costoMovimiento=BigDecimal.ZERO;
    BigDecimal costoTotal=BigDecimal.ZERO;
    BigDecimal costoLinea = BigDecimal.ZERO;

    DAO_OT dao= new DAO_OT();    
    DAO_Movimiento daoMov = new DAO_Movimiento();
    
    ArrayList <Movimiento> movimientos =DAO_Movimiento.getMovimientosOT(idOT);
    ArrayList <ComprasExternasOT> cExternas =dao.getComprasExternas(idOT);
    
    //ArrayList <Movimiento> movimientos =new ArrayList <Movimiento>();
//    Movimiento m = new Movimiento();
//    m.setTipoOT("Salida por OT-OR");
//    m.setComprobante(1);
//    m.setFecha(new Date());
//    m.setNexos(new ArrayList<NexoMovimiento>());
//    NexoMovimiento nexo=new NexoMovimiento();
//    Articulo a=new Articulo();
//    a.setArticuloID(1);
//    a.setDescripcion("descripcion");
//    nexo.setArticulo(a);
//    nexo.setCantidad( BigDecimal.valueOf(3));
//    nexo.setCosto(BigDecimal.valueOf(250));
//    m.getNexos().add(nexo);
//    movimientos.add(m);
//    
//    
//    
//    m = new Movimiento();
//    m.setTipoOT("Salida por OT-OR");
//    m.setComprobante(2);
//    m.setFecha(new Date());
//    m.setNexos(new ArrayList<NexoMovimiento>());
//    nexo=new NexoMovimiento();
//    a=new Articulo();
//    a.setArticuloID(2);
//    a.setDescripcion("dos");
//    nexo.setArticulo(a);
//    nexo.setCantidad( BigDecimal.valueOf(5));
//    nexo.setCosto(BigDecimal.valueOf(100));
//    m.getNexos().add(nexo);
//    
//    nexo=new NexoMovimiento();
//    a=new Articulo();
//    a.setArticuloID(2);
//    a.setDescripcion("dos");
//    nexo.setArticulo(a);
//    nexo.setCantidad( BigDecimal.valueOf(6));
//    nexo.setCosto(BigDecimal.valueOf(100));
//    m.getNexos().add(nexo);
//    
//    movimientos.add(m);
//    
    
    try {
      
      Document document = new Document(PageSize.A4, 30, 30, 30, 30); 
       // PdfWriter writer= PdfWriter.getInstance(document, new FileOutputStream(RESULT)); 
        
        PdfWriter writer=PdfWriter.getInstance(document, sOS);
        
        
        document.open();   
        
        try{
	        Image img = Image.getInstance(RESOURCE);
	        img.setAbsolutePosition(20,750);
	        img.scalePercent(50);
	        document.add(img);
        }
        catch(Exception e){
        	 System.err.println("no se puede cargar la imagen");
        }
        
        PdfContentByte canvas = writer.getDirectContent();
        BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
        canvas.saveState();
        canvas.beginText();
        canvas.moveText(320,780);
        canvas.setFontAndSize(bf, 12);
        canvas.showText("Consulta Movs. para la Referencia OT");
        canvas.endText();
        canvas.restoreState();
        
        
        canvas.saveState();
        canvas.beginText();
        canvas.moveText(400,750);
        canvas.setFontAndSize(bf, 12);
        canvas.showText("Nro: "+Integer.toString(idOT));
        canvas.endText();
        canvas.restoreState();
        
        
        // Creacion de tabla 1         
        PdfPTable tabla1 = new PdfPTable(5);    
        tabla1.setTotalWidth(550);  
        
        PdfPCell cell =new PdfPCell(new Paragraph("Articulo", new Font(FontFamily.HELVETICA, 10)));
        cell.setMinimumHeight(20);        
        tabla1.addCell( cell );
        
        cell =new PdfPCell(new Paragraph("Descripcion", new Font(FontFamily.HELVETICA, 10)));
        cell.setMinimumHeight(20);        
        tabla1.addCell( cell );
        
        cell =new PdfPCell(new Paragraph("Cantidad", new Font(FontFamily.HELVETICA, 10)));
        cell.setMinimumHeight(20);        
        tabla1.addCell( cell );
        
        cell =new PdfPCell(new Paragraph("Costo", new Font(FontFamily.HELVETICA, 10)));
        cell.setMinimumHeight(20);        
        tabla1.addCell( cell );
        
        cell =new PdfPCell(new Paragraph("Total", new Font(FontFamily.HELVETICA, 10)));
        cell.setMinimumHeight(20);        
        tabla1.addCell( cell );
        
        
        for(ComprasExternasOT ce : cExternas){
        	String cabecera="";
          cabecera+="Fecha: "+ce.getFecha()+"    ";
          cabecera+="Proveedor: "+ ce.getProveedor().getNombre()+"    ";
          cabecera+="Comprobante: "+ce.getId()+"    "; 
          
          cell = new PdfPCell(new Paragraph(cabecera, new Font(FontFamily.HELVETICA, 10)));
          cell.setColspan(5);
          cell.setMinimumHeight(20); 
          tabla1.addCell( cell ); 
          
          
          
          cell =new PdfPCell(new Paragraph("Externa", new Font(FontFamily.HELVETICA, 10)));
          cell.setMinimumHeight(20);        
          tabla1.addCell( cell );
          
          cell =new PdfPCell(new Paragraph(ce.getNombreArticulo(), new Font(FontFamily.HELVETICA, 10)));
          cell.setMinimumHeight(20);        
          tabla1.addCell( cell );
          
          cell =new PdfPCell(new Paragraph( ""+ ce.getCantidad() , new Font(FontFamily.HELVETICA, 10)));
          cell.setMinimumHeight(20);        
          tabla1.addCell( cell );
          
          BigDecimal costo=ce.getPrecio_Unitario();
          
          
          cell =new PdfPCell(new Paragraph(""+costo, new Font(FontFamily.HELVETICA, 10)));
          cell.setMinimumHeight(20);        
          tabla1.addCell( cell ); 
          
          
          costoLinea =costo.multiply(BigDecimal.valueOf(ce.getCantidad()));
          costoMovimiento =costoMovimiento.add(costoLinea);
          costoTotal=costoTotal.add(costoLinea);
          
          cell =new PdfPCell(new Paragraph(""+costoLinea, new Font(FontFamily.HELVETICA, 10)));
          cell.setMinimumHeight(20);        
          tabla1.addCell( cell );        
          
        }
        
        
        
        
        
        
        
        
        for (Movimiento mov : movimientos){
          String cabecera="";
          cabecera+="Fecha: "+mov.getFecha()+"    ";
          cabecera+="Tipo Movimiento: "+ daoMov.getDescripcionMov(mov.getCodigoMovimientoID())+"    ";
          cabecera+="Comprobante: "+mov.getMovimientoID()+"    "; 
          
          cell = new PdfPCell(new Paragraph(cabecera, new Font(FontFamily.HELVETICA, 10)));
          cell.setColspan(5);
          cell.setMinimumHeight(20); 
          tabla1.addCell( cell ); 
           
          
          
          ArrayList<NexoMovimiento> nexos= daoMov.getNexos(mov.getMovimientoID()); //(ArrayList<NexoMovimiento>) mov.getNexos();
          costoMovimiento=BigDecimal.ZERO;
          for (NexoMovimiento n :nexos){
            cell =new PdfPCell(new Paragraph(Integer.toString(n.getArticulo().getArticuloID()), new Font(FontFamily.HELVETICA, 10)));
            cell.setMinimumHeight(20);        
            tabla1.addCell( cell );
            
            cell =new PdfPCell(new Paragraph(n.getArticulo().getDescripcion(), new Font(FontFamily.HELVETICA, 10)));
            cell.setMinimumHeight(20);        
            tabla1.addCell( cell );
            
            cell =new PdfPCell(new Paragraph( ""+ n.getCantidad() , new Font(FontFamily.HELVETICA, 10)));
            cell.setMinimumHeight(20);        
            tabla1.addCell( cell );
            
            BigDecimal costo=n.getCosto();
            if(mov.getCodigoMovimientoID()==4){
            	costo = BigDecimal.ZERO.subtract(n.getArticulo().getCostoPesos());
            }
            
            cell =new PdfPCell(new Paragraph(""+costo, new Font(FontFamily.HELVETICA, 10)));
            cell.setMinimumHeight(20);        
            tabla1.addCell( cell );
            
            System.out.println("export " +n);
            System.out.println("export cantidad " +n.getCantidad());
            System.out.println("export costo " +costo);
            
            
            costoLinea = n.getCantidad().multiply(costo);
            costoMovimiento =costoMovimiento.add(costoLinea);
            costoTotal=costoTotal.add(costoLinea);
            
            cell =new PdfPCell(new Paragraph(""+costoLinea, new Font(FontFamily.HELVETICA, 10)));
            cell.setMinimumHeight(20);        
            tabla1.addCell( cell );
          } 
          cell = new PdfPCell(new Paragraph("", new Font(FontFamily.HELVETICA, 10)));
          cell.setColspan(4);
          tabla1.addCell( cell ); 
          
          cell = new PdfPCell(new Paragraph(""+costoMovimiento, new Font(FontFamily.HELVETICA, 12)));
          cell.setMinimumHeight(20);      
          tabla1.addCell( cell );
        } 
        
        cell = new PdfPCell(new Paragraph("Total de los costos $", new Font(FontFamily.HELVETICA, 14)));
        cell.setColspan(4);
        tabla1.addCell( cell ); 
        
        cell = new PdfPCell(new Paragraph(""+costoTotal, new Font(FontFamily.HELVETICA, 14)));
        cell.setMinimumHeight(20);      
        tabla1.addCell( cell );
        
        
        
    
        tabla1.setWidths( new int[]{ 1,4,1,1,1 } );
        tabla1.writeSelectedRows(0,-1,20,710,canvas);
        
        
        document.close();        
//        sOS.flush();
//        sOS.close();
        System.out.println("Todo Bien"); 
    
    }
    catch(Exception e){
      e.printStackTrace();
    }
    
    
    
  }  
  
  
  
  
  
  
}
