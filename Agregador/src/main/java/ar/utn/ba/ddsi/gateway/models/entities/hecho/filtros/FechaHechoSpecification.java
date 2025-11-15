package ar.utn.ba.ddsi.gateway.models.entities.hecho.filtros;

import ar.utn.ba.ddsi.gateway.models.entities.hecho.Hecho;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class FechaHechoSpecification implements HechoSpecification {
    private final LocalDateTime desde;
    private final LocalDateTime hasta;

    public FechaHechoSpecification(LocalDateTime desde, LocalDateTime hasta) {
        this.desde = desde;
        this.hasta = hasta;
    }

    @Override
    public Specification<Hecho> toSpecification() {
        return (root, query, cb) -> {
            if (desde != null && hasta != null) {
                return cb.between(root.get("fechaDeAcontecimiento"), desde, hasta);
            } else if (desde != null) {
                return cb.greaterThanOrEqualTo(root.get("fechaDeAcontecimiento"), desde);
            } else if (hasta != null) {
                return cb.lessThanOrEqualTo(root.get("fechaDeAcontecimiento"), hasta);
            }
            return cb.conjunction();
        };
    }
}