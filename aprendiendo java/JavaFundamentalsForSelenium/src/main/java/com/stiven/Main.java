package com.stiven;

public class Main {
    public static void main(String[] args) {
        Mensaje.mostrar("Bienvenido al gestor de usuarios");

        Usuario u1 = new Usuario("Stiven", 28);
        Empleado e1 = new Empleado("Laura", 32, "QA Engineer", 55000.0);

        u1.mostrarInfo();
        e1.mostrarInfo();

        Mensaje.mostrar("Fin del programa");
    }
}