package ar.utn.ba.ddsi.gateway.models.repositories;

import ar.utn.ba.ddsi.gateway.models.entities.hecho.solicitudes.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISolicitudesRepository extends JpaRepository<Solicitud, Long> {
}
