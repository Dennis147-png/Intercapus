package sistemaintercampus.interfaz.grafica;

import sistemaintercampus.modelo.Bus;
import sistemaintercampus.modelo.Chofer;
import sistemaintercampus.modelo.Sede;
import sistemaintercampus.negocio.SistemaTransporte;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelBuses extends JPanel {

    private SistemaTransporte sistema;
    private VentanaPrincipal ventana;
    private JTable table;
    private DefaultTableModel model;

    public PanelBuses(SistemaTransporte sistema, VentanaPrincipal ventana) {
        this.sistema = sistema;
        this.ventana = ventana;
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("Gestión de Buses", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(title, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{"Placa","Capacidad","Estado","Ubicación","Chofer"},0) {
            public boolean isCellEditable(int r,int c){return false;}
        };
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAgregar = new JButton("Registrar bus");
        JButton btnEditar = new JButton("Editar bus");
        JButton btnEliminar = new JButton("Eliminar bus");
        controls.add(btnAgregar); controls.add(btnEditar); controls.add(btnEliminar);
        add(controls, BorderLayout.SOUTH);

        btnAgregar.addActionListener(e -> registrarBus());
        btnEditar.addActionListener(e -> editarBus());
        btnEliminar.addActionListener(e -> eliminarBus());
    }

    public void actualizarDatos() {
        model.setRowCount(0);
        List<Bus> buses = sistema.getBuses();
        for (Bus b: buses) {
            String ubic = "";
            try {
                Sede s = sistema.obtenerSedeActual(b);
                if (s!=null) ubic = s.getNombre();
                else ubic = b.getLatitudActual()+","+b.getLongitudActual();
            } catch (Exception ex) { ubic = b.getLatitudActual()+","+b.getLongitudActual(); }
            String chofer = b.getChofer()!=null?b.getChofer().getNombre():"-";
            model.addRow(new Object[]{b.getPlaca(), b.getCapacidad(), b.getEstado(), ubic, chofer});
        }
    }

    private void registrarBus() {
        JTextField tfPlaca = new JTextField();
        JTextField tfCap = new JTextField();
        JComboBox<Sede> cbSedes = new JComboBox<>();
        for (Sede s: sistema.getSedes()) cbSedes.addItem(s);
        JPanel p = new JPanel(new GridLayout(3,2,5,5));
        p.add(new JLabel("Placa:")); p.add(tfPlaca);
        p.add(new JLabel("Capacidad:")); p.add(tfCap);
        p.add(new JLabel("Sede inicial:")); p.add(cbSedes);
        int r = JOptionPane.showConfirmDialog(this, p, "Registrar Bus", JOptionPane.OK_CANCEL_OPTION);
        if (r==JOptionPane.OK_OPTION) {
            try {
                String placa = tfPlaca.getText().trim();
                int cap = Integer.parseInt(tfCap.getText().trim());
                Sede sede = (Sede) cbSedes.getSelectedItem();
                if (sede==null) throw new Exception("Seleccione sede");
                // create bus in state DISPONIBLE with sede coords
                Bus b = new Bus(placa.toUpperCase(), cap, "DISPONIBLE", sede.getLatitud(), sede.getLongitud());
                sistema.agregarBus(b);
                JOptionPane.showMessageDialog(this, "Bus registrado");
                actualizarDatos(); ventana.actualizarTodos();
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Capacidad debe ser número");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    private void editarBus() {
        int sel = table.getSelectedRow(); if (sel<0) { JOptionPane.showMessageDialog(this, "Seleccione un bus"); return; }
        String placa = (String) model.getValueAt(sel,0);
        Bus bus = sistema.buscarBus(placa);
        if (bus==null) { JOptionPane.showMessageDialog(this, "Bus no encontrado"); return; }

        JTextField tfCap = new JTextField(String.valueOf(bus.getCapacidad()));
        JComboBox<String> cbEstado = new JComboBox<>(new String[]{"DISPONIBLE","MANTENIMIENTO"});
        cbEstado.setSelectedItem(bus.getEstado());
        JComboBox<Sede> cbSedes = new JComboBox<>();
        for (Sede s: sistema.getSedes()) {
            cbSedes.addItem(s);
            if (sistema.busEstaEnSede(bus, s)) {
                cbSedes.setSelectedItem(s);
            }
        }
        JComboBox<String> cbChofer = new JComboBox<>();
        cbChofer.addItem("-");
        for (sistemaintercampus.modelo.Chofer c: sistema.getChoferes()) {
            cbChofer.addItem(c.getLicencia());
        }
        if (bus.getChofer() != null) {
            cbChofer.setSelectedItem(bus.getChofer().getLicencia());
        }

        JPanel p = new JPanel(new GridLayout(5,2,5,5));
        p.add(new JLabel("Placa (no editable):")); p.add(new JLabel(placa));
        p.add(new JLabel("Capacidad:")); p.add(tfCap);
        p.add(new JLabel("Estado:")); p.add(cbEstado);
        p.add(new JLabel("Sede:")); p.add(cbSedes);
        p.add(new JLabel("Chofer:")); p.add(cbChofer);
        int r = JOptionPane.showConfirmDialog(this, p, "Editar Bus", JOptionPane.OK_CANCEL_OPTION);
        if (r==JOptionPane.OK_OPTION) {
            try {
                int nuevaCap = Integer.parseInt(tfCap.getText().trim());
                String nuevoEstado = (String) cbEstado.getSelectedItem();
                Sede nuevaSede = (Sede) cbSedes.getSelectedItem();
                String licenciaChofer = (String) cbChofer.getSelectedItem();
                sistemaintercampus.modelo.Chofer nuevoChofer = null;
                if (licenciaChofer!=null && !licenciaChofer.equals("-")) nuevoChofer = sistema.buscarChofer(licenciaChofer);
                sistema.editarBus(placa, nuevaCap, nuevoEstado, nuevaSede, nuevoChofer);
                JOptionPane.showMessageDialog(this, "Bus editado");
                actualizarDatos(); ventana.actualizarTodos();
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Capacidad debe ser número");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    private void eliminarBus() {
        int sel = table.getSelectedRow(); if (sel<0) { JOptionPane.showMessageDialog(this, "Seleccione un bus"); return; }
        String placa = (String) model.getValueAt(sel,0);
        int r = JOptionPane.showConfirmDialog(this, "Eliminar bus " + placa + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (r==JOptionPane.YES_OPTION) {
            try {
                sistema.eliminarBus(placa);
                JOptionPane.showMessageDialog(this, "Bus eliminado");
                actualizarDatos(); ventana.actualizarTodos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }
}

