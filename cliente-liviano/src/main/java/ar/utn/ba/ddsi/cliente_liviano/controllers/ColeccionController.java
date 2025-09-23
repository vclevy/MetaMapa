package ar.utn.ba.ddsi.cliente_liviano.controllers;

import ar.utn.ba.ddsi.cliente_liviano.models.dtos.ColeccionCreateDTO;
import ar.utn.ba.ddsi.cliente_liviano.models.dtos.ColeccionDTO;
import ar.utn.ba.ddsi.cliente_liviano.services.impl.AgregadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/coleccion")
public class ColeccionController {

    @Autowired
    private AgregadorService agregador;

}
