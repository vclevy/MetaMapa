package domain.users;

import domain.coleccion.Coleccion;
import domain.hecho.Hecho;
import lombok.Getter;
import lombok.Setter;


@Getter@Setter
public abstract class Usuario {
 private String nombre;
 private String apellido;
 private Integer edad;

 protected TipoDeUsuario tipoDeUsuario = TipoDeUsuario.VISUALIZADOR; // un usuario arranca siendo visualizador

 public Usuario(String nombre, String apellido, Integer edad, TipoDeUsuario tipoDeUsuario) {
  this.nombre = nombre;
  this.apellido = apellido;
  this.edad = edad;
 }

 public void subirHechoAnonimo(Hecho unHecho){
  this.setTipoDeUsuario(TipoDeUsuario.CONTRIBUYENTE); // Una vez que suben un hecho se transforman en Contribuyentes
 }//TODO

 public void subirHecho() {
  this.setTipoDeUsuario(TipoDeUsuario.CONTRIBUYENTE);
 } //TODO

 public void solicitarBorrarHecho(Hecho unHecho){}//TODO

 public void navegarHechos(Coleccion unaColeccion){} //TODO


}


