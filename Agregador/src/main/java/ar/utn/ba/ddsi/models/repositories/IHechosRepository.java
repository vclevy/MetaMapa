package ar.utn.ba.ddsi.models.repositories;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.solicitud.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IHechosRepository extends JpaRepository<Hecho, Long>, JpaSpecificationExecutor<Hecho> {
}
