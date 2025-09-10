package ar.utn.ba.ddsi.ServicioEstadisticas.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "resultados_estadistica")
public class ResultadoEstadistica {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombreEstadistica;
    @Column (name = "clave")
    private String clave;
    @Column(name = "valor", nullable = false)
    private Long valor;
    @Column(name = "fecha_generacion", nullable = false)
    private LocalDateTime fechaGeneracion;
    private String nombreDeLaColeccion;

    public ResultadoEstadistica() {

    }
}

