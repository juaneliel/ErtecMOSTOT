

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date; 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse; 
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import util.ExportMovimientoPDF;
import util.ExportarOTPDF;



@WebServlet("/otapdf")
public class PruebaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PruebaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
      response.setContentType("application/pdf");
//     try {
//          //request.getParameter("otID");
//        
//          // step 1
//          Document document = new Document();
//          // step 2
//          PdfWriter.getInstance(document, response.getOutputStream());
//          // step 3
//          document.open();
//          // step 4
//          document.add(new Paragraph("Hello World"));
//          document.add(new Paragraph(new Date().toString()));
//          // step 5
//          document.close(); 
//      } catch (DocumentException de) {
//          throw new IOException(de.getMessage());
//      } 
      
      
    String idOT=request.getParameter("otID");
    System.out.println(idOT);    
    String tipo=request.getParameter("tipo");
    
    ExportMovimientoPDF.ExportarPDF(Integer.parseInt(idOT),response.getOutputStream()); 
    
//    if (tipo.equals("ot")){
//    	ExportarOTPDF.ExportarPDF(Integer.parseInt(idOT),response.getOutputStream());      
//    }
//    else{
//      if (tipo.equals("mov")){ 	
//      	
//      	ExportMovimientoPDF.ExportarPDF(Integer.parseInt(idOT),response.getOutputStream());
//      
//      
//      }
//    }
	
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
