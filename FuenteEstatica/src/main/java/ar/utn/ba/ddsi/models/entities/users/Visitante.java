package ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteEstatica.models.entities.users;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class Visitante {
    private String nombre;
    private String apellido;
    private Integer edad;
    protected TipoDeVisitante tipoDeVisitante = TipoDeVisitante.VISUALIZADOR;

    public Visitante(String unNombre, String unApellido, Integer unEdad) {
        this.nombre = unNombre;
        this.apellido = unApellido;
        this.edad = unEdad;
    }
}




