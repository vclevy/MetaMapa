package ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteDinamica.models.entities.hecho.filtroHecho;

import ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteEstatica.models.entities.Hecho;
import ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteEstatica.models.hecho.filtroHecho.FiltroHecho;

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
