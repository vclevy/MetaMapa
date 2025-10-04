package ar.utn.ba.ddsi.cliente_liviano.models.dtos;

import lombok.Data;

@Data
public class RegistroForm {
    private String nombre;
    private String apellido;
    private String nombreDeUsuario;
    private String email;
    private String clave;
}