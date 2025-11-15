package ar.utn.ba.ddsi.gateway.models.repositories;

import ar.utn.ba.ddsi.gateway.models.entities.fuentes.Fuente;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IFuenteDeHechosRepository extends JpaRepository<Fuente, Long>, JpaSpecificationExecutor<Fuente> {
}
