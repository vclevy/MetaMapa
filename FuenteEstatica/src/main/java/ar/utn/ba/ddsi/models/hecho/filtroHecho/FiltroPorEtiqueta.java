package ar.utn.ba.ddsi.models.hecho.filtroHecho;


import ar.utn.ba.ddsi.models.entities.Etiqueta;
import ar.utn.ba.ddsi.models.entities.Hecho;

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