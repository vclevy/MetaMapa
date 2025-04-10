package domain.users;

import domain.coleccion.Coleccion;
import domain.hecho.Hecho;
import lombok.Getter;
import lombok.Setter;

import java.util.Scanner;


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
    // todo: ver distintas formas de subir un hecho (anonimo o normal)


    public void subirHecho(Hecho unHecho) {
        // TODO: FUNCIONALIDAD DE MANTENERSE EN ANONIMO Y PASAR A CONTRIBUYENTE
        this.setTipoDeUsuario(TipoDeUsuario.CONTRIBUYENTE);
    }

    public void solicitarBorrarHecho(Hecho unHecho) {
        // USUARIO INGRESA POR CONSOLA LA JUSTIFICACION
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese la justificacion para elimnar el hecho: ");
        String justificacion = scanner.nextLine();

        // HAGO QUE EL HECHO AGREGUE UNA SOLICITUD DE BORRAR EL HECHO CON LA JUSTIFICACION DADA
        unHecho.agregarSolicitudDeElimnacion(justificacion);
    }

    // NAVEGAR HECHOS
    public void buscarHecho(Hecho unHecho) {} // TODO
}


