package sistemaintercampus.modelo;

public class Usuario {
    private int idUsuario;
    private String nombre;
    private String correo;

    public Usuario(int idUsuario, String nombre, String correo) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.correo = correo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public boolean iniciarSesion(String correoIngresado) {
        return correo.equalsIgnoreCase(correoIngresado);
    }

    public void cerrarSesion() {
        System.out.println(nombre + " cerro sesion.");
    }

    @Override
    public String toString() {
        return "ID: " + idUsuario +
                " | Nombre: " + nombre +
                " | Correo: " + correo;
    }
}
