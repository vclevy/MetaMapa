package ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteEstatica.models.importador;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LectorCSV {
    public List<String[]> leerCSV(String archivo) throws IOException, CsvValidationException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(archivo);

        if (inputStream == null) {
            throw new FileNotFoundException("No se encontró el archivo en resources: " + archivo);
        }

        List<String[]> filas = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {
            String[] campos;
            boolean primeraLinea = true;

            while ((campos = reader.readNext()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }
                filas.add(campos);
            }
        }
        return filas;
    }
}
