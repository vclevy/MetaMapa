package ar.utn.ba.ddsi.services.coleccionService.algoritmoConsenso;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.services.fuentes.FuenteDeHechos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlgoritmoMultipleMenciones implements IAlgoritmo {
    @Override
    public List<Hecho> aplicarConsenso(String unHandle) {
        List<FuenteDeHechos> fuentes = coleccionesRepository.findByHandle(unHandle).getFuentesDeHechos();

        Map<Hecho, List<FuenteDeHechos>> mapaHechos = new HashMap<>();

        for (FuenteDeHechos fuente : fuentes) {
            List<Hecho> hechos = fuente.obtenerHechos();

            for (Hecho hecho : hechos) {
                mapaHechos
                        .computeIfAbsent(hecho, unHecho -> new ArrayList<>()) // SI EL HECHO YA ESTA EN EL MAPA, NO HACE NADA, SI NO EXISTE, HACE UN ARRAY ASOCIADO AL HECHO
                        .add(fuente); // AGREGA A LA FUENTE EL ARRAY ASOCIADO
            }
        }

        List<Hecho> hechosConsensuados = new ArrayList<>();

        for (Map.Entry<Hecho, List<FuenteDeHechos>> entrada : mapaHechos.entrySet()) {
            Hecho hecho = entrada.getKey();
            List<FuenteDeHechos> fuentesQueLoMencionan = entrada.getValue();

            if (fuentesQueLoMencionan.size() >= 2) {

                boolean existeConflicto = fuentes.stream()
                        .flatMap(unaFuente -> unaFuente.obtenerHechos().stream())
                        .anyMatch(unHecho ->
                                unHecho.getTitulo().equals(hecho.getTitulo()) && !unHecho.equals(hecho)
                        ); // SI EXISTE UN HECHO CON EL MISMO TITULO Y NO ES IGUAL AL HECHO QUE ESTOY COMPARANDO, HAY CONFLICTO

                if (!existeConflicto) {
                    hechosConsensuados.add(hecho);
                }
            }
        }

        return hechosConsensuados;
    }
}
