import java.io.FileInputStream;
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

import util.ExportarOTPDF;



@WebServlet("/docfunpdf")
public class ServletDocFuncionario extends HttpServlet {
	private static final long serialVersionUID = 1L; 
    public ServletDocFuncionario() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
      response.setContentType("application/pdf");
      
      //corregir la direccion de la carpeta, verificar si no existe crearla, y catchear si no halla
      //el archivo
     try {
       String path=request.getParameter("path"); 
    	 FileInputStream ficheroInput = new FileInputStream(path);
    	 int tamanoInput = ficheroInput.available();
    	 byte[] datosPDF = new byte[tamanoInput];
    	 ficheroInput.read( datosPDF, 0, tamanoInput);
    	 response.setHeader("Content-disposition","inline; filename=Exportar archivo funcionario" );
    	 response.setContentType("application/pdf");
    	 response.setContentLength(tamanoInput);
    	 response.getOutputStream().write(datosPDF);
    	 ficheroInput.close();
      } catch (Exception de) {
          throw new IOException(de.getMessage());
      }  
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
