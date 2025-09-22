package ar.utn.ba.ddsi.models.entities.hecho.filtros;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import org.springframework.data.jpa.domain.Specification;

public interface HechoSpecification {
    Specification<Hecho> toSpecification();
}