package ar.utn.ba.ddsi.gateway.models.entities.coleccion.criterios;

import java.time.LocalDateTime;

import ar.utn.ba.ddsi.gateway.models.entities.coleccion.Criterio;
import ar.utn.ba.ddsi.gateway.models.entities.coleccion.VerificadorDeCriterios;
import ar.utn.ba.ddsi.gateway.models.entities.hecho.Hecho;

public class CriterioFechaEntre implements Criterio {
    private LocalDateTime desde;
    private LocalDateTime hasta;

    public CriterioFechaEntre(LocalDateTime desde, LocalDateTime hasta) {
        this.desde = desde;
        this.hasta = hasta;
    }

    @Override
    public boolean cumple(Hecho hecho) {
        return VerificadorDeCriterios.entreFechas(hecho, desde, hasta);
    }
}
