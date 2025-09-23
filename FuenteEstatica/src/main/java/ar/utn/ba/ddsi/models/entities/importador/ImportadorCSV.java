package ar.utn.ba.ddsi.models.entities.importador;

import ar.utn.ba.ddsi.models.dtos.HechoInputDTO;
import ar.utn.ba.ddsi.models.entities.hecho.Lugar;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

        String nombreArchivo = new File(rutaArchivo).getName();

        for (String[] campos : filas) {
            try {
                String titulo = campos[0].trim();
                String descripcion = campos[1].trim();
                String categoria = campos[2].trim();
                double latitud = Double.parseDouble(campos[3].trim());
                double longitud = Double.parseDouble(campos[4].trim());

                String raw = campos[5].trim();
                LocalDateTime fecha;

                if (raw.length() == 10) { // formato dd/MM/yyyy
                    fecha = LocalDate.parse(raw, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                            .atStartOfDay();
                } else { // formato dd/MM/yyyy HH:mm
                    fecha = LocalDateTime.parse(raw, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                }

                HechoInputDTO unHechoInput = new HechoInputDTO(
                        titulo,
                        descripcion,
                        categoria,
                        new Lugar(latitud, longitud),
                        fecha,
                        nombreArchivo
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
