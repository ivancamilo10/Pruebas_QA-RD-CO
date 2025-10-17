package com.stiven;

// Hereda de Usuario
public class Empleado extends Usuario {
    private String cargo;
    private double salario;

    public Empleado(String nombre, int edad, String cargo, double salario) {
        super(nombre, edad); // llama al constructor de Usuario
        this.cargo = cargo;
        this.salario = salario;
    }

    public String getCargo() {
        return cargo;
    }

    public double getSalario() {
        return salario;
    }

    @Override
    public void mostrarInfo() {
        System.out.println("Empleado: " + getNombre() +
                " | Edad: " + getEdad() +
                " | Cargo: " + cargo +
                " | Salario: $" + salario);
    }
}
