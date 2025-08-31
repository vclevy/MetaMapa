package ar.utn.ba.ddsi.FuenteProxy.models.entities.FiltroHecho;

import ar.utn.ba.ddsi.FuenteProxy.models.entities.hecho.Hecho;
import java.time.LocalDateTime;

public class FiltroFechaAcontecimientoDesde implements IFiltroHecho {

    private final LocalDateTime fechaDesde;

    public FiltroFechaAcontecimientoDesde(LocalDateTime fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    @Override
    public boolean aplica(Hecho hecho) {
        return hecho.getFechaDeAcontecimiento() != null && !hecho.getFechaDeAcontecimiento().isBefore(fechaDesde);
    }
}
