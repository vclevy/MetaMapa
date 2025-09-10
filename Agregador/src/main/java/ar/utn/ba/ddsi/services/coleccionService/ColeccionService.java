package ar.utn.ba.ddsi.services.coleccionService;

import ar.utn.ba.ddsi.models.dtos.input.colecciones.ColeccionInputDTO;
import ar.utn.ba.ddsi.models.dtos.input.colecciones.ColeccionPatchDTO;
import ar.utn.ba.ddsi.models.dtos.input.fuentesDeHechos.FuenteCreateDTO;
import ar.utn.ba.ddsi.models.dtos.input.fuentesDeHechos.FuenteDeleteDTO;
import ar.utn.ba.ddsi.models.repositories.IFuenteDeHechosRepository;
import ar.utn.ba.ddsi.models.dtos.output.ColeccionOutputDTO;
import ar.utn.ba.ddsi.models.dtos.output.FuenteDeHechoOutputDTO;
import ar.utn.ba.ddsi.models.entities.coleccion.Coleccion;
import ar.utn.ba.ddsi.models.entities.fuentes.Fuente;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.repositories.IColeccionesRepository;
import ar.utn.ba.ddsi.services.coleccionService.algoritmoConsenso.IAlgoritmo;
import ar.utn.ba.ddsi.services.georef.LugarService;
import ar.utn.ba.ddsi.services.hechoService.IHechoService;
import ar.utn.ba.ddsi.services.mappers.ColeccionMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.utn.ba.ddsi.services.factory.AlgoritmoFactory;
import java.util.*;

@Service
public class ColeccionService implements IColeccionService {
    @Autowired
    private IColeccionesRepository coleccionesRepository;
    @Autowired
    private IHechoService hechoService;
    @Autowired
    private IFuenteDeHechosRepository fuenteRepository;
    @Autowired
    private LugarService lugarService;

    private final AlgoritmoFactory algoritmoFactory;
    private final ColeccionMapper coleccionMapper;

    public ColeccionService(AlgoritmoFactory algoritmoFactory, ColeccionMapper coleccionMapper) {
        this.algoritmoFactory = algoritmoFactory;
        this.coleccionMapper = coleccionMapper;
    }

    @Override
    public boolean delete(Long id) {
        var coleccion = this.coleccionesRepository.findById(id);
        if (coleccion != null) {
            this.coleccionesRepository.deleteById(id);
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
    public Coleccion findById(Long id) {
        if (this.coleccionesRepository.existsById(id)) {
            return this.coleccionesRepository.findById(id).get();
        } else {
            return null;
        }
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
    public void refrescarColeccion(Coleccion unaColeccion, Fuente nuevaFuente) {
        Fuente fuentePersistida;
        if (nuevaFuente.getId() == null) {
            fuentePersistida = fuenteRepository.save(nuevaFuente); // la persiste si es nueva
        } else {
            fuentePersistida = fuenteRepository.findById(nuevaFuente.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Fuente inexistente con id " + nuevaFuente.getId()));
        }

        List<Hecho> hechosDeColeccionDeUnaFuente = fuentePersistida.obtenerHechos();
        for (Hecho hechoIndice : hechosDeColeccionDeUnaFuente) {
            hechoIndice.setFuente(fuentePersistida);

            //if (unaColeccion.verificadorDeAgregadorDeHechos(hechoIndice)) { TODO!!! @alan @gonzi lo marco para acordarme
            this.hechoService.registrarHechoDesdeFuente(hechoIndice);
            unaColeccion.getHechos().add(hechoIndice);
        }
    }

    @Override
    public ColeccionOutputDTO modificarAtributo(Long id, ColeccionPatchDTO patch) {
        if (this.coleccionesRepository.existsById(id)) {
            Coleccion coleccion = this.coleccionesRepository.findById(id).get();
            switch (patch.getCampo().toLowerCase()) {
                case "titulo" -> coleccion.setTitulo(patch.getNuevoValor());
                case "descripcion" -> coleccion.setDescripcion(patch.getNuevoValor());
                case "algoritmo" -> coleccion.setAlgoritmoDeConsenso(algoritmoFactory.crear(patch.getNuevoAlgoritmoDeConsenso()));
                default -> throw new IllegalArgumentException("Campo inválido: " + patch.getCampo());
            }

            return this.coleccionMapper.toDTO(coleccion);
        }
        return null;
    }

    @Override
    @Transactional
    public ColeccionOutputDTO eliminarUnaFuenteDeUnaColeccion(Long idColeccion, Long idFuente) {
        return coleccionesRepository.findById(idColeccion)
                .map(coleccion -> {
                    List<Hecho> hechosAEliminar = coleccion.getHechos()
                            .stream()
                            .filter(h -> h.getFuente().getId().equals(idFuente))
                            .toList();
                    coleccion.getHechos().removeAll(hechosAEliminar);

                    coleccion.getFuentesDeHechos()
                            .removeIf(fuente -> idFuente.equals(fuente.getId()));

                    Coleccion actualizada = coleccionesRepository.save(coleccion);
                    return coleccionMapper.toDTO(actualizada);
                })
                .orElse(null);
    }

    @Override
    public ColeccionOutputDTO agregarUnaFuenteDeUnaColeccion(Long id, FuenteCreateDTO fuenteDTO) {
        if (this.coleccionesRepository.existsById(id)) {
            Coleccion coleccion = this.coleccionesRepository.findById(id).get();
            Fuente nuevaFuente = new Fuente(fuenteDTO.getTipo(), fuenteDTO.getUrlBase(), lugarService);
            coleccion.getFuentesDeHechos().add(nuevaFuente);
            this.refrescarColeccion(coleccion, nuevaFuente);
            coleccion.aplicarAlgoritmoDeConsenso();
            this.coleccionesRepository.save(coleccion);
            return this.coleccionMapper.toDTO(coleccion);
        }
        return null;
    }

    @Override
    public List<Hecho> obtenerHechosDeColeccionSegunModoDeNavegacion(Long id, String unModoDeNavegacion) {
        var coleccion = coleccionesRepository.findById(id);
        if (coleccion == null) {
            throw new NoSuchElementException("No se encontró la colección con handle: " + id);
        }
        if(unModoDeNavegacion.equalsIgnoreCase("CURADO")) {
            return coleccion.get().getHechosConAlgotimoAplicado();
        }
        else {
            return coleccion.get().getHechos();
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
    public List<FuenteDeHechoOutputDTO> obtenerFuentesDeUnaColeccion(Long id) {
        if(this.coleccionesRepository.existsById(id)) {
            Coleccion coleccion = this.coleccionesRepository.findById(id).get();
            List<FuenteDeHechoOutputDTO> fuentesDeHechoOutputDTOs = new ArrayList<>();

            for (Fuente fuente : coleccion.getFuentesDeHechos()) {
                FuenteDeHechoOutputDTO fuenteDTO = new FuenteDeHechoOutputDTO();
                fuenteDTO.setTipo(fuente.getTipo());
                fuenteDTO.setUrlBase(fuente.getUrlBase());
                fuentesDeHechoOutputDTOs.add(fuenteDTO);
            }

            return fuentesDeHechoOutputDTOs;
        }
        return null;
    }
}