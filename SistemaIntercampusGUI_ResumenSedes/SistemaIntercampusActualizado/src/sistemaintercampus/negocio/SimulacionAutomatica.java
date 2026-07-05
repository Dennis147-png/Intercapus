package sistemaintercampus.negocio;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import sistemaintercampus.modelo.Estudiante;
import sistemaintercampus.modelo.ISimulacion;
import sistemaintercampus.modelo.RutaViaje;
import sistemaintercampus.modelo.Sede;

public class SimulacionAutomatica implements ISimulacion {
    private List<Estudiante> estudiantesRegistrados;

    public SimulacionAutomatica(List<Estudiante> estudiantesRegistrados) {
        this.estudiantesRegistrados = estudiantesRegistrados;
    }

    @Override
    public void simularViaje(RutaViaje viaje) {
        Random random = new Random();
        List<Estudiante> estudiantesDisponibles = new ArrayList<Estudiante>();

        for (Estudiante estudiante : estudiantesRegistrados) {
            estudiantesDisponibles.add(estudiante);
        }

        if (!viaje.getEstadoViaje().equalsIgnoreCase("EN RUTA")) {
            viaje.iniciarViaje();
        }
        viaje.calcularTiempoEstimado();

        System.out.println("\nSIMULACION AUTOMATICA INICIADA");
        System.out.println("Viaje: " + viaje.getIdViaje());
        System.out.println("Ruta: " + viaje.getRuta().mostrarRecorrido());
        System.out.println("Bus: " + viaje.getBus().getPlaca());
        System.out.println("Capacidad: " + viaje.getBus().getCapacidad());

        for (int i = 0; i < viaje.getRuta().obtenerSedes().size(); i++) {
            Sede parada = viaje.getRuta().obtenerSedes().get(i);
            boolean ultimaParada = i == viaje.getRuta().obtenerSedes().size() - 1;

            List<Estudiante> bajan = new ArrayList<Estudiante>();
            List<Estudiante> suben = new ArrayList<Estudiante>();

            if (ultimaParada) {
                for (Estudiante estudiante : viaje.getPasajerosEnBus()) {
                    bajan.add(estudiante);
                }
            } else {
                int cantidadBajan = 0;
                if (viaje.getPasajerosActuales() > 0) {
                    cantidadBajan = random.nextInt(viaje.getPasajerosActuales() + 1);
                }

                List<Estudiante> pasajerosCopia = new ArrayList<Estudiante>();
                for (Estudiante estudiante : viaje.getPasajerosEnBus()) {
                    pasajerosCopia.add(estudiante);
                }

                for (int j = 0; j < cantidadBajan; j++) {
                    int posicion = random.nextInt(pasajerosCopia.size());
                    Estudiante estudiante = pasajerosCopia.remove(posicion);
                    bajan.add(estudiante);
                }

                int espacioDisponible = viaje.getBus().getCapacidad() -
                        viaje.getPasajerosActuales() + bajan.size();

                int maximoSuben = espacioDisponible;
                if (estudiantesDisponibles.size() < maximoSuben) {
                    maximoSuben = estudiantesDisponibles.size();
                }

                int cantidadSuben = 0;
                if (maximoSuben > 0) {
                    cantidadSuben = random.nextInt(maximoSuben + 1);
                }

                for (int j = 0; j < cantidadSuben; j++) {
                    int posicion = random.nextInt(estudiantesDisponibles.size());
                    Estudiante estudiante = estudiantesDisponibles.remove(posicion);
                    suben.add(estudiante);
                }
            }

            viaje.registrarParada(parada, suben, bajan);

            System.out.println("\nParada: " + parada.getNombre());
            System.out.println("Suben: " + suben.size());
            System.out.println("Bajan: " + bajan.size());
            System.out.println("Pasajeros actuales: " + viaje.getPasajerosActuales());
        }

        viaje.finalizarViaje();
        viaje.notificarLlegada();
        System.out.println("SIMULACION FINALIZADA\n");
    }
}
