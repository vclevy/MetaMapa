package domain.users;

import domain.hecho.gestorDeHechos.GestorDeHechos;
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
public class Visitante {
    private String nombre;
    private String apellido;
    private Integer edad;
    protected TipoDeVisitante tipoDeVisitante = TipoDeVisitante.VISUALIZADOR;
    private GestorDeHechos gestorDeHechos = new GestorDeHechos();

    public Visitante(String unNombre, String unApellido, Integer unEdad) {
        this.nombre = unNombre;
        this.apellido = unApellido;
        this.edad = unEdad;
    }

    public void subirHecho(Hecho unHecho) {
        this.gestorDeHechos.cargarHecho(unHecho);
        this.setTipoDeVisitante(TipoDeVisitante.CONTRIBUYENTE);
    }

    public void solicitarBorrarUnHecho(Hecho unHecho) {
        if (tipoDeVisitante.equals(TipoDeVisitante.CONTRIBUYENTE)) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Ingrese Justificacion de eliminacion (al menos 500 caracteres): ");
            String justificacion = scanner.nextLine().trim();

            Solicitud solicitud = new Solicitud(unHecho, justificacion);
            unHecho.agregarSolicitudDeEliminacion(solicitud);
        }
    }

    public List<Hecho> filtrar(Coleccion coleccion, List<FiltroHecho> filtros) {
        List<Hecho> hechosFiltrados = coleccion.getHechos().values().stream()
                .filter(hecho -> filtros.stream().allMatch(f -> f.aplica(hecho))) // <-- aplica todos los filtros
                .collect(Collectors.toCollection(ArrayList::new));

        if (hechosFiltrados.isEmpty()) {
            System.out.println("No se encontraron hechos que coincidan con el filtro.");
        }
        return hechosFiltrados;
    }

}




