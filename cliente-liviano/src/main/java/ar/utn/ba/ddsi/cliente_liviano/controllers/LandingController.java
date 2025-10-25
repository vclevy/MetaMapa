package ar.utn.ba.ddsi.cliente_liviano.controllers;

import ar.utn.ba.ddsi.cliente_liviano.models.ResultadoEstadisticaDTO;
import ar.utn.ba.ddsi.cliente_liviano.models.dashboard.DashboardStats;
import ar.utn.ba.ddsi.cliente_liviano.models.dtos.CategoriaDTO;
import ar.utn.ba.ddsi.cliente_liviano.models.dtos.ColeccionDTO;
import ar.utn.ba.ddsi.cliente_liviano.models.dtos.HechoDTO;
import ar.utn.ba.ddsi.cliente_liviano.models.dtos.HechoFiltroDTO;
import ar.utn.ba.ddsi.cliente_liviano.services.impl.AgregadorService;
import ar.utn.ba.ddsi.cliente_liviano.services.impl.DashboardBuilderService;
import ar.utn.ba.ddsi.cliente_liviano.services.impl.EstadisticasService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/")
public class LandingController {

    private static final Logger log = LoggerFactory.getLogger(LandingController.class);

    private final AgregadorService agregador;
    private final EstadisticasService estadisticasService;
    private final DashboardBuilderService dashboardBuilder;

    public LandingController(AgregadorService agregador,
                             EstadisticasService estadisticasService,
                             DashboardBuilderService dashboardBuilder) {
        this.agregador = agregador;
        this.estadisticasService = estadisticasService;
        this.dashboardBuilder = dashboardBuilder;
    }

    @GetMapping({"", "/"})
    public String landing(Model model) {
        // Lista de hechos destacados
        List<HechoDTO> destacados = agregador.obtenerHechosDestacados(); //TODO Esta mal esto pero bueno a esta altura del doparti lo corregimos despues
        model.addAttribute("destacados", destacados);

        // Todos los hechos para el mapa
        List<HechoDTO> hechos = agregador.obtenerHechosVisibles();
        model.addAttribute("hechos", hechos);

        // Colecciones destacadas
        List<ColeccionDTO> coleccionesDestacadas = agregador.obtenerColeccionesDestacadas();
        model.addAttribute("coleccionesDestacadas", coleccionesDestacadas);

        return "index"; // Vista principal
    }

    @GetMapping("/admin")
    public String adminLanding(@ModelAttribute("filtros") HechoFiltroDTO filtros,
                               Model model) {
        List<ResultadoEstadisticaDTO> resultados = estadisticasService.obtenerTodas();

        DashboardStats dash = dashboardBuilder.build(resultados);
        model.addAttribute("dash", dash);
        model.addAttribute("categoriaTop", dash.getCategoriaConMasHechos());
        model.addAttribute("provinciaTop", dash.getProvinciaConMasHechos());
        model.addAttribute("horasTop", dash.getHoraTopPorCategoria());
        model.addAttribute("provTopPorCat", dash.getProvinciaTopPorCategoria());
        model.addAttribute("generado", dash.getGenerado());
        model.addAttribute("provTopPorCol", dash.getProvinciaTopPorColeccion());
        model.addAttribute("rawStats", resultados);

        List<CategoriaDTO> categorias = agregador.obtenerCategorias();
        model.addAttribute("categorias", categorias);

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        String generadoFmt = dash.getGenerado() != null
                ? dash.getGenerado().format(fmt)
                : "Sin fecha"; // mensaje por defecto si es null

        model.addAttribute("generadoFmt", generadoFmt);

        return "adminLanding";
    }

    @GetMapping("/sobre-nosotros")
    public String sobreNosotros() {
        return "sobreNosotros";
    }
}