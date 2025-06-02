package ar.utn.ba.ddsi.services.spam;

import java.util.Arrays;
import java.util.List;

public class DetectorDeSpamImpl implements DetectorDeSpam {
    private static final List<String> PALABRAS_SPAM = Arrays.asList(
            "eliminar todo", "borrar ya", "urgente", "odio", "mentira", "basura"
    );

    private static final int LONGITUD_MAXIMA_PARA_REVISION = 500;
    private static final int REPETICIONES_MAXIMAS_PERMITIDAS = 30;

    @Override
    public boolean esSpam(String justificacion) {
        String normalizada = justificacion.toLowerCase().trim();

        // Revisión por palabras clave
        for (String palabraSpam : PALABRAS_SPAM) {
            if (normalizada.contains(palabraSpam)) {
                return true;
            }
        }

        if (normalizada.length() > LONGITUD_MAXIMA_PARA_REVISION) {
            if (esRepeticionExcesiva(normalizada)) {
                return true;
            }
        }

        return false;
    }

    private boolean esRepeticionExcesiva(String texto) {
        char anterior = '\0';
        int contador = 0;

        for (char actual : texto.toCharArray()) {
            if (actual == anterior) {
                contador++;
                if (contador >= REPETICIONES_MAXIMAS_PERMITIDAS) {
                    return true;
                }
            } else {
                anterior = actual;
                contador = 1;
            }
        }

        return false;
    }
}
