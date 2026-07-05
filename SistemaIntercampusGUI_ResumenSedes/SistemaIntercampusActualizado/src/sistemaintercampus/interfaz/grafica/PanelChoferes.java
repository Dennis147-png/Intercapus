package sistemaintercampus.interfaz.grafica;

import sistemaintercampus.modelo.Chofer;
import sistemaintercampus.negocio.SistemaTransporte;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelChoferes extends JPanel {

    private SistemaTransporte sistema;
    private VentanaPrincipal ventana;
    private JTable table;
    private DefaultTableModel model;

    public PanelChoferes(SistemaTransporte sistema, VentanaPrincipal ventana) {
        this.sistema = sistema;
        this.ventana = ventana;
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("Gestión de Choferes", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(title, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{"Nombre","Licencia"}, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAgregar = new JButton("Registrar chofer");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        controls.add(btnAgregar); controls.add(btnEditar); controls.add(btnEliminar);
        add(controls, BorderLayout.SOUTH);

        btnAgregar.addActionListener(e -> registrarChofer());
        btnEditar.addActionListener(e -> editarChofer());
        btnEliminar.addActionListener(e -> eliminarChofer());
    }

    public void actualizarDatos() {
        model.setRowCount(0);
        for (Chofer c : sistema.getChoferes()) {
            model.addRow(new Object[]{c.getNombre(), c.getLicencia()});
        }
    }

    private void registrarChofer() {
        JTextField tfNombre = new JTextField();
        JTextField tfLic = new JTextField();
        JPanel p = new JPanel(new GridLayout(2,2,5,5));
        p.add(new JLabel("Nombre:")); p.add(tfNombre);
        p.add(new JLabel("Licencia (LIC-1234):")); p.add(tfLic);
        int r = JOptionPane.showConfirmDialog(this, p, "Registrar Chofer", JOptionPane.OK_CANCEL_OPTION);
        if (r==JOptionPane.OK_OPTION) {
            String nombre = tfNombre.getText().trim();
            String licencia = tfLic.getText().trim().toUpperCase();
            try {
                if (!sistema.validarNombre(nombre)) throw new Exception("Nombre inválido");
                if (!sistema.validarLicencia(licencia)) throw new Exception("Licencia inválida (LIC-1234)");
                sistema.registrarChofer(new Chofer(nombre, licencia));
                JOptionPane.showMessageDialog(this, "Chofer registrado");
                actualizarDatos();
                ventana.actualizarTodos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    private void editarChofer() {
        int sel = table.getSelectedRow();
        if (sel < 0) { JOptionPane.showMessageDialog(this, "Seleccione un chofer"); return; }
        String licenciaActual = (String) model.getValueAt(sel,1);
        Chofer chofer = sistema.buscarChofer(licenciaActual);
        if (chofer==null) { JOptionPane.showMessageDialog(this, "Chofer no encontrado"); return; }

        JTextField tfNombre = new JTextField(chofer.getNombre());
        JTextField tfLic = new JTextField(chofer.getLicencia());
        JPanel p = new JPanel(new GridLayout(2,2,5,5));
        p.add(new JLabel("Nombre:")); p.add(tfNombre);
        p.add(new JLabel("Licencia (LIC-1234):")); p.add(tfLic);
        int r = JOptionPane.showConfirmDialog(this, p, "Editar Chofer", JOptionPane.OK_CANCEL_OPTION);
        if (r==JOptionPane.OK_OPTION) {
            try {
                String nuevoNombre = tfNombre.getText().trim();
                String nuevaLic = tfLic.getText().trim().toUpperCase();
                if (!sistema.validarNombre(nuevoNombre)) throw new Exception("Nombre inválido");
                if (!sistema.validarLicencia(nuevaLic)) throw new Exception("Licencia inválida");
                sistema.editarChofer(licenciaActual, nuevoNombre, nuevaLic);
                JOptionPane.showMessageDialog(this, "Chofer editado");
                actualizarDatos();
                ventana.actualizarTodos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    private void eliminarChofer() {
        int sel = table.getSelectedRow();
        if (sel < 0) { JOptionPane.showMessageDialog(this, "Seleccione un chofer"); return; }
        String licencia = (String) model.getValueAt(sel,1);
        int r = JOptionPane.showConfirmDialog(this, "Eliminar chofer " + licencia + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (r==JOptionPane.YES_OPTION) {
            try {
                sistema.eliminarChofer(licencia);
                JOptionPane.showMessageDialog(this, "Chofer eliminado");
                actualizarDatos();
                ventana.actualizarTodos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }
}

