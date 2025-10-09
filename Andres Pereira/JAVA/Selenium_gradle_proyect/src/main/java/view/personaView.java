package view;

import models.personaModel;
import java.util.List;

public class personaView {

    public void mostrarPesonas(List<personaModel> personas) {
        if (personas.isEmpty()) {
            System.out.println("No hay personas registradas");
        } else {
            System.out.println("\n Lista de personas");
            for (personaModel p : personas) {
                System.out.println(p);
            }
        }
    }

    public void mostrarMensajes(String mensajes) {
        System.out.println(mensajes);
    }

}
