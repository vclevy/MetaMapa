package domain.users;

import domain.coleccion.Hecho;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioRegistrado extends Usuario {
    private String nombre;
    private String apellido;
    private Integer edad;

    public UsuarioRegistrado(String nombre, String apellido, Integer edad, TipoDeUsuario tipoDeUsuario) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
    }
    @Override public void subirHecho(Hecho unHecho){
        this.setTipoDeUsuario(TipoDeUsuario.CONTRIBUYENTE);
    }
}
