package ar.utn.ba.ddsi.gateway.models.entities.fuentes;

import ar.utn.ba.ddsi.gateway.models.dtos.input.hecho.HechoInputEstaticaDTO;
import ar.utn.ba.ddsi.gateway.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.gateway.models.entities.hecho.Lugar;
import ar.utn.ba.ddsi.gateway.models.entities.hecho.OrigenDelHecho;
import ar.utn.ba.ddsi.gateway.services.georef.LugarService;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

public class FuenteEstatica implements IFuenteDeHechos {
    private final WebClient webClient;
    private Long id;
    private final LugarService lugarService;

    public FuenteEstatica(String baseUrl, LugarService lugarService) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
        this.lugarService = lugarService;
    }

    @Override
    public List<Hecho> obtenerHechos() {
        List<HechoInputEstaticaDTO> hechosDTO = webClient.get()
                .uri("/hechos")
                .retrieve()
                .bodyToFlux(HechoInputEstaticaDTO.class)
                .collectList()
                .block();

        List<Hecho> hechos = new ArrayList<>();
        if (hechosDTO != null) {
            for (HechoInputEstaticaDTO dto : hechosDTO) {
                Hecho unHecho = new Hecho();
                unHecho.setIdEnFuente(dto.getId());
                unHecho.setTitulo(dto.getTitulo());
                unHecho.setDescripcion(dto.getDescripcion());
                unHecho.setCategoria(dto.getCategoria());
                unHecho.setFechaDeAcontecimiento(dto.getFechaDeAcontecimiento());
                unHecho.setFechaDeCargaDelHecho(dto.getFechaDeCarga());
                unHecho.setMultimedia(dto.getMultimedia());
                unHecho.setOrigen(OrigenDelHecho.FUENTEESTATICA);
                unHecho.setNombreArchivo(dto.getNombreArchivo());
                unHecho.setPendiente(false);
                unHecho.setFueAceptado(true);

                if (dto.getLugar() != null) {
                    Lugar lugar = new Lugar(dto.getLugar().getLatitud(), dto.getLugar().getLongitud());
                    unHecho.setLugar(lugar); // Por ahora sin provincia
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
