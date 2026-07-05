package sistemaintercampus.interfaz.grafica;

import sistemaintercampus.modelo.Ruta;
import sistemaintercampus.modelo.Sede;
import sistemaintercampus.negocio.SistemaTransporte;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PanelRutas extends JPanel {

    private SistemaTransporte sistema;
    private VentanaPrincipal ventana;
    private JTable table;
    private DefaultTableModel model;

    public PanelRutas(SistemaTransporte sistema, VentanaPrincipal ventana) {
        this.sistema = sistema;
        this.ventana = ventana;
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("Gestión de Rutas", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(title, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{"ID Ruta","Nombre","Distancia","Recorrido"},0) {
            public boolean isCellEditable(int r,int c){return false;}
        };
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAgregar = new JButton("Registrar ruta");
        JButton btnEditar = new JButton("Editar ruta");
        JButton btnEliminar = new JButton("Eliminar ruta");
        controls.add(btnAgregar); controls.add(btnEditar); controls.add(btnEliminar);
        add(controls, BorderLayout.SOUTH);

        btnAgregar.addActionListener(e -> registrarRuta());
        btnEditar.addActionListener(e -> editarRuta());
        btnEliminar.addActionListener(e -> eliminarRuta());
    }

    public void actualizarDatos() {
        model.setRowCount(0);
        for (Ruta r: sistema.getRutas()) {
            model.addRow(new Object[]{r.getIdRuta(), r.getNombreRuta(), r.getDistanciaTotalKm(), r.mostrarRecorrido()});
        }
    }

    private void registrarRuta() {
        JTextField tfId = new JTextField();
        JTextField tfNombre = new JTextField();
        JTextField tfDist = new JTextField();
        DefaultListModel<Sede> listModel = new DefaultListModel<>();
        JList<Sede> list = new JList<>(listModel);
        JComboBox<Sede> cbSedes = new JComboBox<>();
        for (Sede s: sistema.getSedes()) cbSedes.addItem(s);
        JButton btnAgregar = new JButton("Agregar parada");

        JPanel p = new JPanel(new BorderLayout());
        JPanel top = new JPanel(new GridLayout(3,2,5,5));
        top.add(new JLabel("ID Ruta:")); top.add(tfId);
        top.add(new JLabel("Nombre:")); top.add(tfNombre);
        top.add(new JLabel("Distancia (km):")); top.add(tfDist);
        p.add(top, BorderLayout.NORTH);
        JPanel center = new JPanel(new BorderLayout());
        JPanel addPanel = new JPanel(new FlowLayout());
        addPanel.add(cbSedes); addPanel.add(btnAgregar);
        center.add(addPanel, BorderLayout.NORTH);
        center.add(new JScrollPane(list), BorderLayout.CENTER);
        p.add(center, BorderLayout.CENTER);

        btnAgregar.addActionListener(e -> {
            Sede s = (Sede) cbSedes.getSelectedItem(); if (s!=null) listModel.addElement(s);
        });

        int r = JOptionPane.showConfirmDialog(this, p, "Registrar Ruta", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (r==JOptionPane.OK_OPTION) {
            try {
                String id = tfId.getText().trim();
                String nombre = tfNombre.getText().trim();
                double dist = Double.parseDouble(tfDist.getText().trim());
                if (listModel.size()<2) throw new Exception("La ruta debe tener al menos dos paradas");
                List<Sede> sedes = new ArrayList<>();
                for (int i=0;i<listModel.size();i++) sedes.add(listModel.get(i));
                sistema.registrarRuta(id, nombre, dist, sedes);
                JOptionPane.showMessageDialog(this, "Ruta registrada");
                actualizarDatos(); ventana.actualizarTodos();
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Distancia inválida");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    private void editarRuta() {
        int sel = table.getSelectedRow(); if (sel<0) { JOptionPane.showMessageDialog(this, "Seleccione una ruta"); return; }
        String id = (String) model.getValueAt(sel,0);
        Ruta ruta = sistema.buscarRuta(id);
        if (ruta==null) { JOptionPane.showMessageDialog(this, "Ruta no encontrada"); return; }

        JTextField tfNombre = new JTextField(ruta.getNombreRuta());
        JTextField tfDist = new JTextField(String.valueOf(ruta.getDistanciaTotalKm()));
        DefaultListModel<Sede> listModel = new DefaultListModel<>();
        for (Sede s: ruta.getSedes()) listModel.addElement(s);
        JList<Sede> list = new JList<>(listModel);
        JComboBox<Sede> cbSedes = new JComboBox<>();
        for (Sede s: sistema.getSedes()) cbSedes.addItem(s);
        JButton btnAgregar = new JButton("Agregar parada");

        JPanel p = new JPanel(new BorderLayout());
        JPanel top = new JPanel(new GridLayout(2,2,5,5));
        top.add(new JLabel("Nombre:")); top.add(tfNombre);
        top.add(new JLabel("Distancia (km):")); top.add(tfDist);
        p.add(top, BorderLayout.NORTH);
        JPanel center = new JPanel(new BorderLayout());
        JPanel addPanel = new JPanel(new FlowLayout());
        addPanel.add(cbSedes); addPanel.add(btnAgregar);
        center.add(addPanel, BorderLayout.NORTH);
        center.add(new JScrollPane(list), BorderLayout.CENTER);
        p.add(center, BorderLayout.CENTER);

        btnAgregar.addActionListener(e -> { Sede s = (Sede) cbSedes.getSelectedItem(); if (s!=null) listModel.addElement(s); });

        int r = JOptionPane.showConfirmDialog(this, p, "Editar Ruta", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (r==JOptionPane.OK_OPTION) {
            try {
                String nuevoNombre = tfNombre.getText().trim();
                double nuevaDist = Double.parseDouble(tfDist.getText().trim());
                if (listModel.size()<2) throw new Exception("La ruta debe tener al menos dos paradas");
                List<Sede> sedes = new ArrayList<>();
                for (int i=0;i<listModel.size();i++) sedes.add(listModel.get(i));
                sistema.editarRuta(id, nuevoNombre, nuevaDist, sedes);
                JOptionPane.showMessageDialog(this, "Ruta editada");
                actualizarDatos(); ventana.actualizarTodos();
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Distancia inválida");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    private void eliminarRuta() {
        int sel = table.getSelectedRow(); if (sel<0) { JOptionPane.showMessageDialog(this, "Seleccione una ruta"); return; }
        String id = (String) model.getValueAt(sel,0);
        int r = JOptionPane.showConfirmDialog(this, "Eliminar ruta " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (r==JOptionPane.YES_OPTION) {
            try {
                sistema.eliminarRuta(id);
                JOptionPane.showMessageDialog(this, "Ruta eliminada");
                actualizarDatos(); ventana.actualizarTodos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }
}

