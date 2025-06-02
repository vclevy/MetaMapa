package ar.utn.ba.ddsi.models.entities.importador; //Justificaciones de diseño: Patron Strategy e inyector de dependencias


import ar.utn.ba.ddsi.models.dtos.HechoInputDTO;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.util.List;

public interface Importador {
    public List<HechoInputDTO> importarHechos(String archivo) throws CsvValidationException, IOException;
}
