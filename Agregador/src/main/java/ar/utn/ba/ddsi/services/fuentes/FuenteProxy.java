package ar.utn.ba.ddsi.services.fuentes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ar.utn.ba.ddsi.models.dtos.input.fuentesDeHechos.HechoInputProxyDTO;
import ar.utn.ba.ddsi.models.entities.hecho.OrigenDelHecho;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class FuenteProxy implements IFuenteDeHechos {
    private final String urlHechos;
    private final String pathUrl;
    private final WebClient webClient;
    private Long id;

    public FuenteProxy(String urlHechos, @Value("${fuente-proxy-url}") String baseUrl, @Value("${fuente-proxy-path}") String pathUrl) {
        this.urlHechos = urlHechos;
        this.pathUrl = pathUrl;
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    @Override
    public List<Hecho> obtenerHechos() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(pathUrl)
                        .queryParam("url", urlHechos)
                        .build())
                .retrieve()
                .bodyToFlux(HechoInputProxyDTO.class)
                .collectList()
                .map(
                        unosHechosResponse -> {
                            List<Hecho> hechos = new ArrayList<>();
                            for (HechoInputProxyDTO hechoResponeIndice : unosHechosResponse) {
                                Hecho unHecho = new Hecho();
                                unHecho.setIdEnFuente(Long.valueOf(hechoResponeIndice.getId()));
                                unHecho.setTitulo(hechoResponeIndice.getTitulo());
                                unHecho.setDescripcion(hechoResponeIndice.getDescripcion());
                                unHecho.getCategoria().setNombre(hechoResponeIndice.getCategoria());
                                unHecho.setFechaDeAcontecimiento(hechoResponeIndice.getFechaHecho().toLocalDate());
                                unHecho.getLugar().setLatitud(hechoResponeIndice.getLatitud());
                                unHecho.getLugar().setLongitud(hechoResponeIndice.getLongitud());

                                unHecho.setFechaDeCargaDelHecho(LocalDateTime.now());
                                unHecho.setEtiquetas(new ArrayList<>());
                                unHecho.setSolicitudesDeEliminacion(new ArrayList<>());
                                unHecho.setOrigen(OrigenDelHecho.FUENTEPROXY);

                                hechos.add(unHecho);
                            }
                            return hechos;
                        })
                .block();
    }
}
