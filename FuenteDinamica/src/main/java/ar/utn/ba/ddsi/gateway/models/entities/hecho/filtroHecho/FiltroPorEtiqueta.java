package ar.utn.ba.ddsi.gateway.models.entities.hecho.filtroHecho;

import ar.utn.ba.ddsi.gateway.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.gateway.models.entities.hecho.Etiqueta;

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