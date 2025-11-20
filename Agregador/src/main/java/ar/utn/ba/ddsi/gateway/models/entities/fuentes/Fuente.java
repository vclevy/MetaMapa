package ar.utn.ba.ddsi.gateway.models.entities.fuentes;

import ar.utn.ba.ddsi.gateway.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.gateway.services.georef.LugarService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "tipo_fuente", nullable = false)
    private String tipo;

    @Column(name = "url_fuente", nullable = false)
    private String urlBase;

    @OneToMany(mappedBy = "fuente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Hecho> hechos = new ArrayList<>();

    @Transient
    private IFuenteDeHechos fuenteDeHechos;

    public Fuente(String tipo, String nombre, String urlBase, LugarService lugarService) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.urlBase = urlBase;
        inicializarFuenteDeHechos(lugarService);
    }

    public Fuente() {}

    public void inicializarFuenteDeHechos(LugarService lugarService) {
        switch (tipo) {
            case "ESTATICA":
                fuenteDeHechos = new FuenteEstatica(urlBase, lugarService);
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

    public List<Hecho> obtenerHechos() {
        if (fuenteDeHechos == null) {
            throw new IllegalStateException("Fuente de hechos no inicializada");
        }
        return fuenteDeHechos.obtenerHechos();
    }
}

