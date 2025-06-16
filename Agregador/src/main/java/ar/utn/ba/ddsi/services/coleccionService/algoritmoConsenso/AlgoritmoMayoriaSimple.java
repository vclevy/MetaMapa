package ar.utn.ba.ddsi.services.coleccionService.algoritmoConsenso;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.services.fuentes.FuenteDeHechos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlgoritmoMayoriaSimple implements IAlgoritmo {
    @Override
    public List<Hecho> aplicarConsenso(String unHandle) {
        List<FuenteDeHechos> fuentes = coleccionesRepository.findByHandle(unHandle).getFuentesDeHechos();

        Map<Hecho, Integer> conteoHechos = new HashMap<>();

        for (FuenteDeHechos fuente : fuentes) {
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

        return hechosConsensuados;
    }
}
