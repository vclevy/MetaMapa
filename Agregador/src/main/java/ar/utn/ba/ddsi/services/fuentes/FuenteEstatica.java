package ar.utn.ba.ddsi.services.fuentes;

import ar.utn.ba.ddsi.models.dtos.input.HechoInputDTO;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.hecho.OrigenDelHecho;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

public class FuenteEstatica implements FuenteDeHechos {
    private final WebClient webClient;

    public FuenteEstatica(String baseUrl, WebClient webClient) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public List<Hecho> obtenerHechos() {
        return webClient.get()
                .uri("/hechos")
                .retrieve()
                .bodyToFlux(HechoInputDTO.class)
                .collectList()
                .map(unosHechosResponse -> {
                    List<Hecho> hechos = new ArrayList<>();
                    for (HechoInputDTO hechoResponeIndice : unosHechosResponse) {
                        Hecho unHecho = new Hecho(
                                hechoResponeIndice.getIdEnFuente(),
                                hechoResponeIndice.getTitulo(),
                                hechoResponeIndice.getDescripcion(),
                                hechoResponeIndice.getCategoria(),
                                hechoResponeIndice.getFechaAcontecimiento(),
                                hechoResponeIndice.getLatitud(),
                                hechoResponeIndice.getLongitud(),
                                null, // multimedia todavía no se mapea
                                hechoResponeIndice.getUsuario()
                        );
                        unHecho.setOrigen(OrigenDelHecho.FUENTEESTATICA);
                        hechos.add(unHecho);
                    }
                    return hechos;
                })
                .block();
    }
}
