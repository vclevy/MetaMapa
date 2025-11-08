package ar.utn.ba.ddsi.services.factory;

import ar.utn.ba.ddsi.services.coleccionService.algoritmoConsenso.*;
import org.springframework.stereotype.Component;

@Component
public class AlgoritmoFactory {
    public IAlgoritmo crear(AlgoritmoDeConsenso unAlgoritmoDeConsenso) {
        if (unAlgoritmoDeConsenso == null) {
            return null;
        }
        switch (unAlgoritmoDeConsenso) {
            case ABSOLUTA:
                return new AlgoritmoAbsoluta();
            case MAYORIA_SIMPLE:
                return new AlgoritmoMayoriaSimple();
            case MULTIPLES_MENCIONES:
                return new AlgoritmoMultipleMenciones();
            default:
                return null;
        }
    }
}
