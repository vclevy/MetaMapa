package domain.hecho.filtroHecho;

import domain.coleccion.Categoria;
import domain.hecho.Hecho;

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
