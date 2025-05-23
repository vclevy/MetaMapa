package ar.utn.ba.ddsi.services.fuentes;

import java.util.Arrays;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;

@Component
public class FuenteProxy implements FuenteDeHechos {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String url = "https://otra-instancia.metamapa.org";

    @Override
    public List<Hecho> obtenerHechos() {
        ResponseEntity<Hecho[]> response = restTemplate.getForEntity(url + "/hechos", Hecho[].class);
        return Arrays.asList(response.getBody());
    }
}
