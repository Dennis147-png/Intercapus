package sistemaintercampus.interfaz.grafica;

import sistemaintercampus.modelo.Bus;
import sistemaintercampus.modelo.Ruta;
import sistemaintercampus.modelo.RutaViaje;
import sistemaintercampus.negocio.SistemaTransporte;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelViajes extends JPanel {

    private SistemaTransporte sistema;
    private VentanaPrincipal ventana;
    private JTable table;
    private DefaultTableModel model;

    public PanelViajes(SistemaTransporte sistema, VentanaPrincipal ventana) {
        this.sistema = sistema;
        this.ventana = ventana;
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("Gestión de Viajes", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(title, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{"ID","Ruta","Bus","Chofer","Estado","Fecha","Salida","Llegada","Duración","Pasajeros actuales","Pasajeros atendidos"},0) {
            public boolean isCellEditable(int r,int c){return false;}
        };
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnCrear = new JButton("Crear viaje");
        JButton btnIniciar = new JButton("Iniciar viaje");
        JButton btnFinalizar = new JButton("Finalizar viaje");
        JButton btnRegistro = new JButton("Ver registro");
        controls.add(btnCrear); controls.add(btnIniciar); controls.add(btnFinalizar); controls.add(btnRegistro);
        add(controls, BorderLayout.SOUTH);

        btnCrear.addActionListener(e -> crearViaje());
        btnIniciar.addActionListener(e -> iniciarViaje());
        btnFinalizar.addActionListener(e -> finalizarViaje());
        btnRegistro.addActionListener(e -> verRegistro());
    }

    public void actualizarDatos() {
        model.setRowCount(0);
        List<RutaViaje> viajes = sistema.getViajes();
        for (RutaViaje rv: viajes) {
            model.addRow(new Object[]{rv.getIdViaje(), rv.getRuta().getNombreRuta(), rv.getBus().getPlaca(), rv.getBus().getChofer()!=null?rv.getBus().getChofer().getNombre():"-", rv.getEstadoViaje(), rv.getFecha(), rv.getHoraSalida(), rv.getHoraLlegada(), rv.calcularDuracionMinutos(), rv.getPasajerosActuales(), rv.getPasajerosAtendidos()});
        }
    }

    private void crearViaje() {
        JTextField tfId = new JTextField();
        JComboBox<Ruta> cbRutas = new JComboBox<>();
        for (Ruta r: sistema.getRutas()) cbRutas.addItem(r);
        JComboBox<Bus> cbBuses = new JComboBox<>();
        cargarBusesDisponibles(cbBuses, (Ruta) cbRutas.getSelectedItem());
        cbRutas.addActionListener(e -> cargarBusesDisponibles(cbBuses, (Ruta) cbRutas.getSelectedItem()));

        JPanel p = new JPanel(new GridLayout(3,2,5,5));
        p.add(new JLabel("ID viaje:")); p.add(tfId);
        p.add(new JLabel("Ruta:")); p.add(cbRutas);
        p.add(new JLabel("Bus (DISPONIBLE):")); p.add(cbBuses);
        int r = JOptionPane.showConfirmDialog(this, p, "Crear Viaje", JOptionPane.OK_CANCEL_OPTION);
        if (r==JOptionPane.OK_OPTION) {
            try {
                String id = tfId.getText().trim();
                Ruta ruta = (Ruta) cbRutas.getSelectedItem();
                Bus bus = (Bus) cbBuses.getSelectedItem();
                if (id.isEmpty() || ruta==null || bus==null) throw new Exception("Datos incompletos");
                // validations
                if (!bus.tieneChofer()) throw new Exception("El bus debe tener chofer");
                if (!sistema.busEstaEnSede(bus, ruta.getPrimeraSede())) throw new Exception("El bus debe estar en la primera sede de la ruta");
                if (sistema.busTieneViajeActivo(bus)) throw new Exception("El bus tiene viaje pendiente");
                sistema.crearViaje(id, bus.getPlaca(), ruta.getIdRuta());
                JOptionPane.showMessageDialog(this, "Viaje creado");
                actualizarDatos(); ventana.actualizarTodos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    private void cargarBusesDisponibles(JComboBox<Bus> cbBuses, Ruta ruta) {
        cbBuses.removeAllItems();
        if (ruta == null) {
            return;
        }
        for (Bus b: sistema.getBuses()) {
            if ("DISPONIBLE".equalsIgnoreCase(b.getEstado()) &&
                    b.tieneChofer() &&
                    !sistema.busTieneViajeActivo(b) &&
                    sistema.busEstaEnSede(b, ruta.getPrimeraSede())) {
                cbBuses.addItem(b);
            }
        }
    }

    private void iniciarViaje() {
        int sel = table.getSelectedRow();
        if (sel < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un viaje pendiente");
            return;
        }
        String id = (String) model.getValueAt(sel, 0);
        try {
            sistema.iniciarViaje(id);
            JOptionPane.showMessageDialog(this, "Viaje iniciado. El bus quedo EN RUTA.");
            actualizarDatos();
            ventana.actualizarTodos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void finalizarViaje() {
        int sel = table.getSelectedRow();
        if (sel < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un viaje en ruta");
            return;
        }
        String id = (String) model.getValueAt(sel, 0);
        try {
            sistema.finalizarViaje(id);
            JOptionPane.showMessageDialog(this, "Viaje finalizado y registrado correctamente");
            actualizarDatos();
            ventana.actualizarTodos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void verRegistro() {
        int sel = table.getSelectedRow(); if (sel<0) { JOptionPane.showMessageDialog(this, "Seleccione un viaje"); return; }
        String id = (String) model.getValueAt(sel,0);
        try {
            sistema.mostrarRegistroViaje(id);
            // additionally, show a simple dialog with the toString of the RutaViaje if available
            RutaViaje rv = sistema.buscarViaje(id);
            if (rv!=null) {
                StringBuilder sb = new StringBuilder();
                sb.append(rv.toString()).append("\n\n");
                for (sistemaintercampus.modelo.RegistroParada rp: rv.getRegistrosParadas()) {
                    sb.append(rp.toString()).append("\n");
                }
                JTextArea ta = new JTextArea(sb.toString()); ta.setEditable(false);
                JScrollPane sp = new JScrollPane(ta); sp.setPreferredSize(new Dimension(600,400));
                JOptionPane.showMessageDialog(this, sp, "Registro Viaje", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}

