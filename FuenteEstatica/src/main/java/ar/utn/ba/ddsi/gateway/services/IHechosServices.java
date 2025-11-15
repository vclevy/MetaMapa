package ar.utn.ba.ddsi.gateway.services;

import ar.utn.ba.ddsi.gateway.models.dtos.HechoOutputDTO;
import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import java.util.List;

public interface IHechosServices  {
    void importarHechos(List<String> unosArchivos) throws IOException, CsvValidationException;
    List<HechoOutputDTO> getHechos();
}
