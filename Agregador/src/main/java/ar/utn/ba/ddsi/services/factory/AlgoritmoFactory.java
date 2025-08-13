package ar.utn.ba.ddsi.services.factory;

import ar.utn.ba.ddsi.services.coleccionService.algoritmoConsenso.*;
import org.springframework.stereotype.Component;

@Component
public class AlgoritmoFactory {
    public IAlgoritmo crear(AlgoritmoDeConsenso unAlgoritmoDeConsenso) {
        if (unAlgoritmoDeConsenso == null) {
            return null;
        }

        if (AlgoritmoDeConsenso.ABSOLUTA.equals(unAlgoritmoDeConsenso)) {
            return new AlgoritmoAbsoluta();
        }
        else if (AlgoritmoDeConsenso.MAYORIA_SIMPLE.equals(unAlgoritmoDeConsenso)) {
            return new AlgoritmoMayoriaSimple();
        }
        else if (AlgoritmoDeConsenso.MULTIPLES_MENCIONES.equals(unAlgoritmoDeConsenso)) {
            return new AlgoritmoMultipleMenciones();
        }
        else {
            return null;
        }
    }
}
