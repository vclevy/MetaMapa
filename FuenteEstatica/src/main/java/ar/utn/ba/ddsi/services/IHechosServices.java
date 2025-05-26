package ar.utn.ba.ddsi.services;

import ar.utn.ba.ddsi.models.dtos.HechoOutputDTO;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.util.List;

public interface IHechosServices  {
    public void importarHechos(String unRutaAlArchivo) throws IOException, CsvValidationException;
    public List<HechoOutputDTO> getHechos();
}
