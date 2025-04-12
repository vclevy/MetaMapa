package domain.hecho;

import domain.coleccion.Categoria;
import domain.coleccion.Lugar;
import domain.hecho.origenDelHecho.OrigenDelHecho;

import javax.swing.text.StyledEditorKit;
import java.time.LocalDateTime;

public class HechoDeTexto extends Hecho {

    public HechoDeTexto(
            String titulo,
            String descripcion,
            Categoria categoria,
            LocalDateTime fechaAcontecimiento,
            LocalDateTime fechaDeCarga,
            Lugar lugar,
            OrigenDelHecho origen,
            Boolean esAnonimo
    ) {
        super();
    }
}
