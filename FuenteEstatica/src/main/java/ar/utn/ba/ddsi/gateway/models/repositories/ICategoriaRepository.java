package ar.utn.ba.ddsi.gateway.models.repositories;

import ar.utn.ba.ddsi.gateway.models.entities.hecho.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICategoriaRepository extends JpaRepository<Categoria, Long> {
    Optional<Categoria> findByNombre(String nombre);
}
