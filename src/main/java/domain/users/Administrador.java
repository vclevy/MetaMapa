package domain.users;
import domain.coleccion.*; // importa toda la carpeta, sino hay que ir importando x clases
import domain.hecho.*;
import domain.hecho.origenDelHecho.Dataset;
import domain.hecho.origenDelHecho.OrigenDelHecho;

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Administrador {
    private String nombre;

    public void importarHecho(Coleccion unaColeccion, String archivo) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(",");

                String titulo = campos[0];
                String descripcion = campos[1];
                String nombreCategoria = campos[2];
                double latitud = Double.parseDouble(campos[3]); // Tira error aca
                double longitud = Double.parseDouble(campos[4]);
                LocalDateTime fechaAcontecimiento = LocalDateTime.parse(campos[5], formatter);
                LocalDateTime fechaDeCarga = LocalDateTime.now();

                Lugar lugar = new Lugar(latitud, longitud);
                OrigenDelHecho origen = new Dataset();
                Categoria categoria = new Categoria(nombreCategoria);

                Hecho hecho = new HechoDeTexto(titulo, descripcion, categoria, fechaAcontecimiento, fechaDeCarga, lugar, origen);

                unaColeccion.agregarHechos(hecho);
            }
        }
        catch (IOException e) {
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

