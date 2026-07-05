package sistemaintercampus.modelo;

public class Bus {
    private String placa;
    private int capacidad;
    private String estado;
    private double latitudActual;
    private double longitudActual;
    private Chofer chofer;

    public Bus(String placa, int capacidad, String estado,
               double latitudActual, double longitudActual) {
        this.placa = placa;
        this.capacidad = capacidad;
        this.estado = estado;
        this.latitudActual = latitudActual;
        this.longitudActual = longitudActual;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public Chofer getChofer() {
        return chofer;
    }

    public void setChofer(Chofer chofer) {
        this.chofer = chofer;
    }

    public void actualizarUbicacion(double latitud, double longitud) {
        this.latitudActual = latitud;
        this.longitudActual = longitud;
    }

    public void actualizarEstado(String estado) {
        this.estado = estado;
    }

    public boolean tieneChofer() {
        return chofer != null;
    }

    @Override
    public String toString() {
        String datosChofer = chofer == null ? "Sin chofer asignado" : chofer.toString();
        return "Placa: " + placa +
                " | Capacidad: " + capacidad +
                " | Estado: " + estado +
                " | Ubicacion: (" + latitudActual + ", " + longitudActual + ")" +
                " | " + datosChofer;
    }
}
