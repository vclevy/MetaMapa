package ar.utn.ba.ddsi.services.spam;

import java.util.Arrays;
import java.util.List;

public class DetectorDeSpamImpl implements DetectorDeSpam {
    private static final List<String> PALABRAS_SPAM = Arrays.asList(
            // ACA IRIAN LAS PALABRAS A DEFINIR PARA SPAM
    );

    @Override
    public boolean esSpam(String unaJustificacion) {
        String unaJustificacionNormalizada = unaJustificacion.toLowerCase();
        for (String palabraIndice : PALABRAS_SPAM) {
            if (unaJustificacionNormalizada.contains(palabraIndice)) {
                return true;
            }
        }
        return false;
    }
}