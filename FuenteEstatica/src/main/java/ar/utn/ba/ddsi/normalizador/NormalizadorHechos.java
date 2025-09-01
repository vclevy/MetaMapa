package ar.utn.ba.ddsi.normalizador;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public class NormalizadorHechos {

    private final Map<String, String> equivalenciasCategorias = Map.ofEntries(
            Map.entry("incendio forestal", "Incendio Forestal"),
            Map.entry("fuego forestal", "Incendio Forestal"),
            Map.entry("quema", "Incendio Forestal"),
            Map.entry("INCENDIO_FORESTAL", "Incendio Forestal"),

            Map.entry("inundación", "Inundacion"),
            Map.entry("flood", "Inundacion"),

            Map.entry("sismo", "Terremoto"),
            Map.entry("temblor", "Terremoto"),
            Map.entry("earthquake", "Terremoto"),

            Map.entry("apagón", "Corte de Luz"),
            Map.entry("corte eléctrico", "Corte de Luz"),

            Map.entry("marcha", "Protesta"),
            Map.entry("piquete", "Protesta"),

            Map.entry("choque", "Accidente Vial"),
            Map.entry("colisión", "Accidente Vial")
    );

    private static final int UMBRAL_SIMILITUD = 3;

    public Hecho normalizar(Hecho hecho) {
        normalizarCategoria(hecho);
        return hecho;
    }

    private void normalizarCategoria(Hecho hecho) {
        String original = hecho.getCategoria().getNombre().toLowerCase();

        if (equivalenciasCategorias.containsKey(original)) {
            hecho.getCategoria().setNombre(equivalenciasCategorias.get(original));
            return;
        }

        String mejorMatch = buscarSimilar(original, equivalenciasCategorias.keySet());
        if (mejorMatch != null) {
            hecho.getCategoria().setNombre(equivalenciasCategorias.get(mejorMatch));
        }
    }
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
