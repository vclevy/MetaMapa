package ar.utn.ba.ddsi.models.entities.fuentes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import ar.utn.ba.ddsi.models.dtos.input.fuentesDeHechos.HechoInputProxyDTO;
import ar.utn.ba.ddsi.models.entities.hecho.Categoria;
import ar.utn.ba.ddsi.models.entities.hecho.Lugar;
import ar.utn.ba.ddsi.models.entities.hecho.OrigenDelHecho;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.services.georef.LugarService;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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
        return webClient.get()
                .uri("/api/hechos")
                .retrieve()
                .bodyToFlux(HechoInputProxyDTO.class)
                .flatMap(hechoResponseIndice -> {
                    Hecho unHecho = new Hecho();
                    unHecho.setIdEnFuente(hechoResponseIndice.getId());
                    unHecho.setTitulo(hechoResponseIndice.getTitulo());
                    unHecho.setDescripcion(hechoResponseIndice.getDescripcion());
                    Categoria categoria = new Categoria(hechoResponseIndice.getCategoria());
                    unHecho.setCategoria(categoria);
                    unHecho.setFechaDeAcontecimiento(hechoResponseIndice.getFechaHecho().toLocalDate());
                    unHecho.setFechaDeCargaDelHecho(LocalDateTime.now());
                    unHecho.setEtiquetas(new ArrayList<>());
                    unHecho.setSolicitudesDeEliminacion(new ArrayList<>());
                    unHecho.setOrigen(OrigenDelHecho.FUENTEPROXY);

                    Lugar lugar = new Lugar(hechoResponseIndice.getLatitud(), hechoResponseIndice.getLongitud());

                    if (lugar != null) {
                        // Obtener provincia de manera reactiva
                        return lugarService.obtenerProvincia(lugar.getLatitud(), lugar.getLongitud())
                                .map(provincia -> {
                                    lugar.setProvincia((String) provincia);
                                    unHecho.setLugar(lugar);
                                    return unHecho;
                                });
                    } else {
                        unHecho.setLugar(null);
                        return Mono.just(unHecho);
                    }
                })
                .collectList()
                .block();
    }
}
