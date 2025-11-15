package ar.utn.ba.ddsi.gateway.models.repositories;

import ar.utn.ba.ddsi.gateway.models.entities.coleccion.Coleccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IColeccionesRepository extends JpaRepository<Coleccion, Long> {

}
