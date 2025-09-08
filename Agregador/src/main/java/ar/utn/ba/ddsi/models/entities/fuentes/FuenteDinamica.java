package ar.utn.ba.ddsi.models.entities.fuentes;

import java.util.ArrayList;
import java.util.List;
import ar.utn.ba.ddsi.models.dtos.input.fuentesDeHechos.HechoInputDinamicaDTO;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.hecho.Lugar;
import ar.utn.ba.ddsi.models.entities.hecho.OrigenDelHecho;
import ar.utn.ba.ddsi.services.georef.LugarService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Getter
@Setter
public class FuenteDinamica implements IFuenteDeHechos {
    private final WebClient webClient;
    private Long id;
    private LugarService lugarService;

    public FuenteDinamica(String baseUrl, LugarService lugarService) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
        this.lugarService = lugarService;
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
                    for (HechoInputDinamicaDTO hechoResponseIndice : unosHechosResponse) {
                        Hecho unHecho = new Hecho();
                        unHecho.setIdEnFuente((hechoResponseIndice.getId()));
                        unHecho.setTitulo(hechoResponseIndice.getTitulo());
                        unHecho.setDescripcion(hechoResponseIndice.getDescripcion());
                        unHecho.setCategoria(hechoResponseIndice.getCategoria());
                        unHecho.setFechaDeAcontecimiento(hechoResponseIndice.getFechaDeAcontecimiento());
                        Lugar lugarHecho = new Lugar(hechoResponseIndice.getLugar().getLatitud(), hechoResponseIndice.getLugar().getLongitud());
                        if (lugarHecho != null) {
                            String provincia = String.valueOf(lugarService.obtenerProvincia(
                                    lugarHecho.getLatitud(),
                                    lugarHecho.getLongitud()
                            ));
                            lugarHecho.setProvincia(provincia);
                        }
                        unHecho.setLugar(lugarHecho);
                        unHecho.setFechaDeCargaDelHecho(hechoResponseIndice.getFechaDeCarga());
                        unHecho.setUsuarioContribuyente(hechoResponseIndice.getContribuyente());
                        unHecho.setEtiquetas(hechoResponseIndice.getEtiquetas());
                        unHecho.setSolicitudesDeEliminacion(hechoResponseIndice.getSolicitudesDeEliminacion());
                        unHecho.setOrigen(OrigenDelHecho.CONTRIBUYENTE);
                        unHecho.setMultimedia(hechoResponseIndice.getMultimedia());

                        hechos.add(unHecho);
                    }
                    return hechos;
                })
                .block();
    }
}












