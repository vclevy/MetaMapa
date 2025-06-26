package ar.utn.ba.ddsi.services.coleccionService;

import ar.utn.ba.ddsi.models.dtos.input.ColeccionInputDTO;
import ar.utn.ba.ddsi.models.dtos.output.ColeccionOutputDTO;
import ar.utn.ba.ddsi.models.entities.coleccion.Coleccion;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.services.coleccionService.algoritmoConsenso.IAlgoritmo;
import ar.utn.ba.ddsi.services.fuentes.FuenteDeHechos;

import java.util.List;
import java.util.UUID;

public interface IColeccionService {
    public void delete(String unHandle);
    public Coleccion findByHandle (String unHandle);
    public void crear(ColeccionInputDTO unaColeccionInputDTO);
    public void refrescarColecciones();
    public void modoDeNavegacion(Coleccion unaColeccion, String unModoDeNavegacion, IAlgoritmo unAlgoritmoDeConsenso);
    public List<Coleccion> findAll();
}
