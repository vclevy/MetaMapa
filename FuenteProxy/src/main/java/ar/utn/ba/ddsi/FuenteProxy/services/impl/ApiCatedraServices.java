package ar.utn.ba.ddsi.FuenteProxy.services.impl;

import ar.utn.ba.ddsi.FuenteProxy.models.entities.FiltroHecho.*;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.Hecho;
import ar.utn.ba.ddsi.FuenteProxy.services.IApiExternaServices;
import ar.utn.ba.ddsi.FuenteProxy.services.adapters.IApiAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApiCatedraServices implements IApiExternaServices {

    private IApiAdapter apiAdapter;

    @Autowired
    public ApiCatedraServices(IApiAdapter apiAdapter) {
        this.apiAdapter = apiAdapter;
    }

    @Override
    public List<Hecho> obtenerHechosDeAPI() {
        return apiAdapter.obtenerHechos();
    }

    @Override
    public List<Hecho> obtenerHechosFiltrados(List<IFiltroHecho> filtros) {
        return obtenerHechosDeAPI().stream()
                .filter(hecho -> filtros.stream().allMatch(f -> f.aplica(hecho)))
                .collect(Collectors.toList());
    }

}
