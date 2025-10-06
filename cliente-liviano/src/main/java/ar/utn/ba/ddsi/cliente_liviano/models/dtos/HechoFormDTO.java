package ar.utn.ba.ddsi.cliente_liviano.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HechoFormDTO {
    @NotBlank(message="El título es obligatorio.")
    private String titulo;

    @NotBlank(message="La descripcón obligatoria.")
    private String descripcion;

    @NotBlank(message="La categotía obligatoria.")
    private String categoriaNombre;

    @NotNull(message = "La fecha de acontecimiento es obligatoria")
    private LocalDateTime fechaDeAcontecimiento;

    @NotNull(message = "La latitud es obligatoria")
    private Double latitud;

    @NotNull(message = "La longitud es obligatoria")
    private Double longitud;

    private String nombreDeUsuario;

    private Boolean esAnonimo = false;

}