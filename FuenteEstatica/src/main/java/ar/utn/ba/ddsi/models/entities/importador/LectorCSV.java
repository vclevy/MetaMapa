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
            // 📂 Si es una ruta en el sistema de archivos
            inputStream = new FileInputStream(file);
        } else {
            // 📦 Si está en resources
            inputStream = getClass().getClassLoader().getResourceAsStream(archivo);
            if (inputStream == null) {
                throw new FileNotFoundException("No se encontró el archivo ni en el sistema ni en resources: " + archivo);
            }
        }

        List<String[]> filas = new ArrayList<>();
        try (CSVReader reader = new CSVReaderBuilder(new InputStreamReader(inputStream))
                .withCSVParser(new CSVParserBuilder()
                        .withSeparator(',') // 👈 IMPORTANTE
                        .build())
                .build()) {

            String[] campos;
            boolean primeraLinea = true;

            while ((campos = reader.readNext()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue; // salteo cabecera
                }
                filas.add(campos);
            }
        }
        return filas;
    }
}

