package ar.utn.ba.ddsi.services.fuentes;

import java.util.ArrayList;
import java.util.List;

import ar.utn.ba.ddsi.models.dtos.input.fuentesDeHechos.HechoInputDinamicaDTO;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;

import ar.utn.ba.ddsi.models.entities.hecho.Lugar;
import ar.utn.ba.ddsi.models.entities.hecho.OrigenDelHecho;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.reactive.function.client.WebClient;

@Getter
@Setter
public class FuenteDinamica implements IFuenteDeHechos {
    private final WebClient webClient;
    private Long id;
    private final TipoDeFuente tipoDeFuente = TipoDeFuente.DINAMICA;

    public FuenteDinamica(String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public List<Hecho> obtenerHechos() {
        return webClient.get()
                .uri("/hechos")
                .retrieve()
                .bodyToFlux(HechoInputDinamicaDTO.class)
                .collectList()
                .map(unosHechosResponse -> {
                    List<Hecho> hechos = new ArrayList<>();
                    for (HechoInputDinamicaDTO hechoResponeIndice : unosHechosResponse) {
                        Hecho unHecho = new Hecho();
                        unHecho.setId((hechoResponeIndice.getIdEnFuente()));
                        unHecho.setTitulo(hechoResponeIndice.getTitulo());
                        unHecho.setDescripcion(hechoResponeIndice.getDescripcion());
                        unHecho.setCategoria(hechoResponeIndice.getCategoria());
                        unHecho.setFechaDeAcontecimiento(hechoResponeIndice.getFechaDeAcontecimiento());
                        Lugar lugarHecho = new Lugar(hechoResponeIndice.getLugar().getLatitud(), hechoResponeIndice.getLugar().getLongitud());
                        unHecho.setLugar(lugarHecho);
                        unHecho.setFechaDeCargaDelHecho(hechoResponeIndice.getFechaDeCarga());
                        unHecho.setUsuarioContribuyente(hechoResponeIndice.getContribuyente());
                        unHecho.setEtiquetas(hechoResponeIndice.getEtiquetas());
                        unHecho.setSolicitudesDeEliminacion(hechoResponeIndice.getSolicitudesDeEliminacion());
                        unHecho.setOrigen(OrigenDelHecho.CONTRIBUYENTE);
                        unHecho.setMultimedia(hechoResponeIndice.getMultimedia());

                        hechos.add(unHecho);
                    }
                    return hechos;
                })
                .block();
    }
}












