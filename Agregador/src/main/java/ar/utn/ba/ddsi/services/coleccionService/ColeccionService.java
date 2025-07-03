package ar.utn.ba.ddsi.services.coleccionService;

import ar.utn.ba.ddsi.models.dtos.input.colecciones.ColeccionInputDTO;
import ar.utn.ba.ddsi.models.dtos.input.colecciones.ColeccionPatchDTO;
import ar.utn.ba.ddsi.models.dtos.input.fuentesDeHechos.FuenteCreateDTO;
import ar.utn.ba.ddsi.models.entities.coleccion.Coleccion;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.repositories.IColeccionesRepository;
import ar.utn.ba.ddsi.services.coleccionService.algoritmoConsenso.IAlgoritmo;
import ar.utn.ba.ddsi.services.fuentes.FuenteDeHechos;
import ar.utn.ba.ddsi.services.fuentes.FuenteDinamica;
import ar.utn.ba.ddsi.services.fuentes.FuenteEstatica;
import ar.utn.ba.ddsi.services.fuentes.FuenteProxy;
import ar.utn.ba.ddsi.services.hechoService.IHechoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.utn.ba.ddsi.services.factory.AlgoritmoFactory;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Service
public class ColeccionService implements IColeccionService {
    @Autowired
    private IColeccionesRepository coleccionesRepository;

    @Autowired
    private IHechoService hechoService;

    private final AlgoritmoFactory algoritmoFactory;


    public ColeccionService(AlgoritmoFactory algoritmoFactory) {
        this.algoritmoFactory = algoritmoFactory;
    }

    @Override
    public void delete(String unHandle) {
        var coleccion = this.coleccionesRepository.findByHandle(unHandle);
        if (coleccion != null) {
            this.coleccionesRepository.delete(unHandle);
        }
    }

    @Override
    public List<Coleccion> findAll() {
        return this.coleccionesRepository.findAll();
    }

    @Override
    public Coleccion findByHandle(String unHandle) {
        Coleccion coleccion = this.coleccionesRepository.findByHandle(unHandle);

        return coleccion;
    }

    @Override
    public void crear(ColeccionInputDTO unaColeccionInputDTO) {
        IAlgoritmo algoritmo = algoritmoFactory.crear(unaColeccionInputDTO.getAlgoritmo());
        var coleccion = new Coleccion(
                unaColeccionInputDTO.getTitulo(),
                unaColeccionInputDTO.getDescripcion(),
                algoritmo
        );
    this.modoDeNavegacion(coleccion, unaColeccionInputDTO.getModoDeNavegacion(), algoritmo);
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
    public void modoDeNavegacion(Coleccion unaColeccion, String unModoDeNavegacion, IAlgoritmo unAlgoritmoConsenso) {
        if (unModoDeNavegacion.equalsIgnoreCase("irrestricto")) {
            this.coleccionesRepository.save(unaColeccion);

        } else if (unModoDeNavegacion.equalsIgnoreCase("curado")) {
            Coleccion coleccionConAlgoritmoAplicado = unAlgoritmoConsenso.aplicarConsenso(unaColeccion);
            this.coleccionesRepository.save(coleccionConAlgoritmoAplicado);

        } else {
            throw new IllegalArgumentException("Modo de navegacion no existente: " + unModoDeNavegacion);
        }
    }

    @Override
    public void modificarAtributo(String unHandle, ColeccionPatchDTO patch) {
        Coleccion coleccion = this.coleccionesRepository.findByHandle(unHandle);

        switch (patch.getCampo().toLowerCase()) {
            case "titulo" -> coleccion.setTitulo(patch.getNuevoValor());
            case "descripcion" -> coleccion.setDescripcion(patch.getNuevoValor());
            case "algotirmo" -> coleccion.setAlgoritmoDeConsenso(algoritmoFactory.crear(patch.getNuevoValor()));
            default -> throw new IllegalArgumentException("Campo inválido: " + patch.getCampo());
        }
    }

    @Override
    public void eliminarUnaFuenteDeUnaColeccion(String unHandle, Long Id) {
        this.coleccionesRepository.findByHandle(unHandle).getFuentesDeHechos().removeIf(unId -> unId.equals(Id));
    }

    @Override
    public void agregarUnaFuenteDeUnaColeccion(String unHandle, FuenteCreateDTO fuenteDTO) {
        Coleccion coleccion = this.coleccionesRepository.findByHandle(unHandle);

        FuenteDeHechos nuevaFuente = switch (fuenteDTO.getTipo().toUpperCase()) {
            case "DINAMICA" -> new FuenteDinamica(fuenteDTO.getUrlBase());
            case "ESTATICA" -> new FuenteEstatica(fuenteDTO.getUrlBase());
            case "PROXY" -> new FuenteProxy(fuenteDTO.getUrlBase(), fuenteDTO.getUrlProxy(), fuenteDTO.getPathProxy());
            default -> null;
        };

        coleccion.getFuentesDeHechos().add(nuevaFuente);
    }
}

