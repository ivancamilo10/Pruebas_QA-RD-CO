import controllers.personaControler;
import models.personaModel;
import view.personaView;

public class Main {
    
    public static void main(String[] args) {

        personaControler controler = new personaControler();
        personaView view = new personaView();

        controler.agregarPersonas(new personaModel("Andres", 18, "Barranqilla"));
        controler.agregarPersonas(new personaModel("yaseth", 15, "Cartagena"));
        controler.agregarPersonas(new personaModel("wescolt", 25, "Medellin"));

        view.mostrarPesonas(controler.ListarPesonas());

        personaModel encontrada = controler.buscarPersona("yaseth");
        if (encontrada != null) {
            view.mostrarMensajes("\nPersona encontrada " + encontrada);
        } else {
            view.mostrarMensajes("\nPersona no encontrada");
        }

        boolean eliminado = controler.eliminarPersona("yaseth");
        view.mostrarMensajes("Persona eliminada " + eliminado);

        view.mostrarPesonas(controler.ListarPesonas());

    }

}
