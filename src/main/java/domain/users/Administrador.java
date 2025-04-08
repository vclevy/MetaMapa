package domain.users;
import domain.coleccion.*; // importa toda la carpeta, sino hay que ir importando x clases
import domain.hecho.*;
import domain.hecho.origenDelHecho.Dataset;

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Administrador {
    private String nombre;

    public void importarHecho(Coleccion unaColeccion, String archivo) {
        String linea;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); // adapta al formato real

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(",");

                String titulo = campos[0];
                String descripcion = campos[1];
                Categoria categoria = Categoria.valueOf(campos[2].toUpperCase());
                double latitud = Double.parseDouble(campos[3]);
                double longitud = Double.parseDouble(campos[4]);
                LocalDateTime fechaHora = LocalDateTime.parse(campos[5], formatter);

                Lugar lugar = new Lugar(latitud, longitud);

                Hecho hecho = new Hecho(this.nombre, titulo, descripcion, categoria, fechaHora, lugar, new Dataset(), null);

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

