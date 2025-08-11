package ar.utn.ba.ddsi.services.fuentes;

import ar.utn.ba.ddsi.models.dtos.input.fuentesDeHechos.HechoInputDTO;
import ar.utn.ba.ddsi.models.dtos.input.fuentesDeHechos.HechoInputEstaticaDTO;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.hecho.OrigenDelHecho;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

public class FuenteEstatica implements IFuenteDeHechos {
    private final WebClient webClient;
    private Long id;
    private final TipoDeFuente tipoDeFuente = TipoDeFuente.ESTATICA;

    public FuenteEstatica(String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public TipoDeFuente getTipoDeFuente() {
        return tipoDeFuente;
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
                    for (HechoInputEstaticaDTO hechoResponeIndice : unosHechosResponse) {
                        Hecho unHecho = new Hecho();
                        unHecho.setIdEnFuente(hechoResponeIndice.getId());
                        unHecho.setTitulo(hechoResponeIndice.getTitulo());
                        unHecho.setDescripcion(hechoResponeIndice.getDescripcion());
                        unHecho.setCategoria(hechoResponeIndice.getCategoria());
                        unHecho.setFechaDeAcontecimiento(hechoResponeIndice.getFechaDeAcontecimiento());
                        unHecho.setFechaDeCargaDelHecho(hechoResponeIndice.getFechaDeCarga());
                        unHecho.getLugar().setLatitud(hechoResponeIndice.getLugar().getLatitud());
                        unHecho.getLugar().setLongitud(hechoResponeIndice.getLugar().getLongitud());
                        unHecho.setMultimedia(hechoResponeIndice.getMultimedia());

                        // todo: etiquetas, usuario contribuyente??

                        unHecho.setOrigen(OrigenDelHecho.FUENTEESTATICA);
                        hechos.add(unHecho);
                    }
                    return hechos;
                })
                .block();
    }
}
