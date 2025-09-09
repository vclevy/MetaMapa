package ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.resultadosEstadisticas;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "resultados_estadistica")
public class ResultadoEstadistica {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombreEstadistica; // Ej: "Provincia con más hechos"
    private String clave;             // Ej: "Buenos Aires" o "Accidente de tránsito"
    private long valor;               // Ej: cantidad de hechos
    private LocalDateTime fechaGeneracion;

    public ResultadoEstadistica(String nombreEstadistica, String clave, Long valor) {
        this.nombreEstadistica = nombreEstadistica;
        this.clave = clave;
        this.valor = valor;
        this.fechaGeneracion = LocalDateTime.now();
    }
}

