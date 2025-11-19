package ar.utn.ba.ddsi.gateway.cliente_liviano.services.impl;

import ar.utn.ba.ddsi.gateway.cliente_liviano.models.ResultadoEstadisticaDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class EstadisticasService {

    private WebClient webClient;
    @Value("${gateway.url}") String urlGateway;

    @Autowired
    public EstadisticasService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder.baseUrl(urlGateway+"/api/estadisticas").build();
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
