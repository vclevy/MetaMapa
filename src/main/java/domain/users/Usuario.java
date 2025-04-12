package domain.users;

import domain.RepositorioHechosDinámicos.RepositorioHechosDinamicos;
import domain.coleccion.Coleccion;
import domain.hecho.Hecho;
import domain.hecho.solicitudes.Solicitud;
import lombok.Getter;
import lombok.Setter;
import java.util.Scanner;


@Getter@Setter
public abstract class Usuario {
    private String nombre;
    private String apellido;
    private Integer edad;
    protected TipoDeUsuario tipoDeUsuario = TipoDeUsuario.VISUALIZADOR;

    /*----------------------------------- CONSTRUCTOR USUARIO --------------------------------------------*/
    public Usuario(String unNombre, String unApellido, Integer unEdad) {
        this.nombre = unNombre;
        this.apellido = unApellido;
        this.edad = unEdad;
    }

    /*--------------------------------------- SUBIR HECHOS ------------------------------------------------*/

    public void subirHecho(Hecho unHecho){
        RepositorioHechosDinamicos.repositorioHechos.agregarHechos(unHecho);
        if(unHecho.getEsAnonimo()){
            System.out.println("Hecho subido de forma anonima");
            // A IMPLEMENTAR: funcion mostrar, q en base a si es anónimo o no muestre datos del usuario correspondientes
        } else {
            System.out.println("Hecho subido de forma publica");
            unHecho.setEsAnonimo(false);
            //Posible implementacion -> Tabla intermedia con IdHecho (puede ser posicion de array) con IdUsuario ()
        }
            this.setTipoDeUsuario(TipoDeUsuario.CONTRIBUYENTE);
    }

    /*---------------------------------- SOLICITAR BORRAR UN HECHO --------------------------------------*/
    public void solicitarBorrarUnHecho(Hecho unHecho) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese Justificacion de eliminacion (al menos 500 caracteres): ");
        String justificacion = scanner.nextLine().trim();

        Solicitud solicitud = new Solicitud(unHecho, justificacion);
        unHecho.agregarSolicitudDeEliminacion(solicitud);
    }

    /*---------------------------------- NAVEGAR LOS HECHOS DE UNA COLECCION --------------------------------------*/
    public void visualizar(Coleccion unaColeccion) {
        for (Hecho hecho : unaColeccion.getHechos()) {
            System.out.println("Título: " + hecho.getTitulo());
            System.out.println("Descripción: " + hecho.getDescripcion());
            System.out.println("Categoría: " + hecho.getCategoria());
            System.out.println("Fecha de Acontecimiento: " + hecho.getFechaDeAcontecimiento());
            System.out.println("Lugar: " + hecho.getLugar().getNombre());
            System.out.println("¿Anónimo?: " + (hecho.getEsAnonimo() ? "Sí" : "No"));
            System.out.println("-------------------------");
        }
    }

}


