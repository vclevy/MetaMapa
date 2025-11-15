package ar.utn.ba.ddsi.gateway.cliente_liviano.models.dtos;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class HechoFiltroDTO {
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime fechaDesde;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime fechaHasta;
    private Long categoriaId;
    private String provincia;
    private String tipoFuente;
}