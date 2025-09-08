package ar.utn.ba.ddsi.models.entities.hecho;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import reactor.core.publisher.Mono;

@Getter
@Setter
@Embeddable
public class Lugar {
    @Column(name ="latitud", nullable = false)
    private Double latitud;

    @Column(name = "longitud", nullable = false)
    private Double longitud;

    @Column(name= "provicia")
    private String provincia;

    public Lugar(Double latitud, Double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Lugar() {

    }
}