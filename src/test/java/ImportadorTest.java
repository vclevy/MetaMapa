import domain.hecho.Hecho;
import domain.coleccion.Categoria;
import domain.users.Administrador;
import domain.coleccion.Coleccion;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ImportadorTest {

    Categoria categoriaHecho1 = new Categoria("Ráfagas de más de 100 km/h");
//    Categoria categoriaHecho2 =new Categoria(        "Precipitación de granizo");
    @Test
    public void testImportarDesdeArchivoExistente() throws Exception {
        // Ruta local absoluta al archivo real
        String rutaArchivo = "C:\\Users\\delfi\\OneDrive - UTN.BA\\Desktop\\desastres_naturales_argentina_limpio.csv";

        // Crear colección y administrador
        Coleccion coleccion = new Coleccion("Desastres naturales", "bienvenidos a limalaya");
        Administrador importador = new Administrador();

        // Ejecutar la importación
        importador.importarHecho(coleccion, rutaArchivo);

        // Verificar los hechos importados
        List<Hecho> hechos = coleccion.getHechos();
        assertEquals(1, hechos.size());

        // Verificación de los datos del primer hecho
//        Hecho hecho2 = hechos.get(1);
//        assertEquals("Situación crítica por Precipitación de granizo en Chilecito La Rioja", hecho2.getTitulo());
//        assertEquals(categoriaHecho2, hecho2.getCategoria());

        // Verificación del segundo hecho Ráfagas de más de 100 km/h
        Hecho hecho1 = hechos.get(0);
        assertEquals("Ráfagas de más de 100 km/h causa estragos en San Vicente Misiones", hecho1.getTitulo());
        assertEquals(categoriaHecho1, hecho1.getCategoria());
    }

}
