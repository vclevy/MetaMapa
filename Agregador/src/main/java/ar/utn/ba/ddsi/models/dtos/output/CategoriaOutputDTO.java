package ar.utn.ba.ddsi.models.dtos.output;

import ar.utn.ba.ddsi.models.entities.hecho.Categoria;
import lombok.Data;

@Data
public class CategoriaOutputDTO {
    private Long id;
    private String nombre;

    public CategoriaOutputDTO(Categoria categoria) {
        this.id = categoria.getId();
        this.nombre = categoria.getNombre();
    }
}
