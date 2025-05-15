package ar.utn.ba.ddsi.models.entities.users;
import ar.utn.ba.ddsi.models.importador.Importador;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter


public class Administrador {
    private String nombre;
    private Importador importador;
}


