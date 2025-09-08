import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.Coleccion;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.dtos.input.ColeccionInputDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;

@Service
public class ColeccionService {

    private final WebClient webClient;

    public ColeccionService() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8080") // URL de tu agregador
                .build();
    }

    public List<Coleccion> obtenerColecciones() {
        return webClient.get()
                .uri("/api/colecciones") 
                .retrieve()
                .bodyToFlux(ColeccionInputDTO.class)
                .map(this::inputDTOaColeccion)
                .collectList()
                .block();
    }

    private Coleccion inputDTOaColeccion(ColeccionInputDTO dto) {
        Coleccion coleccion = new Coleccion();
        //setear los hechos que le llegan
        coleccion.setNombre(dto.getNombre());
        // mapeás otros campos que necesites
        return coleccion;
    }
}
