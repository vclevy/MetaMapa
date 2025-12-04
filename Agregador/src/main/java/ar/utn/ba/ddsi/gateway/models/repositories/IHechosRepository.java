package ar.utn.ba.ddsi.gateway.models.repositories;

import ar.utn.ba.ddsi.gateway.models.entities.hecho.Hecho;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IHechosRepository extends JpaRepository<Hecho, Long>, JpaSpecificationExecutor<Hecho> {
}
