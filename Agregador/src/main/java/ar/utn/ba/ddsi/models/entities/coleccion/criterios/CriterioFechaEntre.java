package ar.utn.ba.ddsi.models.entities.coleccion.criterios;

import java.time.LocalDate;
import ar.utn.ba.ddsi.models.entities.coleccion.Criterio;
import ar.utn.ba.ddsi.models.entities.coleccion.VerificadorDeCriterios;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;

public class CriterioFechaEntre implements Criterio {
    private LocalDate desde;
    private LocalDate hasta;

    public CriterioFechaEntre(LocalDate desde, LocalDate hasta) {
        this.desde = desde;
        this.hasta = hasta;
    }

    @Override
    public boolean cumple(Hecho hecho) {
        return VerificadorDeCriterios.entreFechas(hecho, desde, hasta);
    }
}
