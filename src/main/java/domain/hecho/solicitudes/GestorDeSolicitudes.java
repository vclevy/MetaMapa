package domain.hecho.solicitudes;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import static domain.hecho.solicitudes.EstadoDeSolicitudDeEliminacion.*;

@Getter
public class GestorDeSolicitudes {

    private List<Solicitud> solicitudesDeEliminacionDeHecho = new ArrayList<>();

    public void aprobarSolicitud(Solicitud unaSolicitud) {
        if(esValida(unaSolicitud)) {
            unaSolicitud.setEstado(APROBADA);
            this.getSolicitudesDeEliminacionDeHechoPendientes().remove(unaSolicitud);
        }
        // TODO Cuando un hecho se quiera mostrar en interfaz, se debe verificar en sus solicitudes de eliminacion asociadas, que no haya ninguna aprobada
    }

    public void rechazarSolicitud(Solicitud unaSolicitud) {
        if(esValida(unaSolicitud)){
        unaSolicitud.setEstado(RECHAZADA);
        this.getSolicitudesDeEliminacionDeHechoPendientes().remove(unaSolicitud);
        }
    }

    public List<Solicitud> getSolicitudesDeEliminacionDeHechoPendientes() {
        return solicitudesDeEliminacionDeHecho.stream().filter(unaSolicitud -> unaSolicitud.getEstado() == PENDIENTE).toList();
    }

    public boolean esValida(Solicitud solicitud) {
        if (solicitud.getJustificacionDeEliminacion() == null || solicitud.getJustificacionDeEliminacion().length() < 500) {
            solicitud.setEstado(RECHAZADA);
            return false;
        }
        return true;
    }


}

