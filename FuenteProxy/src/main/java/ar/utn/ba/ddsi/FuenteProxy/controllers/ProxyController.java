package ar.utn.ba.ddsi.FuenteProxy.controllers;

import ar.utn.ba.ddsi.FuenteProxy.models.entities.Hecho;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ar.utn.ba.ddsi.FuenteProxy.services.IProxyServices;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class ProxyController {

    @Autowired
    private IProxyServices proxyServices;

    @GetMapping("/hechos")
    public List<Hecho> obtenerHechos(@RequestParam(required = false) Map<String, String> filtrosRaw) {
        if (filtrosRaw == null || filtrosRaw.isEmpty()) {
            return proxyServices.obtenerHechosDesdeAPI();
        } else {
            return proxyServices.obtenerHechosConFiltros(filtrosRaw);
        }
    }
}
