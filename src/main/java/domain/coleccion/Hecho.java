package domain.coleccion;

import java.time.LocalDateTime;
import domain.users.Usuario;


public class Hecho {
/*
  - Usuario publicador
  - String titulo
  - String descripcion
  - Enum categoria
  - LocalDateTime fechaHora
  - Lugar lugar
  - Origen origen
  - Etiqueta unaEtiqueta
*/

    private Usuario usuario;
    private String titulo;
    private String descripcion;
    private Enum categoria;
    private LocalDateTime fechaHora;
    private Lugar lugar;
    private Origen origen;
    private Etiqueta unaEtiqueta;
}
