package controllers;

import models.entities.Hecho;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import services.IProxyServices;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ProxyController {

    @Autowired
    private IProxyServices proxyServices;

    @GetMapping("/hechos")
    public List<Hecho> obtenerHechos() {
        return proxyServices.obtenerHechosDesdeAPI();
    }


  // @GetMapping("/colecciones/{identificador}/hechos")
  // public List<Hecho> obtenerHechosDeColeccion(
  //         @PathVariable String identificador,
  //         @RequestParam Map<String, String> filtros) {
  //     return proxyServices.obtenerHechosDeColeccion(identificador, filtros);
  // }

}
