package controllers;

import models.entities.Hecho;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import services.IProxyServices;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hechos")
public class ProxyController {

    @Autowired
    private IProxyServices proxyServices;

    @GetMapping
    public Mono<List<Hecho>> obtenerHechos(@RequestParam Map<String, String> filtros) {
        return proxyServices.obtenerHechosDesdeAPI(filtros);
    }
}
