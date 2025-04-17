package domain.hecho.gestorDeHechos;

import domain.hecho.Hecho;
import domain.repositorioHechosDinamicos.RepositorioHechosDinamicos;


public class GestorDeHechos {

    public void cargarHecho(Hecho unHecho) {
        RepositorioHechosDinamicos.repositorioHechos.agregarHechos(unHecho);
        if (unHecho.getEsAnonimo()) {
            System.out.println("Hecho subido de forma anonima");
            // TODO A IMPLEMENTAR: funcion mostrar, q en base a si es anónimo o no muestre datos del usuario correspondientes
        } else {
            System.out.println("Hecho subido de forma publica");
            unHecho.setEsAnonimo(false);
            // TODO Posible implementacion -> Tabla intermedia con IdHecho (puede ser posicion de array) con IdUsuario ()
        }
    }
    }
