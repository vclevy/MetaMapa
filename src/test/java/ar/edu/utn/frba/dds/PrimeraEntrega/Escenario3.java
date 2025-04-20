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
    public void solicitudDeEliminacionEnEstadoPendiente() {
        // VISITANTE SOLICITA BORRAR UN HECHO --> HECHO DEBERIA ESTAR EN ESTADO PENDIENTE
        String justificacion = "La tecnología desempeña un papel crucial en la mejora de la calidad de vida, ya que permite optimizar procesos, facilitar la comunicación y resolver problemas complejos de forma eficiente. Su implementación responsable puede impulsar el desarrollo sostenible, reducir desigualdades y promover la educación y la innovación. Por ello, invertir en investigación tecnológica y su accesibilidad es esencial para construir sociedades más equitativas y preparadas para los desafíos del futuro.";
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

        // GENERO OTRA SOLICITUD PARA EL MISMO HECHO
        String otraJustificacion = "La educación es una herramienta esencial para el desarrollo personal, social y económico de cualquier sociedad. A través del acceso equitativo a una educación de calidad, se generan oportunidades para que las personas desarrollen su potencial, adquieran conocimientos relevantes y participen activamente en la construcción de un futuro más justo. La educación fomenta valores como la tolerancia, la empatía y la responsabilidad, promoviendo una convivencia pacífica y solidaria. Además, es clave para la innovación y el progreso, ya que impulsa la formación de profesionales capacitados que pueden enfrentar los desafíos del mundo moderno. Invertir en educación no solo mejora las condiciones de vida de las personas, sino que también fortalece la democracia, reduce las desigualdades y contribuye al crecimiento sostenible. Por ello, resulta imprescindible garantizar el acceso universal a una educación inclusiva, equitativa y continua a lo largo de toda la vida.";
        visitante.solicitarBorrarUnHecho(hecho, otraJustificacion);
        administrador.aprobarSolicitud(hecho.getSolicitudesDeEliminacion().get(1)); // POSICION 1, PORQUE LAS SOLICITUD ANTERIOR NO SE BORRA
        //Assertions.assertEquals(coleccion.getHechos().size(), 0);

        // VERIFICO QUE EL ESTADO SE SOLICITUD ESTA EN ESTADO ACEPTADA
        Assertions.assertEquals(hecho.getSolicitudesDeEliminacion().get(1).getEstado(), EstadoDeSolicitudDeEliminacion.APROBADA);
    }
}
