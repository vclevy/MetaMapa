package ar.utn.ba.ddsi.models.entities.hecho;

import ar.utn.ba.ddsi.models.entities.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "modificaciones_hecho")
@Getter
@Setter
public class ModificacionHecho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "editor_id")
    private Usuario editor; // puede ser null

    @Column(nullable = false)
    private String campoEditado;

    @Column(nullable = false)
    private String nuevoValor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hecho_id", nullable = false)
    private Hecho hecho;

    public ModificacionHecho(LocalDateTime fecha, Usuario editor, String campoEditado, String nuevoValor) {
        this.fecha = fecha;
        this.editor = editor;
        this.campoEditado = campoEditado;
        this.nuevoValor = nuevoValor;
    }

    public ModificacionHecho() {
        
    }
}
