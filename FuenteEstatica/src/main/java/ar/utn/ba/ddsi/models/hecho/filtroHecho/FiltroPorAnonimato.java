package ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteEstatica.models.hecho.filtroHecho;

import ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteEstatica.models.entities.Hecho;

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
