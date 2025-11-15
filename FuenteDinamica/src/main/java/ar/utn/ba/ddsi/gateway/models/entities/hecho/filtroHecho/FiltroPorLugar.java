package ar.utn.ba.ddsi.gateway.models.entities.hecho.filtroHecho;

import ar.utn.ba.ddsi.gateway.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.gateway.models.entities.hecho.Lugar;

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