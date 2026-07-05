package sistemaintercampus.modelo;

import java.util.ArrayList;
import java.util.List;

public class Ruta {
    private String idRuta;
    private String nombreRuta;
    private double distanciaTotalKm;
    private List<Sede> sedes;

    public Ruta(String idRuta, String nombreRuta, double distanciaTotalKm) {
        this.idRuta = idRuta;
        this.nombreRuta = nombreRuta;
        this.distanciaTotalKm = distanciaTotalKm;
        this.sedes = new ArrayList<Sede>();
    }

    public String getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(String idRuta) {
        this.idRuta = idRuta;
    }

    public String getNombreRuta() {
        return nombreRuta;
    }

    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
    }

    public double getDistanciaTotalKm() {
        return distanciaTotalKm;
    }

    public void setDistanciaTotalKm(double distanciaTotalKm) {
        this.distanciaTotalKm = distanciaTotalKm;
    }

    public List<Sede> getSedes() {
        return sedes;
    }

    public List<Sede> obtenerSedes() {
        return sedes;
    }

    public void agregarSede(Sede sede) {
        sedes.add(sede);
    }

    public void eliminarSede(int posicion) {
        if (posicion >= 0 && posicion < sedes.size()) {
            sedes.remove(posicion);
        }
    }

    public Sede getPrimeraSede() {
        if (sedes.isEmpty()) {
            return null;
        }
        return sedes.get(0);
    }

    public Sede getUltimaSede() {
        if (sedes.isEmpty()) {
            return null;
        }
        return sedes.get(sedes.size() - 1);
    }

    public String mostrarRecorrido() {
        String recorrido = "";
        for (int i = 0; i < sedes.size(); i++) {
            recorrido += sedes.get(i).getNombre();
            if (i < sedes.size() - 1) {
                recorrido += " -> ";
            }
        }
        return recorrido;
    }

    @Override
    public String toString() {
        return "Ruta: " + idRuta +
                " | Nombre: " + nombreRuta +
                " | Distancia: " + distanciaTotalKm + " km" +
                " | Recorrido: " + mostrarRecorrido();
    }
}
