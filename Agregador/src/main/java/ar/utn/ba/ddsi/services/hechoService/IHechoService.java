package ar.utn.ba.ddsi.services.hechoService;

import ar.utn.ba.ddsi.models.dtos.input.hecho.HechoInputPUTDTO;
import ar.utn.ba.ddsi.models.dtos.output.HechoFiltroDTO;
import ar.utn.ba.ddsi.models.dtos.output.HechoOutputDTO;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;

import java.util.List;

public interface IHechoService {
    void registrarHechoDesdeFuente(Hecho unHecho);
    List<HechoOutputDTO> obtenerHechos();
    HechoOutputDTO modificarHecho(Long idHecho, HechoInputPUTDTO hechoInputDTO);
    void validarModificacion(Hecho hechoAModificar, HechoInputPUTDTO hechoInputDTO);
    List<HechoOutputDTO> filtrarHechos(HechoFiltroDTO filtros);
    List<HechoOutputDTO> obtenerHechosDestacados();
    HechoOutputDTO eliminarHecho(Long idHecho);
    List<HechoOutputDTO> obtenerHechosPendientes();
    List<HechoOutputDTO> obtenerHechosVisibles();
}
