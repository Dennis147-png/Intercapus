package sistemaintercampus.modelo;

public class Estudiante extends Usuario {
    private String idBanner;
    private double latitudActual;
    private double longitudActual;

    public Estudiante(int idUsuario, String nombre, String correo,
                      String idBanner, double latitudActual, double longitudActual) {
        super(idUsuario, nombre, correo);
        this.idBanner = idBanner;
        this.latitudActual = latitudActual;
        this.longitudActual = longitudActual;
    }

    public String getMatricula() {
        return idBanner;
    }

    public String getIdBanner() {
        return idBanner;
    }

    public void setMatricula(String idBanner) {
        this.idBanner = idBanner;
    }

    public void setIdBanner(String idBanner) {
        this.idBanner = idBanner;
    }

    public double getLatitudActual() {
        return latitudActual;
    }

    public void setLatitudActual(double latitudActual) {
        this.latitudActual = latitudActual;
    }

    public double getLongitudActual() {
        return longitudActual;
    }

    public void setLongitudActual(double longitudActual) {
        this.longitudActual = longitudActual;
    }

    public void recibirNotificacion(String mensaje) {
        System.out.println("Notificacion para " + getNombre() + ": " + mensaje);
    }

    @Override
    public String toString() {
        return super.toString() +
                " | ID Banner: " + idBanner +
                " | Ubicacion: (" + latitudActual + ", " + longitudActual + ")";
    }
}
