package ar.utn.ba.ddsi.FuenteProxy.services.impl;

import ar.utn.ba.ddsi.FuenteProxy.models.dtos.SolicitudEliminacionInputDTO;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.Hecho;
import ar.utn.ba.ddsi.FuenteProxy.services.ISolicitudesService;
import org.springframework.web.reactive.function.client.WebClient;

public class MetaMapaSolicitudesServices implements ISolicitudesService {

    private final WebClient webClient;

    public MetaMapaSolicitudesServices(WebClient webClient) {
        this.webClient = webClient;
    }

    public boolean enviarSolicitudEliminacion(Hecho hecho, String justificacion) {
        try {
            SolicitudEliminacionInputDTO dto = new SolicitudEliminacionInputDTO();
            dto.setHecho(hecho);
            dto.setJustificacionDeEliminacion(justificacion);

            webClient.post()
                    .uri("/solicitudes")
                    .bodyValue(dto)
                    .retrieve()
                    .toBodilessEntity()
                    .block();

            return true;
        } catch (Exception e) {
            System.err.println("Error al enviar solicitud de eliminación: " + e.getMessage());
            return false;
        }
    }
}
