package ar.utn.ba.ddsi.gateway.FuenteProxy.models.repositories;

import ar.utn.ba.ddsi.gateway.FuenteProxy.models.entities.solicitudEliminacion.SolicitudEliminacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISolicitudesRepository extends JpaRepository<SolicitudEliminacion, Long> {

}
