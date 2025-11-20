package ar.utn.ba.ddsi.gateway.services.coleccionService;

import ar.utn.ba.ddsi.gateway.models.dtos.input.colecciones.ColeccionInputDTO;
import ar.utn.ba.ddsi.gateway.models.dtos.input.colecciones.ColeccionPatchDTO;
import ar.utn.ba.ddsi.gateway.models.dtos.input.fuentesDeHechos.FuenteCreateDTO;
import ar.utn.ba.ddsi.gateway.models.dtos.output.ColeccionOutputDTO;
import ar.utn.ba.ddsi.gateway.models.dtos.output.FuenteDeHechoOutputDTO;
import ar.utn.ba.ddsi.gateway.models.entities.coleccion.Coleccion;
import ar.utn.ba.ddsi.gateway.models.entities.fuentes.Fuente;
import ar.utn.ba.ddsi.gateway.models.entities.hecho.Hecho;
import java.util.List;

public interface IColeccionService {
    boolean delete(Long id);
    ColeccionOutputDTO crear(ColeccionInputDTO unaColeccionInputDTO);
    void refrescarColecciones();
    List<ColeccionOutputDTO> findAll();
    ColeccionOutputDTO modificarAtributo(Long id, ColeccionPatchDTO patch);
    ColeccionOutputDTO eliminarUnaFuenteDeUnaColeccion(Long idColeccion, Long idFuente);
    ColeccionOutputDTO agregarUnaFuenteDeUnaColeccion(Long id, FuenteCreateDTO fuenteDTO);
    List<Hecho> obtenerHechosDeColeccionSegunModoDeNavegacion(Long id, String modoDeNavegacion);
    void aplicarAlgoritmosAColecciones();
    List<FuenteDeHechoOutputDTO> obtenerFuentesDeUnaColeccion(Long id);
    void refrescarColeccion(Coleccion unaColeccion, Fuente nuevaFuente);
    ColeccionOutputDTO obtenerColeccion(Long id);
    List<ColeccionOutputDTO> obtenerColeccionesDestacadas();
    void aplicarAlgoritmoDeConsenso(Coleccion unaColeccion);
    void editarColeccion(Long idColeccion, ColeccionOutputDTO coleccionOutputDTO);
    String construirClave(String titulo, String descripcion);
}
