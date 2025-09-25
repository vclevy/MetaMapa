package ar.utn.ba.ddsi.cliente_liviano.services.impl;

import ar.utn.ba.ddsi.cliente_liviano.models.ResultadoEstadisticaDTO;
import ar.utn.ba.ddsi.cliente_liviano.models.dtos.CategoriaDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class EstadisticasService {

    private WebClient webClient;

    @Autowired
    public EstadisticasService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8084").build();
    }

    public List<ResultadoEstadisticaDTO>obtenerTodas(){
        return webClient.get()
                .uri("/estadisticas/todas")
                .retrieve()
                .bodyToFlux(ResultadoEstadisticaDTO.class)
                .collectList()
                .block();
    }
}
