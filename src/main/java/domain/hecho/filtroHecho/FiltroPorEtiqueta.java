package domain.hecho.FiltroHecho;
import domain.hecho.etiqueta.Etiqueta;


import domain.hecho.Hecho;

public class FiltroPorEtiqueta implements FiltroHecho {
    private Etiqueta etiqueta;

    public FiltroPorEtiqueta(Etiqueta etiqueta) {
        this.etiqueta = etiqueta;
    }

    @Override
    public boolean aplica(Hecho hecho) {
        return hecho.getEtiquetas().equals(etiqueta);
    }
}