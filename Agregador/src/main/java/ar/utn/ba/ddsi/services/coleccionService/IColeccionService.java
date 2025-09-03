package ar.utn.ba.ddsi.services.coleccionService;

import ar.utn.ba.ddsi.models.dtos.input.colecciones.ColeccionInputDTO;
import ar.utn.ba.ddsi.models.dtos.input.colecciones.ColeccionPatchDTO;
import ar.utn.ba.ddsi.models.dtos.input.fuentesDeHechos.FuenteCreateDTO;
import ar.utn.ba.ddsi.models.dtos.output.ColeccionOutputDTO;
import ar.utn.ba.ddsi.models.dtos.output.FuenteDeHechoOutputDTO;
import ar.utn.ba.ddsi.models.entities.coleccion.Coleccion;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import java.util.List;

public interface IColeccionService {
    boolean delete(Long id);
    Coleccion findById(Long id);
    ColeccionOutputDTO crear(ColeccionInputDTO unaColeccionInputDTO);
    void refrescarColecciones();
    List<ColeccionOutputDTO> findAll();
    ColeccionOutputDTO modificarAtributo(Long id, ColeccionPatchDTO patch);
    ColeccionOutputDTO eliminarUnaFuenteDeUnaColeccion(Long id, String handleFuente);
    ColeccionOutputDTO agregarUnaFuenteDeUnaColeccion(Long id, FuenteCreateDTO fuenteDTO);
    List<Hecho> obtenerHechosDeColeccionSegunModoDeNavegacion(Long id, String modoDeNavegacion);
    void aplicarAlgoritmosAColecciones();
    List<FuenteDeHechoOutputDTO> obtenerFuentesDeUnaColeccion(Long id);
    void refrescarColeccion(Coleccion unaColeccion);
}
