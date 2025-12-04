package ar.utn.ba.ddsi.gateway.models.dtos.output;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HechoFiltroDTO {
    private LocalDateTime fechaDesde;
    private LocalDateTime fechaHasta;
    private Long categoriaId;
    private String tipoFuente;
    private String provincia;

    public HechoFiltroDTO() {}

    public HechoFiltroDTO(LocalDateTime fechaDesde, LocalDateTime fechaHasta,
                          Long categoriaId, String tipoFuente, String provincia) {
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
        this.categoriaId = categoriaId;
        this.tipoFuente = tipoFuente;
        this.provincia = provincia;
    }

}
