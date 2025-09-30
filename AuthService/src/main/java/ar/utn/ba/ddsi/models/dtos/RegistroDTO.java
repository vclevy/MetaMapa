package ar.utn.ba.ddsi.models.dtos;

import lombok.Data;

@Data
public class RegistroDTO {
    private String nombre;
    private String apellido;
    private String nombreDeUsuario;
    private String email;
    private String clave;
}