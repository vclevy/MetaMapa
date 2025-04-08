package domain.hecho;


import java.time.LocalDateTime;
import domain.coleccion.Lugar;
import domain.hecho.origenDelHecho.*;

public abstract class Hecho {
    protected String titulo;
    protected String descripcion;
    protected Enum categoria;
    protected LocalDateTime fechaHora;
    protected Lugar lugar;
    protected OrigenDelHecho origen;
    protected Etiqueta unaEtiqueta;
}
