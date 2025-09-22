package ar.utn.ba.ddsi.cliente_liviano.models.dtos;

import lombok.Data;

@Data
public class HechoFiltroDTO {
    private String fechaDesde;      // ISO string: yyyy-MM-dd
    private String fechaHasta;      // ISO string: yyyy-MM-dd
    private String provincia;
    private Long categoriaId;
    private Long fuenteId;
}