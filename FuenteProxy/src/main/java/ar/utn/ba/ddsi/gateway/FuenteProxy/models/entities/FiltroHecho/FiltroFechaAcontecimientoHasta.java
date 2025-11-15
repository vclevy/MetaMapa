package ar.utn.ba.ddsi.gateway.FuenteProxy.models.entities.FiltroHecho;

import ar.utn.ba.ddsi.gateway.FuenteProxy.models.entities.hecho.Hecho;
import java.time.LocalDateTime;

public class FiltroFechaAcontecimientoHasta implements IFiltroHecho {

    private final LocalDateTime fechaHasta;

    public FiltroFechaAcontecimientoHasta(LocalDateTime fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    @Override
    public boolean aplica(Hecho hecho) {
        return hecho.getFechaDeAcontecimiento() != null && !hecho.getFechaDeAcontecimiento().isAfter(fechaHasta);
    }
}

