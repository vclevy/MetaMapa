package ar.utn.ba.ddsi.models.entities.fuentes;

import ar.utn.ba.ddsi.models.dtos.input.fuentesDeHechos.HechoInputEstaticaDTO;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.hecho.Lugar;
import ar.utn.ba.ddsi.models.entities.hecho.OrigenDelHecho;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.ArrayList;
import java.util.List;

public class FuenteEstatica implements IFuenteDeHechos {
    private final WebClient webClient;
    private Long id;

    public FuenteEstatica(String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public List<Hecho> obtenerHechos() {
        return webClient.get()
                .uri("/hechos")
                .retrieve()
                .bodyToFlux(HechoInputEstaticaDTO.class)
                .collectList()
                .map(unosHechosResponse -> {
                    List<Hecho> hechos = new ArrayList<>();
                    for (HechoInputEstaticaDTO hechoResponseIndice : unosHechosResponse) {
                        Hecho unHecho = new Hecho();
                        unHecho.setIdEnFuente(hechoResponseIndice.getId());
                        unHecho.setTitulo(hechoResponseIndice.getTitulo());
                        unHecho.setDescripcion(hechoResponseIndice.getDescripcion());
                        unHecho.setCategoria(hechoResponseIndice.getCategoria());
                        unHecho.setFechaDeAcontecimiento(hechoResponseIndice.getFechaDeAcontecimiento());
                        unHecho.setFechaDeCargaDelHecho(hechoResponseIndice.getFechaDeCarga());
                        Lugar lugarHecho = null;
                        if (hechoResponseIndice.getLugar() != null) {
                            lugarHecho = new Lugar(
                                    hechoResponseIndice.getLugar().getLatitud(),
                                    hechoResponseIndice.getLugar().getLongitud()
                            );
                        }
                        unHecho.setLugar(lugarHecho);
                        unHecho.setMultimedia(hechoResponseIndice.getMultimedia());
                        unHecho.setOrigen(OrigenDelHecho.FUENTEESTATICA);

                        hechos.add(unHecho);
                    }
                    return hechos;
                })
                .block();
    }
}
