package domain.users;

import domain.coleccion.Hecho;

public class UsuarioAnonimo extends Usuario {

    public void registrarse(String nombre, String apellido, Integer edad){
        UsuarioRegistrado usuario = new UsuarioRegistrado(nombre, apellido, edad, tipoDeUsuario);
    }

    @Override public void subirHecho(Hecho unHecho){
        this.setTipoDeUsuario(TipoDeUsuario.CONTRIBUYENTE);
    }
}
