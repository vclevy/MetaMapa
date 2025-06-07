package ar.utn.ba.ddsi.FuenteProxy.services.adapters.impl;

import ar.utn.ba.ddsi.FuenteProxy.models.entities.Hecho;
import ar.utn.ba.ddsi.FuenteProxy.services.adapters.IApiAdapter;
import ar.utn.ba.ddsi.FuenteProxy.services.connectors.ApiCatedraConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("ApiCatedraAdapter")
public class ApiCatedraAdapter implements IApiAdapter {

    private final ApiCatedraConnector connector;
    private final HechoAdapter hechoAdapter;

    @Autowired
    public ApiCatedraAdapter(ApiCatedraConnector connector) {
        this.connector = connector;
        this.hechoAdapter = new HechoAdapter();
    }

    @Override
    public List<Hecho> obtenerHechos() {
        return connector.obtenerHechos().stream()
                .map(hechoAdapter::adaptar)
                .collect(Collectors.toList());
    }
}
