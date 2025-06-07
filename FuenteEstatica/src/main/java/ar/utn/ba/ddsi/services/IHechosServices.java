package ar.utn.ba.ddsi.services;

import ar.utn.ba.ddsi.models.dtos.HechoInputDTO;
import ar.utn.ba.ddsi.models.dtos.HechoOutputDTO;
import ar.utn.ba.ddsi.models.entities.hecho.Categoria;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.util.List;

public interface IHechosServices  {
    public void importarHechos(List<String> unosArchivos) throws IOException, CsvValidationException;
    public List<HechoOutputDTO> getHechos();
    public Hecho procesarHechosInput(HechoInputDTO unHechoInput);
    public HechoOutputDTO hechoOutputDTO(Hecho hecho);
    public Hecho convertirHechoInputEnHecho(HechoInputDTO hechoInputDTO);
    public Long definirId();
}
