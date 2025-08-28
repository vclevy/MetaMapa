package ar.utn.ba.ddsi.services.impl;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import org.springframework.stereotype.Service;

import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.*;

@Service
public class NormalizadorHechos {

    private final Map<String, String> equivalenciasCategorias = Map.of(
            "incendio forestal", "INCENDIO_FORESTAL",
            "fuego forestal", "INCENDIO_FORESTAL"
    );

    private static final int UMBRAL_SIMILITUD = 3; // distancia máxima de edición permitida

    public Hecho normalizar(Hecho hecho) {
        normalizarCategoria(hecho);
        return hecho;
    }

    private void normalizarCategoria(Hecho hecho) {
        String original = hecho.getCategoria().getNombre().toLowerCase();

        // 1. Buscar en diccionario
        if (equivalenciasCategorias.containsKey(original)) {
            hecho.getCategoria().setNombre(equivalenciasCategorias.get(original));
            return;
        }

        // 2. Fuzzy Matching
        String mejorMatch = buscarSimilar(original, equivalenciasCategorias.keySet());
        if (mejorMatch != null) {
            hecho.getCategoria().setNombre(equivalenciasCategorias.get(mejorMatch));
        }
        // else: lo dejamos como está → luego un curador lo revisa
    }

    //Si hay algun atributo mas para normalizar es como el metodo de arriba

    private String buscarSimilar(String valor, Set<String> candidatos) {
        LevenshteinDistance levenshtein = new LevenshteinDistance();
        int mejorDistancia = Integer.MAX_VALUE;
        String mejorMatch = null;

        for (String candidato : candidatos) {
            int distancia = levenshtein.apply(valor, candidato);
            if (distancia < mejorDistancia) {
                mejorDistancia = distancia;
                mejorMatch = candidato;
            }
        }

        return (mejorDistancia <= UMBRAL_SIMILITUD) ? mejorMatch : null;
    }
}
