package ar.utn.ba.ddsi.gateway.FuenteProxy.services.adapters;

import ar.utn.ba.ddsi.gateway.FuenteProxy.models.entities.hecho.Hecho;
import java.util.List;

public interface IApiAdapter {
    List<Hecho> obtenerHechos();
}

