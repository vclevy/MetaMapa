package ar.utn.ba.ddsi.models.repositories;

import ar.utn.ba.ddsi.models.entities.coleccion.Coleccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IColeccionesRepository extends JpaRepository<Coleccion, Long> {

}
