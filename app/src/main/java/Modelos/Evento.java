package Modelos;

public class Evento {
    private String Descripcion;
    private String Expositores;
    private String Ubucacion;
    private String Auditorio;

    public Evento() {
    }

    public Evento(String descripcion, String expositores, String ubucacion, String auditorio) {
        Descripcion = descripcion;
        Expositores = expositores;
        Ubucacion = ubucacion;
        Auditorio = auditorio;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getExpositores() {
        return Expositores;
    }

    public void setExpositores(String expositores) {
        Expositores = expositores;
    }

    public String getUbucacion() {
        return Ubucacion;
    }

    public void setUbucacion(String ubucacion) {
        Ubucacion = ubucacion;
    }

    public String getAuditorio() {
        return Auditorio;
    }

    public void setAuditorio(String auditorio) {
        Auditorio = auditorio;
    }


}
