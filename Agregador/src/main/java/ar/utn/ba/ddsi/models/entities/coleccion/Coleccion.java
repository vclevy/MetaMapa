package ar.utn.ba.ddsi.models.entities.coleccion;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.solicitud.EstadoDeSolicitudDeEliminacion;
import ar.utn.ba.ddsi.services.coleccionService.algoritmoConsenso.IAlgoritmo;
import ar.utn.ba.ddsi.models.entities.fuentes.Fuente;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "coleccion")
public class Coleccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "handle_coleccion", nullable = false)
    private String handle;

    @Column(name = "titulo", nullable = false, length = 255)
    private String titulo;

    @Column(name = "descripcion", nullable = false, length = 255)
    private String descripcion;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "coleccion_id")
    private List<Fuente> fuentesDeHechos = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "coleccion_id")
    private List<Hecho> hechos = new ArrayList<>();

    @Transient
    private List<Criterio> criterioDePertenencia = new ArrayList<>();

    @Transient
    private IAlgoritmo algoritmoDeConsenso;

    @Transient
    private List<Hecho> hechosConAlgotimoAplicado;

    public Coleccion (String titulo, String descripcion,IAlgoritmo algoritmoDeConsenso) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.handle = UUID.randomUUID().toString();
        this.algoritmoDeConsenso = algoritmoDeConsenso;
        this.criterioDePertenencia = List.of();
    }

    public Coleccion() {}

    public boolean cumpleCriterios(Hecho hecho, List<Criterio> criterios) {
        for (Criterio criterioIndice : criterios) {
            if (!criterioIndice.cumple(hecho)) return false;
        }
        return true;
    }

    public boolean verificadorDeAgregadorDeHechos(Hecho unHecho) {
        return !this.cumpleCriterios(unHecho, this.criterioDePertenencia) || this.tieneSolicitudDeEliminacionAprobada(unHecho);
    }

    public boolean tieneSolicitudDeEliminacionAprobada(Hecho unHecho) {
        return unHecho
                .getSolicitudesDeEliminacion()
                .stream()
                .anyMatch(unaSolicitud -> unaSolicitud.getEstado() == EstadoDeSolicitudDeEliminacion.APROBADA);
    }

    public void aplicarAlgoritmoDeConsenso() {
        if (this.algoritmoDeConsenso == null) {
            return;
        }

        this.hechosConAlgotimoAplicado = this.algoritmoDeConsenso.aplicarConsenso(this);
    }
}
