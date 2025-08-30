package ar.utn.ba.ddsi.models.entities.importador;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LectorCSV {

    public List<String[]> leerCSV(String archivo) throws IOException, CsvValidationException {
        InputStream inputStream;

        File file = new File(archivo);
        if (file.exists()) {
            inputStream = new FileInputStream(file);
        } else {
            inputStream = getClass().getClassLoader().getResourceAsStream(archivo);
            if (inputStream == null) {
                throw new FileNotFoundException("No se encontró el archivo ni en el sistema ni en resources: " + archivo);
            }
        }

        List<String[]> filas = new ArrayList<>();
        try (CSVReader reader = new CSVReaderBuilder(new InputStreamReader(inputStream))
                .withCSVParser(new CSVParserBuilder()
                        .withSeparator(',')
                        .build())
                .build()) {

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

