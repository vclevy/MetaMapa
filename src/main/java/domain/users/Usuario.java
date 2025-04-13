package domain.users;

import domain.RepositorioHechosDinámicos.RepositorioHechosDinamicos;
import domain.coleccion.Coleccion;
import domain.hecho.FiltroHecho.FiltroHecho;
import domain.hecho.Hecho;
import domain.hecho.solicitudes.Solicitud;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


@Getter@Setter
public class Usuario {
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
    public void subirHecho(Hecho unHecho) {
        RepositorioHechosDinamicos.repositorioHechos.agregarHechos(unHecho);
        if (unHecho.getEsAnonimo()) {
            System.out.println("Hecho subido de forma anonima");
            // TODO A IMPLEMENTAR: funcion mostrar, q en base a si es anónimo o no muestre datos del usuario correspondientes
        } else {
            System.out.println("Hecho subido de forma publica");
            unHecho.setEsAnonimo(false);
            // TODO Posible implementacion -> Tabla intermedia con IdHecho (puede ser posicion de array) con IdUsuario ()
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

    public void navegar(Coleccion coleccion, FiltroHecho filtro) {
        List<Hecho> hechosFiltrados = coleccion.getHechos().stream()
                .filter(filtro::aplica)
                .collect(Collectors.toCollection(ArrayList::new));

        if (hechosFiltrados.isEmpty()) {
            System.out.println("No se encontraron hechos que coincidan con el filtro.");
            return;
        }

        Coleccion nuevaColeccion = new Coleccion("NuevaCol", "para Imprimir");
        nuevaColeccion.agregarHechos(hechosFiltrados.toArray(new Hecho[0])); // agregarHechs espera un array de Hechos, no una List<Hecho>
        visualizar(nuevaColeccion);
    }
}




