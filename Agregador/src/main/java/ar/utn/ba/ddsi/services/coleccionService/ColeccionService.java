package ar.utn.ba.ddsi.services.coleccionService;

import ar.utn.ba.ddsi.models.dtos.input.colecciones.ColeccionInputDTO;
import ar.utn.ba.ddsi.models.dtos.input.colecciones.ColeccionPatchDTO;
import ar.utn.ba.ddsi.models.dtos.input.fuentesDeHechos.FuenteCreateDTO;
import ar.utn.ba.ddsi.models.dtos.output.ColeccionOutputDTO;
import ar.utn.ba.ddsi.models.dtos.output.FuenteDeHechoOutputDTO;
import ar.utn.ba.ddsi.models.entities.coleccion.Coleccion;
import ar.utn.ba.ddsi.models.entities.hecho.Categoria;
import ar.utn.ba.ddsi.models.entities.hecho.Etiqueta;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.hecho.Lugar;
import ar.utn.ba.ddsi.models.entities.solicitud.Solicitud;
import ar.utn.ba.ddsi.models.entities.usuario.Usuario;
import ar.utn.ba.ddsi.models.repositories.IColeccionesRepository;
import ar.utn.ba.ddsi.models.repositories.impl.HechosRepository;
import ar.utn.ba.ddsi.services.coleccionService.algoritmoConsenso.IAlgoritmo;
import ar.utn.ba.ddsi.services.fuentes.*;
import ar.utn.ba.ddsi.services.hechoService.IHechoService;
import ar.utn.ba.ddsi.services.mappers.ColeccionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.utn.ba.ddsi.services.factory.AlgoritmoFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ColeccionService implements IColeccionService {
    @Autowired
    private IColeccionesRepository coleccionesRepository;
    @Autowired
    private IHechoService hechoService;
    private final AlgoritmoFactory algoritmoFactory;
    private final ColeccionMapper coleccionMapper;

    public ColeccionService(AlgoritmoFactory algoritmoFactory, ColeccionMapper coleccionMapper) {
        this.algoritmoFactory = algoritmoFactory;
        this.coleccionMapper = coleccionMapper;
    }

    @Override
    public boolean delete(String unHandle) {
        var coleccion = this.coleccionesRepository.findByHandle(unHandle);
        if (coleccion != null) {
            this.coleccionesRepository.delete(unHandle);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<ColeccionOutputDTO> findAll() {
        List<ColeccionOutputDTO> coleccionOutputDTOS = new ArrayList<>();

        for (Coleccion coleccionIndice : this.coleccionesRepository.findAll()) {
            coleccionOutputDTOS.add(this.coleccionMapper.toDTO(coleccionIndice));
        }

        return coleccionOutputDTOS;
    }

    @Override
    public Coleccion findByHandle(String unHandle) {
        Coleccion coleccion = this.coleccionesRepository.findByHandle(unHandle);
        return coleccion;
    }

    @Override
    public ColeccionOutputDTO crear(ColeccionInputDTO unaColeccionInputDTO) {
        IAlgoritmo algoritmo = algoritmoFactory.crear(unaColeccionInputDTO.getAlgoritmo());
        var coleccion = new Coleccion(
                unaColeccionInputDTO.getTitulo(),
                unaColeccionInputDTO.getDescripcion(),
                algoritmo
        );

        coleccion.aplicarAlgoritmoDeConsenso();
        this.coleccionesRepository.save(coleccion);
        return this.coleccionMapper.toDTO(coleccion);
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
    public void refrescarColeccion(Coleccion unaColeccion) {
        unaColeccion.getFuentesDeHechos()
                .forEach(unaFuenteDeHecho -> {
                    List<Hecho> hechosDeColeccionDeUnaFuente = unaFuenteDeHecho.obtenerHechos();
                    for (Hecho hechoIndice : hechosDeColeccionDeUnaFuente) {
                        //if (unaColeccion.verificadorDeAgregadorDeHechos(hechoIndice)) { TODO!!! @alan @gonzi lo marco para acordarme
                            unaColeccion.getHechos().add(hechoIndice);

                            this.hechoService.registrarHechoDesdeFuente(hechoIndice);

                        }
                 //   }
                });
    }

    @Override
    public ColeccionOutputDTO modificarAtributo(String unHandle, ColeccionPatchDTO patch) {
        Coleccion coleccion = this.coleccionesRepository.findByHandle(unHandle);

        switch (patch.getCampo().toLowerCase()) {
            case "titulo" -> coleccion.setTitulo(patch.getNuevoValor());
            case "descripcion" -> coleccion.setDescripcion(patch.getNuevoValor());
            case "algoritmo" -> coleccion.setAlgoritmoDeConsenso(algoritmoFactory.crear(patch.getNuevoAlgoritmoDeConsenso()));
            default -> throw new IllegalArgumentException("Campo inválido: " + patch.getCampo());
        }

        return this.coleccionMapper.toDTO(coleccion);
    }

    @Override
    public ColeccionOutputDTO eliminarUnaFuenteDeUnaColeccion(String handleColeccion, String handleFuente) {
        Coleccion coleccion = this.coleccionesRepository.findByHandle(handleColeccion);
        coleccion.getFuentesDeHechos()
                .removeIf(fuente -> handleFuente.equals(fuente.getHandleFuente()));
        return this.coleccionMapper.toDTO(coleccion);
    }

    @Override
    public ColeccionOutputDTO agregarUnaFuenteDeUnaColeccion(String unHandle, FuenteCreateDTO fuenteDTO) {
        Coleccion coleccion = this.coleccionesRepository.findByHandle(unHandle);
        Fuente nuevaFuente = new Fuente(fuenteDTO.getTipo(), fuenteDTO.getUrlBase());

        coleccion.getFuentesDeHechos().add(nuevaFuente);
        this.refrescarColeccion(coleccion);
        this.coleccionesRepository.save(coleccion);
        return this.coleccionMapper.toDTO(coleccion);
    }

    @Override
    public List<Hecho> obtenerHechosDeColeccionSegunModoDeNavegacion(String unHandle, String unModoDeNavegacion) {
        var coleccion = coleccionesRepository.findByHandle(unHandle);
        System.out.println("Colección encontrada: " + coleccion);

        if (coleccion == null) {
            throw new NoSuchElementException("No se encontró la colección con handle: " + unHandle);
        }

        if(unModoDeNavegacion.equalsIgnoreCase("CURADO")) {
            return coleccion.getHechosConAlgotimoAplicado();
        }
        else {
            return coleccion.getHechos();
        }
    }

    @Override
    public void aplicarAlgoritmosAColecciones() {
        List<Coleccion> colecciones = this.coleccionesRepository.findAll();

        for(Coleccion coleccionIndice : colecciones) {
            coleccionIndice.aplicarAlgoritmoDeConsenso();
        }
    }

    @Override
    public List<FuenteDeHechoOutputDTO> obtenerFuentesDeUnaColeccion(String unHandle) {
        Coleccion coleccion = this.coleccionesRepository.findByHandle(unHandle);
        List<FuenteDeHechoOutputDTO> fuentesDeHechoOutputDTOs = new ArrayList<>();

        for (Fuente fuente : coleccion.getFuentesDeHechos()) {
            FuenteDeHechoOutputDTO fuenteDTO = new FuenteDeHechoOutputDTO();
            fuenteDTO.setHandle(fuente.getHandleFuente());
            fuenteDTO.setTipo(fuente.getTipo());
            fuenteDTO.setUrlBase(fuente.getUrlBase());
            fuentesDeHechoOutputDTOs.add(fuenteDTO);
        }

        return fuentesDeHechoOutputDTOs;
    }

}