Sistema Intercampus UDLA - Version consola corregida

Cambios principales:

1. Se agrego inicio de sesion por rol:
   - Estudiante con ID Banner.
   - Administrador con numero de empleado.

2. Se agregaron listas de:
   - Estudiantes.
   - Administradores.

3. El menu de estudiante permite:
   - Ver informacion personal.
   - Ver rutas.
   - Ver buses en recorrido.
   - Consultar ubicacion del bus.
   - Calcular tiempo estimado bus-parada.
   - Ver ocupacion.
   - Ver historial.

4. El menu administrador permite:
   - CRUD de buses.
   - CRUD de choferes.
   - CRUD de rutas.
   - Crear viajes.
   - Simular viajes.
   - Ver reportes.
   - Registrar y mostrar usuarios.

5. Validaciones incluidas:
   - Nombre solo letras.
   - Correo valido.
   - ID Banner unico con formato A001.
   - Numero empleado unico con formato EMP001.
   - Licencia unica con formato LIC-1234.
   - Placa unica con formato ABC-1234.
   - Capacidad mayor que cero.
   - Opciones numericas con try-catch.
   - No crear viaje con bus sin chofer.
   - No crear viaje con bus en ruta o programado.
   - No crear viaje si el bus no esta en la primera parada de la ruta.

6. La simulacion automatica:
   - Usa estudiantes reales registrados.
   - Registra quienes suben.
   - Registra quienes bajan.
   - Respeta capacidad.
   - En la ultima parada baja a los pasajeros restantes.
   - Guarda duracion y pasajeros atendidos.

7. Se mantiene la estructura:
   - modelo
   - negocio
   - interfaz

8. La interfaz grafica no fue modificada en esta version.
