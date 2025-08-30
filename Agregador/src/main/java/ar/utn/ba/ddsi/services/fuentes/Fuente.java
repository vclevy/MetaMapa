package ar.utn.ba.ddsi.services.fuentes;


import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Fuente {
    private String handleFuente;
    private String tipo;
    private String urlBase;
    private IFuenteDeHechos fuenteDeHechos;

    public Fuente(String tipo, String urlBase) {
        this.handleFuente = UUID.randomUUID().toString();
        this.tipo = tipo;
        this.urlBase = urlBase;
        inicializarFuenteDeHechos();
    }

    public List<Hecho> obtenerHechos() {
        if (fuenteDeHechos == null) {
            throw new IllegalStateException("Fuente de hechos no inicializada");
        }
        return fuenteDeHechos.obtenerHechos();
    }

    public void inicializarFuenteDeHechos() {
        switch (tipo) {
            case "ESTATICA":
                fuenteDeHechos = new FuenteEstatica(urlBase);
                break;
            case "DINAMICA":
                fuenteDeHechos = new FuenteDinamica(urlBase);
                break;
            case "PROXY":
                fuenteDeHechos = new FuenteProxy(urlBase);
                break;
            // otros casos si existen
            default:
                fuenteDeHechos = null;
        }
    }
}
