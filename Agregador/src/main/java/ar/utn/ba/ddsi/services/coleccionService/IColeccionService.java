package ar.utn.ba.ddsi.services.coleccionService;

import ar.utn.ba.ddsi.models.dtos.input.ColeccionInputDTO;
import ar.utn.ba.ddsi.models.dtos.output.ColeccionOutputDTO;
import ar.utn.ba.ddsi.models.entities.coleccion.Coleccion;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;

import java.util.List;
import java.util.UUID;

public interface IColeccionService {
    public void delete(String unHandle);
    public Coleccion findByHandle (String unHandle);
    public void crear(ColeccionInputDTO unaColeccionInputDTO);
    public void refrescarColecciones();
    public List<Hecho> aplicarConsenso(String unHandle, List <Hecho> unosHechos, String algoritmoConsenso);
    public List<Hecho> aplicarMencionMultiple(String unHandle, List<Hecho> unosHechos);
    public List<Hecho> aplicarMayoriaSimple(String unHandle, List<Hecho> unosHechos);
    public List<Hecho> aplicarAbsoluto(String unHandle, List<Hecho> unosHechos);
}
