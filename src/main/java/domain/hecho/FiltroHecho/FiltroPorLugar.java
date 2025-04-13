package domain.hecho.FiltroHecho;

import domain.coleccion.Lugar;
import domain.hecho.Hecho;

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