package sistemaintercampus.interfaz.grafica;

import sistemaintercampus.negocio.SistemaTransporte;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaPrincipal extends JFrame {

    private SistemaTransporte sistema;

    private JPanel cardsPanel;
    private CardLayout cardLayout;

    // Panels
    private PanelLogin panelLogin;
    private PanelEstudiante panelEstudiante;
    private PanelAdministrador panelAdministrador;
    private PanelBuses panelBuses;
    private PanelChoferes panelChoferes;
    private PanelRutas panelRutas;
    private PanelViajes panelViajes;
    private PanelReportes panelReportes;

    // Navigation buttons
    private JButton btnHome, btnBuses, btnChoferes, btnRutas, btnViajes, btnReportes, btnMostrarEstudiantes, btnMostrarAdmins, btnRegistrarUsuario, btnCerrarSesion, btnSalir;

    public VentanaPrincipal() {
        super("Sistema Intercampus UDLA");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);

        sistema = new SistemaTransporte();

        initComponents();
    }

    private void initComponents() {
        getContentPane().setLayout(new BorderLayout());

        // Left navigation
        JPanel left = new JPanel();
        left.setBackground(new Color(30, 87, 153));
        left.setPreferredSize(new Dimension(220, getHeight()));
        left.setLayout(new GridLayout(12, 1, 5, 5));

        btnHome = makeNavButton("Inicio");
        btnBuses = makeNavButton("Buses");
        btnChoferes = makeNavButton("Choferes");
        btnRutas = makeNavButton("Rutas");
        btnViajes = makeNavButton("Viajes");
        btnReportes = makeNavButton("Reportes");
        btnMostrarEstudiantes = makeNavButton("Estudiantes");
        btnMostrarAdmins = makeNavButton("Administradores");
        btnRegistrarUsuario = makeNavButton("Registrar usuario");
        btnCerrarSesion = makeNavButton("Cerrar sesión");
        btnSalir = makeNavButton("Salir");

        left.add(btnHome);
        left.add(btnBuses);
        left.add(btnChoferes);
        left.add(btnRutas);
        left.add(btnViajes);
        left.add(btnReportes);
        left.add(btnMostrarEstudiantes);
        left.add(btnMostrarAdmins);
        left.add(btnRegistrarUsuario);
        left.add(btnCerrarSesion);
        left.add(btnSalir);

        getContentPane().add(left, BorderLayout.WEST);

        // Center cards
        cardLayout = new CardLayout();
        cardsPanel = new JPanel(cardLayout);

        panelLogin = new PanelLogin(sistema, this);
        panelEstudiante = new PanelEstudiante(sistema, this);
        panelAdministrador = new PanelAdministrador(sistema, this);
        panelBuses = new PanelBuses(sistema, this);
        panelChoferes = new PanelChoferes(sistema, this);
        panelRutas = new PanelRutas(sistema, this);
        panelViajes = new PanelViajes(sistema, this);
        panelReportes = new PanelReportes(sistema, this);

        cardsPanel.add(panelLogin, "home");
        cardsPanel.add(panelEstudiante, "estudiante");
        cardsPanel.add(panelAdministrador, "administrador");
        cardsPanel.add(panelBuses, "buses");
        cardsPanel.add(panelChoferes, "choferes");
        cardsPanel.add(panelRutas, "rutas");
        cardsPanel.add(panelViajes, "viajes");
        cardsPanel.add(panelReportes, "reportes");

        getContentPane().add(cardsPanel, BorderLayout.CENTER);

        // initial state
        setNavEnabledForGuest();
        addListeners();
        showCard("home");
    }

    private JButton makeNavButton(String text) {
        JButton b = new JButton(text);
        b.setFocusPainted(false);
        b.setBackground(new Color(70,130,180));
        b.setForeground(Color.WHITE);
        return b;
    }

    private void addListeners() {
        btnHome.addActionListener(e -> showCard("home"));
        btnBuses.addActionListener(e -> { showCard("buses"); panelBuses.actualizarDatos(); });
        btnChoferes.addActionListener(e -> { showCard("choferes"); panelChoferes.actualizarDatos(); });
        btnRutas.addActionListener(e -> { showCard("rutas"); panelRutas.actualizarDatos(); });
        btnViajes.addActionListener(e -> { showCard("viajes"); panelViajes.actualizarDatos(); });
        btnReportes.addActionListener(e -> { showCard("reportes"); panelReportes.actualizarDatos(); });
        btnMostrarEstudiantes.addActionListener(e -> mostrarEstudiantes());
        btnMostrarAdmins.addActionListener(e -> mostrarAdministradores());
        btnRegistrarUsuario.addActionListener(e -> showCard("administrador"));
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
        btnSalir.addActionListener(e -> System.exit(0));
    }

    public void showCard(String name) {
        if ("home".equals(name)) panelLogin.actualizarDatos();
        if ("estudiante".equals(name)) panelEstudiante.actualizarDatos();
        if ("administrador".equals(name)) panelAdministrador.actualizarDatos();
        if ("buses".equals(name)) panelBuses.actualizarDatos();
        if ("choferes".equals(name)) panelChoferes.actualizarDatos();
        if ("rutas".equals(name)) panelRutas.actualizarDatos();
        if ("viajes".equals(name)) panelViajes.actualizarDatos();
        if ("reportes".equals(name)) panelReportes.actualizarDatos();
        cardLayout.show(cardsPanel, name);
    }

    public void setNavEnabledForAdmin() {
        btnBuses.setEnabled(true);
        btnChoferes.setEnabled(true);
        btnRutas.setEnabled(true);
        btnViajes.setEnabled(true);
        btnReportes.setEnabled(true);
        btnMostrarEstudiantes.setEnabled(true);
        btnMostrarAdmins.setEnabled(true);
        btnRegistrarUsuario.setEnabled(true);
        btnCerrarSesion.setEnabled(true);
    }

    public void setNavEnabledForStudent() {
        btnBuses.setEnabled(false);
        btnChoferes.setEnabled(false);
        btnRutas.setEnabled(false);
        btnViajes.setEnabled(false);
        btnReportes.setEnabled(false);
        btnMostrarEstudiantes.setEnabled(false);
        btnMostrarAdmins.setEnabled(false);
        btnRegistrarUsuario.setEnabled(false);
        btnCerrarSesion.setEnabled(true);
    }

    public void setNavEnabledForGuest() {
        btnBuses.setEnabled(false);
        btnChoferes.setEnabled(false);
        btnRutas.setEnabled(false);
        btnViajes.setEnabled(false);
        btnReportes.setEnabled(false);
        btnMostrarEstudiantes.setEnabled(false);
        btnMostrarAdmins.setEnabled(false);
        btnRegistrarUsuario.setEnabled(false);
        btnCerrarSesion.setEnabled(false);
    }

    public SistemaTransporte getSistema() {
        return sistema;
    }

    public void mostrarEstudiantes() {
        StringBuilder sb = new StringBuilder();
        sb.append("ESTUDIANTES REGISTRADOS\n\n");
        if (sistema.getEstudiantes().isEmpty()) {
            sb.append("No existen estudiantes registrados.");
        } else {
            for (sistemaintercampus.modelo.Estudiante e : sistema.getEstudiantes()) {
                sb.append("ID Banner: ").append(e.getIdBanner())
                        .append(" | Nombre: ").append(e.getNombre())
                        .append(" | Correo: ").append(e.getCorreo())
                        .append("\n");
            }
        }
        mostrarTexto("Estudiantes", sb.toString());
    }

    public void mostrarAdministradores() {
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

    private void mostrarTexto(String titulo, String texto) {
        JTextArea ta = new JTextArea(texto);
        ta.setEditable(false);
        JScrollPane sp = new JScrollPane(ta);
        sp.setPreferredSize(new Dimension(700, 400));
        JOptionPane.showMessageDialog(this, sp, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    public void cerrarSesion() {
        setNavEnabledForGuest();
        showCard("home");
        JOptionPane.showMessageDialog(this, "Sesión cerrada");
    }

    public void actualizarTodos() {
        panelBuses.actualizarDatos();
        panelChoferes.actualizarDatos();
        panelRutas.actualizarDatos();
        panelViajes.actualizarDatos();
        panelReportes.actualizarDatos();
        panelEstudiante.actualizarDatos();
        panelLogin.actualizarDatos();
    }
}



