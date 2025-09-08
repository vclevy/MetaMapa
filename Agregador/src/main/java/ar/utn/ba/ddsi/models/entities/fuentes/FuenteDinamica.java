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
        // Obtener todos los DTOs de la fuente
        List<HechoInputDinamicaDTO> hechosDTO = webClient.get()
                .uri("/hechos")
                .retrieve()
                .bodyToFlux(HechoInputDinamicaDTO.class)
                .collectList()
                .block(); // Bloqueamos para obtener la lista

        List<Hecho> hechos = new ArrayList<>();
        if (hechosDTO != null) {
            for (HechoInputDinamicaDTO dto : hechosDTO) {
                Hecho unHecho = new Hecho();
                unHecho.setIdEnFuente(dto.getId());
                unHecho.setTitulo(dto.getTitulo());
                unHecho.setDescripcion(dto.getDescripcion());
                unHecho.setCategoria(dto.getCategoria());
                unHecho.setFechaDeAcontecimiento(dto.getFechaDeAcontecimiento());
                unHecho.setFechaDeCargaDelHecho(dto.getFechaDeCarga());
                unHecho.setEtiquetas(dto.getEtiquetas());
                unHecho.setSolicitudesDeEliminacion(dto.getSolicitudesDeEliminacion());
                unHecho.setOrigen(OrigenDelHecho.CONTRIBUYENTE);
                unHecho.setMultimedia(dto.getMultimedia());
                unHecho.setUsuarioContribuyente(dto.getContribuyente());

                // Crear el lugar
                if (dto.getLugar() != null) {
                    Lugar lugar = new Lugar(dto.getLugar().getLatitud(), dto.getLugar().getLongitud());

                    // Obtenemos la provincia de manera sincrónica
                    String provincia = lugarService.obtenerProvincia(lugar.getLatitud(), lugar.getLongitud());
                    lugar.setProvincia(provincia);

                    unHecho.setLugar(lugar);
                } else {
                    unHecho.setLugar(null);
                }

                hechos.add(unHecho);
            }
        }

        return hechos;
    }



}












