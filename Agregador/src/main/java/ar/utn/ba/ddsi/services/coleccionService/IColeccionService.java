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
    public boolean delete(String unHandle);
    public Coleccion findByHandle (String unHandle);
    public ColeccionOutputDTO crear(ColeccionInputDTO unaColeccionInputDTO);
    public void refrescarColecciones();
    public List<ColeccionOutputDTO> findAll();
    public ColeccionOutputDTO modificarAtributo(String unHandle, ColeccionPatchDTO patch);
    public ColeccionOutputDTO eliminarUnaFuenteDeUnaColeccion(String handleColeccion, String handleFuente);
    public ColeccionOutputDTO agregarUnaFuenteDeUnaColeccion(String unHandle, FuenteCreateDTO fuenteDTO);
    public List<Hecho> obtenerHechosDeColeccionSegunModoDeNavegacion(String unHandle, String modoDeNavegacion);
    public void aplicarAlgoritmosAColecciones();
    public List<FuenteDeHechoOutputDTO> obtenerFuentesDeUnaColeccion(String unHandle);
    public void refrescarColeccion(Coleccion unaColeccion);
}
