package ar.utn.ba.ddsi.models.hecho.filtroHecho;


import ar.utn.ba.ddsi.models.entities.Hecho;
import ar.utn.ba.ddsi.models.entities.Lugar;

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