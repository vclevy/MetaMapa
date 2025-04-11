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

    // TODO: VER COMO HACER SI EL USUARIO QUIERE SUBIR DE FORMA ANONIMA O QUE SE DE A CONOCER
    public void subirHecho(Hecho unHecho) {
        this.tipoDeFormaParaSubirUnHecho();
        this.setTipoDeUsuario(TipoDeUsuario.CONTRIBUYENTE);
    }

    public void tipoDeFormaParaSubirUnHecho() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("¿Desea subir el hecho de forma anonima? (s/n)");
        String respuesta = scanner.nextLine();

        if(respuesta.equals("s")) {
            System.out.println("Hecho subido de forma anonima");
        } else {
            System.out.print("Ingrese su nombre (obligatorio): ");
            String nombreIngresado = scanner.nextLine().trim();

            while (nombreIngresado.isEmpty()) {
                System.out.println("El nombre no puede estar vacío. Intente nuevamente.");
                System.out.print("Ingrese su nombre (obligatorio): ");
                nombreIngresado = scanner.nextLine().trim();
            }
            this.nombre = nombreIngresado;

            System.out.print("¿Desea ingresar su apellido? (s/n): ");
            if (scanner.nextLine().equalsIgnoreCase("s")) {
                System.out.print("Apellido: ");
                this.apellido = scanner.nextLine();
            }

            System.out.print("¿Desea ingresar su edad? (s/n): ");
            if (scanner.nextLine().equalsIgnoreCase("s")) {
                System.out.print("Edad: ");
                this.edad = scanner.nextInt();
            }

            System.out.println("Hecho subido como contribuyente identificado.");
        }
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


