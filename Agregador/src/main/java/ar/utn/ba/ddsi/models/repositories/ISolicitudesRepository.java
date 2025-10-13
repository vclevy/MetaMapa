package ar.utn.ba.ddsi.models.repositories;

import ar.utn.ba.ddsi.models.entities.solicitud.EstadoDeSolicitudDeEliminacion;
import ar.utn.ba.ddsi.models.entities.solicitud.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ISolicitudesRepository extends JpaRepository<Solicitud, Long> {
    List<Solicitud> findByEstado(EstadoDeSolicitudDeEliminacion estado);
    boolean existsByHechoIdInAndEstado(List<Long> hechoIds, EstadoDeSolicitudDeEliminacion estado);
}