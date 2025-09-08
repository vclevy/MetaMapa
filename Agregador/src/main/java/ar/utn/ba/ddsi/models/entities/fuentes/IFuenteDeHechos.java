package ar.utn.ba.ddsi.models.entities.fuentes;

import java.util.List;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import reactor.core.publisher.Mono;

public interface IFuenteDeHechos {
    List<Hecho> obtenerHechos();
}
