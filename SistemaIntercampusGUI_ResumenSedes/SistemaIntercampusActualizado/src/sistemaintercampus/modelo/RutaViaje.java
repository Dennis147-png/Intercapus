package sistemaintercampus.modelo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class RutaViaje {
    private String idViaje;
    private Bus bus;
    private Ruta ruta;
    private String estadoViaje;
    private int tiempoEstimadoLlegadaMinutos;
    private LocalDate fecha;
    private LocalTime horaSalida;
    private LocalTime horaLlegada;
    private int pasajerosActuales;
    private int pasajerosAtendidos;
    private List<RegistroParada> registrosParadas;
    private List<Estudiante> pasajerosEnBus;

    public RutaViaje(String idViaje, Bus bus, Ruta ruta) {
        this.idViaje = idViaje;
        this.bus = bus;
        this.ruta = ruta;
        this.estadoViaje = "PENDIENTE";
        this.tiempoEstimadoLlegadaMinutos = 0;
        this.pasajerosActuales = 0;
        this.pasajerosAtendidos = 0;
        this.registrosParadas = new ArrayList<RegistroParada>();
        this.pasajerosEnBus = new ArrayList<Estudiante>();
    }

    public String getIdViaje() {
        return idViaje;
    }

    public Bus getBus() {
        return bus;
    }

    public Ruta getRuta() {
        return ruta;
    }

    public String getEstadoViaje() {
        return estadoViaje;
    }

    public void setEstadoViaje(String estadoViaje) {
        this.estadoViaje = estadoViaje;
    }

    public int getTiempoEstimadoLlegadaMinutos() {
        return tiempoEstimadoLlegadaMinutos;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHoraSalida() {
        return horaSalida;
    }

    public LocalTime getHoraLlegada() {
        return horaLlegada;
    }

    public int getPasajerosActuales() {
        return pasajerosActuales;
    }

    public int getPasajerosAtendidos() {
        return pasajerosAtendidos;
    }

    public List<Estudiante> getPasajerosEnBus() {
        return pasajerosEnBus;
    }

    public List<RegistroParada> getRegistrosParadas() {
        return registrosParadas;
    }

    public void iniciarViaje() {
        if (fecha == null) {
            fecha = LocalDate.now();
        }
        if (horaSalida == null) {
            horaSalida = LocalTime.now().withSecond(0).withNano(0);
        }
        estadoViaje = "EN RUTA";
        bus.actualizarEstado("EN RUTA");
    }

    public void finalizarViaje() {
        if (horaSalida == null) {
            horaSalida = LocalTime.now().withSecond(0).withNano(0);
        }
        int duracion = tiempoEstimadoLlegadaMinutos;
        if (duracion <= 0) {
            duracion = 1;
        }
        horaLlegada = horaSalida.plusMinutes(duracion);
        estadoViaje = "FINALIZADO";
        bus.actualizarEstado("DISPONIBLE");
    }

    public void calcularTiempoEstimado() {
        double velocidadPromedio = 25.0;

        double horas = ruta.getDistanciaTotalKm() / velocidadPromedio;
        tiempoEstimadoLlegadaMinutos = (int) Math.ceil(horas * 60);
    }

    public boolean registrarParada(Sede parada, int suben, int bajan) {
        if (bajan > pasajerosActuales) {
            return false;
        }

        int nuevoTotal = pasajerosActuales - bajan + suben;
        if (nuevoTotal > bus.getCapacidad()) {
            return false;
        }

        pasajerosActuales = nuevoTotal;
        registrosParadas.add(new RegistroParada(parada, suben, bajan, pasajerosActuales));
        bus.actualizarUbicacion(parada.getLatitud(), parada.getLongitud());
        return true;
    }

    public boolean registrarParada(Sede parada, List<Estudiante> suben, List<Estudiante> bajan) {
        if (bajan.size() > pasajerosEnBus.size()) {
            return false;
        }

        int nuevoTotal = pasajerosActuales - bajan.size() + suben.size();
        if (nuevoTotal > bus.getCapacidad()) {
            return false;
        }

        for (Estudiante estudiante : bajan) {
            pasajerosEnBus.remove(estudiante);
        }

        for (Estudiante estudiante : suben) {
            pasajerosEnBus.add(estudiante);
        }

        pasajerosActuales = nuevoTotal;
        pasajerosAtendidos += suben.size();
        registrosParadas.add(new RegistroParada(parada, suben, bajan, pasajerosActuales));
        bus.actualizarUbicacion(parada.getLatitud(), parada.getLongitud());
        return true;
    }

    public long calcularDuracionMinutos() {
        if (horaSalida == null || horaLlegada == null) {
            return 0;
        }
        return ChronoUnit.MINUTES.between(horaSalida, horaLlegada);
    }

    public void notificarLlegada() {
        System.out.println("El viaje " + idViaje + " llego a su destino.");
    }

    @Override
    public String toString() {
        return "Viaje: " + idViaje +
                " | Bus: " + bus.getPlaca() +
                " | Ruta: " + ruta.getNombreRuta() +
                " | Estado: " + estadoViaje +
                " | Fecha: " + fecha +
                " | Salida: " + horaSalida +
                " | Llegada: " + horaLlegada +
                " | Duracion: " + calcularDuracionMinutos() + " min" +
                " | Pasajeros actuales: " + pasajerosActuales +
                " | Pasajeros atendidos: " + pasajerosAtendidos +
                " | Tiempo estimado: " + tiempoEstimadoLlegadaMinutos + " min";
    }
}
