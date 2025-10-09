package controllers;

import models.personaModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class personaControlerTest {
    private personaControler controler;

    @BeforeEach
    void setUp() {
        controler = new personaControler();
        controler.agregarPersonas(new personaModel("Andres", 19, "Barranquilla"));
        controler.agregarPersonas(new personaModel("Maria", 12, "Malambo"));
    }

    @Test
    void testAgregarPesona() {
        personaModel nueva = new personaModel("andres", 18, "Barranquilla");
        controler.agregarPersonas(nueva);
        ArrayList<personaModel> lista = controler.ListarPesonas();
        assertTrue(lista.contains(nueva));
    }
    @Test
    void testBuscarPorNombreInexistente() {
        personaModel encontrada = controler.buscarPersona("Pedro");
        assertNull(encontrada, "Debe retornar null si la persona no existe");
    }

    @Test
    void testEliminarPersona() {
        boolean eliminado = controler.eliminarPersona("Andres");
        assertTrue(eliminado, "Debe eliminar correctamente");
        assertNull(controler.buscarPersona("Andres"), "No debe poder encontrar a la persona eliminada");
    }

    @Test
    void testListarPersonas() {
        ArrayList<personaModel> lista = controler.ListarPesonas();
        assertEquals(2, lista.size(), "Debe haber dos personas al inicio");
    }
}
