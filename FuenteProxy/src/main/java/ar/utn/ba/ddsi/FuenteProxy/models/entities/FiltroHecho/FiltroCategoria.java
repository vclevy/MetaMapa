package ar.utn.ba.ddsi.FuenteProxy.models.entities.FiltroHecho;

import ar.utn.ba.ddsi.FuenteProxy.models.entities.hecho.Hecho;

public class FiltroCategoria implements IFiltroHecho {

    private final String categoriaBuscada;

    public FiltroCategoria(String categoriaBuscada) {
        this.categoriaBuscada = categoriaBuscada;
    }

    @Override
    public boolean aplica(Hecho hecho) {
        if (hecho.getCategoria() == null || hecho.getCategoria().getNombre() == null) {
            return false;
        }
        return hecho.getCategoria().getNombre().equalsIgnoreCase(categoriaBuscada);
    }
}
