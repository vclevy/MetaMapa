package ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteEstatica.models.fuentes;

import ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteEstatica.models.entities.Hecho;
import com.opencsv.exceptions.CsvValidationException;
import ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteEstatica.models.importador.Config;
import ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteEstatica.models.importador.Importador;
import ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteEstatica.models.importador.ImportadorCSV;
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
