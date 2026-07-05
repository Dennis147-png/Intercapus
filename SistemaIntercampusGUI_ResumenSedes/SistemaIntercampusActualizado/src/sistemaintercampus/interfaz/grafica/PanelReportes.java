package sistemaintercampus.interfaz.grafica;

import sistemaintercampus.modelo.RutaViaje;
import sistemaintercampus.modelo.RegistroParada;
import sistemaintercampus.modelo.Estudiante;
import sistemaintercampus.negocio.SistemaTransporte;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelReportes extends JPanel {

    private SistemaTransporte sistema;
    private VentanaPrincipal ventana;

    public PanelReportes(SistemaTransporte sistema, VentanaPrincipal ventana) {
        this.sistema = sistema;
        this.ventana = ventana;
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        JLabel title = new JLabel("Reportes y Registro de Viajes", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(title, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(4, 1, 10, 10));
        center.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));
        JButton btnResumen = new JButton("Ver resumen general de viajes");
        JButton btnVer = new JButton("Seleccionar viaje y ver registro completo");
        JButton btnEstudiantes = new JButton("Mostrar estudiantes registrados");
        JButton btnAdministradores = new JButton("Mostrar administradores registrados");
        center.add(btnResumen);
        center.add(btnVer);
        center.add(btnEstudiantes);
        center.add(btnAdministradores);
        add(center, BorderLayout.CENTER);

        btnResumen.addActionListener(e -> mostrarResumenGeneral());
        btnVer.addActionListener(e -> seleccionarViaje());
        btnEstudiantes.addActionListener(e -> mostrarEstudiantes());
        btnAdministradores.addActionListener(e -> mostrarAdministradores());
    }

    public void actualizarDatos() {
        // Los datos se leen directamente desde SistemaTransporte al presionar cada boton.
    }

    private void mostrarResumenGeneral() {
        StringBuilder sb = new StringBuilder();
        sb.append("RESUMEN GENERAL DE VIAJES\n\n");
        if (sistema.getViajes().isEmpty()) {
            sb.append("No hay viajes registrados.");
        } else {
            for (RutaViaje rv : sistema.getViajes()) {
                sb.append("ID viaje: ").append(rv.getIdViaje()).append('\n');
                sb.append("Ruta: ").append(rv.getRuta().getNombreRuta()).append(" | ").append(rv.getRuta().mostrarRecorrido()).append('\n');
                sb.append("Bus: ").append(rv.getBus().getPlaca()).append('\n');
                sb.append("Chofer: ").append(rv.getBus().getChofer()!=null?rv.getBus().getChofer().getNombre():"-").append('\n');
                sb.append("Estado: ").append(rv.getEstadoViaje()).append('\n');
                sb.append("Fecha: ").append(rv.getFecha()).append('\n');
                sb.append("Salida: ").append(rv.getHoraSalida()).append(" | Llegada: ").append(rv.getHoraLlegada()).append('\n');
                sb.append("Duración: ").append(rv.calcularDuracionMinutos()).append(" minutos\n");
                sb.append("Pasajeros actuales: ").append(rv.getPasajerosActuales()).append('\n');
                sb.append("Pasajeros atendidos: ").append(rv.getPasajerosAtendidos()).append("\n");
                sb.append("--------------------------------------------\n");
            }
        }
        mostrarTexto("Resumen general", sb.toString());
    }

    private void seleccionarViaje() {
        List<RutaViaje> viajes = sistema.getViajes();
        if (viajes.isEmpty()) { JOptionPane.showMessageDialog(this, "No hay viajes"); return; }
        String[] ids = new String[viajes.size()];
        for (int i=0;i<viajes.size();i++) ids[i]=viajes.get(i).getIdViaje();
        String sel = (String) JOptionPane.showInputDialog(this, "Seleccione viaje:", "Viajes", JOptionPane.PLAIN_MESSAGE, null, ids, ids[0]);
        if (sel==null) return;
        try {
            RutaViaje rv = sistema.buscarViaje(sel);
            if (rv==null) { JOptionPane.showMessageDialog(this, "Viaje no encontrado"); return; }
            StringBuilder sb = new StringBuilder();
            sb.append("REPORTE DEL VIAJE\n\n");
            sb.append("ID viaje: ").append(rv.getIdViaje()).append('\n');
            sb.append("Ruta: ").append(rv.getRuta().getNombreRuta()).append('\n');
            sb.append("Recorrido: ").append(rv.getRuta().mostrarRecorrido()).append('\n');
            sb.append("Bus: ").append(rv.getBus().getPlaca()).append('\n');
            sb.append("Chofer: ").append(rv.getBus().getChofer()!=null?rv.getBus().getChofer().getNombre():"-").append('\n');
            sb.append("Fecha: ").append(rv.getFecha()).append('\n');
            sb.append("Salida: ").append(rv.getHoraSalida()).append('\n');
            sb.append("Llegada: ").append(rv.getHoraLlegada()).append('\n');
            sb.append("Duración: ").append(rv.calcularDuracionMinutos()).append(" minutos\n");
            sb.append("Estado: ").append(rv.getEstadoViaje()).append('\n');
            sb.append("Pasajeros atendidos: ").append(rv.getPasajerosAtendidos()).append('\n');
            sb.append("Pasajeros actuales: ").append(rv.getPasajerosActuales()).append("\n\n");
            sb.append("DETALLE POR PARADA\n\n");
            if (rv.getRegistrosParadas().isEmpty()) {
                sb.append("El viaje todavía no tiene registros de paradas.\n");
            }
            for (RegistroParada rp: rv.getRegistrosParadas()) {
                sb.append("Parada: ").append(rp.getParada().getNombre())
                        .append(" (ID: ").append(rp.getParada().getIdSede()).append(")\n");
                sb.append("Estudiantes que subieron:\n");
                appendEstudiantes(sb, rp.getEstudiantesSuben());
                sb.append("Estudiantes que bajaron:\n");
                appendEstudiantes(sb, rp.getEstudiantesBajan());
                sb.append("Pasajeros actuales: ").append(rp.getPasajerosActuales()).append('\n');
                sb.append("-------------------------\n");
            }
            mostrarTexto("Reporte viaje", sb.toString());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void mostrarEstudiantes() {
        StringBuilder sb = new StringBuilder();
        sb.append("ESTUDIANTES REGISTRADOS\n\n");
        if (sistema.getEstudiantes().isEmpty()) {
            sb.append("No existen estudiantes registrados.");
        } else {
            for (Estudiante e : sistema.getEstudiantes()) {
                sb.append("ID Banner: ").append(e.getIdBanner())
                        .append(" | Nombre: ").append(e.getNombre())
                        .append(" | Correo: ").append(e.getCorreo())
                        .append("\n");
            }
        }
        mostrarTexto("Estudiantes", sb.toString());
    }

    private void mostrarAdministradores() {
        StringBuilder sb = new StringBuilder();
        sb.append("ADMINISTRADORES REGISTRADOS\n\n");
        if (sistema.getAdministradores().isEmpty()) {
            sb.append("No existen administradores registrados.");
        } else {
            for (sistemaintercampus.modelo.Administrador a : sistema.getAdministradores()) {
                sb.append("Empleado: ").append(a.getNumeroEmpleado())
                        .append(" | Nombre: ").append(a.getNombre())
                        .append(" | Correo: ").append(a.getCorreo())
                        .append("\n");
            }
        }
        mostrarTexto("Administradores", sb.toString());
    }

    private void appendEstudiantes(StringBuilder sb, List<Estudiante> estudiantes) {
        if (estudiantes.isEmpty()) {
            sb.append(" - Ninguno\n");
            return;
        }
        for (Estudiante e: estudiantes) {
            sb.append(" - ").append(e.getIdBanner())
                    .append(" | ").append(e.getNombre())
                    .append(" | ").append(e.getCorreo())
                    .append('\n');
        }
    }

    private void mostrarTexto(String titulo, String texto) {
        JTextArea ta = new JTextArea(texto);
        ta.setEditable(false);
        JScrollPane sp = new JScrollPane(ta);
        sp.setPreferredSize(new Dimension(760, 450));
        JOptionPane.showMessageDialog(this, sp, titulo, JOptionPane.INFORMATION_MESSAGE);
    }
}
