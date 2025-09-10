package ar.utn.ba.ddsi.models.entities.fuentes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import ar.utn.ba.ddsi.models.dtos.input.hecho.HechoInputProxyDTO;
import ar.utn.ba.ddsi.models.entities.hecho.Categoria;
import ar.utn.ba.ddsi.models.entities.hecho.Lugar;
import ar.utn.ba.ddsi.models.entities.hecho.OrigenDelHecho;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.services.georef.LugarService;
import org.springframework.web.reactive.function.client.WebClient;

public class FuenteProxy implements IFuenteDeHechos {
    private final WebClient webClient;
    private Long id;
    private final TipoDeFuente tipoDeFuente = TipoDeFuente.PROXY;
    private final LugarService lugarService;

    public FuenteProxy(String baseUrl, LugarService lugarSerivce) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
        this.lugarService = lugarSerivce;
    }

    @Override
    public List<Hecho> obtenerHechos() {
        List<HechoInputProxyDTO> hechosDTO = webClient.get()
                .uri("/api/hechos")
                .retrieve()
                .bodyToFlux(HechoInputProxyDTO.class)
                .collectList()
                .block();

        List<Hecho> hechos = new ArrayList<>();
        if (hechosDTO != null) {
            for (HechoInputProxyDTO dto : hechosDTO) {
                Hecho unHecho = new Hecho();
                unHecho.setIdEnFuente(dto.getId());
                unHecho.setTitulo(dto.getTitulo());
                unHecho.setDescripcion(dto.getDescripcion());
                unHecho.setCategoria(new Categoria(dto.getCategoria()));
                unHecho.setFechaDeAcontecimiento(dto.getFechaHecho().toLocalDate());
                unHecho.setFechaDeCargaDelHecho(LocalDateTime.now());
                unHecho.setEtiquetas(new ArrayList<>());
                unHecho.setSolicitudesDeEliminacion(new ArrayList<>());
                unHecho.setOrigen(OrigenDelHecho.FUENTEPROXY);

                if (dto.getLatitud() != null && dto.getLongitud() != null) {
                    Lugar lugar = new Lugar(dto.getLatitud(), dto.getLongitud());
                    unHecho.setLugar(lugar);
                } else {
                    unHecho.setLugar(null);
                }
                hechos.add(unHecho);
            }

            for (Hecho hecho : hechos) {
                if (hecho.getLugar() != null) {
                    String provincia = lugarService.obtenerProvincia(
                            hecho.getLugar().getLatitud(),
                            hecho.getLugar().getLongitud()
                    );
                    hecho.getLugar().setProvincia(provincia);
                }
            }
        }

        return hechos;
    }
}
