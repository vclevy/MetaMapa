package ar.utn.ba.ddsi.ServicioEstadisticas.models.repositories;

import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.resultadosEstadisticas.ResultadoEstadistica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEstadisticasRepository extends JpaRepository<ResultadoEstadistica, Long> {
}
