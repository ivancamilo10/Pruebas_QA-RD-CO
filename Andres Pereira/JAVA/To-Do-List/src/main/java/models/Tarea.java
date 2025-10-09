package models;

public class Tarea {
    private String descriptcion;
    private boolean completada;

    public Tarea(String descriptcion) {
        this.descriptcion = descriptcion;
        this.completada = false;
    }

    public String getDescriptcoin() {
        return descriptcion;
    }

    public boolean isCompletada() {
        return completada;
    }

    public void marcaCompletada() {
        this.completada = true;
    }

    @Override
    public String toString() {
        return (completada ? "[✔]" : "[ ]" + " " + descriptcion);
    }
}
