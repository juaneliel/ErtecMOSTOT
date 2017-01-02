package model.DAO;

public class DAO_infoService {  
    private String codigo="";
    private String nombreCliente="";
    private String equipo="";
    private long visitas;
    private long tiempoRespuesta;
    private double tiempoHoras;
    private long horasAntel;
    private long horasConmutador;
    private long horasRed;
    public String getCodigo() {
      return codigo;
    }
    public void setCodigo(String codigo) {
      this.codigo = codigo;
    }
    public String getNombreCliente() {
      return nombreCliente;
    }
    public void setNombreCliente(String nombreCliente) {
      this.nombreCliente = nombreCliente;
    }
    public String getEquipo() {
      return equipo;
    }
    public void setEquipo(String equipo) {
      this.equipo = equipo;
    }
    public long getVisitas() {
      return visitas;
    }
    public void setVisitas(long visitas) {
      this.visitas = visitas;
    }
    public long getTiempoRespuesta() {
      return tiempoRespuesta;
    }
    public void setTiempoRespuesta(long tiempoRespuesta) {
      this.tiempoRespuesta = tiempoRespuesta;
    }


    public double getTiempoHoras() {
      return tiempoHoras;
    }
    public void setTiempoHoras(double tiempoHoras) {
      this.tiempoHoras = tiempoHoras;
    }
    public long getHorasAntel() {
      return horasAntel;
    }
    public void setHorasAntel(long horasAntel) {
      this.horasAntel = horasAntel;
    }
    public long getHorasConmutador() {
      return horasConmutador;
    }
    public void setHorasConmutador(long horasConmutador) {
      this.horasConmutador = horasConmutador;
    }
    public long getHorasRed() {
      return horasRed;
    }
    public void setHorasRed(long horasRed) {
      this.horasRed = horasRed;
    } 
    
    
         

}
