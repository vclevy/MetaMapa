package ar.utn.ba.ddsi.models.entities.hecho.filtroHecho;

import ar.utn.ba.ddsi.models.entities.hecho.Categoria;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;

public class FiltroPorCategoria implements FiltroHecho {
    private Categoria categoria;

    public FiltroPorCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public boolean aplica(Hecho hecho) {
        return hecho.getCategoria().equals(categoria);
    }
}
