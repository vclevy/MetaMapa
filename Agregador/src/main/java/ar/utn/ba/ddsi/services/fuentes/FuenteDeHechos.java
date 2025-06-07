package ar.utn.ba.ddsi.services.fuentes;

import java.util.List;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.hecho.OrigenDelHecho;

public interface FuenteDeHechos {
    List<Hecho> obtenerHechos();
}
