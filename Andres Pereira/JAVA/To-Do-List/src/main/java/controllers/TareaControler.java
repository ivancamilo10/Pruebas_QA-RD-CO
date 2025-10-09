package controllers;

import java.util.ArrayList;
import java.util.List;
import models.Tarea;

public class TareaControler {

    private List<Tarea> tareas = new ArrayList<>();

    public void agregarTarea(String descripcion) {
        tareas.add(new Tarea(descripcion));
        System.out.println("Tarea agregada" + descripcion);
    }

    public void listaTarea() {
        if (tareas.isEmpty()) {
            System.out.println("No hay tareas registradas");
            return;
        }
        System.out.println("Lista de tareas");
        for (int i = 0; i < tareas.size(); i++) {
            System.out.println((i + 1) + "." + tareas.get(i));
        }
    }

    public void marcaCompletada(int indice) {
        if (indice < 1 || indice > tareas.size()) {
            System.out.println("Indice invalido");
            return;
        }

        tareas.get(indice - 1).marcaCompletada();
        System.out.println("Tarea marcada como completada");
    }

    public void eliminarTarea(int indice) {
        if (indice < 1 || indice > tareas.size()) {
            System.out.println("Indice invalido");
            return;
        }

        Tarea eliminada = tareas.remove(indice - 1);
        System.out.println("Tarea eliminada" + eliminada.getDescriptcoin());
    }
}
