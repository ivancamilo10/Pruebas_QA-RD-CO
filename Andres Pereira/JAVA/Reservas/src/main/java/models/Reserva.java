package models;

public class Reserva {
    private Cliente cliente;
    private Habitacion habitacion;
    private int noches;

    public Reserva(Cliente cliente, Habitacion habitacion, int noches) {
        this.cliente = cliente;
        this.habitacion = habitacion;
        this.noches = noches;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Habitacion geHabitacion() {
        return habitacion;
    }

    public int getNoches() {
        return noches;
    }

    @Override
    public String toString() {
        return "Reserva de " + cliente + " en " +
                habitacion.getTipo() + " #" + habitacion.getNumero() +
                " por " + noches + " noches.";
    }
}
