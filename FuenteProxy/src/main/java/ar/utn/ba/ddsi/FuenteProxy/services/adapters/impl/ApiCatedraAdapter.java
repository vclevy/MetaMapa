package ar.utn.ba.ddsi.FuenteProxy.services.adapters.impl;

import ar.utn.ba.ddsi.FuenteProxy.conversores.HechoMapper;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.FuenteProxy.services.adapters.IApiAdapter;
import ar.utn.ba.ddsi.FuenteProxy.services.connectors.impl.ApiCatedraConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component("ApiCatedraAdapter")
public class ApiCatedraAdapter implements IApiAdapter {

    private final ApiCatedraConnector connector;
    private final HechoMapper hechoMapper;

    @Autowired
    public ApiCatedraAdapter(ApiCatedraConnector connector, HechoMapper hechoMapper) {
        this.connector = connector;
        this.hechoMapper = hechoMapper;
    }

    @Override
    public List<Hecho> obtenerHechos() {
        return connector.obtenerHechos().stream()
                .map(hechoMapper::adaptar)
                .collect(Collectors.toList());
    }
}
