import controllers.reservaControler;
import view.reservaView;

public class Main {
    public static void main(String[] args) {
        reservaControler reserva = new reservaControler();
        reservaView view = new reservaView(reserva);
        view.mostrarMenu();
    }
}
