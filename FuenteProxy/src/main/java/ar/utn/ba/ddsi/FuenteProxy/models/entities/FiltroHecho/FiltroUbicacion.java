package ar.utn.ba.ddsi.FuenteProxy.models.entities.FiltroHecho;

import ar.utn.ba.ddsi.FuenteProxy.models.entities.Hecho;

public class FiltroUbicacion implements IFiltroHecho {

    private final double latitudEsperada;
    private final double longitudEsperada;

    public FiltroUbicacion(double latitudEsperada, double longitudEsperada) {
        this.latitudEsperada = latitudEsperada;
        this.longitudEsperada = longitudEsperada;
    }

    @Override
    public boolean aplica(Hecho hecho) {
        return hecho.getLatitud() == latitudEsperada &&
                hecho.getLongitud() == longitudEsperada;
    }
}
