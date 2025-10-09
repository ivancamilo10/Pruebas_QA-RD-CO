import models.Cliente;
import org.junit.jupiter.api.Test;

import controllers.reservaControler;

import static org.junit.jupiter.api.Assertions.*;

public class ReservaControllerTest {

    @Test
    public void testCrearYCancelarReserva() {
        reservaControler controller = new reservaControler();
        Cliente cliente = new Cliente("Andres", "123");

        String resultado = controller.crearReserva(cliente, 101, 3);
        assertTrue(resultado.contains("Reserva creada"));

        String cancelar = controller.cancelarReserva("123");
        assertTrue(cancelar.contains("Reserva cancelada"));
    }

    @Test
    public void testHabitacionOcupada() {
        reservaControler controller = new reservaControler();
        Cliente cliente1 = new Cliente("Juan", "101");
        Cliente cliente2 = new Cliente("Maria", "102");

        controller.crearReserva(cliente1, 101, 2);
        String resultado = controller.crearReserva(cliente2, 101, 2);

        assertEquals("La habitacion ya esta ocupada", resultado);
    }
}
