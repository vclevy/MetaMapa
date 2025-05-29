package ar.utn.ba.ddsi.FuenteProxy.services.adapters.impl;

import ar.utn.ba.ddsi.FuenteProxy.models.dtos.ColeccionDTO;
import ar.utn.ba.ddsi.FuenteProxy.models.dtos.HechoProxyDTO;
import ar.utn.ba.ddsi.FuenteProxy.models.dtos.SolicitudEliminacionInputDTO;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.Coleccion;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.FiltroHecho.FiltroCategoria;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.FiltroHecho.FiltroFechaAcontecimientoDesde;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.FiltroHecho.FiltroFechaAcontecimientoHasta;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.FiltroHecho.IFiltroHecho;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.Hecho;
import ar.utn.ba.ddsi.FuenteProxy.services.adapters.IHechoAdapter;
import ar.utn.ba.ddsi.FuenteProxy.services.adapters.IMetamapaAdapter;
import ar.utn.ba.ddsi.FuenteProxy.services.connectors.MetaMapaConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MetamapaAdapter implements IMetamapaAdapter {

    private final MetaMapaConnector connector;
    private final IHechoAdapter hechoAdapter;

    @Autowired
    public MetamapaAdapter(MetaMapaConnector connector, IHechoAdapter hechoAdapter) {
        this.connector = connector;
        this.hechoAdapter = hechoAdapter;
    }

    @Override
    public List<Hecho> obtenerHechos() {
        List<HechoProxyDTO> externos = connector.obtenerHechos();
        return externos.stream()
                .map(hechoAdapter::adaptar)
                .collect(Collectors.toList());
    }

    @Override
    public List<Hecho> obtenerHechosConFiltros(Map<String, String> filtrosRaw) {
        List<IFiltroHecho> filtros = new ArrayList<>();

        if (filtrosRaw == null || filtrosRaw.isEmpty()) {
            return obtenerHechos();
        }

        if (filtrosRaw.containsKey("categoria")) {
            filtros.add(new FiltroCategoria(filtrosRaw.get("categoria")));
        }

        try {
            String desdeStr = filtrosRaw.get("fecha_acontecimiento_desde");
            if (desdeStr != null && !desdeStr.isBlank()) {
                filtros.add(new FiltroFechaAcontecimientoDesde(LocalDateTime.parse(desdeStr)));
            }

            String hastaStr = filtrosRaw.get("fecha_acontecimiento_hasta");
            if (hastaStr != null && !hastaStr.isBlank()) {
                filtros.add(new FiltroFechaAcontecimientoHasta(LocalDateTime.parse(hastaStr)));
            }
        } catch (DateTimeParseException e) {
            System.err.println("Error al parsear fechas: " + e.getMessage());
        }

        return obtenerHechos().stream()
                .filter(hecho -> filtros.stream().allMatch(f -> f.aplica(hecho)))
                .collect(Collectors.toList());
    }

    @Override
    public List<Coleccion> obtenerColecciones() {
        List<ColeccionDTO> externos = connector.obtenerColecciones();
        return externos.stream().map(this::convertirDTOaColeccion).collect(Collectors.toList());
    }

    private Coleccion convertirDTOaColeccion(ColeccionDTO dto) {
        Coleccion coleccion = new Coleccion(dto.getTitulo(), dto.getDescripcion());
        List<Hecho> hechos = dto.getHechosOutputDtos() != null
                ? dto.getHechosOutputDtos().stream().map(hechoAdapter::adaptar).collect(Collectors.toList())
                : Collections.emptyList();
        coleccion.setHechos(hechos);
        coleccion.setCriterioDePertenencia(Collections.emptyList());
        return coleccion;
    }

    @Override
    public List<Hecho> obtenerHechosDeColeccion(String identificador, Map<String, String> filtros) {
        List<HechoProxyDTO> externos = connector.obtenerHechosDeColeccion(identificador, filtros);
        return externos.stream().map(hechoAdapter::adaptar).collect(Collectors.toList());
    }

    @Override
    public boolean enviarSolicitudEliminacion(Hecho unHecho, String justificacion) {
        SolicitudEliminacionInputDTO dto = new SolicitudEliminacionInputDTO();
        dto.setHecho(unHecho);
        dto.setJustificacionDeEliminacion(justificacion);
        return connector.enviarSolicitudEliminacion(dto);
    }
}
