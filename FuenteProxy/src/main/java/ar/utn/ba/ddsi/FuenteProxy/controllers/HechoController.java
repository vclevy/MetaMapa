package ar.utn.ba.ddsi.FuenteProxy.controllers;

import ar.utn.ba.ddsi.FuenteProxy.models.dtos.FiltroHechoDTO;
import ar.utn.ba.ddsi.FuenteProxy.models.dtos.OutPut.HechoOutputDTO;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.FiltroHecho.IFiltroHecho;
import ar.utn.ba.ddsi.FuenteProxy.conversores.FiltroMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ar.utn.ba.ddsi.FuenteProxy.services.IApiExternaServices;
import java.util.List;


@RestController
@RequestMapping("/api")

public class HechoController {

    @Autowired
    private IApiExternaServices proxyServices;

    @Autowired
    private FiltroMapper filtroAdapter;

    @GetMapping("/hechos")
    public List<HechoOutputDTO> obtenerHechos(@RequestBody(required = false) List<FiltroHechoDTO> filtrosDto) {
        if (filtrosDto == null || filtrosDto.isEmpty()) {
            return proxyServices.obtenerHechosDeAPI();
        }
        List<IFiltroHecho> filtros = filtroAdapter.adaptarLista(filtrosDto);
        return proxyServices.obtenerHechosFiltrados(filtros);
    }

}
