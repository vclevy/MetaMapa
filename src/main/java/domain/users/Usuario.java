package domain.users;

import domain.coleccion.Coleccion;
import domain.hecho.Hecho;
import lombok.Getter;
import lombok.Setter;


@Getter@Setter
public abstract class Usuario {
    private String nombre;
    private String apellido;
    private Integer edad;
    protected TipoDeUsuario tipoDeUsuario = TipoDeUsuario.VISUALIZADOR;

    // INICIALIZAR USUARIO
    public Usuario(String unNombre, String unApellido, Integer unEdad) {
        this.nombre = unNombre;
        this.apellido = unApellido;
        this.edad = unEdad;
    }

    // SUBIR UN HECHO
    public void subirHecho(Hecho unHecho) {
        // TODO: FUNCIONALIDAD DE MANTENERSE EN ANONIMO Y PASAR A CONTRIBUYENTE
    }

    // SOLICITAR BORRAR UN HECHO
    public void solicitarBorrarHecho(Hecho unHecho) {} // TODO

    // NAVEGAR HECHOS
    public void buscarHecho(Hecho unHecho) {} // TODO
}


