package sistemaintercampus.interfaz;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import sistemaintercampus.modelo.Administrador;
import sistemaintercampus.modelo.Bus;
import sistemaintercampus.modelo.Chofer;
import sistemaintercampus.modelo.Estudiante;
import sistemaintercampus.modelo.Ruta;
import sistemaintercampus.modelo.RutaViaje;
import sistemaintercampus.modelo.Sede;
import sistemaintercampus.negocio.SistemaTransporte;

public class MainIntercampus {

    public static void main(String[] args) {
        SistemaTransporte sistema = new SistemaTransporte();
        Scanner sc = new Scanner(System.in);
        int opcion = 0;

        do {
            try {
                menu();
                opcion = Integer.parseInt(sc.nextLine());

                switch (opcion) {
                    case 1: {
                        System.out.println("\nREGISTRO DE ESTUDIANTE");
                        System.out.print("Nombre (Ejemplo: Maria Lopez): ");
                        String nombre = sc.nextLine();
                        System.out.print("Correo (Ejemplo: maria@udla.edu.ec): ");
                        String correo = sc.nextLine();
                        System.out.print("ID Banner (Ejemplo: A001): ");
                        String idBanner = sc.nextLine().toUpperCase();
                        sistema.mostrarSedes();
                        System.out.print("ID de sede actual: ");
                        String idSede = sc.nextLine();
                        Sede sede = sistema.buscarSede(idSede);

                        sistema.registrarEstudiante(nombre, correo, idBanner, sede);
                        System.out.println("Estudiante registrado correctamente.");
                        break;
                    }
                    case 2: {
                        System.out.println("\nREGISTRO DE ADMINISTRADOR");
                        System.out.print("Nombre (Ejemplo: Carlos Perez): ");
                        String nombre = sc.nextLine();
                        System.out.print("Correo (Ejemplo: admin@udla.edu.ec): ");
                        String correo = sc.nextLine();
                        System.out.print("Numero empleado (Ejemplo: EMP001): ");
                        String numeroEmpleado = sc.nextLine().toUpperCase();

                        sistema.registrarAdministrador(nombre, correo, numeroEmpleado);
                        System.out.println("Administrador registrado correctamente.");
                        break;
                    }
                    case 3: {
                        System.out.println("\nINICIO DE SESION ESTUDIANTE");
                        System.out.print("Ingrese ID Banner: ");
                        String idBanner = sc.nextLine().toUpperCase();
                        Estudiante estudiante = sistema.iniciarSesionEstudiante(idBanner);

                        System.out.println("\nBienvenido estudiante");
                        System.out.println("Nombre: " + estudiante.getNombre());
                        System.out.println("Correo: " + estudiante.getCorreo());
                        System.out.println("ID Banner: " + estudiante.getIdBanner());

                        int opcionEstudiante = 0;
                        do {
                            try {
                                System.out.println("\n===== MENU ESTUDIANTE =====");
                                System.out.println("1. Ver mi informacion");
                                System.out.println("2. Ver rutas");
                                System.out.println("3. Ver buses en recorrido");
                                System.out.println("4. Consultar ubicacion de bus");
                                System.out.println("5. Calcular tiempo estimado bus - parada");
                                System.out.println("6. Ver ocupacion de bus");
                                System.out.println("7. Ver historial de viajes");
                                System.out.println("8. Cerrar sesion");
                                System.out.print("Ingrese opcion: ");
                                opcionEstudiante = Integer.parseInt(sc.nextLine());

                                switch (opcionEstudiante) {
                                    case 1: {
                                        System.out.println(estudiante);
                                        break;
                                    }
                                    case 2: {
                                        sistema.mostrarRutas();
                                        break;
                                    }
                                    case 3: {
                                        sistema.mostrarBusesEnRuta();
                                        break;
                                    }
                                    case 4: {
                                        sistema.mostrarBusesEnRuta();
                                        System.out.print("Placa del bus: ");
                                        String placa = sc.nextLine().toUpperCase();
                                        Bus bus = sistema.buscarBus(placa);
                                        if (bus == null) {
                                            System.out.println("No existe ese bus.");
                                        } else if (!bus.getEstado().equalsIgnoreCase("EN RUTA")) {
                                            System.out.println("El bus no esta en recorrido.");
                                        } else {
                                            System.out.println("Ubicacion: " + sistema.obtenerUbicacionBus(bus));
                                        }
                                        break;
                                    }
                                    case 5: {
                                        sistema.mostrarBusesEnRuta();
                                        System.out.print("Placa del bus: ");
                                        String placa = sc.nextLine().toUpperCase();
                                        Bus bus = sistema.buscarBus(placa);
                                        if (bus == null) {
                                            System.out.println("No existe ese bus.");
                                            break;
                                        }
                                        if (!bus.getEstado().equalsIgnoreCase("EN RUTA")) {
                                            System.out.println("El bus no esta en recorrido.");
                                            break;
                                        }

                                        sistema.mostrarSedes();
                                        System.out.print("ID de parada destino: ");
                                        String idSede = sc.nextLine();
                                        Sede sede = sistema.buscarSede(idSede);
                                        if (sede == null) {
                                            System.out.println("Parada invalida.");
                                            break;
                                        }
                                        int tiempo = sistema.calcularTiempoEstimadoBusSede(bus, sede);
                                        System.out.println("Tiempo estimado aproximado: " + tiempo + " minutos");
                                        break;
                                    }
                                    case 6: {
                                        sistema.mostrarBusesEnRuta();
                                        System.out.print("Placa del bus: ");
                                        String placa = sc.nextLine().toUpperCase();
                                        Bus bus = sistema.buscarBus(placa);
                                        if (bus == null) {
                                            System.out.println("No existe ese bus.");
                                        } else {
                                            System.out.println("Ocupacion: " + sistema.obtenerOcupacionBus(bus));
                                        }
                                        break;
                                    }
                                    case 7: {
                                        sistema.mostrarViajes();
                                        break;
                                    }
                                    case 8: {
                                        estudiante.cerrarSesion();
                                        break;
                                    }
                                    default: {
                                        System.out.println("Opcion invalida.");
                                        break;
                                    }
                                }
                            } catch (NumberFormatException ex) {
                                System.out.println("Debe ingresar un numero valido.");
                            } catch (Exception ex) {
                                System.out.println(ex.getMessage());
                            }
                        } while (opcionEstudiante != 8);
                        break;
                    }
                    case 4: {
                        System.out.println("\nINICIO DE SESION ADMINISTRADOR");
                        System.out.print("Ingrese numero de empleado: ");
                        String numeroEmpleado = sc.nextLine().toUpperCase();
                        Administrador administrador = sistema.iniciarSesionAdministrador(numeroEmpleado);
                        System.out.println("Bienvenido " + administrador.getNombre());

                        int opcionAdmin = 0;
                        do {
                            try {
                                System.out.println("\n===== MENU ADMINISTRADOR =====");
                                System.out.println("1. Mostrar paradas");
                                System.out.println("2. Mostrar rutas");
                                System.out.println("3. Registrar ruta");
                                System.out.println("4. Editar ruta");
                                System.out.println("5. Eliminar ruta");
                                System.out.println("6. Mostrar buses");
                                System.out.println("7. Registrar bus");
                                System.out.println("8. Editar bus");
                                System.out.println("9. Eliminar bus");
                                System.out.println("10. Mostrar choferes");
                                System.out.println("11. Registrar chofer");
                                System.out.println("12. Editar chofer");
                                System.out.println("13. Eliminar chofer");
                                System.out.println("14. Asignar chofer a bus");
                                System.out.println("15. Crear viaje");
                                System.out.println("16. Mostrar viajes");
                                System.out.println("17. Iniciar viaje");
                                System.out.println("18. Finalizar viaje");
                                System.out.println("19. Mostrar registro de viaje");
                                System.out.println("20. Registrar estudiante");
                                System.out.println("21. Registrar administrador");
                                System.out.println("22. Mostrar estudiantes");
                                System.out.println("23. Mostrar administradores");
                                System.out.println("24. Cerrar sesion");
                                System.out.print("Ingrese opcion: ");
                                opcionAdmin = Integer.parseInt(sc.nextLine());

                                switch (opcionAdmin) {
                                    case 1: {
                                        sistema.mostrarSedes();
                                        break;
                                    }
                                    case 2: {
                                        sistema.mostrarRutas();
                                        break;
                                    }
                                    case 3: {
                                        System.out.println("\nREGISTRAR RUTA");
                                        System.out.print("ID ruta (Ejemplo: R3): ");
                                        String idRuta = sc.nextLine().toUpperCase();
                                        System.out.print("Nombre ruta: ");
                                        String nombreRuta = sc.nextLine();
                                        System.out.print("Distancia total km: ");
                                        double distancia = Double.parseDouble(sc.nextLine());

                                        List<Sede> sedesRuta = new ArrayList<Sede>();
                                        String continuar = "S";
                                        while (continuar.equalsIgnoreCase("S")) {
                                            sistema.mostrarSedes();
                                            System.out.print("ID de sede a agregar: ");
                                            String idSede = sc.nextLine();
                                            Sede sede = sistema.buscarSede(idSede);
                                            if (sede != null) {
                                                sedesRuta.add(sede);
                                                System.out.println("Sede agregada.");
                                            } else {
                                                System.out.println("Sede invalida.");
                                            }
                                            System.out.print("Desea agregar otra sede? (S/N): ");
                                            continuar = sc.nextLine();
                                        }

                                        sistema.registrarRuta(idRuta, nombreRuta, distancia, sedesRuta);
                                        System.out.println("Ruta registrada correctamente.");
                                        break;
                                    }
                                    case 4: {
                                        System.out.println("\nEDITAR RUTA");
                                        sistema.mostrarRutas();
                                        System.out.print("ID ruta a editar: ");
                                        String idRuta = sc.nextLine().toUpperCase();
                                        System.out.print("Nuevo nombre: ");
                                        String nombreRuta = sc.nextLine();
                                        System.out.print("Nueva distancia total km: ");
                                        double distancia = Double.parseDouble(sc.nextLine());

                                        List<Sede> sedesRuta = new ArrayList<Sede>();
                                        String continuar = "S";
                                        while (continuar.equalsIgnoreCase("S")) {
                                            sistema.mostrarSedes();
                                            System.out.print("ID de sede para la ruta: ");
                                            String idSede = sc.nextLine();
                                            Sede sede = sistema.buscarSede(idSede);
                                            if (sede != null) {
                                                sedesRuta.add(sede);
                                                System.out.println("Sede agregada.");
                                            } else {
                                                System.out.println("Sede invalida.");
                                            }
                                            System.out.print("Desea agregar otra sede? (S/N): ");
                                            continuar = sc.nextLine();
                                        }

                                        sistema.editarRuta(idRuta, nombreRuta, distancia, sedesRuta);
                                        System.out.println("Ruta editada correctamente.");
                                        break;
                                    }
                                    case 5: {
                                        sistema.mostrarRutas();
                                        System.out.print("ID ruta a eliminar: ");
                                        String idRuta = sc.nextLine().toUpperCase();
                                        sistema.eliminarRuta(idRuta);
                                        System.out.println("Ruta eliminada correctamente.");
                                        break;
                                    }
                                    case 6: {
                                        sistema.mostrarBuses();
                                        break;
                                    }
                                    case 7: {
                                        System.out.println("\nREGISTRAR BUS");
                                        System.out.print("Placa (Formato ABC-1234): ");
                                        String placa = sc.nextLine().toUpperCase();
                                        System.out.print("Capacidad: ");
                                        int capacidad = Integer.parseInt(sc.nextLine());
                                        sistema.mostrarSedes();
                                        System.out.print("ID sede donde esta ubicado: ");
                                        String idSede = sc.nextLine();
                                        Sede sede = sistema.buscarSede(idSede);
                                        if (sede == null) {
                                            System.out.println("Sede invalida.");
                                            break;
                                        }
                                        Bus bus = new Bus(placa, capacidad, "DISPONIBLE",
                                                sede.getLatitud(), sede.getLongitud());
                                        sistema.agregarBus(bus);
                                        System.out.println("Bus registrado correctamente en estado DISPONIBLE.");
                                        break;
                                    }
                                    case 8: {
                                        System.out.println("\nEDITAR BUS");
                                        sistema.mostrarBuses();
                                        System.out.print("Placa del bus a editar: ");
                                        String placa = sc.nextLine().toUpperCase();
                                        System.out.print("Nueva capacidad: ");
                                        int capacidad = Integer.parseInt(sc.nextLine());
                                        System.out.print("Nuevo estado (DISPONIBLE/MANTENIMIENTO): ");
                                        String estado = sc.nextLine().toUpperCase();
                                        sistema.mostrarSedes();
                                        System.out.print("ID nueva sede: ");
                                        String idSede = sc.nextLine();
                                        Sede sede = sistema.buscarSede(idSede);
                                        sistema.mostrarChoferes();
                                        System.out.print("Licencia del chofer o NINGUNO: ");
                                        String licencia = sc.nextLine().toUpperCase();
                                        Chofer chofer = null;
                                        if (!licencia.equalsIgnoreCase("NINGUNO")) {
                                            chofer = sistema.buscarChofer(licencia);
                                            if (chofer == null) {
                                                System.out.println("Chofer invalido.");
                                                break;
                                            }
                                        }

                                        sistema.editarBus(placa, capacidad, estado, sede, chofer);
                                        System.out.println("Bus editado correctamente.");
                                        break;
                                    }
                                    case 9: {
                                        sistema.mostrarBuses();
                                        System.out.print("Placa del bus a eliminar: ");
                                        String placa = sc.nextLine().toUpperCase();
                                        sistema.eliminarBus(placa);
                                        System.out.println("Bus eliminado correctamente.");
                                        break;
                                    }
                                    case 10: {
                                        sistema.mostrarChoferes();
                                        break;
                                    }
                                    case 11: {
                                        System.out.println("\nREGISTRAR CHOFER");
                                        System.out.print("Nombre (Ejemplo: Carlos Mendoza): ");
                                        String nombre = sc.nextLine();
                                        System.out.print("Licencia (Formato LIC-1234): ");
                                        String licencia = sc.nextLine().toUpperCase();
                                        sistema.registrarChofer(new Chofer(nombre, licencia));
                                        System.out.println("Chofer registrado correctamente.");
                                        break;
                                    }
                                    case 12: {
                                        System.out.println("\nEDITAR CHOFER");
                                        sistema.mostrarChoferes();
                                        System.out.print("Licencia actual: ");
                                        String licenciaActual = sc.nextLine().toUpperCase();
                                        System.out.print("Nuevo nombre: ");
                                        String nombre = sc.nextLine();
                                        System.out.print("Nueva licencia (Formato LIC-1234): ");
                                        String nuevaLicencia = sc.nextLine().toUpperCase();
                                        sistema.editarChofer(licenciaActual, nombre, nuevaLicencia);
                                        System.out.println("Chofer editado correctamente.");
                                        break;
                                    }
                                    case 13: {
                                        sistema.mostrarChoferes();
                                        System.out.print("Licencia del chofer a eliminar: ");
                                        String licencia = sc.nextLine().toUpperCase();
                                        sistema.eliminarChofer(licencia);
                                        System.out.println("Chofer eliminado correctamente.");
                                        break;
                                    }
                                    case 14: {
                                        sistema.mostrarBuses();
                                        System.out.print("Placa del bus: ");
                                        String placa = sc.nextLine().toUpperCase();
                                        sistema.mostrarChoferes();
                                        System.out.print("Licencia del chofer: ");
                                        String licencia = sc.nextLine().toUpperCase();
                                        sistema.asignarChoferABus(placa, licencia);
                                        System.out.println("Chofer asignado correctamente.");
                                        break;
                                    }
                                    case 15: {
                                        System.out.println("\nCREAR VIAJE");
                                        System.out.print("ID viaje: ");
                                        String idViaje = sc.nextLine().toUpperCase();
                                        sistema.mostrarRutas();
                                        System.out.print("ID ruta: ");
                                        String idRuta = sc.nextLine().toUpperCase();
                                        sistema.mostrarBuses();
                                        System.out.print("Placa del bus: ");
                                        String placa = sc.nextLine().toUpperCase();
                                        RutaViaje viaje = sistema.crearViaje(idViaje, placa, idRuta);
                                        System.out.println("Viaje creado correctamente.");
                                        System.out.println(viaje);
                                        break;
                                    }
                                    case 16: {
                                        sistema.mostrarViajes();
                                        break;
                                    }
                                    case 17: {
                                        sistema.mostrarViajes();
                                        System.out.print("ID viaje a iniciar: ");
                                        String idViaje = sc.nextLine().toUpperCase();
                                        sistema.iniciarViaje(idViaje);
                                        System.out.println("Viaje iniciado. El bus quedo EN RUTA.");
                                        break;
                                    }
                                    case 18: {
                                        sistema.mostrarViajes();
                                        System.out.print("ID viaje a finalizar: ");
                                        String idViaje = sc.nextLine().toUpperCase();
                                        sistema.finalizarViaje(idViaje);
                                        System.out.println("Viaje finalizado correctamente.");
                                        break;
                                    }
                                    case 19: {
                                        sistema.mostrarViajes();
                                        System.out.print("ID viaje: ");
                                        String idViaje = sc.nextLine().toUpperCase();
                                        sistema.mostrarRegistroViaje(idViaje);
                                        break;
                                    }
                                    case 20: {
                                        System.out.println("\nREGISTRO DE ESTUDIANTE");
                                        System.out.print("Nombre (Ejemplo: Maria Lopez): ");
                                        String nombre = sc.nextLine();
                                        System.out.print("Correo (Ejemplo: maria@udla.edu.ec): ");
                                        String correo = sc.nextLine();
                                        System.out.print("ID Banner (Ejemplo: A001): ");
                                        String idBanner = sc.nextLine().toUpperCase();
                                        sistema.mostrarSedes();
                                        System.out.print("ID de sede actual: ");
                                        String idSede = sc.nextLine();
                                        Sede sede = sistema.buscarSede(idSede);

                                        sistema.registrarEstudiante(nombre, correo, idBanner, sede);
                                        System.out.println("Estudiante registrado correctamente.");
                                        break;
                                    }
                                    case 21: {
                                        System.out.println("\nREGISTRO DE ADMINISTRADOR");
                                        System.out.print("Nombre (Ejemplo: Carlos Perez): ");
                                        String nombre = sc.nextLine();
                                        System.out.print("Correo (Ejemplo: admin@udla.edu.ec): ");
                                        String correo = sc.nextLine();
                                        System.out.print("Numero empleado (Ejemplo: EMP001): ");
                                        String nuevoNumeroEmpleado = sc.nextLine().toUpperCase();

                                        sistema.registrarAdministrador(nombre, correo, nuevoNumeroEmpleado);
                                        System.out.println("Administrador registrado correctamente.");
                                        break;
                                    }
                                    case 22: {
                                        sistema.mostrarEstudiantes();
                                        break;
                                    }
                                    case 23: {
                                        sistema.mostrarAdministradores();
                                        break;
                                    }
                                    case 24: {
                                        administrador.cerrarSesion();
                                        break;
                                    }
                                    default: {
                                        System.out.println("Opcion invalida.");
                                        break;
                                    }
                                }
                            } catch (NumberFormatException ex) {
                                System.out.println("Debe ingresar un numero valido.");
                            } catch (Exception ex) {
                                System.out.println(ex.getMessage());
                            }
                        } while (opcionAdmin != 24);
                        break;
                    }
                    case 5: {
                        System.out.println("Saliendo del sistema...");
                        break;
                    }
                    default: {
                        System.out.println("Opcion invalida.");
                        break;
                    }
                }
            } catch (NumberFormatException ex) {
                System.out.println("Debe ingresar un numero valido.");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        } while (opcion != 5);
    }

    public static void menu() {
        System.out.println("\n===== SISTEMA INTERCAMPUS UDLA =====");
        System.out.println("1. Registrarse como estudiante");
        System.out.println("2. Registrarse como administrador");
        System.out.println("3. Iniciar sesion estudiante");
        System.out.println("4. Iniciar sesion administrador");
        System.out.println("5. Salir");
        System.out.print("Ingrese opcion: ");
    }
}
