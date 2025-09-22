package ar.utn.ba.ddsi.models.entities.hecho.filtros;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import org.springframework.data.jpa.domain.Specification;

public class UbicacionHechoSpecification implements HechoSpecification {
    private final String provincia;

    public UbicacionHechoSpecification(String provincia) {
        this.provincia = provincia;
    }

    @Override
    public Specification<Hecho> toSpecification() {
        return (root, query, cb) -> {
            if (provincia == null || provincia.isBlank()) return cb.conjunction();
            return cb.equal(root.get("lugar").get("provincia"), provincia);
        };
    }
}
