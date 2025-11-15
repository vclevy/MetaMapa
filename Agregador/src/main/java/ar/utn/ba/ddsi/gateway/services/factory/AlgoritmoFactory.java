package ar.utn.ba.ddsi.gateway.services.factory;

import ar.utn.ba.ddsi.gateway.services.coleccionService.algoritmoConsenso.*;
import org.springframework.stereotype.Component;

@Component
public class AlgoritmoFactory {

    private final AlgoritmoAbsoluta algoritmoAbsoluta;
    private final AlgoritmoMayoriaSimple algoritmoMayoriaSimple;
    private final AlgoritmoMultipleMenciones algoritmoMultipleMenciones;

    public AlgoritmoFactory(
            AlgoritmoAbsoluta algoritmoAbsoluta,
            AlgoritmoMayoriaSimple algoritmoMayoriaSimple,
            AlgoritmoMultipleMenciones algoritmoMultiplesMenciones
    ) {
        this.algoritmoAbsoluta = algoritmoAbsoluta;
        this.algoritmoMayoriaSimple = algoritmoMayoriaSimple;
        this.algoritmoMultipleMenciones = algoritmoMultiplesMenciones;
    }

    public IAlgoritmo crear(AlgoritmoDeConsenso unAlgoritmoDeConsenso) {
        if (unAlgoritmoDeConsenso == null) {
            return null;
        }

        switch (unAlgoritmoDeConsenso) {
            case ABSOLUTA:
                return algoritmoAbsoluta;
            case MAYORIA_SIMPLE:
                return algoritmoMayoriaSimple;
            case MULTIPLES_MENCIONES:
                return algoritmoMultipleMenciones;
            default:
                return null;
        }
    }
}

