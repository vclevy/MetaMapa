package ar.utn.ba.ddsi.services.hechoService;

import ar.utn.ba.ddsi.models.dtos.input.hecho.HechoInputDTO;
import ar.utn.ba.ddsi.models.dtos.input.hecho.HechoInputPUTDTO;
import ar.utn.ba.ddsi.models.dtos.output.HechoOutputDTO;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;

import java.util.List;

public interface IHechoService {
    public void registrarHechoDesdeFuente(Hecho unHecho);
    public List<HechoOutputDTO> obtenerHechos();
    public HechoOutputDTO modificarHecho(Long idHecho, HechoInputPUTDTO hechoInputDTO);
    public void validarModificacion(Hecho hechoAModificar, HechoInputPUTDTO hechoInputDTO);
}
