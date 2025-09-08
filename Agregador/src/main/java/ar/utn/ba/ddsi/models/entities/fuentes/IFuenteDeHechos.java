package ar.utn.ba.ddsi.models.entities.fuentes;

import java.util.List;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;

public interface IFuenteDeHechos {
    List<Hecho> obtenerHechos();
}
