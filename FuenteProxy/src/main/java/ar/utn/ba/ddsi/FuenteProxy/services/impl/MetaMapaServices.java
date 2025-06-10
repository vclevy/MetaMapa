package ar.utn.ba.ddsi.FuenteProxy.services.impl;

import ar.utn.ba.ddsi.FuenteProxy.models.entities.Coleccion;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.FiltroHecho.IFiltroHecho;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.Hecho;
import ar.utn.ba.ddsi.FuenteProxy.services.IMetamapaServices;
import ar.utn.ba.ddsi.FuenteProxy.services.adapters.IMetamapaAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MetaMapaServices implements IMetamapaServices {

    private final IMetamapaAdapter metamapaAdapter;

    @Autowired
    public MetaMapaServices(IMetamapaAdapter metamapaAdapter) {
        this.metamapaAdapter = metamapaAdapter;
    }

    @Override
    public List<Hecho> obtenerHechos() {
        return metamapaAdapter.obtenerHechos();
    }

    public List<Hecho> obtenerHechosConFiltros(List<IFiltroHecho> filtros) {
        return obtenerHechos().stream()
                .filter(hecho -> filtros.stream().allMatch(f -> f.aplica(hecho)))
                .collect(Collectors.toList());
    }

    @Override
    public List<Coleccion> obtenerColecciones() {
        return metamapaAdapter.obtenerColecciones();
    }

    @Override
    public List<Hecho> obtenerHechosDeColeccionConFiltro(String identificador, List<IFiltroHecho> filtros) {
        List<Hecho> hechos = metamapaAdapter.obtenerHechosDeColeccion(identificador);
        return hechos.stream()
                .filter(hecho -> filtros.stream().allMatch(f -> f.aplica(hecho)))
                .collect(Collectors.toList());
    }

    @Override
    public boolean enviarSolicitudEliminacion(Hecho unHecho, String justificacion) {
        return metamapaAdapter.enviarSolicitudEliminacion(unHecho, justificacion);
    }
}
