import domain.hecho.Hecho;
import domain.coleccion.Categoria;
import domain.users.Administrador;
import domain.coleccion.Coleccion;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ImportadorTest {

    Categoria categoriaHecho1 = new Categoria("Ráfagas de más de 100 km/h");
    Categoria categoriaHecho2 = new Categoria("Precipitación de granizo");
    @Test
    public void testImportarDesdeArchivoExistente() throws Exception {
    // Ruta local absoluta al archivo real
    String rutaArchivo = "C:\\Users\\user\\Downloads\\desastres_naturales_argentina.csv";

        // Crear colección y administrador
        Coleccion coleccion = new Coleccion("Desastres naturales", "bienvenidos a limalaya");
        Administrador importador = new Administrador();

        // Ejecutar la importación
        importador.importarHecho(coleccion, rutaArchivo);

        // Verificar los hechos importados
        Hecho[] hechos = coleccion.getHechos().toArray(new Hecho[0]);
        System.out.print(hechos[0]);
        // Verificación del primer hecho Ráfagas de más de 100 km/h
        Hecho hecho1 = hechos[0];
        System.out.print(hecho1.getTitulo());
        assertEquals("Ráfagas de más de 100 km/h causa estragos en San Vicente Misiones", hecho1.getTitulo());
        assertEquals(categoriaHecho1, hecho1.getCategoria());


        //Verificación de los datos del segundo hecho
        Hecho hecho2 = hechos[1];
        assertEquals("Situación crítica por Precipitación de granizo en Chilecito La Rioja", hecho2.getTitulo());
        assertEquals(categoriaHecho2, hecho2.getCategoria());


    }

}
