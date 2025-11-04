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

        // Mapa para contar cuántas veces se menciona cada hecho
        Map<Hecho, Integer> conteoHechos = new HashMap<>();

        // Contamos las menciones de cada hecho en las fuentes
        for (Fuente fuente : fuentes) {
            for (Hecho hecho : fuente.obtenerHechos()) {
                conteoHechos.put(hecho, conteoHechos.getOrDefault(hecho, 0) + 1);
            }
        }

        // Lista para almacenar los hechos que cumplen con la mayoría simple
        List<Hecho> hechosConsensuados = new ArrayList<>();

        // Verificamos cuáles hechos cumplen con la condición de mayoría simple
        for (Map.Entry<Hecho, Integer> entrada : conteoHechos.entrySet()) {
            Hecho hecho = entrada.getKey();
            int cantidadMenciones = entrada.getValue();

            // Si un hecho tiene menciones mayor o igual a la mitad de las fuentes, lo agregamos a la lista
            if (cantidadMenciones >= Math.ceil(fuentes.size() / 2.0)) {
                hechosConsensuados.add(hecho);
            }
        }

        // Devolvemos la lista de hechos consensuados
        return hechosConsensuados;
    }
}