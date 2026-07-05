package sistemaintercampus.interfaz.grafica;

import sistemaintercampus.negocio.SistemaTransporte;

import javax.swing.*;
import java.awt.*;

public class PanelAdministrador extends JPanel {

    private SistemaTransporte sistema;
    private VentanaPrincipal ventana;

    public PanelAdministrador(SistemaTransporte sistema, VentanaPrincipal ventana) {
        this.sistema = sistema;
        this.ventana = ventana;
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        setBackground(new Color(250, 250, 250));

        JLabel title = new JLabel("Menú Administrador", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(title, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(5,2,10,10));
        center.setBorder(BorderFactory.createEmptyBorder(20,40,20,40));

        JButton b1 = new JButton("Gestionar Buses");
        JButton b2 = new JButton("Gestionar Choferes");
        JButton b3 = new JButton("Gestionar Rutas");
        JButton b4 = new JButton("Crear Viaje");
        JButton b5 = new JButton("Iniciar / finalizar viaje");
        JButton b6 = new JButton("Ver reportes/historial");
        JButton b7 = new JButton("Mostrar estudiantes");
        JButton b8 = new JButton("Mostrar administradores");
        JButton b9 = new JButton("Registrar usuarios");
        JButton b10 = new JButton("Cerrar sesión");

        center.add(b1); center.add(b2); center.add(b3); center.add(b4); center.add(b5); center.add(b6); center.add(b7); center.add(b8); center.add(b9); center.add(b10);
        add(center, BorderLayout.CENTER);

        b1.addActionListener(e -> { ventana.showCard("buses"); ventana.actualizarTodos(); });
        b2.addActionListener(e -> { ventana.showCard("choferes"); ventana.actualizarTodos(); });
        b3.addActionListener(e -> { ventana.showCard("rutas"); ventana.actualizarTodos(); });
        b4.addActionListener(e -> { ventana.showCard("viajes"); ventana.actualizarTodos(); });
        b5.addActionListener(e -> { ventana.showCard("viajes"); ventana.actualizarTodos(); });
        b6.addActionListener(e -> { ventana.showCard("reportes"); ventana.actualizarTodos(); });
        b7.addActionListener(e -> ventana.mostrarEstudiantes());
        b8.addActionListener(e -> ventana.mostrarAdministradores());
        b9.addActionListener(e -> ventana.showCard("home"));
        b10.addActionListener(e -> {
            ventana.setNavEnabledForGuest(); ventana.showCard("home"); JOptionPane.showMessageDialog(this, "Sesión cerrada");
        });
    }

    public void actualizarDatos() {
        // nothing for now
    }
}


