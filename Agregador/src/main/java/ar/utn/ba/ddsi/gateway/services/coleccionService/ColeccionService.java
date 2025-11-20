package ar.utn.ba.ddsi.gateway.services.coleccionService;

import ar.utn.ba.ddsi.gateway.models.dtos.input.colecciones.ColeccionInputDTO;
import ar.utn.ba.ddsi.gateway.models.dtos.input.colecciones.ColeccionPatchDTO;
import ar.utn.ba.ddsi.gateway.models.dtos.input.fuentesDeHechos.FuenteCreateDTO;
import ar.utn.ba.ddsi.gateway.models.repositories.IFuenteDeHechosRepository;
import ar.utn.ba.ddsi.gateway.models.dtos.output.ColeccionOutputDTO;
import ar.utn.ba.ddsi.gateway.models.dtos.output.FuenteDeHechoOutputDTO;
import ar.utn.ba.ddsi.gateway.models.entities.coleccion.Coleccion;
import ar.utn.ba.ddsi.gateway.models.entities.fuentes.Fuente;
import ar.utn.ba.ddsi.gateway.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.gateway.models.repositories.IColeccionesRepository;
import ar.utn.ba.ddsi.gateway.services.coleccionService.algoritmoConsenso.IAlgoritmo;
import ar.utn.ba.ddsi.gateway.services.georef.LugarService;
import ar.utn.ba.ddsi.gateway.services.hechoService.IHechoService;
import ar.utn.ba.ddsi.gateway.services.mappers.ColeccionMapper;
import ar.utn.ba.ddsi.gateway.services.solicitudService.ISolicitudesService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.utn.ba.ddsi.gateway.services.factory.AlgoritmoFactory;
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
    @Transactional
    public void refrescarColecciones() {
        List<Coleccion> colecciones = coleccionesRepository.findAll();

        for (Coleccion coleccion : colecciones) {
            for (Fuente fuente : coleccion.getFuentesDeHechos()) {
                refrescarColeccion(coleccion, fuente);
            }
        }
    }

    @Override
    @Transactional
    public void refrescarColeccion(Coleccion unaColeccion, Fuente nuevaFuente) {
        Fuente fuentePersistida;

        if (nuevaFuente.getId() == null) {
            fuentePersistida = fuenteRepository.save(nuevaFuente);
        } else {
            fuentePersistida = fuenteRepository.findById(nuevaFuente.getId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Fuente inexistente con id " + nuevaFuente.getId()));
        }

        fuentePersistida.inicializarFuenteDeHechos(lugarService);

        Set<String> clavesExistentes = unaColeccion.getHechos().stream()
                .map(h -> construirClave(h.getTitulo(), h.getDescripcion()))
                .collect(Collectors.toSet());

        List<Hecho> hechosDeFuente = fuentePersistida.obtenerHechos();

        for (Hecho hecho : hechosDeFuente) {
            hecho.setFuente(fuentePersistida);
            String claveHecho = construirClave(hecho.getTitulo(), hecho.getDescripcion());
            if (clavesExistentes.contains(claveHecho)) {
                // Ya existe => no lo agregamos
                continue;
            }

            // Registrar y agregar
            this.hechoService.registrarHechoDesdeFuente(hecho);
            unaColeccion.getHechos().add(hecho);

            // Registrar la clave nueva para evitar duplicados dentro de este mismo refresco
            clavesExistentes.add(claveHecho);

            // Ejecutar consenso
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

            // 1) Filtramos consensuados sin solicitudes aprobadas
            List<Hecho> curadosSinDuplicados = coleccion.getHechos().stream()
                    .filter(h -> !solicitudesService.tieneSolicitudAprobada(List.of(h)))
                    .filter(Hecho::getEstaConsensuado)
                    // 2) Nos quedamos con un solo hecho por "clave lógica"
                    .collect(Collectors.toMap(
                            h -> construirClave(h.getTitulo(), h.getDescripcion()),
                            h -> h,
                            (h1, h2) -> h1      // si hay dos con la misma clave, nos quedamos con el primero
                    ))
                    .values()
                    .stream()
                    .toList();

            System.out.println(">>> Hechos CURADOS devueltos (sin duplicados) = " + curadosSinDuplicados.size());
            return curadosSinDuplicados;
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
    @Transactional
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
    @Transactional
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

    @Override
    public void editarColeccion(Long idColeccion, ColeccionOutputDTO coleccionOutputDTO) {

        //TODO
    }

    @Override
    public String construirClave(String titulo, String descripcion) {
        return (titulo == null ? "" : titulo) + "||" + (descripcion == null ? "" : descripcion);
    }
}