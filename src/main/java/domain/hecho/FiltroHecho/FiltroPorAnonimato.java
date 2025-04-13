package domain.hecho.FiltroHecho;

import domain.hecho.Hecho;

public class FiltroPorAnonimato implements FiltroHecho {
    private boolean esAnonimo;

    public FiltroPorAnonimato(boolean esAnonimo) {
        this.esAnonimo = esAnonimo;
    }

    @Override
    public boolean aplica(Hecho hecho) {
        return hecho.getEsAnonimo().equals(esAnonimo);
    }
}
