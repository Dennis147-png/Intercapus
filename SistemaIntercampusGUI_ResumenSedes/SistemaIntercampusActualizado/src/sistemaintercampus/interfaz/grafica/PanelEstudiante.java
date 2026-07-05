package sistemaintercampus.interfaz.grafica;

import sistemaintercampus.negocio.SistemaTransporte;
import sistemaintercampus.modelo.Bus;
import sistemaintercampus.modelo.Estudiante;
import sistemaintercampus.modelo.Ruta;
import sistemaintercampus.modelo.RutaViaje;
import sistemaintercampus.modelo.Sede;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelEstudiante extends JPanel {

    private SistemaTransporte sistema;
    private VentanaPrincipal ventana;

    public PanelEstudiante(SistemaTransporte sistema, VentanaPrincipal ventana) {
        this.sistema = sistema;
        this.ventana = ventana;
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 255));

        JLabel title = new JLabel("Menú Estudiante", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(title, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(9,1,5,5));
        center.setBorder(BorderFactory.createEmptyBorder(20,40,20,40));

        JButton b1 = new JButton("Ver mi información");
        JButton b2 = new JButton("Ver rutas");
        JButton b3 = new JButton("Ver sedes disponibles");
        JButton b4 = new JButton("Ver buses en recorrido");
        JButton b5 = new JButton("Consultar ubicación del bus");
        JButton b6 = new JButton("Calcular tiempo estimado bus-parada");
        JButton b7 = new JButton("Ver ocupación del bus");
        JButton b8 = new JButton("Ver historial de viajes");
        JButton b9 = new JButton("Cerrar sesión");

        center.add(b1); center.add(b2); center.add(b3); center.add(b4); center.add(b5);
        center.add(b6); center.add(b7); center.add(b8); center.add(b9);
        add(center, BorderLayout.CENTER);

        b1.addActionListener(e -> verMiInformacion());
        b2.addActionListener(e -> verRutas());
        b3.addActionListener(e -> verSedes());
        b4.addActionListener(e -> verBusesEnRecorrido());
        b5.addActionListener(e -> consultarUbicacionBus());
        b6.addActionListener(e -> calcularTiempoEstimado());
        b7.addActionListener(e -> verOcupacionBus());
        b8.addActionListener(e -> verHistorial());
        b9.addActionListener(e -> { ventana.setNavEnabledForGuest(); ventana.showCard("home"); JOptionPane.showMessageDialog(this, "Sesión cerrada"); });
    }

    public void actualizarDatos() {
        // Este panel carga datos al abrir cada opcion.
    }

    private String pedirIdBanner() {
        return JOptionPane.showInputDialog(this, "Ingrese su ID Banner:");
    }

    private void verMiInformacion() {
        String id = pedirIdBanner(); if (id==null) return;
        try {
            Estudiante est = sistema.buscarEstudiantePorBanner(id.trim());
            if (est==null) { JOptionPane.showMessageDialog(this, "Estudiante no encontrado"); return; }
            StringBuilder sb = new StringBuilder();
            sb.append("Nombre: ").append(est.getNombre()).append('\n');
            sb.append("Correo: ").append(est.getCorreo()).append('\n');
            sb.append("ID Banner: ").append(est.getIdBanner()).append('\n');
            JOptionPane.showMessageDialog(this, sb.toString(), "Información", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void verRutas() {
        List<Ruta> rutas = sistema.getRutas();
        StringBuilder sb = new StringBuilder();
        for (Ruta r: rutas) {
            sb.append(r.getIdRuta()).append(" - ").append(r.getNombreRuta()).append('\n');
            sb.append("   Recorrido: ").append(r.mostrarRecorrido()).append('\n');
        }
        mostrarTexto("Rutas", sb.length()==0 ? "No hay rutas" : sb.toString());
    }

    private void verSedes() {
        StringBuilder sb = new StringBuilder();
        sb.append("SEDES DISPONIBLES\n\n");
        for (Sede s : sistema.getSedes()) {
            sb.append("ID: ").append(s.getIdSede())
                    .append(" | Nombre: ").append(s.getNombre())
                    .append(" | Coordenadas: ").append(s.getLatitud())
                    .append(", ").append(s.getLongitud()).append('\n');
        }
        mostrarTexto("Sedes disponibles", sb.toString());
    }

    private void verBusesEnRecorrido() {
        try {
            StringBuilder sb = new StringBuilder();
            for (Bus bus : sistema.getBuses()) {
                if (bus.getEstado().equalsIgnoreCase("EN RUTA")) {
                    sb.append(bus.getPlaca())
                            .append(" | Ubicación: ")
                            .append(sistema.obtenerUbicacionBus(bus))
                            .append(" | Ocupación: ")
                            .append(sistema.obtenerOcupacionBus(bus))
                            .append('\n');
                }
            }
            mostrarTexto("Buses en recorrido", sb.length()==0 ? "No hay buses en recorrido" : sb.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void consultarUbicacionBus() {
        JComboBox<Bus> cbBuses = crearComboBusesEnRuta();
        if (cbBuses.getItemCount()==0) { JOptionPane.showMessageDialog(this, "No hay buses en recorrido"); return; }
        int r = JOptionPane.showConfirmDialog(this, cbBuses, "Seleccione bus en recorrido", JOptionPane.OK_CANCEL_OPTION);
        if (r != JOptionPane.OK_OPTION) return;
        try {
            Bus bus = (Bus) cbBuses.getSelectedItem();
            if (bus==null) { JOptionPane.showMessageDialog(this, "Seleccione un bus"); return; }
            String ubic = sistema.obtenerUbicacionBus(bus);
            JOptionPane.showMessageDialog(this, ubic, "Ubicación", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void calcularTiempoEstimado() {
        JComboBox<Bus> cbBuses = crearComboBusesEnRuta();
        JComboBox<Sede> cbSedes = new JComboBox<Sede>();
        for (Sede s : sistema.getSedes()) cbSedes.addItem(s);

        if (cbBuses.getItemCount()==0) { JOptionPane.showMessageDialog(this, "No hay buses en recorrido"); return; }
        if (cbSedes.getItemCount()==0) { JOptionPane.showMessageDialog(this, "No hay sedes registradas"); return; }

        JPanel panel = new JPanel(new GridLayout(2,2,5,5));
        panel.add(new JLabel("Bus en recorrido:")); panel.add(cbBuses);
        panel.add(new JLabel("Sede destino:")); panel.add(cbSedes);

        int r = JOptionPane.showConfirmDialog(this, panel, "Calcular tiempo estimado", JOptionPane.OK_CANCEL_OPTION);
        if (r != JOptionPane.OK_OPTION) return;

        try {
            Bus bus = (Bus) cbBuses.getSelectedItem();
            Sede sede = (Sede) cbSedes.getSelectedItem();
            if (bus==null || sede==null) { JOptionPane.showMessageDialog(this, "Seleccione bus y sede"); return; }
            JOptionPane.showMessageDialog(this,
                    "Bus: " + bus.getPlaca() + "\nDestino: " + sede.getNombre() + "\nTiempo estimado: " +
                            sistema.calcularTiempoEstimadoBusSede(bus, sede) + " minutos",
                    "Tiempo estimado",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void verOcupacionBus() {
        JComboBox<Bus> cbBuses = crearComboBusesEnRuta();
        if (cbBuses.getItemCount()==0) { JOptionPane.showMessageDialog(this, "No hay buses en recorrido"); return; }
        int r = JOptionPane.showConfirmDialog(this, cbBuses, "Seleccione bus en recorrido", JOptionPane.OK_CANCEL_OPTION);
        if (r != JOptionPane.OK_OPTION) return;
        try {
            Bus bus = (Bus) cbBuses.getSelectedItem();
            if (bus==null) { JOptionPane.showMessageDialog(this, "Seleccione un bus"); return; }
            String ocu = sistema.obtenerOcupacionBus(bus);
            JOptionPane.showMessageDialog(this, ocu, "Ocupación", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void verHistorial() {
        try {
            StringBuilder sb = new StringBuilder();
            for (RutaViaje viaje : sistema.getViajes()) {
                sb.append(viaje.toString()).append("\n\n");
            }
            mostrarTexto("Historial", sb.length()==0 ? "No existen viajes registrados" : sb.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private JComboBox<Bus> crearComboBusesEnRuta() {
        JComboBox<Bus> cbBuses = new JComboBox<Bus>();
        for (Bus bus : sistema.getBuses()) {
            if (bus.getEstado().equalsIgnoreCase("EN RUTA")) {
                cbBuses.addItem(bus);
            }
        }
        return cbBuses;
    }

    private void mostrarTexto(String titulo, String texto) {
        JTextArea ta = new JTextArea(texto);
        ta.setEditable(false);
        JScrollPane sp = new JScrollPane(ta);
        sp.setPreferredSize(new Dimension(700, 400));
        JOptionPane.showMessageDialog(this, sp, titulo, JOptionPane.INFORMATION_MESSAGE);
    }
}
