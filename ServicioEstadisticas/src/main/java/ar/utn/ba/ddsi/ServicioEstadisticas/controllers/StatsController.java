package ar.utn.ba.ddsi.ServicioEstadisticas.controllers;

import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.Coleccion;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.exportador.Exportador;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.exportador.ExportadorCSV;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.ResultadoEstadistica;
import ar.utn.ba.ddsi.ServicioEstadisticas.services.impl.ColeccionService;
import ar.utn.ba.ddsi.ServicioEstadisticas.services.impl.StatsServices;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.File;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
@RestController
@RequestMapping("/estadisticas")
public class StatsController {
    @Autowired
    ColeccionService coleccionService;
    @Autowired
    StatsServices statsServices;
    @Autowired
    private final Exportador<ResultadoEstadistica> exportadorCSV;

    @GetMapping("/todas") // no lo hace como csv
    public List<ResultadoEstadistica> todas() throws Exception {
        List<Coleccion> colecciones = coleccionService.obtenerColecciones();
        return statsServices.calcularTodas(colecciones);
    }

    @GetMapping("/exportacion/csv")
    public ResponseEntity<String> exportarCSV() throws Exception {
        List<Coleccion> colecciones = coleccionService.obtenerColecciones();
        List<ResultadoEstadistica> resultados = statsServices.calcularTodas(colecciones);
        Exportador<ResultadoEstadistica> exportador = new ExportadorCSV();

        String proyectoPath = new File("").getAbsolutePath();
        Path rutaCarpeta = Paths.get(proyectoPath, "ServicioEstadisticas", "archivosCSV");

        if (!Files.exists(rutaCarpeta)) {
            Files.createDirectories(rutaCarpeta);
        }

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String nombreArchivo = "estadisticas_" + timestamp + ".csv";

        Path rutaArchivo = rutaCarpeta.resolve(nombreArchivo);

        try (Writer writer = Files.newBufferedWriter(rutaArchivo, StandardCharsets.UTF_8)) {
            exportador.exportar(resultados, writer);
            writer.flush();
        }

        return ResponseEntity.ok("Archivo CSV generado en: " + rutaArchivo.toAbsolutePath());
    }
}