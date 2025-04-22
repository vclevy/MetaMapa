package domain.utils.importador;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import domain.coleccion.Categoria;
import domain.coleccion.Coleccion;
import domain.hecho.Hecho;
import domain.hecho.OrigenDelHecho;
import domain.hecho.lugar.Lugar;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ImportadorCSV implements Importador {
    @Override
    public void importarHechos(String archivo, Coleccion coleccion) throws IOException, CsvValidationException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(archivo);

        if (inputStream == null) {
            throw new FileNotFoundException("No se encontró el archivo en resources: " + archivo);
        }

        try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {
            String[] campos;
            boolean primeraLinea = true;

            while ((campos = reader.readNext()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }

                try {
                    String titulo = campos[0].trim();
                    String descripcion = campos[1].trim();
                    String categoria = campos[2].trim();
                    double latitud = Double.parseDouble(campos[3].trim());
                    double longitud = Double.parseDouble(campos[4].trim());
                    LocalDate fecha = LocalDate.parse(campos[5].trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                    Hecho hecho = new Hecho(
                            titulo,
                            descripcion,
                            new Categoria(categoria),
                            fecha,
                            LocalDateTime.now(),
                            new Lugar(latitud, longitud),
                            OrigenDelHecho.DATASET,
                            false
                    );

                    coleccion.agregarHecho(hecho);
                } catch (Exception e) {
                    System.err.println("Error al procesar una fila del CSV:");
                    e.printStackTrace();
                }
            }
        }
    }


}
