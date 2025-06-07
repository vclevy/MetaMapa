package ar.utn.ba.ddsi.FuenteProxy.controllers;

import ar.utn.ba.ddsi.FuenteProxy.models.dtos.FiltroHechoDTO;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.FiltroHecho.IFiltroHecho;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.Hecho;
import ar.utn.ba.ddsi.FuenteProxy.services.adapters.impl.FiltroAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ar.utn.ba.ddsi.FuenteProxy.services.IApiCatedraServices;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")

public class HechoController {

    @Autowired
    private IApiCatedraServices proxyServices;

    @Autowired
    private FiltroAdapter filtroAdapter;

    @PostMapping("/hechos")
    public List<Hecho> obtenerHechos(@RequestBody(required = false) List<FiltroHechoDTO> filtrosDto) {
        if (filtrosDto == null || filtrosDto.isEmpty()) {
            return proxyServices.obtenerHechosDesdeAPI();
        }
        List<IFiltroHecho> filtros = filtroAdapter.adaptarLista(filtrosDto);
        return proxyServices.obtenerHechosConFiltros(filtros);
    }

}
