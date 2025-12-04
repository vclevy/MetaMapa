package ar.utn.ba.ddsi.gateway.FuenteProxy.models.entities.FiltroHecho;

import ar.utn.ba.ddsi.gateway.FuenteProxy.models.entities.hecho.Hecho;

public class FiltroUbicacion implements IFiltroHecho {

    private final double latitudEsperada;
    private final double longitudEsperada;

    public FiltroUbicacion(double latitudEsperada, double longitudEsperada) {
        this.latitudEsperada = latitudEsperada;
        this.longitudEsperada = longitudEsperada;
    }

    @Override
    public boolean aplica(Hecho hecho) {
        return hecho.getLugar().getLatitud() == latitudEsperada &&
                hecho.getLugar().getLongitud() == longitudEsperada;
    }
}
