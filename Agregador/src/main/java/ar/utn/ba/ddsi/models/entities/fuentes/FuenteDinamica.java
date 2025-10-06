package ar.utn.ba.ddsi.models.entities.fuentes;

import java.util.ArrayList;
import java.util.List;
import ar.utn.ba.ddsi.models.dtos.input.hecho.HechoInputDinamicaDTO;
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
        List<HechoInputDinamicaDTO> hechosDTO = webClient.get()
                .uri("/hechos")
                .retrieve()
                .bodyToFlux(HechoInputDinamicaDTO.class)
                .collectList()
                .block();

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
                unHecho.setNombreDeUsuario(dto.getNombreDeUsuario());

                if (dto.getLugar() != null) {
                    Lugar lugar = new Lugar(dto.getLugar().getLatitud(), dto.getLugar().getLongitud());
                    unHecho.setLugar(lugar);
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












