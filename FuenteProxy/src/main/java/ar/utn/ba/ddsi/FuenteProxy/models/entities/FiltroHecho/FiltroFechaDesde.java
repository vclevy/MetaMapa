package ar.utn.ba.ddsi.FuenteProxy.models.entities.FiltroHecho;

import ar.utn.ba.ddsi.FuenteProxy.models.entities.hecho.Hecho;
import java.time.LocalDateTime;

public class FiltroFechaDesde implements IFiltroHecho {

    private final LocalDateTime fechaLimite;

    public FiltroFechaDesde(LocalDateTime fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    @Override
    public boolean aplica(Hecho hecho) {
        return hecho.getFechaDeAcontecimiento() != null && !hecho.getFechaDeAcontecimiento().isBefore(fechaLimite);
    }

}
