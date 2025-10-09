package view;

import controllers.TareaControler;
import java.util.Scanner;

public class TareaView {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TareaControler controller = new TareaControler();
        int opcion;

        do {
            System.out.println("\n=== MENÚ DE TAREAS ===");
            System.out.println("1. Agregar tarea");
            System.out.println("2. Ver tareas");
            System.out.println("3. Marcar tarea como completada");
            System.out.println("4. Eliminar tarea");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> {
                    System.out.print("Descripción de la tarea: ");
                    String desc = sc.nextLine();
                    controller.agregarTarea(desc);
                }
                case 2 -> controller.listaTarea();
                case 3 -> {
                    controller.listaTarea();
                    System.out.print("Número de tarea a completar: ");
                    int num = sc.nextInt();
                    controller.marcaCompletada(num);
                }
                case 4 -> {
                    controller.listaTarea();
                    System.out.print("Número de tarea a eliminar: ");
                    int num = sc.nextInt();
                    controller.eliminarTarea(num);
                }
                case 0 -> System.out.println("👋 Saliendo...");
                default -> System.out.println("❌ Opción no válida.");
            }
        } while (opcion != 0);

        sc.close();
    }
}
