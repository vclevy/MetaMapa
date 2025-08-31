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
    boolean delete(String unHandle);
    Coleccion findByHandle (String unHandle);
    ColeccionOutputDTO crear(ColeccionInputDTO unaColeccionInputDTO);
    void refrescarColecciones();
    List<ColeccionOutputDTO> findAll();
    ColeccionOutputDTO modificarAtributo(String unHandle, ColeccionPatchDTO patch);
    ColeccionOutputDTO eliminarUnaFuenteDeUnaColeccion(String handleColeccion, String handleFuente);
    ColeccionOutputDTO agregarUnaFuenteDeUnaColeccion(String unHandle, FuenteCreateDTO fuenteDTO);
    List<Hecho> obtenerHechosDeColeccionSegunModoDeNavegacion(String unHandle, String modoDeNavegacion);
    void aplicarAlgoritmosAColecciones();
    List<FuenteDeHechoOutputDTO> obtenerFuentesDeUnaColeccion(String unHandle);
    void refrescarColeccion(Coleccion unaColeccion);
}
