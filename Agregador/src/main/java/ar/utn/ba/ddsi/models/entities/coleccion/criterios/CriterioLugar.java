package ar.utn.ba.ddsi.models.entities.coleccion.criterios;

import ar.utn.ba.ddsi.models.entities.coleccion.Criterio;
import ar.utn.ba.ddsi.models.entities.coleccion.VerificadorDeCriterios;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.hecho.Lugar;

public class CriterioLugar implements Criterio {
    private Lugar lugar;

    public CriterioLugar(Lugar lugar) {
        this.lugar = lugar;
    }

    @Override
    public boolean cumple(Hecho hecho) {
        return VerificadorDeCriterios.cumpleLugar(hecho, lugar);
    }
}
