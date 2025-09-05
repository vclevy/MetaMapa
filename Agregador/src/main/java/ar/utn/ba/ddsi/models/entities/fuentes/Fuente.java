package ar.utn.ba.ddsi.models.entities.fuentes;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "fuente")
public class Fuente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_fuente", nullable = false)
    private String tipo;

    @Column(name = "url_fuente", nullable = false)
    private String urlBase;

    @Transient
    private IFuenteDeHechos fuenteDeHechos;

    public Fuente(String tipo, String urlBase) {
        this.tipo = tipo;
        this.urlBase = urlBase;
        inicializarFuenteDeHechos();
    }

    public Fuente() {}

    public List<Hecho> obtenerHechos() {
        if (fuenteDeHechos == null) {
            throw new IllegalStateException("Fuente de hechos no inicializada");
        }
        return fuenteDeHechos.obtenerHechos();
    }
    @PostLoad
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
