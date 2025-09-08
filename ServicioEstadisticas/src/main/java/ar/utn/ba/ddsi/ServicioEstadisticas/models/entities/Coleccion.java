package ar.utn.ba.ddsi.ServicioEstadisticas.models.entities;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name="colecciones")
public class Coleccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;

    private List<Hecho> hechos;

}
