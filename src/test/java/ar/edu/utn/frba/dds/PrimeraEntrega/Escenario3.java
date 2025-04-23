package ar.edu.utn.frba.dds.PrimeraEntrega;

import domain.hecho.Categoria;
import domain.hecho.Hecho;
import domain.hecho.OrigenDelHecho;
import domain.hecho.lugar.Lugar;
import domain.users.Visitante;
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
    }

    @Test
    public void solicitudDeEliminacionEnEstadoPendiente() {
        // INSTANCIO UN VISITANTE
        visitante = new Visitante(
                "Matias",
                "Perez",
                24
        );

        // VISITANTE SOLICITA BORRAR UN HECHO --> HECHO DEBERIA ESTAR EN ESTADO PENDIENTE
        visitante.solicitarBorrarUnHecho(hecho);


    }
}
