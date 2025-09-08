package ar.utn.ba.ddsi.ServicioEstadisticas.models.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Table(name = "hechos")
@Entity
@Setter@Getter
public class Hecho {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String provincia;
    private String categoria;
    private LocalDateTime timestamp;
}