package ar.utn.ba.ddsi.FuenteProxy.services.impl;

import ar.utn.ba.ddsi.FuenteProxy.models.entities.FiltroHecho.*;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.Hecho;
import ar.utn.ba.ddsi.FuenteProxy.models.repositories.IHechosRepository;
import ar.utn.ba.ddsi.FuenteProxy.services.IApiCatedraServices;
import ar.utn.ba.ddsi.FuenteProxy.services.adapters.IApiAdapter;
import ar.utn.ba.ddsi.FuenteProxy.services.adapters.impl.ApiCatedraAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ApiCatedraServices implements IApiCatedraServices {

    private IApiAdapter apiAdapter;

    @Autowired
    public ApiCatedraServices(IApiAdapter apiAdapter) {
        this.apiAdapter = apiAdapter;
    }

    @Override
    public List<Hecho> obtenerHechosDesdeAPI() {
        return apiAdapter.obtenerHechos();
    }

    @Override
    public List<Hecho> obtenerHechosConFiltros(Map<String, String> filtrosRaw) {
        List<IFiltroHecho> filtros = new ArrayList<>();

        if (filtrosRaw == null || filtrosRaw.isEmpty()) {
            return obtenerHechosDesdeAPI();
        }

        if (filtrosRaw.containsKey("categoria")) {
            filtros.add(new FiltroCategoria(filtrosRaw.get("categoria")));
        }

        try {
            String desdeStr = filtrosRaw.get("fecha_acontecimiento_desde");
            if (desdeStr != null && !desdeStr.isBlank()) {
                filtros.add(new FiltroFechaAcontecimientoDesde(LocalDateTime.parse(desdeStr)));
            }

            String hastaStr = filtrosRaw.get("fecha_acontecimiento_hasta");
            if (hastaStr != null && !hastaStr.isBlank()) {
                filtros.add(new FiltroFechaAcontecimientoHasta(LocalDateTime.parse(hastaStr)));
            }
        } catch (DateTimeParseException e) {
            System.err.println("Error al parsear fechas: " + e.getMessage());
        }

        return obtenerHechosDesdeAPI().stream()
                .filter(hecho -> filtros.stream().allMatch(f -> f.aplica(hecho)))
                .collect(Collectors.toList());
    }
}
