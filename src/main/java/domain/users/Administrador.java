package domain.users;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import domain.coleccion.*;
import domain.hecho.*;
import domain.hecho.origenDelHecho.Dataset;
import domain.hecho.solicitudes.Solicitud;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import static domain.hecho.solicitudes.EstadoDeSolicitudDeEliminacion.*;


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
                            new Dataset(),
                            false
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
    public Coleccion crearColeccion(String titulo, String descripcion) {
        Coleccion coleccion = new Coleccion(titulo, descripcion);
        return coleccion;
    }

    /*--------------------------------------- ADMINISTRAR SOLICITUDES--------------------------------------*/
    public List<Solicitud> getSolicitudesDeEliminacionDeHechoPendientes() {
        return solicitudesDeEliminacionDeHecho.stream().filter(unaSolicitud -> unaSolicitud.getEstado() == PENDIENTE).toList();
    }

    public List<Solicitud> solicitudesValidas() {
        for (Solicitud solicitud : getSolicitudesDeEliminacionDeHechoPendientes()) {
            if (solicitud.getJustificacionDeEliminacion() == null || solicitud.getJustificacionDeEliminacion().length() < 500) {
                solicitud.setEstado(RECHAZADA);
                throw new IllegalArgumentException("La justificacion debe tener al menos 500 caracteres");
            }
        }
        return getSolicitudesDeEliminacionDeHechoPendientes().stream().filter(solicitud -> solicitud.getEstado()!=RECHAZADA).toList();
    }

        public void evaluarSolicitud() {
            Scanner scanner = new Scanner(System.in);

            for (Solicitud solicitud : solicitudesValidas()) {
                System.out.println("----- Solicitud -----");
                System.out.println("Hecho solicitado: " + solicitud.getHecho().getTitulo());
                System.out.println("Motivo: " + solicitud.getJustificacionDeEliminacion());

                System.out.print("¿Aprobar esta solicitud? (s/n): ");
                String input = scanner.nextLine().trim().toLowerCase();

                if (input.equals("s")) {
                    aprobarSolicitud(solicitud);
                    System.out.println("Solicitud aprobada.");
                } else {
                    rechazarSolicitud(solicitud);
                    System.out.println("Solicitud rechazada.");
                }

                System.out.println("----------------------\n");
            }
        }

        public void aprobarSolicitud(Solicitud unaSolicitud) {
            unaSolicitud.setEstado(APROBADA);
            this.getSolicitudesDeEliminacionDeHechoPendientes().remove(unaSolicitud);
            //Cuando un hecho se quiera mostrar en interfaz, se debe verificar en sus solicitudes de eliminacion asociadas, que no haya ninguna aprobada
        }

        public void rechazarSolicitud (Solicitud unaSolicitud) {
            unaSolicitud.setEstado(RECHAZADA);
            this.getSolicitudesDeEliminacionDeHechoPendientes().remove(unaSolicitud);
        }
}


