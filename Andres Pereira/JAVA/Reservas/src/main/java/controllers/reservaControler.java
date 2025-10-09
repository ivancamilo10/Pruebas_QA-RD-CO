package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Cliente;
import models.Habitacion;
import models.Reserva;

public class reservaControler {
    private List<Habitacion> habitacions = new ArrayList<>();
    private List<Reserva> reserva = new ArrayList<>();

    public reservaControler() {
        habitacions.add(new Habitacion(100, "Sencilla"));
        habitacions.add(new Habitacion(101, "Doble"));
        habitacions.add(new Habitacion(102, "Suite"));
    }

    public List<Habitacion> listaHabitacions() {
        return habitacions;
    }

    public String crearReserva(Cliente cliente, int numeroHabitacion, int noche) {
        for (Habitacion h : habitacions) {
            if (h.getNumero() == numeroHabitacion) {
                if (!h.isDisponible()) {
                    return "La habitacion ya esta ocupada";
                }
                h.setDisponible(false);
                Reserva reservas = new Reserva(cliente, h, noche);
                reserva.add(reservas);
                return "Reserva creada" + reservas;
            }
        }
        return "Habitacion no encontrada";
    }

    public String cancelarReserva(String documentoCliente) {
        for (Reserva r : reserva) {
            if (r.getCliente().getDocumento().equals(documentoCliente)) {
                r.geHabitacion().setDisponible(true);
                reserva.remove(r);
                return "Reserva cancelada " + r.getCliente().getNombre();
            }
        }
        return "No se encontro reserva de este cliente";
    }

    public List<Reserva> ListaReserva() {
        return reserva;
    }
}
