package ar.utn.ba.ddsi.FuenteProxy.models.entities.FiltroHecho;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.Hecho;

import java.time.LocalDateTime;

public class FiltroFechaAcontecimientoHasta implements IFiltroHecho {

    private final LocalDateTime fechaHasta;

    public FiltroFechaAcontecimientoHasta(LocalDateTime fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    @Override
    public boolean aplica(Hecho hecho) {
        return hecho.getFechaHecho() != null && !hecho.getFechaHecho().isAfter(fechaHasta);
    }
}

