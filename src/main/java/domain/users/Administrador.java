package domain.users;
import domain.coleccion.*; // importa toda la carpeta, sino hay que ir importando x clases

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Administrador {

    public void importarHecho(Coleccion unaColeccion, String archivo) {
        String linea;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); // adapta al formato real

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(",");

                String titulo = campos[0];
                String descripcion = campos[1];
                Categoria categoria = Categoria.valueOf(campos[2]);
                LocalDateTime fechaHora = LocalDateTime.parse(campos[3], formatter);
                Lugar lugar = new Lugar(campos[4]); // adapta a tu constructor
                OrigenDelHecho origen = new OrigenDelHecho(campos[5]);
                Etiqueta etiqueta = new Etiqueta(campos[6]);

                Hecho hecho = new Hecho(this, titulo, descripcion, categoria, fechaHora, lugar, origen, etiqueta);

                unaColeccion.agregarHechos(hecho);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void crearColeccion(Hecho... hechos){} //TODO
    public void gestionarSolicitudEliminacion(Hecho hecho) {

    }//TODO

    public void eliminarHecho(Hecho hecho) {

    }//TODO


}

