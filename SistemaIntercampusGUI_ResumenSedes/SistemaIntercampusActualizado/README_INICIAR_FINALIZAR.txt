CAMBIO AGREGADO: INICIAR Y FINALIZAR VIAJE

Flujo correcto:
1. Crear viaje: el bus queda PROGRAMADO y el viaje queda PENDIENTE.
2. Iniciar viaje: el viaje queda EN RUTA y el bus queda EN RUTA.
   Para que el estudiante pueda consultar ubicación, el bus se ubica en un punto intermedio entre la primera y segunda parada.
3. Finalizar viaje: se ejecuta la simulación automática, se guardan registros de estudiantes que suben/bajan, se calcula hora de llegada y el bus vuelve a DISPONIBLE.

No se usa movimiento real por temporizador ni hilos para mantener el nivel de Programación II.
