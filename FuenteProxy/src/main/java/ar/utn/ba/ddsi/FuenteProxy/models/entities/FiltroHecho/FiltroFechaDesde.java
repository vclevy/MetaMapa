package ar.utn.ba.ddsi.FuenteProxy.models.entities.FiltroHecho;

import ar.utn.ba.ddsi.FuenteProxy.models.entities.Hecho;

import java.time.LocalDateTime;

public class FiltroFechaDesde implements IFiltroHecho {

    private final LocalDateTime fechaLimite;

    public FiltroFechaDesde(LocalDateTime fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    @Override
    public boolean aplica(Hecho hecho) {
        return hecho.getFechaHecho() != null && !hecho.getFechaHecho().isBefore(fechaLimite);
    }

}
