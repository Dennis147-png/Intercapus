package sistemaintercampus.modelo;

import java.util.ArrayList;
import java.util.List;

public class RegistroParada {
    private Sede parada;
    private int suben;
    private int bajan;
    private int pasajerosActuales;
    private List<Estudiante> estudiantesSuben;
    private List<Estudiante> estudiantesBajan;

    public RegistroParada(Sede parada, int suben, int bajan, int pasajerosActuales) {
        this.parada = parada;
        this.suben = suben;
        this.bajan = bajan;
        this.pasajerosActuales = pasajerosActuales;
        this.estudiantesSuben = new ArrayList<Estudiante>();
        this.estudiantesBajan = new ArrayList<Estudiante>();
    }

    public RegistroParada(Sede parada, List<Estudiante> estudiantesSuben,
                          List<Estudiante> estudiantesBajan, int pasajerosActuales) {
        this.parada = parada;
        this.estudiantesSuben = new ArrayList<Estudiante>(estudiantesSuben);
        this.estudiantesBajan = new ArrayList<Estudiante>(estudiantesBajan);
        this.suben = estudiantesSuben.size();
        this.bajan = estudiantesBajan.size();
        this.pasajerosActuales = pasajerosActuales;
    }

    public Sede getParada() {
        return parada;
    }

    public int getSuben() {
        return suben;
    }

    public int getBajan() {
        return bajan;
    }

    public int getPasajerosActuales() {
        return pasajerosActuales;
    }

    public List<Estudiante> getEstudiantesSuben() {
        return estudiantesSuben;
    }

    public List<Estudiante> getEstudiantesBajan() {
        return estudiantesBajan;
    }

    public String mostrarEstudiantes(List<Estudiante> lista) {
        if (lista.isEmpty()) {
            return "Ninguno\n";
        }

        String texto = "";
        for (Estudiante estudiante : lista) {
            texto += estudiante.getIdBanner() + " - " + estudiante.getNombre() + "\n";
        }
        return texto;
    }

    @Override
    public String toString() {
        return "\nParada: " + parada.getNombre() +
                "\nSuben: " + suben +
                "\n" + mostrarEstudiantes(estudiantesSuben) +
                "Bajan: " + bajan +
                "\n" + mostrarEstudiantes(estudiantesBajan) +
                "Pasajeros actuales: " + pasajerosActuales;
    }
}
