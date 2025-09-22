package ar.utn.ba.ddsi.models.entities.hecho.filtros;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class HechoSpecifications {
    public static Specification<Hecho> combinar(List<HechoSpecification> filtros) {
        Specification<Hecho> spec = Specification.where(null);
        for (HechoSpecification f : filtros) {
            spec = spec.and(f.toSpecification());
        }
        return spec;
    }
}
