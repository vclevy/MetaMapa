package ar.utn.ba.ddsi.FuenteProxy.services.adapters;

import ar.utn.ba.ddsi.FuenteProxy.models.dtos.HechoProxyDTO;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.Hecho;

public interface IHechoAdapter {
    Hecho adaptar(HechoProxyDTO dto);
}
