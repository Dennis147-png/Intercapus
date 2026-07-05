package sistemaintercampus.modelo;

public class Sede {
    private String idSede;
    private String nombre;
    private double latitud;
    private double longitud;

    public Sede(String idSede, String nombre, double latitud, double longitud) {
        this.idSede = idSede;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getIdSede() {
        return idSede;
    }

    public void setIdSede(String idSede) {
        this.idSede = idSede;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    @Override
    public String toString() {
        return idSede + " - " + nombre + " (" + latitud + ", " + longitud + ")";
    }
}
