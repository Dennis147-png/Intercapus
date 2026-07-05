package sistemaintercampus.negocio;

import java.util.ArrayList;
import java.util.List;
import sistemaintercampus.modelo.Administrador;
import sistemaintercampus.modelo.Bus;
import sistemaintercampus.modelo.Chofer;
import sistemaintercampus.modelo.Estudiante;
import sistemaintercampus.modelo.ISimulacion;
import sistemaintercampus.modelo.RegistroParada;
import sistemaintercampus.modelo.Ruta;
import sistemaintercampus.modelo.RutaViaje;
import sistemaintercampus.modelo.Sede;

public class    SistemaTransporte {
    private List<Sede> sedes;
    private List<Bus> buses;
    private List<Chofer> choferes;
    private List<Ruta> rutas;
    private List<RutaViaje> viajes;
    private List<Estudiante> estudiantes;
    private List<Administrador> administradores;

    public SistemaTransporte() {
        sedes = new ArrayList<Sede>();
        buses = new ArrayList<Bus>();
        choferes = new ArrayList<Chofer>();
        rutas = new ArrayList<Ruta>();
        viajes = new ArrayList<RutaViaje>();
        estudiantes = new ArrayList<Estudiante>();
        administradores = new ArrayList<Administrador>();
        cargarDatosIniciales();
    }

    public List<Sede> getSedes() {
        return sedes;
    }

    public List<Bus> getBuses() {
        return buses;
    }

    public List<Chofer> getChoferes() {
        return choferes;
    }

    public List<Ruta> getRutas() {
        return rutas;
    }

    public List<RutaViaje> getViajes() {
        return viajes;
    }

    public List<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public List<Administrador> getAdministradores() {
        return administradores;
    }

    public void cargarDatosIniciales() {
        Sede granados = new Sede("S1", "Granados", -0.167600, -78.472710);
        Sede park = new Sede("S2", "Park", -0.162555, -78.459218);
        Sede arena = new Sede("S3", "Arena", -0.160660, -78.450590);
        Sede colon = new Sede("S4", "Colon", -0.202467, -78.485194);

        sedes.add(granados);
        sedes.add(park);
        sedes.add(arena);
        sedes.add(colon);

        Chofer chofer1 = new Chofer("Carlos Mendoza", "LIC-0001");
        Chofer chofer2 = new Chofer("Luis Andrade", "LIC-0002");
        choferes.add(chofer1);
        choferes.add(chofer2);

        Bus bus1 = new Bus("ABC-1234", 35, "DISPONIBLE", granados.getLatitud(), granados.getLongitud());
        Bus bus2 = new Bus("DEF-5678", 30, "DISPONIBLE", park.getLatitud(), park.getLongitud());
        bus1.setChofer(chofer1);
        bus2.setChofer(chofer2);
        buses.add(bus1);
        buses.add(bus2);

        Ruta ruta1 = new Ruta("R1", "Granados - Park - Arena", 6.5);
        ruta1.agregarSede(granados);
        ruta1.agregarSede(park);
        ruta1.agregarSede(arena);

        Ruta ruta2 = new Ruta("R2", "Colon - Granados - Park - Arena", 8.0);
        ruta2.agregarSede(colon);
        ruta2.agregarSede(granados);
        ruta2.agregarSede(park);
        ruta2.agregarSede(arena);

        rutas.add(ruta1);
        rutas.add(ruta2);

        estudiantes.add(new Estudiante(1, "Dennis Pullupaxi", "dennis@udla.edu.ec",
                "A001", granados.getLatitud(), granados.getLongitud()));
        estudiantes.add(new Estudiante(2, "Maria Lopez", "maria@udla.edu.ec",
                "A002", park.getLatitud(), park.getLongitud()));
        estudiantes.add(new Estudiante(3, "Juan Perez", "juan@udla.edu.ec",
                "A003", arena.getLatitud(), arena.getLongitud()));
        estudiantes.add(new Estudiante(4, "Ana Torres", "ana@udla.edu.ec",
                "A004", colon.getLatitud(), colon.getLongitud()));

        administradores.add(new Administrador(10, "Admin Transporte",
                "admin@udla.edu.ec", "EMP001"));
    }

