package ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteEstatica.models.entities.users;
import ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteEstatica.models.importador.Importador;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter


public class Administrador {
    private String nombre;
    private Importador importador;
}


