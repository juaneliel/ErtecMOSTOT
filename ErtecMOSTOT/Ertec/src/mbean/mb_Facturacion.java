package mbean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import model.Cliente;
import model.Contrato;
import model.DAO.DAO_FacturacionAutomaticaContratos;

@ManagedBean(name="mb_Facturacion")
@ViewScoped
public class mb_Facturacion {

  private String descripcion;
	private String descripcionAuxiliar;
	private double iva;
	private Contrato ContratoSelected;
	
	private void facturacionAutomatica(Date fecha){
		//se obtiene el mes de la fecha y se pasa a facturar 
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		int mes = cal.get(Calendar.MONTH);	
		switch (mes) {
			case 1:   
				facturaAutomatica(fecha,"A", 12, iva);
				facturaAutomatica(fecha,"S", 6, iva);
				facturaAutomatica(fecha,"T", 3, iva);
				facturaAutomatica(fecha,"M", 1, iva);
				break;
			case 7:   
				facturaAutomatica(fecha,"S", 6, iva);
				facturaAutomatica(fecha,"T", 3, iva);
				facturaAutomatica(fecha,"M", 1, iva);
				break;
			case 4:    
				facturaAutomatica(fecha,"T", 3, iva);
				facturaAutomatica(fecha,"M", 1, iva);
				break;	
			case 10:
				facturaAutomatica(fecha,"T", 3, iva);
				facturaAutomatica(fecha,"M", 1, iva);
				break;			
			default:    
				facturaAutomatica(fecha,"M", 1, iva);
				break;					
		}
	}
	
	private void facturaAutomatica (Date fecha, String frecuencia,int cantidad,double iva){
		ArrayList<Contrato> contratos= DAO_FacturacionAutomaticaContratos.getContratosFrecuencia(frecuencia);
		boolean Puedo_Grabar;
    BigDecimal Importe;
    BigDecimal Iva_Total;
    BigDecimal Total;
    Date Fecha_Final_Factura;
		for(Contrato contrato : contratos){
			Calendar cal = Calendar.getInstance();
			cal.setTime(fecha);
			cal.add(Calendar.MONTH, cantidad);	
			Fecha_Final_Factura =cal.getTime();
			 Puedo_Grabar = false;
           Importe = BigDecimal.ZERO;
           Iva_Total = BigDecimal.ZERO;
           Total = BigDecimal.ZERO;
           Date NoFacturar;
           if (contrato.getNoFacturar()!=null){
          	 NoFacturar = contrato.getFechaInicio();
           }
           else{
          	 NoFacturar = contrato.getNoFacturar();
           }
           //DateDiff("d", MaskEdBox1, contrato.getFechaFin()) 
           if (contrato.getFechaFin().after(fecha)){ 
          	   if (fecha.after(NoFacturar)){ 
          	  	   if (contrato.getFechaFin().after(Fecha_Final_Factura)){
                       //calculo Importe Factura **************
                       Importe = contrato.getCuota().multiply(BigDecimal.valueOf(cantidad));
                       Puedo_Grabar = true;
          	  	   }else{
                       Importe = sacoImporte(fecha, contrato.getFechaFin(),contrato.getCuota());
                       Puedo_Grabar = true;
          	  	   }
          	   }else{
                   if ( NoFacturar.before(Fecha_Final_Factura) && 
                  		 contrato.getNoFacturar().before(contrato.getFechaFin()) &&
                  		 Fecha_Final_Factura.before(contrato.getFechaFin()) ){
                       Importe = sacoImporte(fecha, NoFacturar, contrato.getCuota());
                       Puedo_Grabar = true;
          	   		 }
           		 }
               if (Puedo_Grabar) {
              	   Double ivat = iva / 100;
                   Iva_Total = Importe.multiply(BigDecimal.valueOf(ivat));
                   Total = Importe.add(Iva_Total);
                   //verificar como obtener contrato y cliente
                   graboFacturaAutomatica(contrato,Importe, Iva_Total, Total, descripcion, descripcionAuxiliar);
                   //Numero = Numero + 1
							 }
           }
       //Call Actualizo_Nro_FacturaContrato(Numero)
		}
	}
	
	private BigDecimal sacoImporte(Date fechaFin,Date fechaIni,BigDecimal importe){
    long Dias;
    long mes ;
    int diaTot;
    int mesTot;
    int anoTot;
    
    Calendar iniF= Calendar.getInstance();
    iniF.setTime(fechaIni);
    int anoIni = iniF.get(Calendar.YEAR);
    int mesIni = iniF.get(Calendar.MONTH)+1;//enero da 0 por eso +1
    int diaIni = iniF.get(Calendar.DAY_OF_MONTH);
    
    Calendar finF = Calendar.getInstance();
    finF.setTime(fechaFin);
    int anoFin = finF.get(Calendar.YEAR);
    int mesFin = finF.get(Calendar.MONTH)+1;
    int diaFin = finF.get(Calendar.DAY_OF_MONTH);
     
//************   SACO CANTIDAD DE DIAS ***************
    if (diaIni >= diaFin) {
        diaTot = diaIni - diaFin;
    }else{
        diaTot = diaIni + 30 - diaFin;
        mesIni = mesIni - 1;
    } 

    if (diaTot == 29) {
    	if(mesIni==4 || mesIni==6 || mesIni==9 || mesIni==11){
    		diaTot = 30;
    	}
    }
    
    if (diaTot == 27 || diaTot == 28) {
        if (mesIni == 2) {
        	diaTot = 30;
        }
    }
     
//*********** SACO CANTIDAD DE MESES ***************
    if (mesIni >= mesFin) {
        mesTot = mesIni - mesFin;
    }else{
        mesTot = mesIni + 12 - mesFin;
        anoIni = anoIni - 1;
    }
   
//************* SACO CANTIDAD DE AÑOS *****************
    if (anoIni >= anoFin){
    	anoTot = anoIni - anoFin;
    }else{
    	anoTot = 0;
    }
    
//creo salida
    BigDecimal auxDT = (importe.multiply(BigDecimal.valueOf(diaTot))).divide(BigDecimal.valueOf(30));
    BigDecimal auxMT = importe.multiply(BigDecimal.valueOf(mesTot));
    //importer * (Año_Tot * 12) que es importer?    
    BigDecimal auxAT = BigDecimal.valueOf(12).multiply(BigDecimal.valueOf(anoTot));
    
    return auxDT.add(auxMT).add(auxAT);
	}
  
	

