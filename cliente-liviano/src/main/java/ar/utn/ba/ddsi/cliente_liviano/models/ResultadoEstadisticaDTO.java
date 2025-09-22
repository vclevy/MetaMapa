package ar.utn.ba.ddsi.cliente_liviano.models;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResultadoEstadisticaDTO {
    private Long id;
    private String nombreEstadistica;
    private String clave;
    private Long valor;
    private LocalDateTime fechaGeneracion;
    private String nombreDeLaColeccion;

    public ResultadoEstadisticaDTO(String nombreEstadistica, String clave, Long valor, String nombreDeLaColeccion) {
        this.nombreEstadistica = nombreEstadistica;
        this.clave = clave;
        this.valor = valor;
        this.fechaGeneracion = LocalDateTime.now();
        this.nombreDeLaColeccion = nombreDeLaColeccion;
    }

    public ResultadoEstadisticaDTO() {

    }
}

