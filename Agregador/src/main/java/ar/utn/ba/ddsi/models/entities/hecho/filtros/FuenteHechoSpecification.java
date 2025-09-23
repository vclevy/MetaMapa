package ar.utn.ba.ddsi.models.entities.hecho.filtros;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import org.springframework.data.jpa.domain.Specification;

public class FuenteHechoSpecification implements HechoSpecification {
    private final String tipoFuente;

    public FuenteHechoSpecification(String tipoFuente) {
        this.tipoFuente = tipoFuente;
    }

    @Override
    public Specification<Hecho> toSpecification() {
        return (root, query, cb) -> {
            if (tipoFuente == null || tipoFuente.isBlank()) {
                return cb.conjunction();
            }
            return cb.equal(
                    cb.upper(root.get("fuente").get("tipo")),
                    tipoFuente.toUpperCase()
            );
        };
    }
}


