package ar.edu.utn.frba.dds.PrimeraEntrega;

import domain.coleccion.Categoria;
import domain.coleccion.Coleccion;
import domain.coleccion.CriterioDePertenencia;
import domain.hecho.*;
import domain.hecho.FiltroHecho.FiltroHecho;
import domain.hecho.etiqueta.Etiqueta;
import domain.hecho.lugar.Lugar;
import domain.users.Visitante;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Escenario1 {

    private Hecho hecho0;
    private Hecho hecho1;
    private Hecho hecho2;
    private Hecho hecho3;
    private Hecho hecho4;
    private Coleccion coleccionPrueba;

    @BeforeEach
    public void setUp() {
        hecho0 = new Hecho(
                "Caída de aeronave impacta en Olavarría",
                "Grave caída de aeronave ocurrió en las inmediaciones de Olavarría, Buenos Aires...",
                new Categoria("Caída de aeronave"),
                LocalDate.of(2001, 11, 29),
                LocalDateTime.now(),
                new Lugar(-36.868375, -60.343297),
                OrigenDelHecho.CARGA_MANUAL,
                false
        );

        hecho1 = new Hecho(
                "Serio incidente: Accidente con maquinaria industrial en Chos Malal, Neuquén",
                "Un grave accidente con maquinaria industrial se registró en Chos Malal, Neuquén...",
                new Categoria("Accidente con maquinaria industrial"),
                LocalDate.of(2001, 8, 16),
                LocalDateTime.now(),
                new Lugar(-37.345571, -70.241485),
                OrigenDelHecho.CARGA_MANUAL,
                false
        );

        hecho2 = new Hecho(
                "Caída de aeronave impacta en Venado Tuerto, Santa Fe",
                "Grave caída de aeronave ocurrió en Venado Tuerto, Santa Fe...",
                new Categoria("Caída de aeronave"),
                LocalDate.of(2008, 8, 8),
                LocalDateTime.now(),
                new Lugar(-33.768051, -61.921032),
                OrigenDelHecho.CARGA_MANUAL,
                true
        );

        hecho3 = new Hecho(
                "Accidente en paso a nivel deja múltiples daños en Pehuajó, Buenos Aires",
                "Grave accidente en paso a nivel en Pehuajó, Buenos Aires...",
                new Categoria("Accidente en paso a nivel"),
                LocalDate.of(2020, 1, 27),
                LocalDateTime.now(),
                new Lugar(-35.855811, -61.940589),
                OrigenDelHecho.CARGA_MANUAL,
                false
        );

        hecho4 = new Hecho(
                "Devastador Derrumbe en obra en construcción afecta a Presidencia Roque Sáenz Peña",
                "Un grave derrumbe en obra en construcción en Sáenz Peña, Chaco...",
                new Categoria("Derrumbe en obra en construcción"),
                LocalDate.of(2016, 6, 4),
                LocalDateTime.now(),
                new Lugar(-26.780008, -60.458782),
                OrigenDelHecho.CARGA_MANUAL,
                false
        );

        coleccionPrueba = new Coleccion();
        coleccionPrueba.setTitulo("Colección prueba");
        coleccionPrueba.setDescripcion("Esto es una prueba");
    }

    @Test
    public void crearColeccion() {
        coleccionPrueba.agregarHecho(hecho0);
        coleccionPrueba.agregarHecho(hecho1);
        coleccionPrueba.agregarHecho(hecho2);
        coleccionPrueba.agregarHecho(hecho3);
        coleccionPrueba.agregarHecho(hecho4);

        System.out.println("Hechos en la colección '" + coleccionPrueba.getTitulo() + "':");
        coleccionPrueba.getHechos().values().forEach(h -> {
            System.out.println("- " + h.getTitulo() + ": " + h.getDescripcion());
        });
    }

    @Test
    public void criterioDePertenencia1() {
        class CriterioFechaEntre2000y2010 implements CriterioDePertenencia {
            private final LocalDate fechaInicio = LocalDate.of(2000, 1, 1);
            private final LocalDate fechaFin = LocalDate.of(2010, 1, 1);

            @Override
            public boolean cumple(Hecho unHecho) {
                LocalDate fecha = unHecho.getFechaDeAcontecimiento();
                return (fecha != null) && (!fecha.isBefore(fechaInicio) && !fecha.isAfter(fechaFin));
            }
        }

        coleccionPrueba.agregarCriterioDePertenencia(new CriterioFechaEntre2000y2010());

        coleccionPrueba.agregarHecho(hecho0);
        coleccionPrueba.agregarHecho(hecho1);
        coleccionPrueba.agregarHecho(hecho2);
        coleccionPrueba.agregarHecho(hecho3);
        coleccionPrueba.agregarHecho(hecho4);

        Map<String, Hecho> hechos = coleccionPrueba.getHechos();

        assertEquals(3, hechos.size());
        assertTrue(hechos.containsKey(hecho0.getTitulo()));
        assertTrue(hechos.containsKey(hecho1.getTitulo()));
        assertTrue(hechos.containsKey(hecho2.getTitulo()));
    }

    @Test
    public void criterioDePertenencia2() {
        class CriterioFechaEntre2000y2010 implements CriterioDePertenencia {
            private final LocalDate fechaInicio = LocalDate.of(2000, 1, 1);
            private final LocalDate fechaFin = LocalDate.of(2010, 1, 1);

            @Override
            public boolean cumple(Hecho unHecho) {
                LocalDate fecha = unHecho.getFechaDeAcontecimiento();
                return (fecha != null) && (!fecha.isBefore(fechaInicio) && !fecha.isAfter(fechaFin));
            }
        }

        class CriterioCategoriaCaidaDeAeronave implements CriterioDePertenencia {
            @Override
            public boolean cumple(Hecho unHecho) {
                return unHecho.getCategoria() != null
                        && "caída de aeronave".equalsIgnoreCase(unHecho.getCategoria().getNombre());
            }
        }

        coleccionPrueba.agregarCriterioDePertenencia(new CriterioFechaEntre2000y2010());
        coleccionPrueba.agregarCriterioDePertenencia(new CriterioCategoriaCaidaDeAeronave());

        coleccionPrueba.agregarHecho(hecho0);
        coleccionPrueba.agregarHecho(hecho1);
        coleccionPrueba.agregarHecho(hecho2);
        coleccionPrueba.agregarHecho(hecho3);
        coleccionPrueba.agregarHecho(hecho4);

        Map<String, Hecho> hechos = coleccionPrueba.getHechos();

        assertEquals(2, hechos.size());
        assertTrue(hechos.containsKey(hecho0.getTitulo()));
        assertTrue(hechos.containsKey(hecho2.getTitulo()));
    }

    @Test
    public void filtroPorCategoriaYTitulo(){
        FiltroHecho filtroCategoria = new FiltroHecho() {
            @Override
            public boolean aplica(Hecho hecho) {
                return hecho.getCategoria() != null
                        && "Caída de Aeronave".equalsIgnoreCase(hecho.getCategoria().getNombre());
            }
        };

        FiltroHecho filtroTitulo = new FiltroHecho() {
            @Override
            public boolean aplica(Hecho hecho) {
                return "un título".equalsIgnoreCase(hecho.getTitulo());
            }
        };

        List<FiltroHecho> filtros = List.of(filtroCategoria, filtroTitulo);

        Visitante visualizador = new Visitante("Juan","Perez",30);
        List<Hecho> filtrados = visualizador.filtrar(coleccionPrueba, filtros);

        // Verificación
        assertTrue(filtrados.isEmpty(), "No debería haber hechos que cumplan con ambos filtros.");
    }

    @Test
    public void etiquetas(){

        Etiqueta olavarria = new Etiqueta("Olavarria");
        Etiqueta grave = new Etiqueta("grave");

        hecho0.agregarEtiquetas(olavarria,grave);

        assertTrue(hecho0.getEtiquetas().contains(olavarria), "Debe contener la etiqueta 'Olavarria'");
        assertTrue(hecho0.getEtiquetas().contains(grave), "Debe contener la etiqueta 'grave'");
    }
}
