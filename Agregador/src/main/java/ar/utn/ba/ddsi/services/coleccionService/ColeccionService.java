package ar.utn.ba.ddsi.services.coleccionService;

import ar.utn.ba.ddsi.models.dtos.input.colecciones.ColeccionInputDTO;
import ar.utn.ba.ddsi.models.dtos.input.colecciones.ColeccionPatchDTO;
import ar.utn.ba.ddsi.models.dtos.input.fuentesDeHechos.FuenteCreateDTO;
import ar.utn.ba.ddsi.models.repositories.IFuenteDeHechosRepository;
import ar.utn.ba.ddsi.models.dtos.output.ColeccionOutputDTO;
import ar.utn.ba.ddsi.models.dtos.output.FuenteDeHechoOutputDTO;
import ar.utn.ba.ddsi.models.entities.coleccion.Coleccion;
import ar.utn.ba.ddsi.models.entities.fuentes.Fuente;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.repositories.IColeccionesRepository;
import ar.utn.ba.ddsi.models.repositories.ISolicitudesRepository;
import ar.utn.ba.ddsi.services.coleccionService.algoritmoConsenso.IAlgoritmo;
import ar.utn.ba.ddsi.services.georef.LugarService;
import ar.utn.ba.ddsi.services.hechoService.IHechoService;
import ar.utn.ba.ddsi.services.mappers.ColeccionMapper;
import ar.utn.ba.ddsi.services.solicitudService.ISolicitudesService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.utn.ba.ddsi.services.factory.AlgoritmoFactory;
import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    private ISolicitudesService solicitudesService;

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

    public ColeccionOutputDTO obtenerColeccion(Long id) {
        var coleccion = this.coleccionesRepository.findById(id).get();
        return this.coleccionMapper.toDTO(coleccion);
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
    public ColeccionOutputDTO crear(ColeccionInputDTO unaColeccionInputDTO) {
        var coleccion = new Coleccion(
                unaColeccionInputDTO.getTitulo(),
                unaColeccionInputDTO.getDescripcion(),
                unaColeccionInputDTO.getAlgoritmo()
        );

        // Al crear probablemente aún no haya hechos, pero no molesta
        aplicarAlgoritmoDeConsenso(coleccion);

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
            fuentePersistida = fuenteRepository.save(nuevaFuente);
        } else {
            fuentePersistida = fuenteRepository.findById(nuevaFuente.getId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Fuente inexistente con id " + nuevaFuente.getId()));
        }

        fuentePersistida.setLugarService(lugarService);
        fuentePersistida.inicializarFuenteDeHechos();

        List<Hecho> hechosDeFuente = fuentePersistida.obtenerHechos();
        for (Hecho hecho : hechosDeFuente) {
            hecho.setFuente(fuentePersistida);

            // Verificador de agregador de hechos
            // if (unaColeccion.verificadorDeAgregadorDeHechos(hecho)) {
            //     unaColeccion.getHechos().add(hecho);
            // }

            this.hechoService.registrarHechoDesdeFuente(hecho);
            unaColeccion.getHechos().add(hecho);
            aplicarAlgoritmoDeConsenso(unaColeccion);
        }
    }

    @Override
    public ColeccionOutputDTO modificarAtributo(Long id, ColeccionPatchDTO patch) {
        if (this.coleccionesRepository.existsById(id)) {
            Coleccion coleccion = this.coleccionesRepository.findById(id).get();
            switch (patch.getCampo().toLowerCase()) {
                case "titulo" -> coleccion.setTitulo(patch.getNuevoValor());
                case "descripcion" -> coleccion.setDescripcion(patch.getNuevoValor());
                case "algoritmo" -> {
                    coleccion.setAlgoritmoDeConsensoEnumerado(patch.getNuevoAlgoritmoDeConsenso());
                    aplicarAlgoritmoDeConsenso(coleccion);
                }
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

            Optional<Fuente> fuenteExistente = coleccion.getFuentesDeHechos().stream()
                    .filter(f -> f.getUrlBase().equalsIgnoreCase(fuenteDTO.getUrlBase())
                            && f.getTipo().equals(fuenteDTO.getTipo()))
                    .findFirst();

            Fuente fuente;
            if (fuenteExistente.isPresent()) {
                // Si ya existe, usar la existente
                fuente = fuenteExistente.get();
            } else {
                fuente = new Fuente(fuenteDTO.getTipo(), fuenteDTO.getNombre(), fuenteDTO.getUrlBase(), lugarService);
                System.out.printf(" NOMBRE FUENTE DTO %s\n", fuenteDTO.getNombre());
                System.out.printf("Creando Fuente %s\n", fuente.getNombre());

                coleccion.getFuentesDeHechos().add(fuente);
            }

            this.refrescarColeccion(coleccion, fuente);
            aplicarAlgoritmoDeConsenso(coleccion);
            this.coleccionesRepository.save(coleccion);
            return this.coleccionMapper.toDTO(coleccion);

        }
        return null;
    }

    @Override
    public List<Hecho> obtenerHechosDeColeccionSegunModoDeNavegacion(Long id, String unModoDeNavegacion) {
        Optional<Coleccion> coleccionOpt = coleccionesRepository.findById(id);

        if (coleccionOpt.isEmpty()) {
            return Collections.emptyList();
        }

        Coleccion coleccion = coleccionOpt.get();

        System.out.println(">>> MODO = " + unModoDeNavegacion);
        System.out.println(">>> Hechos totales en coleccion " + id + " = " + coleccion.getHechos().size());

        if (unModoDeNavegacion.equalsIgnoreCase("CURADO")) {
            aplicarAlgoritmoDeConsenso(coleccion);
            coleccionesRepository.save(coleccion);

            List<Hecho> curados = coleccion.getHechos().stream()
                    .filter(h -> !solicitudesService.tieneSolicitudAprobada(List.of(h)))
                    .filter(Hecho::getEstaConsensuado)
                    .toList();

            System.out.println(">>> Hechos CURADOS devueltos = " + curados.size());
            return curados;
        }

        List<Hecho> irrestrictos = coleccion.getHechos().stream()
                .filter(h -> !solicitudesService.tieneSolicitudAprobada(List.of(h)))
                .toList();

        System.out.println(">>> Hechos IRRESTRICTOS devueltos = " + irrestrictos.size());
        return irrestrictos;
    }

    @Override
    public void aplicarAlgoritmosAColecciones() {
        List<Coleccion> colecciones = this.coleccionesRepository.findAll();
        for (Coleccion coleccionIndice : colecciones) {
            if (!coleccionIndice.getHechos().isEmpty()) {
                aplicarAlgoritmoDeConsenso(coleccionIndice); // marca flags en hechos
                coleccionesRepository.save(coleccionIndice);
            }
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

    @Override
    public List<ColeccionOutputDTO> obtenerColeccionesDestacadas() {
        List<Coleccion> todas = coleccionesRepository.findAll();
        Collections.shuffle(todas);
        List<Coleccion> seleccionadas = todas.stream()
                .limit(6)
                .collect(Collectors.toList());
        return seleccionadas.stream()
                .map(coleccionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void aplicarAlgoritmoDeConsenso(Coleccion unaColeccion) {
        IAlgoritmo algoritmo = algoritmoFactory.crear(unaColeccion.getAlgoritmoDeConsensoEnumerado());
        if (algoritmo != null) {
            algoritmo.aplicarConsenso(unaColeccion);
        }
    }
}