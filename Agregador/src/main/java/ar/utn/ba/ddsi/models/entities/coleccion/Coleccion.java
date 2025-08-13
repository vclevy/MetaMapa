package ar.utn.ba.ddsi.models.entities.coleccion;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;

import ar.utn.ba.ddsi.models.entities.solicitud.EstadoDeSolicitudDeEliminacion;
import ar.utn.ba.ddsi.services.coleccionService.algoritmoConsenso.IAlgoritmo;
import ar.utn.ba.ddsi.services.fuentes.Fuente;
import ar.utn.ba.ddsi.services.fuentes.FuenteEstatica;
import ar.utn.ba.ddsi.services.fuentes.IFuenteDeHechos;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Coleccion {
    private String titulo;
    private String descripcion;
    private String handle;
    private List<Fuente> fuentesDeHechos = new ArrayList<>();
    private List<Criterio> criterioDePertenencia;
    private IAlgoritmo algoritmoDeConsenso;
    private List<Hecho> hechos = new ArrayList<>();
    private List<Hecho> hechosConAlgotimoAplicado;

    public Coleccion (String titulo, String descripcion,IAlgoritmo algoritmoDeConsenso) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.handle = UUID.randomUUID().toString();
        this.algoritmoDeConsenso = algoritmoDeConsenso;
        this.criterioDePertenencia = List.of();
    }

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

    public void agregarHecho(Hecho hecho) {
            this.hechos.add(hecho);

    }
}
