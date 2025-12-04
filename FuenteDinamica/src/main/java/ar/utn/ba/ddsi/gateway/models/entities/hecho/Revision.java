package ar.utn.ba.ddsi.gateway.models.entities.hecho;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class Revision {

    private EstadoRevision estado;
    private String comentario;

    public Revision(EstadoRevision estado, String comentario) {
        this.estado = estado;
        this.comentario = comentario;
    }

    public boolean estaPendiente() {
        return estado == EstadoRevision.PENDIENTE;
    }
}