	private void graboFacturaAutomatica(Contrato contrato, BigDecimal importe,BigDecimal iva, BigDecimal total,String descripcion,String descripcionAuxiliar){
		DAO_FacturacionAutomaticaContratos.graboFacturaAutomatica(contrato,importe,iva,total,descripcion,descripcionAuxiliar);
	}
	
	private void graboFacturaAutomatica(BigDecimal importe, BigDecimal iva, String descripcion, String descripcion1, String moneda){
//  Set FacturacionAutomaticaContratos = New ADODB.Recordset
//  strsql = "Insert Into FacturacionAutomaticaContratos " & _
//  "([Numero],[ClienteID],[ContratoID],[Tipo],[Fecha],[Importe],[Iva],[Total],[Descripcion],[Moneda],[DescripcionAuxiliar])" & _
//  "values('" & CLng(FacturacionManualContratos.SSPanel3) & "', '" & Val(FacturacionManualContratos.Text3) & "' " & _
//  ", '" & Val(FacturacionManualContratos.Text2) & "', '" & UCase$(FacturacionManualContratos.Text1) & "' " & _
//  ", '" & Format$(FacturacionManualContratos.MaskEdBox1, "dd/mm/yyyy") & "', '" & Importe & "' " & _
//  ", '" & (Importe * iva / 100) & "', '" & Importe * ((iva / 100) + 1) & "', '" & Descripcion & "', '" & Moneda & "' " & _
//  ", '" & Descripcion1 & "' )"
//  FacturacionAutomaticaContratos.Open strsql, BDCTASCTES, , , adCmdText
	}

	
	private void graboAumentoCuota(Contrato contrato,BigDecimal cuota,BigDecimal aumento, Date fecha){
  
//  Dim Cliente As Integer
//  Dim Contrato As Integer
//  
//  '*********** grabo base de aumentos ***********'
//  Cliente = Contratos("ClienteID")
//  Contrato = Contratos("ContratoID")
//  
//  Set AumentoContratos = New ADODB.Recordset
//  strsql = "Insert Into AumentoContratos " & _
//  "([ClienteID],[ContratoID],[TipoContrato],[CuotaAnterior],[PorAumento],[AumentoMes],[CuotaNueva]) " & _
//  "values('" & Contratos("ClienteID") & "', '" & Contratos("ContratoID") & "', '" & Contratos("Tipo") & "' " & _
//  ", '" & Contratos("Cuota") & "', '" & Aumento & "', '" & Cuota - Contratos("Cuota") & "', '" & Cuota & "' )"
//  AumentoContratos.Open strsql, BDCTASCTES, , , adCmdText
//  
//  '********** actualizo base de contratos **************** 
		contrato.setCuota(cuota);
		contrato.setUltimoAjuste(fecha);
		DAO_FacturacionAutomaticaContratos.updateCuota(contrato);
	}



	public void aumentarContrato(Date fecha, BigDecimal aumento){
  	DAO_FacturacionAutomaticaContratos.deleteAll();
  	ArrayList<Contrato> contratos=DAO_FacturacionAutomaticaContratos.getContratos();
  	for (Contrato contrato : contratos){
  		if ( contrato.getNoAumentar()==null  || !fecha.before(contrato.getNoAumentar()) ){
        if ( !fecha.before(contrato.getFechaFin()) ){ 
        	BigDecimal aux = (aumento.divide(BigDecimal.valueOf(100))).add(BigDecimal.valueOf(1));
        	BigDecimal cuota=contrato.getCuota().multiply(aux);
          graboAumentoCuota(contrato,cuota, aumento,fecha);
        }
  		}        
  	}
  }    

	

	private void facturacionInicial(Date fechaIni, Date fechaFin,BigDecimal imp){
	  iva = sacoIva();
	  BigDecimal importe = sacoImporte(fechaIni, fechaFin,imp);
	  String moneda = ContratoSelected.getMoneda();
    if (!moneda.equals("U$S")){
	      moneda = " " + moneda + " ";
	  }
	  String descripcion = " " + "de Inst. " + " " + "Tel. del " + fechaIni + " al " + " " + 
	  		fechaFin + " a razon de " + " " + moneda + " " + imp + " mensuales";
	  String descripcion1 = "-" + fechaIni + " al " + fechaFin;
	  graboFacturaAutomatica(importe, BigDecimal.valueOf(iva), descripcion, descripcion1, moneda);
	  //inicioFormulario();
	}
	
	private double sacoIva(){
		return 0.0d;
	}
	
	
}
