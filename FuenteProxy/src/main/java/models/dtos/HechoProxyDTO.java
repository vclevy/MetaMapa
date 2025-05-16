package models.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class HechoProxyDTO {
        private String titulo;
        private String descripcion;
        private String categoria;
        private LocalDate fechaAcontecimiento;
        private LocalDateTime fechaCarga;
        private Double latitud;
        private Double longitud;
        private String fuente; // Por si viene info del proveedor
}
