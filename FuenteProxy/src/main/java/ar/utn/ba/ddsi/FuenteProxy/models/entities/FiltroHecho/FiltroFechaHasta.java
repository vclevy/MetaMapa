package ar.utn.ba.ddsi.FuenteProxy.models.entities.FiltroHecho;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.hecho.Hecho;
import java.time.LocalDateTime;


public class FiltroFechaHasta implements IFiltroHecho {

    private final LocalDateTime fechaLimite;

    public FiltroFechaHasta(LocalDateTime fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    @Override
    public boolean aplica(Hecho hecho) {
        return hecho.getFechaDeAcontecimiento() != null && !hecho.getFechaDeAcontecimiento().isAfter(fechaLimite);
    }
}
