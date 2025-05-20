package ar.utn.ba.ddsi.models.hecho.filtroHecho;


import ar.utn.ba.ddsi.models.entities.Hecho;
import ar.utn.ba.ddsi.models.hecho.Categoria;

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
