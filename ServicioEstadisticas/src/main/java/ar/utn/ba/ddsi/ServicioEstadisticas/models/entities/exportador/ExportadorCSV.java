package ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.exportador;

import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.resultadosEstadisticas.ResultadoEstadistica;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.time.format.DateTimeFormatter;
import java.util.List;
@Data
@Component
public class ExportadorCSV implements Exportador<ResultadoEstadistica> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void exportar(List<ResultadoEstadistica> datos, String pathCSV) throws Exception {
        try (Writer writer = new FileWriter(pathCSV)) {
            exportar(datos, writer);
        }
    }

    @Override
    public void exportar(List<ResultadoEstadistica> datos, Writer writer) throws Exception {
        // Escribimos cabecera
        writer.write("Nombre Estadistica,Clave,Valor,Fecha Generacion,Nombre de la Coleccion\n");

        // Escribimos cada fila
        for (ResultadoEstadistica r : datos) {
            writer.write(String.format("%s,%s,%s,%s,%s\n",
                    r.getNombreEstadistica(),
                    r.getClave(),
                    r.getValor(),
                    r.getFechaGeneracion().format(FORMATTER),
                    r.getNombreDeLaColeccion()
            ));
        }
        writer.flush();
    }
}
