package ar.edu.utn.frba.dds.PrimeraEntrega;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import domain.coleccion.Coleccion;
import domain.hecho.Hecho;
import domain.users.Administrador;
import domain.utils.importador.Config;
import domain.utils.importador.ImportadorCSV;
import org.junit.jupiter.api.Test;

public class Escenario2 {
    @Test
    public void importarHechosDesdeCSVTest() {
        Administrador admin = new Administrador();
        admin.elegirImportador(new ImportadorCSV());

        Coleccion coleccion = admin.importarHechos();

        // Verificar si la colección está vacía
        assertFalse(coleccion.getHechos().isEmpty(), "La colección no debería estar vacía después de importar.");

        // Imprimimos el primer hecho importado
        Hecho primerHecho = coleccion.getHechos().values().iterator().next();
        System.out.println("Primer Hecho Importado: ");
        System.out.println("Título: " + primerHecho.getTitulo());
        System.out.println("Descripción: " + primerHecho.getDescripcion());
        System.out.println("Fecha: " + primerHecho.getFechaDeAcontecimiento());
    }



    @Test
    public void testConfigCargado() {
        String ruta = Config.get("ruta.archivo.hechos");
        System.out.println("Ruta del CSV desde config: " + ruta);
        assertNotNull(ruta);
    }
}

