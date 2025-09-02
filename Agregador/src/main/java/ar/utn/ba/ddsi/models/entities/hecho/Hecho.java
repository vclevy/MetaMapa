package ar.utn.ba.ddsi.models.entities.hecho;

import ar.utn.ba.ddsi.models.entities.usuario.Usuario;
import ar.utn.ba.ddsi.models.entities.solicitud.Solicitud;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@Entity
@Table(name = "hecho")
public class Hecho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_en_fuente", nullable = false)
    private Long idEnFuente;

    @Column(name = "titulo", nullable = false, length = 255)
    private String titulo;

    @Column(name = "descripcion", nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @Column(name = "fecha_acontecimiento", nullable = false)
    private LocalDate fechaDeAcontecimiento;

    @Column(name = "fecha_de_carga", nullable = false)
    private LocalDateTime fechaDeCargaDelHecho;

    @Embedded
    private Lugar lugar;

    @ElementCollection
    @CollectionTable(name = "hecho_multimedia", joinColumns = @JoinColumn(name = "hecho_id"))
    private List<String> multimedia;


    private Usuario usuarioContribuyente;
    private List<Etiqueta> etiquetas;
    private List<Solicitud> solicitudesDeEliminacion = new ArrayList<>();
    private Long idAgregador;
    private OrigenDelHecho origen;
    private Boolean esAnonimo = true;
    private Boolean fueEliminado = false;
}
