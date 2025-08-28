package ar.utn.ba.ddsi.FuenteProxy.models.repositories;

import ar.utn.ba.ddsi.FuenteProxy.models.entities.hecho.Hecho;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IHechosRepository extends JpaRepository<Hecho, Long> {
}
