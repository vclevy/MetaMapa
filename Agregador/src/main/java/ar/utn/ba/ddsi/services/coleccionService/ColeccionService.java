package ar.utn.ba.ddsi.services.coleccionService;

import ar.utn.ba.ddsi.models.dtos.input.ColeccionInputDTO;
import ar.utn.ba.ddsi.models.entities.coleccion.Coleccion;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.repositories.IColeccionesRepository;
import ar.utn.ba.ddsi.models.repositories.IHechosRepository;
import ar.utn.ba.ddsi.services.fuentes.FuenteDeHechos;
import ar.utn.ba.ddsi.services.hechoService.IHechoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ColeccionService implements IColeccionService {
    @Autowired
    private IColeccionesRepository coleccionesRepository;

    @Autowired
    private IHechoService hechoService;

    @Override
    public void delete(String unHandle) {
        var coleccion = this.coleccionesRepository.findByHandle(unHandle);
        if (coleccion != null) {
            this.coleccionesRepository.delete(unHandle);
        }
    }

    @Override
    public Coleccion findByHandle(String unHandle) {
        Coleccion coleccion = this.coleccionesRepository.findByHandle(unHandle);

        return coleccion;
    }

    @Override
    public void crear(ColeccionInputDTO unaColeccionInputDTO) {
        var coleccion = new Coleccion(
                unaColeccionInputDTO.getTitulo(),
                unaColeccionInputDTO.getDescripcion()
        );

        this.coleccionesRepository.save(coleccion);
    }

    @Override
    public void refrescarColecciones() {
        List<Coleccion> colecciones = coleccionesRepository.findAll();

        for(Coleccion coleccionIndice : colecciones) {
            coleccionIndice.
                    getFuentesDeHechos()
                    .forEach(unaFuenteDeHechos -> {
                        List<Hecho> hechosDeColeccionDeUnaFuente = unaFuenteDeHechos.obtenerHechos();
                        for (Hecho hechoIndice : hechosDeColeccionDeUnaFuente) {
                            if (coleccionIndice.verificadorDeAgregadorDeHechos(hechoIndice)) {
                                coleccionIndice.getHechos().add(hechoIndice);
                                this.hechoService.registrarHechoDesdeFuente(hechoIndice);
                            }
                        }
                    });
        }
    }

    @Override
    public List<Hecho> modoDeNavegacion(String unHandle, String unModoDeNavegacion, String unAlgoritmoConsenso) {
        Coleccion coleccion = this.coleccionesRepository.findByHandle(unHandle);

        if (coleccion == null) {
            throw new IllegalArgumentException("Colección no encontrada: " + unHandle);
        }

        if (unModoDeNavegacion.equalsIgnoreCase("irrestricto")) {
            return coleccion.getHechos();
        } else if (unModoDeNavegacion.equalsIgnoreCase("curado")) {
            return aplicarConsenso(unHandle, coleccion.getHechos(), unAlgoritmoConsenso);
        } else {
            throw new IllegalArgumentException("Modo de navegacion no existente: " + unModoDeNavegacion);
        }
    }

    @Override // TODO
    public List<Hecho> aplicarConsenso(String unHandle, List<Hecho> unosHechos, String unAlgoritmoConsenso) {
        switch (unAlgoritmoConsenso) {
            case "mencionMultiple":
                return aplicarMencionMultiple(unHandle, unosHechos);
            case "mayoriaSimple":
                return aplicarMayoriaSimple(unHandle, unosHechos);
            case "absoluto":
                return aplicarAbsoluto(unHandle, unosHechos);
            default:
                throw new IllegalArgumentException("Algoritmo de consenso no válido: " + unAlgoritmoConsenso);
        }
    }

    @Override
    public List<Hecho> aplicarMencionMultiple(String unHandle, List<Hecho> unosHechos) {

        return unosHechos;
    }

    @Override
    public List<Hecho> aplicarMayoriaSimple(String unHandle, List<Hecho> unosHechos) {
        return unosHechos;
    }

    @Override
    public List<Hecho> aplicarAbsoluto(String unHandle, List<Hecho> unosHechos) {
        return unosHechos;
    }
}

