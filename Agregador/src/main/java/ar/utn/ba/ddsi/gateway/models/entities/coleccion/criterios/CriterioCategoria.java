package ar.utn.ba.ddsi.gateway.models.entities.coleccion.criterios;

import ar.utn.ba.ddsi.gateway.models.entities.coleccion.Criterio;
import ar.utn.ba.ddsi.gateway.models.entities.coleccion.VerificadorDeCriterios;
import ar.utn.ba.ddsi.gateway.models.entities.hecho.Categoria;
import ar.utn.ba.ddsi.gateway.models.entities.hecho.Hecho;

public class CriterioCategoria implements Criterio {
    private Categoria categoria;

    public CriterioCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public boolean cumple(Hecho hecho) {
        return VerificadorDeCriterios.cumpleCategoria(hecho, categoria);
    }
}
