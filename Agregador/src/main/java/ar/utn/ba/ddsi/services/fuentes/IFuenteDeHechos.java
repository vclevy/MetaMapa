package ar.utn.ba.ddsi.services.fuentes;

import java.util.List;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.hecho.OrigenDelHecho;

public interface IFuenteDeHechos {
    List<Hecho> obtenerHechos();
    Long getId();
    TipoDeFuente getTipoDeFuente();
    void setId(Long id);
}
