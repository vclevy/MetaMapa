package domain.repositorioHechosDinamicos;

import domain.hecho.Hecho;
import lombok.Getter;

import java.util.Collections;
import java.util.List;


@Getter
public class RepositorioHechosDinamicos {

    public static RepositorioHechosDinamicos repositorioHechos = new RepositorioHechosDinamicos();

    private List<Hecho> hechosSubidos;

    public void agregarHechos(Hecho ... unosHechos){
        Collections.addAll(this.hechosSubidos, unosHechos);
    }
}
