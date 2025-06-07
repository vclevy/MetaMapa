package ar.utn.ba.ddsi.services.coleccionService;

import ar.utn.ba.ddsi.models.dtos.input.ColeccionInputDTO;
import ar.utn.ba.ddsi.models.dtos.output.ColeccionOutputDTO;
import ar.utn.ba.ddsi.models.entities.coleccion.Coleccion;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;

import java.util.List;

public interface IColeccionService {
    public void delete(String unHandle);
    public Coleccion findByHandle (String unHandle);
    public void crear(ColeccionInputDTO unaColeccionInputDTO);
    public void refrescarColecciones();
}
