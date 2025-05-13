package ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteEstatica.models.hecho.filtroHecho;

import ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteEstatica.models.entities.Lugar;
import ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteEstatica.models.entities.Hecho;

public class FiltroPorLugar implements FiltroHecho {
    private Lugar lugar;

    public FiltroPorLugar(Lugar lugar) {
        this.lugar = lugar;
    }

    @Override
    public boolean aplica(Hecho hecho) {
        return hecho.getLugar().equals(lugar);
    }
}