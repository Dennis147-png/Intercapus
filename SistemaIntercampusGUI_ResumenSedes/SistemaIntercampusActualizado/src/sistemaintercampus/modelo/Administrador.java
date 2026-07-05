package sistemaintercampus.modelo;

public class Administrador extends Usuario {
    private String numeroEmpleado;

    public Administrador(int idUsuario, String nombre, String correo, String numeroEmpleado) {
        super(idUsuario, nombre, correo);
        this.numeroEmpleado = numeroEmpleado;
    }

    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }

    public void setNumeroEmpleado(String numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    @Override
    public String toString() {
        return super.toString() + " | Numero empleado: " + numeroEmpleado;
    }
}
