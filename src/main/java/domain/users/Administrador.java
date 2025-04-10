package domain.users;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import domain.coleccion.*; // importa toda la carpeta, sino hay que ir importando x clases
import domain.hecho.*;
import domain.hecho.origenDelHecho.Dataset;
import domain.hecho.origenDelHecho.OrigenDelHecho;

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;


public class Administrador {
    private String nombre;

//    public void importarHecho(Coleccion unaColeccion, String archivo) {
//
//        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
//            String linea;
//            boolean primeraLinea = true; // Inicialmente me encuentro en la primera linea. Caso true -> son encabezados
//            while ((linea = br.readLine()) != null) {
//
//                if (primeraLinea) {
//                    if (linea.toLowerCase().contains("titulo") || linea.toLowerCase().contains("título") || linea.toLowerCase().contains("descripcion")|| linea.toLowerCase().contains("descripción")) {
//                        primeraLinea = false;
//                        continue; // Salteo la primer linea y lo paso a false para continuar la lectura
//                    }
//                    primeraLinea = false; // Si no contiene encabezados, comienzo a leer
//                }
//
////                String[] campos = linea.split(",");
//                String[] campos = linea.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1); // Divido solo por comas si no estan dentro de comillas y verifica que haya una cantidad par de comillas.
//
//                for (int i = 0; i < campos.length; i++) {// Elimino comillas
//                    campos[i] = campos[i].trim().replaceAll("^\"|\"$", "");
//                }
//
//                String titulo = campos[0];
//                String descripcion = campos[1];
//                String nombreCategoria = campos[2];
//                double latitud = Double.parseDouble(campos[3]);
//                double longitud = Double.parseDouble(campos[4]);
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//                LocalDate fecha = LocalDate.parse(campos[5].trim(), formatter);
//                LocalDateTime fechaAcontecimiento = fecha.atStartOfDay();
//
//                LocalDateTime fechaDeCarga = LocalDateTime.now();
//
//                Lugar lugar = new Lugar(latitud, longitud);
//                OrigenDelHecho origen = new Dataset();
//                Categoria categoria = new Categoria(nombreCategoria);
//
//                Hecho hecho = new HechoDeTexto(titulo, descripcion, categoria, fechaAcontecimiento, fechaDeCarga, lugar, origen);
//
//                unaColeccion.agregarHechos(hecho);
//            }
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

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

    public Coleccion crearColeccion(String titulo, String descripcion){
        Coleccion coleccion = new Coleccion(titulo,descripcion);
        return coleccion;
    }


    public void gestionarSolicitudEliminacion(Hecho hecho) {

    }//TODO

    public void eliminarHecho(Hecho hecho) {

    }//TODO
}