    public boolean validarNombre(String nombre) {
        return nombre != null && nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+") && !nombre.trim().isEmpty();
    }

    public boolean validarCorreo(String correo) {
        return correo != null && correo.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

    public boolean validarPlaca(String placa) {
        return placa != null && placa.matches("[A-Z]{3}-\\d{4}");
    }

    public boolean validarLicencia(String licencia) {
        return licencia != null && licencia.matches("LIC-\\d{4}");
    }

    public boolean validarIdBanner(String idBanner) {
        return idBanner != null && idBanner.matches("A\\d{3}");
    }

    public boolean validarNumeroEmpleado(String numeroEmpleado) {
        return numeroEmpleado != null && numeroEmpleado.matches("EMP\\d{3}");
    }

    public boolean validarEstadoBus(String estado) {
        return estado != null &&
                (estado.equalsIgnoreCase("DISPONIBLE") ||
                estado.equalsIgnoreCase("PROGRAMADO") ||
                estado.equalsIgnoreCase("EN RUTA") ||
                estado.equalsIgnoreCase("MANTENIMIENTO"));
    }

    public Bus buscarBus(String placa) {
        for (Bus bus : buses) {
            if (bus.getPlaca().equalsIgnoreCase(placa)) {
                return bus;
            }
        }
        return null;
    }

    public Chofer buscarChofer(String licencia) {
        for (Chofer chofer : choferes) {
            if (chofer.getLicencia().equalsIgnoreCase(licencia)) {
                return chofer;
            }
        }
        return null;
    }

    public Ruta buscarRuta(String idRuta) {
        for (Ruta ruta : rutas) {
            if (ruta.getIdRuta().equalsIgnoreCase(idRuta)) {
                return ruta;
            }
        }
        return null;
    }

    public RutaViaje buscarViaje(String idViaje) {
        for (RutaViaje viaje : viajes) {
            if (viaje.getIdViaje().equalsIgnoreCase(idViaje)) {
                return viaje;
            }
        }
        return null;
    }

    public Sede buscarSede(String idSede) {
        for (Sede sede : sedes) {
            if (sede.getIdSede().equalsIgnoreCase(idSede)) {
                return sede;
            }
        }
        return null;
    }

    public Estudiante buscarEstudiantePorBanner(String idBanner) {
        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getIdBanner().equalsIgnoreCase(idBanner)) {
                return estudiante;
            }
        }
        return null;
    }

    public Administrador buscarAdministradorPorEmpleado(String numeroEmpleado) {
        for (Administrador administrador : administradores) {
            if (administrador.getNumeroEmpleado().equalsIgnoreCase(numeroEmpleado)) {
                return administrador;
            }
        }
        return null;
    }

    public Estudiante iniciarSesionEstudiante(String idBanner) throws Exception {
        Estudiante estudiante = buscarEstudiantePorBanner(idBanner);
        if (estudiante == null) {
            throw new Exception("No existe un estudiante con ese ID Banner.");
        }
        return estudiante;
    }

    public Administrador iniciarSesionAdministrador(String numeroEmpleado) throws Exception {
        Administrador administrador = buscarAdministradorPorEmpleado(numeroEmpleado);
        if (administrador == null) {
            throw new Exception("No existe un administrador con ese numero de empleado.");
        }
        return administrador;
    }

    public void registrarEstudiante(String nombre, String correo, String idBanner, Sede sede) throws Exception {
        if (!validarNombre(nombre)) {
            throw new Exception("Nombre invalido. Solo debe tener letras y espacios.");
        }
        if (!validarCorreo(correo)) {
            throw new Exception("Correo invalido. Ejemplo: nombre@udla.edu.ec");
        }
        if (!validarIdBanner(idBanner)) {
            throw new Exception("ID Banner invalido. Formato correcto: A001");
        }
        if (buscarEstudiantePorBanner(idBanner) != null) {
            throw new Exception("Ya existe un estudiante con ese ID Banner.");
        }
        if (sede == null) {
            throw new Exception("Debe seleccionar una sede valida.");
        }

        int id = estudiantes.size() + administradores.size() + 1;
        estudiantes.add(new Estudiante(id, nombre, correo, idBanner.toUpperCase(),
                sede.getLatitud(), sede.getLongitud()));
    }

    public void registrarAdministrador(String nombre, String correo, String numeroEmpleado) throws Exception {
        if (!validarNombre(nombre)) {
            throw new Exception("Nombre invalido. Solo debe tener letras y espacios.");
        }
        if (!validarCorreo(correo)) {
            throw new Exception("Correo invalido. Ejemplo: nombre@udla.edu.ec");
        }
        if (!validarNumeroEmpleado(numeroEmpleado)) {
            throw new Exception("Numero de empleado invalido. Formato correcto: EMP001");
        }
        if (buscarAdministradorPorEmpleado(numeroEmpleado) != null) {
            throw new Exception("Ya existe un administrador con ese numero de empleado.");
        }

        int id = estudiantes.size() + administradores.size() + 1;
        administradores.add(new Administrador(id, nombre, correo, numeroEmpleado.toUpperCase()));
    }

    public void registrarChofer(Chofer chofer) throws Exception {
        if (!validarNombre(chofer.getNombre())) {
            throw new Exception("Nombre invalido. Ejemplo: Carlos Mendoza");
        }
        if (!validarLicencia(chofer.getLicencia())) {
            throw new Exception("Licencia invalida. Formato correcto: LIC-1234");
        }
        if (buscarChofer(chofer.getLicencia()) != null) {
            throw new Exception("Ya existe un chofer con esa licencia.");
        }
        chofer.setLicencia(chofer.getLicencia().toUpperCase());
        choferes.add(chofer);
    }

    public void editarChofer(String licenciaActual, String nuevoNombre, String nuevaLicencia) throws Exception {
        Chofer chofer = buscarChofer(licenciaActual);
        if (chofer == null) {
            throw new Exception("No existe un chofer con esa licencia.");
        }
        if (!validarNombre(nuevoNombre)) {
            throw new Exception("Nombre invalido. Solo debe tener letras y espacios.");
        }
        if (!validarLicencia(nuevaLicencia)) {
            throw new Exception("Licencia invalida. Formato correcto: LIC-1234");
        }
        Chofer repetido = buscarChofer(nuevaLicencia);
        if (repetido != null && repetido != chofer) {
            throw new Exception("Ya existe otro chofer con esa licencia.");
        }

        chofer.setNombre(nuevoNombre);
        chofer.setLicencia(nuevaLicencia.toUpperCase());
    }

    public void eliminarChofer(String licencia) throws Exception {
        Chofer chofer = buscarChofer(licencia);
        if (chofer == null) {
            throw new Exception("No existe un chofer con esa licencia.");
        }
        for (Bus bus : buses) {
            if (bus.getChofer() == chofer) {
                throw new Exception("No se puede eliminar. El chofer esta asignado al bus " + bus.getPlaca());
            }
        }
        choferes.remove(chofer);
    }

    public void agregarBus(Bus bus) throws Exception {
        if (!validarPlaca(bus.getPlaca())) {
            throw new Exception("Placa invalida. Formato correcto: ABC-1234");
        }
        if (buscarBus(bus.getPlaca()) != null) {
            throw new Exception("Ya existe un bus con esa placa.");
        }
        if (bus.getCapacidad() <= 0) {
            throw new Exception("La capacidad debe ser mayor que cero.");
        }
        bus.actualizarEstado("DISPONIBLE");
        buses.add(bus);
    }

    public void editarBus(String placa, int nuevaCapacidad, String nuevoEstado,
                          Sede nuevaSede, Chofer nuevoChofer) throws Exception {
        Bus bus = buscarBus(placa);
        if (bus == null) {
            throw new Exception("No existe un bus con esa placa.");
        }
        if (bus.getEstado().equalsIgnoreCase("EN RUTA")) {
            throw new Exception("No se puede editar un bus que esta en ruta.");
        }
        if (busTieneViajeActivo(bus)) {
            throw new Exception("No se puede editar un bus con viaje pendiente o en ruta.");
        }
        if (nuevaCapacidad <= 0) {
            throw new Exception("La capacidad debe ser mayor que cero.");
        }
        if (nuevoEstado.equalsIgnoreCase("EN RUTA") || nuevoEstado.equalsIgnoreCase("PROGRAMADO")) {
            throw new Exception("El estado EN RUTA o PROGRAMADO solo se asigna al crear o simular viajes.");
        }
        if (!nuevoEstado.equalsIgnoreCase("DISPONIBLE") &&
                !nuevoEstado.equalsIgnoreCase("MANTENIMIENTO")) {
            throw new Exception("Estado invalido. Use DISPONIBLE o MANTENIMIENTO.");
        }
        if (nuevaSede == null) {
            throw new Exception("Debe seleccionar una sede valida.");
        }

        bus.setCapacidad(nuevaCapacidad);
        bus.setEstado(nuevoEstado.toUpperCase());
        bus.setChofer(nuevoChofer);
        bus.actualizarUbicacion(nuevaSede.getLatitud(), nuevaSede.getLongitud());
    }

    public void eliminarBus(String placa) throws Exception {
        Bus bus = buscarBus(placa);
        if (bus == null) {
            throw new Exception("No existe un bus con esa placa.");
        }
        for (RutaViaje viaje : viajes) {
            if (viaje.getBus() == bus && !viaje.getEstadoViaje().equalsIgnoreCase("FINALIZADO")) {
                throw new Exception("No se puede eliminar. El bus tiene un viaje activo o pendiente.");
            }
        }
        buses.remove(bus);
    }

    public void asignarChoferABus(String placa, String licencia) throws Exception {
        Bus bus = buscarBus(placa);
        Chofer chofer = buscarChofer(licencia);

        if (bus == null) {
            throw new Exception("No existe un bus con esa placa.");
        }
        if (chofer == null) {
            throw new Exception("No existe un chofer con esa licencia.");
        }
        if (bus.getEstado().equalsIgnoreCase("EN RUTA")) {
            throw new Exception("No se puede cambiar chofer a un bus en ruta.");
        }

        bus.setChofer(chofer);
    }

    public void registrarRuta(String idRuta, String nombreRuta, double distanciaTotalKm,
                              List<Sede> sedesRuta) throws Exception {
        if (idRuta == null || idRuta.trim().isEmpty()) {
            throw new Exception("El ID de la ruta es obligatorio.");
        }
        if (buscarRuta(idRuta) != null) {
            throw new Exception("Ya existe una ruta con ese ID.");
        }
        if (nombreRuta == null || nombreRuta.trim().isEmpty()) {
            throw new Exception("El nombre de la ruta es obligatorio.");
        }
        if (distanciaTotalKm <= 0) {
            throw new Exception("La distancia debe ser mayor que cero.");
        }
        if (sedesRuta.size() < 2) {
            throw new Exception("La ruta debe tener al menos dos paradas.");
        }

        Ruta ruta = new Ruta(idRuta.toUpperCase(), nombreRuta, distanciaTotalKm);
        for (Sede sede : sedesRuta) {
            ruta.agregarSede(sede);
        }
        rutas.add(ruta);
    }

    public void editarRuta(String idRuta, String nuevoNombre, double nuevaDistancia,
                           List<Sede> nuevasSedes) throws Exception {
        Ruta ruta = buscarRuta(idRuta);
        if (ruta == null) {
            throw new Exception("No existe una ruta con ese ID.");
        }
        if (rutaTieneViajeActivo(ruta)) {
            throw new Exception("No se puede editar una ruta con viajes pendientes o en ruta.");
        }
        if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
            throw new Exception("El nombre de la ruta es obligatorio.");
        }
        if (nuevaDistancia <= 0) {
            throw new Exception("La distancia debe ser mayor que cero.");
        }
        if (nuevasSedes.size() < 2) {
            throw new Exception("La ruta debe tener al menos dos paradas.");
        }

        ruta.setNombreRuta(nuevoNombre);
        ruta.setDistanciaTotalKm(nuevaDistancia);
        ruta.getSedes().clear();
        for (Sede sede : nuevasSedes) {
            ruta.agregarSede(sede);
        }
    }

    public void eliminarRuta(String idRuta) throws Exception {
        Ruta ruta = buscarRuta(idRuta);
        if (ruta == null) {
            throw new Exception("No existe una ruta con ese ID.");
        }
        for (RutaViaje viaje : viajes) {
            if (viaje.getRuta() == ruta) {
                throw new Exception("No se puede eliminar. La ruta ya tiene viajes registrados.");
            }
        }
        rutas.remove(ruta);
    }

    public boolean rutaTieneViajeActivo(Ruta ruta) {
        for (RutaViaje viaje : viajes) {
            if (viaje.getRuta() == ruta && !viaje.getEstadoViaje().equalsIgnoreCase("FINALIZADO")) {
                return true;
            }
        }
        return false;
    }

    public boolean busTieneViajeActivo(Bus bus) {
        for (RutaViaje viaje : viajes) {
            if (viaje.getBus() == bus && !viaje.getEstadoViaje().equalsIgnoreCase("FINALIZADO")) {
                return true;
            }
        }
        return false;
    }

    public boolean busEstaEnSede(Bus bus, Sede sede) {
        return calcularDistanciaBusSede(bus, sede) < 0.02;
    }

    public RutaViaje crearViaje(String idViaje, String placaBus, String idRuta) throws Exception {
        Bus bus = buscarBus(placaBus);
        Ruta ruta = buscarRuta(idRuta);

        if (idViaje == null || idViaje.trim().isEmpty()) {
            throw new Exception("El ID del viaje es obligatorio.");
        }
        if (buscarViaje(idViaje) != null) {
            throw new Exception("Ya existe un viaje con ese ID.");
        }
        if (bus == null) {
            throw new Exception("No existe el bus seleccionado.");
        }
        if (ruta == null) {
            throw new Exception("No existe la ruta seleccionada.");
        }
        if (ruta.getSedes().size() < 2) {
            throw new Exception("La ruta debe tener al menos dos paradas.");
        }
        if (bus.getChofer() == null) {
            throw new Exception("El bus no tiene chofer asignado.");
        }
        if (!bus.getEstado().equalsIgnoreCase("DISPONIBLE")) {
            throw new Exception("El bus debe estar DISPONIBLE para crear un viaje.");
        }
        if (busTieneViajeActivo(bus)) {
            throw new Exception("El bus ya tiene un viaje pendiente o en ruta.");
        }
        if (!busEstaEnSede(bus, ruta.getPrimeraSede())) {
            throw new Exception("El bus debe estar en la primera parada de la ruta: " +
                    ruta.getPrimeraSede().getNombre());
        }

        RutaViaje viaje = new RutaViaje(idViaje.toUpperCase(), bus, ruta);
        viaje.calcularTiempoEstimado();
        viajes.add(viaje);
        bus.actualizarEstado("PROGRAMADO");
        return viaje;
    }

    public void iniciarViaje(String idViaje) throws Exception {
        RutaViaje viaje = buscarViaje(idViaje);

        if (viaje == null) {
            throw new Exception("No existe el viaje seleccionado.");
        }
        if (!viaje.getEstadoViaje().equalsIgnoreCase("PENDIENTE")) {
            throw new Exception("Solo se puede iniciar un viaje pendiente.");
        }
        if (!viaje.getBus().getEstado().equalsIgnoreCase("PROGRAMADO")) {
            throw new Exception("El bus debe estar PROGRAMADO para iniciar el viaje.");
        }

        viaje.iniciarViaje();

        if (viaje.getRuta().obtenerSedes().size() >= 2) {
            Sede origen = viaje.getRuta().obtenerSedes().get(0);
            Sede siguiente = viaje.getRuta().obtenerSedes().get(1);
            double latMedia = (origen.getLatitud() + siguiente.getLatitud()) / 2;
            double lonMedia = (origen.getLongitud() + siguiente.getLongitud()) / 2;
            viaje.getBus().actualizarUbicacion(latMedia, lonMedia);
        }
    }

    public void finalizarViaje(String idViaje) throws Exception {
        RutaViaje viaje = buscarViaje(idViaje);

        if (viaje == null) {
            throw new Exception("No existe el viaje seleccionado.");
        }
        if (viaje.getEstadoViaje().equalsIgnoreCase("FINALIZADO")) {
            throw new Exception("Este viaje ya fue finalizado.");
        }
        if (!viaje.getEstadoViaje().equalsIgnoreCase("EN RUTA")) {
            throw new Exception("Primero debe iniciar el viaje para poder finalizarlo.");
        }
        if (estudiantes.isEmpty()) {
            throw new Exception("No existen estudiantes registrados para simular el viaje.");
        }

        ISimulacion simulacion = new SimulacionAutomatica(estudiantes);
        simulacion.simularViaje(viaje);
    }

    public void simularViajeAutomatico(String idViaje) throws Exception {
        finalizarViaje(idViaje);
    }

    public double calcularDistanciaEntrePuntos(double lat1, double lon1, double lat2, double lon2) {
        double radioTierraKm = 6371.0;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) *
                        Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) *
                        Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return radioTierraKm * c;
    }

    public double calcularDistanciaBusSede(Bus bus, Sede sede) {
        return calcularDistanciaEntrePuntos(
                bus.getLatitudActual(),
                bus.getLongitudActual(),
                sede.getLatitud(),
                sede.getLongitud());
    }

    public int calcularTiempoEstimadoBusSede(Bus bus, Sede sede) {
        double velocidadPromedio = 25.0;
        double distancia = calcularDistanciaBusSede(bus, sede);
        return (int) Math.ceil((distancia / velocidadPromedio) * 60);
    }

    public String obtenerUbicacionBus(Bus bus) {
        Sede sedeActual = obtenerSedeActual(bus);
        if (sedeActual != null) {
            return "Sede " + sedeActual.getNombre();
        }

        RutaViaje viajeActivo = buscarViajeActivoPorBus(bus);
        if (viajeActivo != null) {
            List<Sede> paradas = viajeActivo.getRuta().obtenerSedes();
            for (int i = 0; i < paradas.size() - 1; i++) {
                Sede a = paradas.get(i);
                Sede b = paradas.get(i + 1);
                double latMin = Math.min(a.getLatitud(), b.getLatitud()) - 0.001;
                double latMax = Math.max(a.getLatitud(), b.getLatitud()) + 0.001;
                double lonMin = Math.min(a.getLongitud(), b.getLongitud()) - 0.001;
                double lonMax = Math.max(a.getLongitud(), b.getLongitud()) + 0.001;
                if (bus.getLatitudActual() >= latMin && bus.getLatitudActual() <= latMax &&
                        bus.getLongitudActual() >= lonMin && bus.getLongitudActual() <= lonMax) {
                    return "Entre " + a.getNombre() + " y " + b.getNombre() +
                            " | Coordenadas: (" + bus.getLatitudActual() + ", " + bus.getLongitudActual() + ")";
                }
            }
        }

        return "Coordenadas: (" + bus.getLatitudActual() + ", " + bus.getLongitudActual() + ")";
    }

    public RutaViaje buscarViajeActivoPorBus(Bus bus) {
        for (RutaViaje viaje : viajes) {
            if (viaje.getBus() == bus && viaje.getEstadoViaje().equalsIgnoreCase("EN RUTA")) {
                return viaje;
            }
        }
        return null;
    }

    public Sede obtenerSedeActual(Bus bus) {
        for (Sede sede : sedes) {
            if (busEstaEnSede(bus, sede)) {
                return sede;
            }
        }
        return null;
    }

    public Bus consultarBusCercano(Estudiante estudiante) {
        Bus busCercano = null;
        double menorDistancia = Double.MAX_VALUE;

        for (Bus bus : buses) {
            if (bus.getEstado().equalsIgnoreCase("DISPONIBLE") ||
                    bus.getEstado().equalsIgnoreCase("EN RUTA")) {

                double distancia = calcularDistanciaEntrePuntos(
                        estudiante.getLatitudActual(),
                        estudiante.getLongitudActual(),
                        bus.getLatitudActual(),
                        bus.getLongitudActual());

                if (distancia < menorDistancia) {
                    menorDistancia = distancia;
                    busCercano = bus;
                }
            }
        }

        return busCercano;
    }

    public void mostrarSedes() {
        System.out.println("\nPARADAS DISPONIBLES");
        for (Sede sede : sedes) {
            System.out.println(sede);
        }
    }

    public void mostrarBuses() {
        System.out.println("\nBUSES REGISTRADOS");
        if (buses.isEmpty()) {
            System.out.println("No existen buses registrados.");
            return;
        }
        for (Bus bus : buses) {
            String chofer = bus.getChofer() == null ? "Sin chofer" : bus.getChofer().getNombre();
            System.out.println("Placa: " + bus.getPlaca() +
                    " | Capacidad: " + bus.getCapacidad() +
                    " | Estado: " + bus.getEstado() +
                    " | Ubicacion: " + obtenerUbicacionBus(bus) +
                    " | Chofer: " + chofer);
        }
    }

    public void mostrarBusesEnRuta() {
        System.out.println("\nBUSES EN RECORRIDO");
        boolean hayBuses = false;
        for (Bus bus : buses) {
            if (bus.getEstado().equalsIgnoreCase("EN RUTA")) {
                System.out.println("Placa: " + bus.getPlaca() +
                        " | Ubicacion: " + obtenerUbicacionBus(bus) +
                        " | Ocupacion: " + obtenerOcupacionBus(bus));
                hayBuses = true;
            }
        }
        if (!hayBuses) {
            System.out.println("No hay buses en recorrido.");
        }
    }

    public void mostrarChoferes() {
        System.out.println("\nCHOFERES REGISTRADOS");
        if (choferes.isEmpty()) {
            System.out.println("No existen choferes registrados.");
            return;
        }
        for (Chofer chofer : choferes) {
            System.out.println(chofer);
        }
    }

    public void mostrarRutas() {
        System.out.println("\nRUTAS REGISTRADAS");
        if (rutas.isEmpty()) {
            System.out.println("No existen rutas registradas.");
            return;
        }
        for (Ruta ruta : rutas) {
            System.out.println(ruta);
        }
    }

    public void mostrarViajes() {
        System.out.println("\nVIAJES REGISTRADOS");
        if (viajes.isEmpty()) {
            System.out.println("No existen viajes registrados.");
            return;
        }
        for (RutaViaje viaje : viajes) {
            System.out.println(viaje);
        }
    }

    public void mostrarEstudiantes() {
        System.out.println("\nESTUDIANTES REGISTRADOS");
        if (estudiantes.isEmpty()) {
            System.out.println("No existen estudiantes registrados.");
            return;
        }
        for (Estudiante estudiante : estudiantes) {
            System.out.println(estudiante);
        }
    }

    public void mostrarAdministradores() {
        System.out.println("\nADMINISTRADORES REGISTRADOS");
        if (administradores.isEmpty()) {
            System.out.println("No existen administradores registrados.");
            return;
        }
        for (Administrador administrador : administradores) {
            System.out.println(administrador);
        }
    }

    public void mostrarRegistroViaje(String idViaje) throws Exception {
        RutaViaje viaje = buscarViaje(idViaje);

        if (viaje == null) {
            throw new Exception("No existe el viaje seleccionado.");
        }

        System.out.println("\nREGISTRO DEL VIAJE");
        System.out.println("Viaje: " + viaje.getIdViaje());
        System.out.println("Ruta: " + viaje.getRuta().mostrarRecorrido());
        System.out.println("Bus: " + viaje.getBus().getPlaca());
        String chofer = viaje.getBus().getChofer() == null ?
                "Sin chofer asignado" : viaje.getBus().getChofer().getNombre();
        System.out.println("Chofer: " + chofer);
        System.out.println("Fecha: " + viaje.getFecha());
        System.out.println("Hora salida: " + viaje.getHoraSalida());
        System.out.println("Hora llegada: " + viaje.getHoraLlegada());
        System.out.println("Duracion: " + viaje.calcularDuracionMinutos() + " minutos");
        System.out.println("Estado: " + viaje.getEstadoViaje());
        System.out.println("Pasajeros atendidos: " + viaje.getPasajerosAtendidos());

        if (viaje.getRegistrosParadas().isEmpty()) {
            System.out.println("El viaje aun no tiene registros de paradas.");
            return;
        }

        for (RegistroParada registro : viaje.getRegistrosParadas()) {
            System.out.println(registro);
        }
    }

    public String obtenerOcupacionBus(Bus bus) {
        for (RutaViaje viaje : viajes) {
            if (viaje.getBus() == bus && viaje.getEstadoViaje().equalsIgnoreCase("EN RUTA")) {
                return viaje.getPasajerosActuales() + "/" + bus.getCapacidad();
            }
        }
        return "0/" + bus.getCapacidad();
    }
}
