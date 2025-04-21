package ar.edu.utn.frba.dds.PrimeraEntrega;

import domain.coleccion.Categoria;
import domain.coleccion.Coleccion;
import domain.hecho.Hecho;
import domain.hecho.OrigenDelHecho;
import domain.hecho.lugar.Lugar;
import domain.hecho.solicitudes.EstadoDeSolicitudDeEliminacion;
import domain.hecho.solicitudes.Solicitud;
import domain.users.Administrador;
import domain.users.TipoDeVisitante;
import domain.users.Visitante;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Escenario3 {
    private Hecho hecho;
    private Visitante visitante;

    @BeforeEach
    public void setUp() {
        // INSTANCIO EL HECHO
        hecho = new Hecho(
                "Brote de enfermedad contagiosa causa estragos en San Lorenzo, Santa Fe",
                "Grave brote de enfermedad contagiosa ocurrió en las inmediaciones de San Lorenzo, Santa Fe. El incidente dejó varios heridos y daños materiales. Se ha declarado estado de emergencia en la región para facilitar la asistencia.",
                new Categoria("Evento sanitario"),
                LocalDate.of(2005, 7, 5),
                LocalDateTime.now(),
                new Lugar(-32.786098, -60.741543),
                OrigenDelHecho.CARGA_MANUAL,
                false
        );

        // INSTANCIO UN VISITANTE
        visitante = new Visitante(
                "Matias",
                "Perez",
                24
        );

        // SETEO A CONTRIBUYENTE
        visitante.setTipoDeVisitante(TipoDeVisitante.CONTRIBUYENTE);
    }

    @Test
    public void gestionDeLasSolicitudes() {
        // VISITANTE SOLICITA BORRAR UN HECHO --> HECHO DEBERIA ESTAR EN ESTADO PENDIENTE
        String justificacion = "La tecnología desempeña un papel crucial en la mejora de la calidad de vida, ya que permite optimizar procesos, facilitar la comunicación y resolver problemas complejos de forma eficiente. Su implementación responsable puede impulsar el desarrollo sostenible, reducir desigualdades y promover la educación y la innovación. Por ello, invertir en investigación tecnológica y su accesibilidad es esencial para construir sociedades más equitativas y preparadas para los desafíos del futuro y asi poder comprar nuevos elemtnos para poder pasarla mejor bla bla bla.";
        visitante.solicitarBorrarUnHecho(hecho, justificacion);

        // QUE LA SOLICITUD ESTE EN ESTADO PENDIENTE
        Assertions.assertEquals(hecho.getSolicitudesDeEliminacion().get(0).getEstado(), EstadoDeSolicitudDeEliminacion.PENDIENTE);

        // RECHAZO LA SOLICITUD
        Administrador administrador = new Administrador();
        administrador.rechazarSolicitud(hecho.getSolicitudesDeEliminacion().get(0));
        Assertions.assertEquals(hecho.getSolicitudesDeEliminacion().get(0).getEstado(), EstadoDeSolicitudDeEliminacion.RECHAZADA);

        // VERIFICO QUE SE AGREGA A UNA COLECICON
        Coleccion coleccion = new Coleccion();
        coleccion.agregarHecho(hecho);
        Assertions.assertEquals(coleccion.getHechos().size(), 1);

        // GENERO UNA NUEVA SOLICITUD PARA EL MISMO HECHO
        String otraJustificacion = "La programación es una disciplina que combina lógica, creatividad y precisión. A través del desarrollo de software, es posible construir soluciones que impactan profundamente en la vida cotidiana, desde aplicaciones móviles hasta complejos sistemas distribuidos. Para un programador, escribir código no es simplemente dar instrucciones a una computadora, sino una forma de pensamiento estructurado que permite resolver problemas reales de manera eficiente. A medida que se adquiere experiencia, se valoran más las buenas prácticas como el uso de principios SOLID, la separación de responsabilidades y la reutilización de componentes. Además, trabajar en equipo y mantener una comunicación clara es tan importante como dominar un lenguaje de programación. Un buen proyecto no sólo depende del código, sino también del contexto, la planificación y la colaboración entre sus miembros. La tecnología avanza rápidamente, por lo que el aprendizaje constante es parte esencial de esta profesión.";
        visitante.solicitarBorrarUnHecho(hecho, otraJustificacion);
        administrador.aprobarSolicitud(hecho.getSolicitudesDeEliminacion().get(1));

        // VERIFICO QUE EL HECHO NO SE PUEDA AGREGAR A LA COLECCION Y QUE LA COLECCION ESTE VACIA PORQUE SE DEBERIA ELIMINAR EL HECHO QUE FUE ACEPTADA SU SOLICITUD DE ELIMINACION
        coleccion.agregarHecho(hecho);
        coleccion.eliminarHechoPorSolicitudDeEliminacionAprobada(hecho);
        Assertions.assertEquals(coleccion.getHechos().size(), 0);

        // VERIFICO QUE EL ESTADO SE SOLICITUD ESTA EN ESTADO ACEPTADA
        Assertions.assertEquals(hecho.getSolicitudesDeEliminacion().get(1).getEstado(), EstadoDeSolicitudDeEliminacion.APROBADA);
    }
}
