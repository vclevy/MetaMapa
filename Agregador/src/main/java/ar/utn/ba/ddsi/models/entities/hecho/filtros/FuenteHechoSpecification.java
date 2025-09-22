package ar.utn.ba.ddsi.models.entities.hecho.filtros;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import org.springframework.data.jpa.domain.Specification;

public class FuenteHechoSpecification implements HechoSpecification {
    private final Long fuenteId;

    public FuenteHechoSpecification(Long fuenteId) {
        this.fuenteId = fuenteId;
    }

    @Override
    public Specification<Hecho> toSpecification() {
        return (root, query, cb) -> {
            if (fuenteId == null) return cb.conjunction();
            return cb.equal(root.get("fuente").get("id"), fuenteId);
        };
    }
}

