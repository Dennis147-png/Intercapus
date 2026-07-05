package sistemaintercampus.interfaz.grafica;

import javax.swing.SwingUtilities;

public class MainGrafico {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new sistemaintercampus.interfaz.grafica.VentanaPrincipal().setVisible(true);
        });
    }
}


