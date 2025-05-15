package ar.utn.ba.ddsi.models.fuentes;


import ar.utn.ba.ddsi.models.entities.Hecho;
import ar.utn.ba.ddsi.models.importador.Config;
import ar.utn.ba.ddsi.models.importador.Importador;
import ar.utn.ba.ddsi.models.importador.ImportadorCSV;
import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FuenteEstatica implements Fuente{

    private String fuenteArchivo;

    Importador importadorCSV = new ImportadorCSV();


    public List<Hecho> importarHechos() {
        String fuenteArchivo = Config.get("ruta.archivo.hechos");
        List<Hecho> hechos = new ArrayList<>();

        try {
            hechos = importadorCSV.importarHechos(fuenteArchivo);
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return hechos;
    }


}
