package ar.utn.ba.ddsi.gateway.models.entities.hecho.filtros;

import ar.utn.ba.ddsi.gateway.models.entities.hecho.Hecho;
import org.springframework.data.jpa.domain.Specification;

public class CategoriaHechoSpecification implements HechoSpecification {
    private final Long categoriaId;

    public CategoriaHechoSpecification(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    @Override
    public Specification<Hecho> toSpecification() {
        return (root, query, cb) -> {
            if (categoriaId == null) return cb.conjunction();
            return cb.equal(root.get("categoria").get("id"), categoriaId);
        };
    }
}
