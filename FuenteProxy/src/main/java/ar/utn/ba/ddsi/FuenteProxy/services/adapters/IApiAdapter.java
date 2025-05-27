package ar.utn.ba.ddsi.FuenteProxy.services.adapters;

import ar.utn.ba.ddsi.FuenteProxy.models.entities.Hecho;
import java.util.List;

public interface IApiAdapter {
    List<Hecho> obtenerHechos();
}

