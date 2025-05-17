package ar.utn.ba.ddsi.controllers;

import ar.utn.ba.ddsi.services.ISolcitudesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudesController {
    @Autowired
    private ISolcitudesService solcitudesService;
}
