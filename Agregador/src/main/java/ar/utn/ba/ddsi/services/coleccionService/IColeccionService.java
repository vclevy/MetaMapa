package ar.utn.ba.ddsi.services.coleccionService;

import ar.utn.ba.ddsi.models.dtos.input.colecciones.ColeccionInputDTO;
import ar.utn.ba.ddsi.models.dtos.input.colecciones.ColeccionPatchDTO;
import ar.utn.ba.ddsi.models.dtos.input.fuentesDeHechos.FuenteCreateDTO;
import ar.utn.ba.ddsi.models.entities.coleccion.Coleccion;
import ar.utn.ba.ddsi.services.coleccionService.algoritmoConsenso.IAlgoritmo;

import java.util.List;

public interface IColeccionService {
    public void delete(String unHandle);
    public Coleccion findByHandle (String unHandle);
    public void crear(ColeccionInputDTO unaColeccionInputDTO);
    public void refrescarColecciones();
    public void modoDeNavegacion(Coleccion unaColeccion, String unModoDeNavegacion, IAlgoritmo unAlgoritmoDeConsenso);
    public List<Coleccion> findAll();
    public void modificarAtributo(String unHandle, ColeccionPatchDTO patch);
    public void eliminarUnaFuenteDeUnaColeccion(String unHandle, Long Id);
    public void agregarUnaFuenteDeUnaColeccion(String unHandle, FuenteCreateDTO fuenteDTO);
}
