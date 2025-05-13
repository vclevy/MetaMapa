package ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteEstatica.models.importador;

import com.opencsv.exceptions.CsvValidationException;
import ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteEstatica.models.hecho.Categoria;
import ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteEstatica.models.entities.Hecho;
import ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteEstatica.models.hecho.OrigenDelHecho;
import ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteEstatica.models.entities.Lugar;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import java.io.IOException;
import java.util.ArrayList;

public class ImportadorCSV implements Importador {
    private LectorCSV lectorCSV = new LectorCSV();

    @Override
    public List<Hecho> importarHechos(String archivo) throws IOException, CsvValidationException {
        List<String[]> filas = lectorCSV.leerCSV(archivo);
        List<Hecho> hechosImportados = new ArrayList<>();

        for (String[] campos : filas) {
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
                hechosImportados.add(hecho);
            } catch (Exception e) {
                System.err.println("Error al procesar una fila del CSV:");
                e.printStackTrace();
            }
        }

        return hechosImportados;
    }
}
