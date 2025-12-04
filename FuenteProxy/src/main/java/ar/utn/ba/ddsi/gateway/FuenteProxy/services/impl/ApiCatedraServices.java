package ar.utn.ba.ddsi.gateway.FuenteProxy.services.impl;

import ar.utn.ba.ddsi.gateway.FuenteProxy.conversores.HechoMapper;
import ar.utn.ba.ddsi.gateway.FuenteProxy.models.dtos.output.HechoOutputDTO;

import ar.utn.ba.ddsi.gateway.FuenteProxy.models.entities.FiltroHecho.IFiltroHecho;
import ar.utn.ba.ddsi.gateway.FuenteProxy.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.gateway.FuenteProxy.models.repositories.IHechosRepository;
import ar.utn.ba.ddsi.gateway.FuenteProxy.services.IApiExternaServices;
import ar.utn.ba.ddsi.gateway.FuenteProxy.services.adapters.IApiAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApiCatedraServices implements IApiExternaServices {

    private IApiAdapter apiAdapter;
    private final HechoMapper hechoMapper;
    private IHechosRepository hechosRepository;

    @Autowired
    public ApiCatedraServices(IApiAdapter apiAdapter, HechoMapper hechoMapper, IHechosRepository hechosRepository) {
        this.apiAdapter = apiAdapter;
        this.hechoMapper = hechoMapper;
        this.hechosRepository = hechosRepository;
    }

    @Override
    public List<HechoOutputDTO> obtenerHechosDeAPI() {
        List<Hecho> hechosApi= apiAdapter.obtenerHechos();
        hechosApi.forEach(h -> h.setId(null));
        hechosRepository.saveAll(hechosApi);
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
