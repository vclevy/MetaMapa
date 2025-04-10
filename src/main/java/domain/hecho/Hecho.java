package domain.hecho;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import domain.coleccion.Categoria;
import domain.coleccion.Lugar;
import domain.hecho.origenDelHecho.*;
import domain.hecho.solicitudes.Solicitud;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class Hecho {
    protected String titulo;
    protected String descripcion;
    protected Categoria categoria;
    protected LocalDateTime fechaDeAcontecimiento;
    protected LocalDateTime fechaDeCarga;
    protected Lugar lugar;
    protected OrigenDelHecho origen;
    protected List<Solicitud> solicitudesDeEliminacion;
    // protected Etiqueta unaEtiqueta; SOLO LOS HECHOS Q APORTAN LOS CONTRIBUS


    // AGREGAR SOLICITUD DE ELIMINACION A UN HECHO
    public void agregarSolicitudDeElimnacion(String unaJustificacion) {
        Solicitud unaSolicitd = new Solicitud(unaJustificacion);
        solicitudesDeEliminacion.add(unaSolicitd);
    }
}
