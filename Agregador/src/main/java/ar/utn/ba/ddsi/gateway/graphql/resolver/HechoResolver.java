package ar.utn.ba.ddsi.gateway.graphql.resolver;

import ar.utn.ba.ddsi.gateway.graphql.input.HechoFilter;
import ar.utn.ba.ddsi.gateway.models.dtos.output.HechoOutputDTO;
import ar.utn.ba.ddsi.gateway.models.entities.fuentes.Fuente;
import ar.utn.ba.ddsi.gateway.models.entities.hecho.Categoria;
import ar.utn.ba.ddsi.gateway.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.gateway.services.hechoService.HechoService;
import ar.utn.ba.ddsi.gateway.services.mappers.HechoMapper;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HechoResolver{

    private final HechoService hechosService;
    private final HechoMapper hechoMapper;

    public HechoResolver(HechoService hechosService, HechoMapper hechoMapper) {
        this.hechosService = hechosService;
        this.hechoMapper = hechoMapper;
    }

    @QueryMapping
    public List<Hecho> hechos(@Argument HechoFilter filter) {
        return hechosService.buscarHechos(filter);
    }

    @QueryMapping
    public Hecho hecho(@Argument Long id) {
        HechoOutputDTO dto = hechosService.obtenerHechoPorId(id);
        if (dto == null) return null;

        // Mapear DTO → entidad Hecho
        Hecho hecho = new Hecho();
        hecho.setId(dto.getId());
        hecho.setTitulo(dto.getTitulo());
        hecho.setDescripcion(dto.getDescripcion());
        hecho.setFechaDeAcontecimiento(dto.getFechaDeAcontecimiento());
        hecho.setLugar(dto.getLugar());
        hecho.setEtiquetas(dto.getEtiquetas());
        hecho.setMultimedia(dto.getMultimedia());
        hecho.setNombreDeUsuario(dto.getNombreDeUsuario());
        hecho.setNombreArchivo(dto.getNombreArchivo());
        hecho.setEsAnonimo(dto.getEsAnonimo());
        hecho.setIdEnFuente(dto.getIdEnFuente());

        // Mapear categoría
        Categoria categoria = new Categoria();
        categoria.setNombre(dto.getCategoriaNombre());
        hecho.setCategoria(categoria);

        // Mapear fuente
        Fuente fuente = new Fuente();
        fuente.setNombre(dto.getFuenteNombre());
        hecho.setFuente(fuente);

        return hecho;
    }
}