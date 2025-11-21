package ar.utn.ba.ddsi.gateway.graphql.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HechoFilter {
    private String titulo;
    private String fuente;
    private String desde;
    private String hasta;
}
