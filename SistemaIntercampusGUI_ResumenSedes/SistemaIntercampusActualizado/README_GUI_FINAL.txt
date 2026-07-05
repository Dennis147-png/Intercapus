Sistema Intercampus UDLA - Interfaz grafica corregida

Comprobaciones realizadas:

1. La interfaz grafica compila junto con el proyecto.
2. Solo existe una instancia de SistemaTransporte en VentanaPrincipal.
3. Se elimino la carga doble de datos iniciales.
4. Los paneles se actualizan al cambiar de pantalla con showCard().
5. Despues de registrar, editar o eliminar datos se llama a ventana.actualizarTodos().
6. Al crear viaje, el combo de buses se recarga con datos actuales.
7. El combo de buses para viaje solo muestra buses:
   - DISPONIBLES.
   - Con chofer asignado.
   - Sin viaje pendiente o en ruta.
   - Ubicados en la primera parada de la ruta seleccionada.
8. Se comprobo por prueba que un bus nuevo valido aparece en el combo de viajes.
9. Se comprobo que un bus programado deja de aparecer como opcion.
10. El estudiante ya no puede entrar por navegacion lateral a paneles CRUD de buses o rutas.

Correcciones aplicadas:

- VentanaPrincipal.java
- PanelBuses.java
- PanelChoferes.java
- PanelLogin.java
- PanelViajes.java
- PanelEstudiante.java

Clase para ejecutar la interfaz:

sistemaintercampus.interfaz.grafica.MainGrafico
