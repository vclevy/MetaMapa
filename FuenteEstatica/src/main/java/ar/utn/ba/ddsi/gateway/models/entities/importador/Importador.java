package ar.utn.ba.ddsi.gateway.models.entities.importador;

import ar.utn.ba.ddsi.gateway.models.dtos.HechoInputDTO;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.util.List;

public interface Importador {
    List<HechoInputDTO> importarHechos(String archivo) throws CsvValidationException, IOException;
}
