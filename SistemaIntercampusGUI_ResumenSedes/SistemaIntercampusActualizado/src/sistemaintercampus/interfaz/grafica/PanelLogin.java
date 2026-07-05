package sistemaintercampus.interfaz.grafica;

import sistemaintercampus.negocio.SistemaTransporte;
import sistemaintercampus.modelo.Sede;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PanelLogin extends JPanel {

    private SistemaTransporte sistema;
    private VentanaPrincipal ventana;

    private JButton btnRegEstudiante, btnRegAdmin, btnLoginEst, btnLoginAdmin, btnSalir;

    public PanelLogin(SistemaTransporte sistema, VentanaPrincipal ventana) {
        this.sistema = sistema;
        this.ventana = ventana;
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 248, 255));

        JLabel title = new JLabel("Sistema Intercampus UDLA", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(title, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(5, 1, 10, 10));
        center.setOpaque(false);
        btnRegEstudiante = new JButton("Registrarse como estudiante");
        btnRegAdmin = new JButton("Registrarse como administrador");
        btnLoginEst = new JButton("Iniciar sesión estudiante");
        btnLoginAdmin = new JButton("Iniciar sesión administrador");
        btnSalir = new JButton("Salir");

        center.add(btnRegEstudiante);
        center.add(btnRegAdmin);
        center.add(btnLoginEst);
        center.add(btnLoginAdmin);
        center.add(btnSalir);

        add(center, BorderLayout.CENTER);

        btnRegEstudiante.addActionListener(e -> mostrarRegistroEstudiante());
        btnRegAdmin.addActionListener(e -> mostrarRegistroAdministrador());
        btnLoginEst.addActionListener(e -> loginEstudiante());
        btnLoginAdmin.addActionListener(e -> loginAdministrador());
        btnSalir.addActionListener(e -> System.exit(0));
    }

    public void actualizarDatos() {
        // nothing for now
    }

    private void mostrarRegistroEstudiante() {
        JPanel panel = new JPanel(new GridLayout(4,2,5,5));
        JTextField tfNombre = new JTextField();
        JTextField tfCorreo = new JTextField();
        JTextField tfId = new JTextField();
        JComboBox<Sede> cbSedes = new JComboBox<>();

        List<Sede> sedes = sistema.getSedes();
        for (Sede s: sedes) cbSedes.addItem(s);

        panel.add(new JLabel("Nombre:")); panel.add(tfNombre);
        panel.add(new JLabel("Correo:")); panel.add(tfCorreo);
        panel.add(new JLabel("ID Banner:")); panel.add(tfId);
        panel.add(new JLabel("Sede actual:")); panel.add(cbSedes);

        int result = JOptionPane.showConfirmDialog(this, panel, "Registro Estudiante", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String nombre = tfNombre.getText().trim();
            String correo = tfCorreo.getText().trim();
            String id = tfId.getText().trim().toUpperCase();
            Sede sede = (Sede) cbSedes.getSelectedItem();

            try {
                if (!sistema.validarNombre(nombre)) throw new Exception("Nombre inválido");
                if (!sistema.validarCorreo(correo)) throw new Exception("Correo inválido");
                if (!sistema.validarIdBanner(id)) throw new Exception("ID Banner inválido (Ej: A001)");
                if (sede == null) throw new Exception("Seleccione una sede");
                sistema.registrarEstudiante(nombre, correo, id, sede);
                JOptionPane.showMessageDialog(this, "Estudiante registrado correctamente");
                ventana.actualizarTodos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    private void mostrarRegistroAdministrador() {
        JPanel panel = new JPanel(new GridLayout(3,2,5,5));
        JTextField tfNombre = new JTextField();
        JTextField tfCorreo = new JTextField();
        JTextField tfNum = new JTextField();

        panel.add(new JLabel("Nombre:")); panel.add(tfNombre);
        panel.add(new JLabel("Correo:")); panel.add(tfCorreo);
        panel.add(new JLabel("Número empleado:")); panel.add(tfNum);

        int result = JOptionPane.showConfirmDialog(this, panel, "Registro Administrador", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String nombre = tfNombre.getText().trim();
            String correo = tfCorreo.getText().trim();
            String num = tfNum.getText().trim().toUpperCase();

            try {
                if (!sistema.validarNombre(nombre)) throw new Exception("Nombre inválido");
                if (!sistema.validarCorreo(correo)) throw new Exception("Correo inválido");
                if (!sistema.validarNumeroEmpleado(num)) throw new Exception("Número empleado inválido (Ej: EMP001)");
                sistema.registrarAdministrador(nombre, correo, num);
                JOptionPane.showMessageDialog(this, "Administrador registrado correctamente");
                ventana.actualizarTodos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    private void loginEstudiante() {
        String id = JOptionPane.showInputDialog(this, "Ingrese ID Banner:");
        if (id == null || id.trim().isEmpty()) return;
        try {
            sistema.iniciarSesionEstudiante(id.trim());
            JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso");
            ventana.setNavEnabledForStudent();
            ventana.showCard("estudiante");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void loginAdministrador() {
        String num = JOptionPane.showInputDialog(this, "Ingrese número empleado:");
        if (num == null || num.trim().isEmpty()) return;
        try {
            sistema.iniciarSesionAdministrador(num.trim());
            JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso");
            ventana.setNavEnabledForAdmin();
            ventana.showCard("administrador");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}

