package ar.utn.ba.ddsi.services;

import ar.utn.ba.ddsi.models.dtos.HechoOutputDTO;
import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import java.util.List;

public interface IHechosServices  {
    public void importarHechos(List<String> unosArchivos) throws IOException, CsvValidationException;
    public List<HechoOutputDTO> getHechos();
}
