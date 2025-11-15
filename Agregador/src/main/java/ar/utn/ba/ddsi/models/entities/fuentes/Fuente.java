package ar.utn.ba.ddsi.models.entities.fuentes;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.services.georef.LugarService;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
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

    @Column(name="nombre")
    private String nombre;

    @OneToMany(mappedBy = "fuente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Hecho> hechos = new ArrayList<>();

    @Column(name = "tipo_fuente", nullable = false)
    private String tipo;

    @Column(name = "url_fuente", nullable = false)
    private String urlBase;

    @Transient
    private IFuenteDeHechos fuenteDeHechos;

    @Transient
    private LugarService lugarService;

    public Fuente(String tipo, String nombre, String urlBase, LugarService lugarService) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.urlBase = urlBase;
        this.lugarService = lugarService;
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
                fuenteDeHechos = new FuenteEstatica(urlBase,lugarService);
                break;
            case "DINAMICA":
                fuenteDeHechos = new FuenteDinamica(urlBase, lugarService);
                break;
            case "PROXY":
                fuenteDeHechos = new FuenteProxy(urlBase, lugarService);
                break;
            default:
                fuenteDeHechos = null;
        }
    }
}
