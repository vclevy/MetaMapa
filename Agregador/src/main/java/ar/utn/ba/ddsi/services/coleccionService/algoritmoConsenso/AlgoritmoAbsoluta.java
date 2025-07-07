package ar.utn.ba.ddsi.services.coleccionService.algoritmoConsenso;

import ar.utn.ba.ddsi.models.entities.coleccion.Coleccion;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.services.fuentes.FuenteDeHechos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlgoritmoAbsoluta implements IAlgoritmo {
    @Override
    public List<Hecho> aplicarConsenso(Coleccion unaColeccion) {
        List<FuenteDeHechos> fuentes = unaColeccion.getFuentesDeHechos();

        Map<Hecho, Integer> conteoHechos = new HashMap<>();

        for (FuenteDeHechos fuente : fuentes) {
            for (Hecho hecho : fuente.obtenerHechos()) {
                conteoHechos.put(hecho, conteoHechos.getOrDefault(hecho, 0) + 1);
            }
        }

        List<Hecho> hechosConsensuados = new ArrayList<>();

        for (Map.Entry<Hecho, Integer> entrada : conteoHechos.entrySet()) {
            if (entrada.getValue() == fuentes.size()) {
                hechosConsensuados.add(entrada.getKey());
            }
        }

        unaColeccion.setHechos(hechosConsensuados);
        return unaColeccion.getHechos();
    }

}
