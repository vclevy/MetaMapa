package ar.utn.ba.ddsi.models.entities.importador;

import ar.utn.ba.ddsi.models.dtos.HechoInputDTO;
import ar.utn.ba.ddsi.models.entities.hecho.Lugar;
import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImportadorCSV implements Importador {

    private static final LectorCSV lectorCSV = new LectorCSV();

    @Override
    public List<HechoInputDTO> importarHechos(String rutaArchivo)
            throws IOException, CsvValidationException {

        List<String[]> filas = lectorCSV.leerCSV(rutaArchivo);
        List<HechoInputDTO> hechos = new ArrayList<>();

        for (String[] campos : filas) {
            try {
                String titulo = campos[0].trim();
                String descripcion = campos[1].trim();
                String categoria = campos[2].trim();
                double latitud = Double.parseDouble(campos[3].trim());
                double longitud = Double.parseDouble(campos[4].trim());
                LocalDate fecha = LocalDate.parse(
                        campos[5].trim(),
                        DateTimeFormatter.ofPattern("dd/MM/yyyy")
                );

                HechoInputDTO unHechoInput = new HechoInputDTO(
                        titulo,
                        descripcion,
                        categoria,
                        new Lugar(latitud, longitud),
                        fecha
                );

                hechos.add(unHechoInput);

            } catch (Exception e) {
                System.err.println("Error al procesar fila del CSV: " + Arrays.toString(campos));
                e.printStackTrace();
            }
        }

        return hechos;
    }
}
