package ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteDinamica.models.entities.hecho.solicitudes;

import ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteEstatica.models.entities.Hecho;
import ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteEstatica.models.entities.users.Visitante;
import ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteEstatica.models.hecho.solicitudes.EstadoDeSolicitudDeEliminacion;
import ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteEstatica.models.hecho.solicitudes.HistorialSolicitud;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Solicitud {
    private Integer id;
    private String justificacionDeEliminacion;
    private Hecho hecho;
    private EstadoDeSolicitudDeEliminacion estado = EstadoDeSolicitudDeEliminacion.PENDIENTE;
    private LocalDateTime fechaSolicitud;
    private LocalDateTime fechaDeEvaluacionDeSolicitud;
    private Visitante visitanteQueCargoLaSolicitud;
    private List<HistorialSolicitud> historialSolicitud = new ArrayList<>();

    public Solicitud (Hecho unHecho, String unaJustificacion) {
        this.justificacionDeEliminacion = unaJustificacion;
        this.hecho = unHecho;
    }
}
