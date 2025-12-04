package ar.utn.ba.ddsi.gateway.models.entities.hecho.filtroHecho;

import ar.utn.ba.ddsi.gateway.models.entities.hecho.Hecho;

public class FiltroPorAnonimato implements FiltroHecho {
    private boolean esAnonimo;

    public FiltroPorAnonimato(boolean esAnonimo) {
        this.esAnonimo = esAnonimo;
    }

    @Override
    public boolean aplica(Hecho hecho) {
        return hecho.getEsAnonimo().equals(esAnonimo);
    }
}
