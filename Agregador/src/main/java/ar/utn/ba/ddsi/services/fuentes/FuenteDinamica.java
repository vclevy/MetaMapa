package ar.utn.ba.ddsi.services.fuentes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ar.utn.ba.ddsi.models.dtos.input.fuentesDeHechos.HechoInputDinamicaDTO;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;

import ar.utn.ba.ddsi.models.entities.hecho.Multimedia;
import ar.utn.ba.ddsi.models.entities.hecho.OrigenDelHecho;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.stereotype.Component;

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
                        unHecho.setIdEnFuente(Integer.toUnsignedLong(hechoResponeIndice.getIdEnFuente()));
                        unHecho.setTitulo(hechoResponeIndice.getTitulo());
                        unHecho.setDescripcion(hechoResponeIndice.getDescripcion());
                        unHecho.setCategoria(hechoResponeIndice.getCategoria());
                        unHecho.setFechaDeAcontecimiento(hechoResponeIndice.getFechaDeAcontecimiento());
                        unHecho.setFechaDeCargaDelHecho(hechoResponeIndice.getFechaDeCarga());
                        unHecho.getLugar().setLatitud(hechoResponeIndice.getLugar().getLatitud());
                        unHecho.getLugar().setLongitud(hechoResponeIndice.getLugar().getLongitud());
                        unHecho.setUsuarioContribuyente(hechoResponeIndice.getContribuyente());
                        unHecho.setEtiquetas(hechoResponeIndice.getEtiquetas());
                        unHecho.setSolicitudesDeEliminacion(hechoResponeIndice.getSolicitudesDeEliminacion());
                        unHecho.setOrigen(OrigenDelHecho.FUENTEDINAMICA);
                        List<Multimedia> multimediaList = hechoResponeIndice.getMultimedia()
                                .stream()
                                .map(ruta -> {
                                    Multimedia m = new Multimedia();
                                    m.setRutaAlArchivo(ruta);
                                    return m;
                                })
                                .collect(Collectors.toList());

                        unHecho.setMultimedia(multimediaList);

                        hechos.add(unHecho);
                    }
                    return hechos;
                })
                .block();
    }
}












