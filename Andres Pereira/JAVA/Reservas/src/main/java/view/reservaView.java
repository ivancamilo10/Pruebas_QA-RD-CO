package view;

import controllers.reservaControler;
import models.Cliente;
import java.util.Scanner;

public class reservaView {

    private reservaControler controler;
    private Scanner scanner;

    public reservaView(reservaControler controler) {
        this.controler = controler;
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        int opcion;

        do {
            System.out.println("\n=== SISTEMA DE RESERVAS ===");
            System.out.println("1. Listar habitaciones");
            System.out.println("2. Crear reserva");
            System.out.println("3. Cancelar reserva");
            System.out.println("4. Ver reservas");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> controler.listaHabitacions().forEach(System.out::println);
                case 2 -> {
                    System.out.print("Nombre del cliente: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Documento del cliente: ");
                    String documento = scanner.nextLine();
                    System.out.print("Número de habitación: ");
                    int numero = scanner.nextInt();
                    System.out.print("Número de noches: ");
                    int noches = scanner.nextInt();
                    scanner.nextLine(); // limpiar buffer

                    Cliente cliente = new Cliente(nombre, documento);
                    String resultado = controler.crearReserva(cliente, numero, noches);
                    System.out.println(resultado);
                }
                case 3 -> {
                    System.out.println("Documento del cliente");
                    String documento = scanner.nextLine();
                    String resultado = controler.cancelarReserva(documento);
                    System.out.println(resultado);
                }
                case 4 -> controler.ListaReserva().forEach(System.out::println);
                case 0 -> System.out.println("Saliendo");
                default -> System.out.println("Opcion no valida");

            }

        } while (opcion != 0);
    }
}
