package ar.utn.ba.ddsi.services.fuentes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import ar.utn.ba.ddsi.models.dtos.input.fuentesDeHechos.HechoInputProxyDTO;
import ar.utn.ba.ddsi.models.entities.hecho.Categoria;
import ar.utn.ba.ddsi.models.entities.hecho.Lugar;
import ar.utn.ba.ddsi.models.entities.hecho.OrigenDelHecho;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import org.springframework.web.reactive.function.client.WebClient;

public class FuenteProxy implements IFuenteDeHechos {
    private final WebClient webClient;
    private Long id;
    private final TipoDeFuente tipoDeFuente = TipoDeFuente.PROXY;

    public FuenteProxy(String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public List<Hecho> obtenerHechos() {
        return webClient.get()
                .uri("/api/hechos")
                .retrieve()
                .bodyToFlux(HechoInputProxyDTO.class)
                .collectList()
                .map(
                        unosHechosResponse -> {
                            List<Hecho> hechos = new ArrayList<>();
                            for (HechoInputProxyDTO hechoResponseIndice : unosHechosResponse) {
                                Hecho unHecho = new Hecho();
                                unHecho.setId(hechoResponseIndice.getId());
                                unHecho.setTitulo(hechoResponseIndice.getTitulo());
                                unHecho.setDescripcion(hechoResponseIndice.getDescripcion());
                                Categoria categoria = new Categoria(hechoResponseIndice.getCategoria());
                                unHecho.setCategoria(categoria);
                                unHecho.setFechaDeAcontecimiento(hechoResponseIndice.getFechaHecho().toLocalDate());

                                Lugar lugar = new Lugar(hechoResponseIndice.getLatitud(), hechoResponseIndice.getLongitud());
                                unHecho.setLugar(lugar);

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
