package ar.utn.ba.ddsi.models.repositories;

import ar.utn.ba.ddsi.models.entities.fuentes.Fuente;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IFuenteDeHechosRepository extends JpaRepository<Fuente, Long>, JpaSpecificationExecutor<Fuente> {
}
