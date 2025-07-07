package ar.utn.ba.ddsi.services.coleccionService;

import ar.utn.ba.ddsi.models.dtos.input.colecciones.ColeccionInputDTO;
import ar.utn.ba.ddsi.models.dtos.input.colecciones.ColeccionPatchDTO;
import ar.utn.ba.ddsi.models.dtos.input.fuentesDeHechos.FuenteCreateDTO;
import ar.utn.ba.ddsi.models.entities.coleccion.Coleccion;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.services.coleccionService.algoritmoConsenso.IAlgoritmo;
import ar.utn.ba.ddsi.services.modosDeNavegacion.ModoDeNavegacion;

import java.util.List;

public interface IColeccionService {
    public boolean delete(String unHandle);
    public Coleccion findByHandle (String unHandle);
    public Coleccion crear(ColeccionInputDTO unaColeccionInputDTO);
    public void refrescarColecciones();
    public List<Coleccion> findAll();
    public void modificarAtributo(String unHandle, ColeccionPatchDTO patch);
    public void eliminarUnaFuenteDeUnaColeccion(String unHandle, Long Id);
    public void agregarUnaFuenteDeUnaColeccion(String unHandle, FuenteCreateDTO fuenteDTO);
    public List<Hecho> obtenerHechosDeColeccionSegunModoDeNavegacion(String unHandle, ModoDeNavegacion modoDeNavegacion);
}
