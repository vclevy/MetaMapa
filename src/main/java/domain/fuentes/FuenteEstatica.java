package domain.fuentes;

import com.opencsv.exceptions.CsvValidationException;
import domain.coleccion.Coleccion;
import domain.utils.importador.Config;
import domain.utils.importador.Importador;
import domain.utils.importador.ImportadorCSV;
import java.io.IOException;

public class FuenteEstatica implements Fuente{

    Importador importadorCSV = new ImportadorCSV();

    public Coleccion importarHechos() {
        Coleccion coleccion = new Coleccion();
        String archivo = Config.get("ruta.archivo.hechos");
        try {
            importadorCSV.importarHechos(archivo, coleccion);
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return coleccion;
    }

}
