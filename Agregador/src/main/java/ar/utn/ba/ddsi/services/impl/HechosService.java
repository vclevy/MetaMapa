package ar.utn.ba.ddsi.services.impl;

import ar.utn.ba.ddsi.models.dtos.input.HechoInputDTO;
import ar.utn.ba.ddsi.models.dtos.output.HechoOutputDTO;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.hecho.OrigenDelHecho;
import ar.utn.ba.ddsi.models.repositories.IHechosRepository;
import ar.utn.ba.ddsi.services.IHechosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ar.utn.ba.ddsi.models.dtos.input.HechoInputDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HechosService implements IHechosService {
    @Autowired
    private IHechosRepository hechosRepository;

    WebClient client = WebClient.create("http://localhost:8080");

    private final WebClient fuenteEstaticaClient;
    private final WebClient fuenteDinamicaClient;
    private final WebClient fuenteProxyClient;

    public HechosService() {
        this.fuenteEstaticaClient = WebClient.builder()
                .baseUrl("http://localhost:8081") // fuente estática
                .build();
        this.fuenteDinamicaClient = WebClient.builder()
                .baseUrl("http://localhost:8082") // fuente dinámica
                .build();
        this.fuenteProxyClient = WebClient.builder()
                .baseUrl("http://localhost:8083") // fuente proxy
                .build();
    }

    @Override
    public HechoOutputDTO findById(Integer id) {
        var hecho = this.hechosRepository.findById(id);
        if(hecho == null) {
            return null;
        }
        return hechoOutputDTO(hecho);
    }

    @Override
    public void eliminar(Integer id) {
        var hecho = this.hechosRepository.findById(id);
        if(hecho != null){
            this.hechosRepository.delete(hecho);
        }
    }

    @Override
    public List<HechoOutputDTO> findAll() {
        return this.hechosRepository.findAll().stream().map(this::hechoOutputDTO).collect(Collectors.toList());
    }

    @Scheduled(cron = "0 0 * * * *")
    public void actualizarHechosDeTodasLasFuentes() {
        List<Hecho> hechosActualizados = this.obtenerHechosDeTodasLasFuentes();
        hechosActualizados.forEach(unHecho -> hechosRepository.save(unHecho));
    }

    public List<Hecho> obtenerHechosDeTodasLasFuentes() {
        List<Hecho> hechos = new ArrayList<>();
        hechos.addAll(obtenerHechosDesde(fuenteEstaticaClient));
        hechos.addAll(obtenerHechosDesde(fuenteDinamicaClient));
        hechos.addAll(obtenerHechosDesde(fuenteProxyClient));
        return hechos;
    }

    private List<Hecho> obtenerHechosDesde(WebClient client) {
        Mono<List<Hecho>> hechosMono = client.get()
                .uri("/hechos")
                .retrieve()
                .bodyToFlux(Hecho.class)
                .collectList();

        return hechosMono.block();
    }

    private HechoOutputDTO hechoOutputDTO(Hecho unHecho) {
        HechoOutputDTO hechoOutputDTO = new HechoOutputDTO();
        hechoOutputDTO.setTitulo(unHecho.getTitulo());
        hechoOutputDTO.setDescripcion(unHecho.getDescripcion());
        hechoOutputDTO.setCategoria(unHecho.getCategoria());
        hechoOutputDTO.setFechaDeAcontecimiento(unHecho.getFechaDeAcontecimiento());
        hechoOutputDTO.setLugar(unHecho.getLugar());
        hechoOutputDTO.setSolicitudesDeEliminacion(unHecho.getSolicitudesDeEliminacion());
        hechoOutputDTO.setEtiquetas(unHecho.getEtiquetas());
        hechoOutputDTO.setMultimedia(unHecho.getMultimedia());
        hechoOutputDTO.setContribuyente(unHecho.getContribuyente());
        return hechoOutputDTO;
    }

}
