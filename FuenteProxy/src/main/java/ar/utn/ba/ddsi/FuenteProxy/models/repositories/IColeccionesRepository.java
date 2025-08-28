package ar.utn.ba.ddsi.FuenteProxy.models.repositories;

import ar.utn.ba.ddsi.FuenteProxy.models.entities.Coleccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IColeccionesRepository extends JpaRepository<Coleccion, Long> {

}
