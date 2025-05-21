package ar.utn.ba.ddsi.FuenteProxy.services.adapters;

import ar.utn.ba.ddsi.FuenteProxy.models.entities.Coleccion;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.Hecho;

import java.util.List;
import java.util.Map;

public interface IApiAdapter {
    List<Hecho> obtenerHechos();
}

