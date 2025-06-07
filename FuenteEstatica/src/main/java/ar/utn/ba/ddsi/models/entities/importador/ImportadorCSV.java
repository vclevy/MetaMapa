package ar.utn.ba.ddsi.models.entities.importador;

import ar.utn.ba.ddsi.models.dtos.HechoInputDTO;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.hecho.Lugar;
import ar.utn.ba.ddsi.models.entities.hecho.OrigenDelHecho;
import com.opencsv.exceptions.CsvValidationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import java.io.IOException;
import java.util.ArrayList;

public class ImportadorCSV implements Importador {
    private static LectorCSV lectorCSV = new LectorCSV();

    public List<HechoInputDTO> importarHechos(String archivo) throws CsvValidationException, IOException {
        List<String[]> filas = lectorCSV.leerCSV(archivo);
        List<HechoInputDTO> hechosPorImportar = new ArrayList<>();

        for (String[] campos : filas) {
            try {
                String titulo = campos[0].trim();
                String descripcion = campos[1].trim();
                String categoria = campos[2].trim();
                double latitud = Double.parseDouble(campos[3].trim());
                double longitud = Double.parseDouble(campos[4].trim());
                LocalDate fecha = LocalDate.parse(campos[5].trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                HechoInputDTO unHechoInput = new HechoInputDTO(titulo, descripcion, categoria, new Lugar(latitud, longitud), fecha);
                hechosPorImportar.add(unHechoInput);
            }
            catch (Exception e) {
                System.err.println("Error al procesar una fila del CSV:");
                e.printStackTrace();
            }
        }

        return hechosPorImportar;
    }
}
