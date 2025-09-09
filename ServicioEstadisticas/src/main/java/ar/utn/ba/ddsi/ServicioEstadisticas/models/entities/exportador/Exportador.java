package ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.exportador;

import org.springframework.stereotype.Component;

import java.io.Writer;
import java.util.List;

public interface Exportador<T> {
    void exportar(List<T> datos, String pathCSV) throws Exception; // para exportar a archivo
    void exportar(List<T> datos, Writer writer) throws Exception;    // para exportar a cualquier Writer
}


