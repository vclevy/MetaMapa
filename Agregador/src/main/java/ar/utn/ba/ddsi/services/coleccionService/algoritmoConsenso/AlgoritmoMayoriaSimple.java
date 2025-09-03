package ar.utn.ba.ddsi.services.coleccionService.algoritmoConsenso;

import ar.utn.ba.ddsi.models.entities.coleccion.Coleccion;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.fuentes.Fuente;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlgoritmoMayoriaSimple implements IAlgoritmo {
    @Override
    public List<Hecho> aplicarConsenso(Coleccion unaColeccion) {
        List<Fuente> fuentes = unaColeccion.getFuentesDeHechos();

        Map<Hecho, Integer> conteoHechos = new HashMap<>();

        for (Fuente fuente : fuentes) {
            for (Hecho hecho : fuente.obtenerHechos()) {
                conteoHechos.put(hecho, conteoHechos.getOrDefault(hecho, 0) + 1);
            }
        }

        List<Hecho> hechosConsensuados = new ArrayList<>();

        for (Map.Entry<Hecho, Integer> entrada : conteoHechos.entrySet()) {
            Hecho hecho = entrada.getKey();
            int cantidadMenciones = entrada.getValue();

            if (cantidadMenciones >= Math.ceil(fuentes.size() / 2.0)) {
                hechosConsensuados.add(hecho);
            }
        }

        unaColeccion.setHechos(hechosConsensuados);
        return unaColeccion.getHechos();
    }
}
