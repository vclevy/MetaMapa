package domain.users;

import domain.coleccion.Hecho;
import lombok.Getter;
import lombok.Setter;


@Getter@Setter
public abstract class Usuario {
 protected TipoDeUsuario tipoDeUsuario = TipoDeUsuario.VISUALIZADOR;

 public void subirHecho(Hecho unHecho){}
 public void solicitarBorrarHecho(Hecho unHecho){}
}



