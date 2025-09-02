package ar.utn.ba.ddsi.models.entities.usuario;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Table(name = "usuarios")
@Entity
public class Usuario {

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;
}