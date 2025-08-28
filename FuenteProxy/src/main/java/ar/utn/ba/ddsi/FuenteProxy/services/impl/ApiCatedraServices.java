package ar.utn.ba.ddsi.FuenteProxy.services.impl;

import ar.utn.ba.ddsi.FuenteProxy.conversores.HechoMapper;
import ar.utn.ba.ddsi.FuenteProxy.models.dtos.OutPut.HechoOutputDTO;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.FiltroHecho.*;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.FuenteProxy.services.IApiExternaServices;
import ar.utn.ba.ddsi.FuenteProxy.services.adapters.IApiAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApiCatedraServices implements IApiExternaServices {

    private IApiAdapter apiAdapter;
    private final HechoMapper hechoMapper;

    @Autowired
    public ApiCatedraServices(IApiAdapter apiAdapter, HechoMapper hechoMapper) {
        this.apiAdapter = apiAdapter;
        this.hechoMapper = hechoMapper;
    }

    @Override
    public List<HechoOutputDTO> obtenerHechosDeAPI() {
        List<Hecho> hechosApi= apiAdapter.obtenerHechos();
        // normalizar TODO
        //save(hechos) TODO
        return hechosApi.stream()
                .map(hechoMapper::aOutputDTO)
                .collect(Collectors.toList());
    }

    public List<HechoOutputDTO> obtenerHechosFiltrados(List<IFiltroHecho> filtros) {
        List<Hecho> hechosApi = apiAdapter.obtenerHechos();

        List<Hecho> hechosFiltrados = hechosApi.stream()
                .filter(hecho -> filtros.stream().allMatch(f -> f.aplica(hecho)))
                .toList();

        return hechosFiltrados.stream()
                .map(hechoMapper::aOutputDTO)
                .toList();
    }
}
