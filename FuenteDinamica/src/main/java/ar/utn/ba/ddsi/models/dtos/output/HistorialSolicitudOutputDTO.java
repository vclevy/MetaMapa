package ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteDinamica.models.dtos.output;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class HistorialSolicitudOutputDTO {
    private String estado;
    private LocalDateTime fechaModificacion;
    private String administrador;

}
