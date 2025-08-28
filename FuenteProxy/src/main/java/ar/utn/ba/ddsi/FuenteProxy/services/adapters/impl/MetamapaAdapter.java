package ar.utn.ba.ddsi.FuenteProxy.services.adapters.impl;

import ar.utn.ba.ddsi.FuenteProxy.conversores.HechoMapper;
import ar.utn.ba.ddsi.FuenteProxy.models.dtos.ColeccionDTO;
import ar.utn.ba.ddsi.FuenteProxy.models.dtos.Input.HechoInputDTO;
import ar.utn.ba.ddsi.FuenteProxy.models.dtos.SolicitudEliminacionInputDTO;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.Coleccion;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.FuenteProxy.services.adapters.IMetamapaAdapter;
import ar.utn.ba.ddsi.FuenteProxy.services.connectors.impl.MetaMapaConnectorConcreto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MetamapaAdapter implements IMetamapaAdapter {

    private final MetaMapaConnectorConcreto connector;
    private final HechoMapper hechoMapper;

    @Autowired
    public MetamapaAdapter(MetaMapaConnectorConcreto connector, HechoMapper hechoMapper) {
        this.connector = connector;
        this.hechoMapper = hechoMapper;
    }

    @Override
    public List<Hecho> obtenerHechos() {
        List<HechoInputDTO> externos = connector.obtenerHechos();
        return externos.stream()
                .map(hechoMapper::adaptar)
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
                ? dto.getHechosOutputDtos().stream().map(hechoMapper::adaptar).collect(Collectors.toList())
                : Collections.emptyList();
        coleccion.setHechos(hechos);
        coleccion.setCriterioDePertenencia(Collections.emptyList());
        return coleccion;
    }

    @Override
    public List<Hecho> obtenerHechosDeColeccion(String identificador) {
        List<HechoInputDTO> externos = connector.obtenerHechosDeColeccion(identificador);
        return externos.stream()
                .map(hechoMapper::adaptar)
                .collect(Collectors.toList());
    }

    @Override
    public boolean enviarSolicitudEliminacion(Hecho unHecho, String justificacion) {
        SolicitudEliminacionInputDTO dto = new SolicitudEliminacionInputDTO();
        dto.setHecho(unHecho);
        dto.setJustificacionDeEliminacion(justificacion);
        return connector.enviarSolicitudEliminacion(dto);
    }

    @Override
    public List<Hecho> obtenerHechosDeColeccionConModo(String identificador, String modoNavegacion) {
        List<HechoInputDTO> externos = connector.obtenerHechosDeColeccionConModo(identificador, modoNavegacion);
        return externos.stream()
                .map(hechoMapper::adaptar)
                .collect(Collectors.toList());
    }
}
