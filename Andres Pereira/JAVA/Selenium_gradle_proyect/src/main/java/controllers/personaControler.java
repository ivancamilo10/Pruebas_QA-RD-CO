package controllers;

import java.util.ArrayList;
import models.personaModel;

public class personaControler {
    private ArrayList<personaModel> Personas = new ArrayList<>();

    public void agregarPersonas(personaModel persona) {
        Personas.add(persona);
    }

    public ArrayList<personaModel> ListarPesonas() {
        return Personas;
    }

    public personaModel buscarPersona(String nombre) {
        for (personaModel p : Personas) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return p;
            }
        }
        return null;
    }

    public boolean eliminarPersona(String nombre) {
        personaModel encontrada = buscarPersona(nombre);
        if (encontrada != null) {
            Personas.remove(encontrada);
            return true;
        }
        return false;
    }
}