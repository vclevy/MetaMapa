package ar.utn.ba.ddsi.FuenteProxy.models.dtos;

import lombok.Data;


@Data
public class HechoProxyDTO {
    private int id;
    private String titulo;
    private String descripcion;
    private String categoria;
    private double latitud;
    private double longitud;
    private String fecha_hecho;  // como viene en el JSON, string ISO
    private String created_at;    // idem
    private String updated_at;    // idem

}
