package ar.utn.ba.ddsi.services.factory;

import org.springframework.stereotype.Component;
import ar.utn.ba.ddsi.services.coleccionService.algoritmoConsenso.IAlgoritmo;
import ar.utn.ba.ddsi.services.coleccionService.algoritmoConsenso.AlgoritmoAbsoluta;
import ar.utn.ba.ddsi.services.coleccionService.algoritmoConsenso.AlgoritmoMayoriaSimple;
import ar.utn.ba.ddsi.services.coleccionService.algoritmoConsenso.AlgoritmoMultipleMenciones;

@Component
public class AlgoritmoFactory {

    public IAlgoritmo crear(String tipo) {
        return switch (tipo.toLowerCase()) {
            case "absoluta" -> new AlgoritmoAbsoluta();
            case "mayoriasimple" -> new AlgoritmoMayoriaSimple();
            case "multiplemenciones" -> new AlgoritmoMultipleMenciones();
            case "ninguno" -> null;
            default -> throw new IllegalArgumentException("Algoritmo inválido: " + tipo);
        };
    }
}
