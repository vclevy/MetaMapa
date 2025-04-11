package domain.users;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import domain.coleccion.*; // importa toda la carpeta, sino hay que ir importando x clases
import domain.hecho.*;
import domain.hecho.origenDelHecho.Dataset;
import domain.hecho.origenDelHecho.OrigenDelHecho;
import domain.hecho.solicitudes.EstadoDeSolicitudDeEliminacion;
import domain.hecho.solicitudes.Solicitud;

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;


public class Administrador {
    private String nombre;
    private List<Solicitud> solicitudesDeEliminacionDeHecho;

    /*---------------------------------------- IMPORTAR ARCHIVO ------------------------------------------*/
    public void importarHecho(Coleccion unaColeccion, String archivo) {
        try (CSVReader reader = new CSVReader(new FileReader(archivo))) {
            String[] campos;
            boolean primeraLinea = true;

            while ((campos = reader.readNext()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }

                try {
                    String titulo = campos[0].trim();
                    String descripcion = campos[1].trim();
                    String categoria = campos[2].trim();
                    double latitud = Double.parseDouble(campos[3].trim());
                    double longitud = Double.parseDouble(campos[4].trim());
                    LocalDate fecha = LocalDate.parse(campos[5].trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                    Hecho hecho = new HechoDeTexto(
                            titulo,
                            descripcion,
                            new Categoria(categoria),
                            fecha.atStartOfDay(),
                            LocalDateTime.now(),
                            new Lugar(latitud, longitud),
                            new Dataset()
                    );

                    unaColeccion.agregarHechos(hecho);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException | CsvValidationException e) {
            System.err.println("Error al leer el archivo: " + archivo);
            e.printStackTrace();
        }
    }

    /*------------------------------------------ CREAR COLECCION -----------------------------------------*/
    public Coleccion crearColeccion(String titulo, String descripcion){
        Coleccion coleccion = new Coleccion(titulo,descripcion);
        return coleccion;
    }

    /*--------------------------------------- ADMINISTRAR SOLICITUDES--------------------------------------*/
    public List<Solicitud> getSolicitudesDeEliminacionDeHechoPendientes() {
        return solicitudesDeEliminacionDeHecho.stream().filter(unaSolicitud -> unaSolicitud.getEstado() == EstadoDeSolicitudDeEliminacion.PENDIENTE).toList();
    }

    public void gestionarSolicitudesPendientes() {
        getSolicitudesDeEliminacionDeHechoPendientes().forEach(this::evaluarEstadoDeSolicitudPendiente);
    }

    public void evaluarEstadoDeSolicitudPendiente(Solicitud unaSolicitud) {
        if (unaSolicitud.getJustificacionDeEliminacion() == null || unaSolicitud.getJustificacionDeEliminacion().length() < 500) {
            unaSolicitud.setEstado(EstadoDeSolicitudDeEliminacion.RECHAZADA);
            throw new IllegalArgumentException("La justificacion debe tener al menos 500 caracteres");
        } else {
            unaSolicitud.setEstado(EstadoDeSolicitudDeEliminacion.APROBADA);
            // TODO: BORRAR DE LA COLECCION
        }
    }
}

