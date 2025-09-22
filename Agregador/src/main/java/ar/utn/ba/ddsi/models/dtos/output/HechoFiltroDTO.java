package ar.utn.ba.ddsi.models.dtos.output;

import java.time.LocalDateTime;

public class HechoFiltroDTO {
    private LocalDateTime fechaDesde;
    private LocalDateTime fechaHasta;
    private Long categoriaId;
    private Long fuenteId;
    private String provincia;

    public HechoFiltroDTO() {}

    public HechoFiltroDTO(LocalDateTime fechaDesde, LocalDateTime fechaHasta,
                          Long categoriaId, Long fuenteId, String provincia) {
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
        this.categoriaId = categoriaId;
        this.fuenteId = fuenteId;
        this.provincia = provincia;
    }

    public LocalDateTime getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(LocalDateTime fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public LocalDateTime getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(LocalDateTime fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public Long getFuenteId() {
        return fuenteId;
    }

    public void setFuenteId(Long fuenteId) {
        this.fuenteId = fuenteId;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
}
