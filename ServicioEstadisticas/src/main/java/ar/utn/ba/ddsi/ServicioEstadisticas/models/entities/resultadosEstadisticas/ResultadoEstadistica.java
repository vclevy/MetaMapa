package ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.resultadosEstadisticas;

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
    private String clave;
    @Column(name = "valores", nullable = false)
    private Long valor;
    @Column(name = "fechas_generacion", nullable = false)
    private LocalDateTime fechaGeneracion;
    private String nombreDeLaColeccion;

    public ResultadoEstadistica(String nombreEstadistica, String clave, Long valor, String nombreDeLaColeccion) {
        this.nombreEstadistica = nombreEstadistica;
        this.clave = clave;
        this.valor = valor;
        this.fechaGeneracion = LocalDateTime.now();
        this.nombreDeLaColeccion = nombreDeLaColeccion;
    }

    public ResultadoEstadistica() {

    }
}

