package ar.utn.ba.ddsi.services.fuentes;

import java.util.List;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;

public interface FuenteDeHechos {
    List<Hecho> obtenerHechos();
}
