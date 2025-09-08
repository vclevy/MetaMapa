package ar.utn.ba.ddsi.models.repositories;

import ar.utn.ba.ddsi.models.entities.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuariosRepository extends JpaRepository<Usuario, Long> {}
