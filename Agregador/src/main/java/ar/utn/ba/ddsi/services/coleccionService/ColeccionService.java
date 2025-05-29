package ar.utn.ba.ddsi.services.coleccionService;

import ar.utn.ba.ddsi.models.dtos.input.ColeccionInputDTO;
import ar.utn.ba.ddsi.models.dtos.output.ColeccionOutputDTO;
import ar.utn.ba.ddsi.models.entities.coleccion.Coleccion;
import ar.utn.ba.ddsi.models.entities.coleccion.Criterio;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.solicitud.EstadoDeSolicitudDeEliminacion;
import ar.utn.ba.ddsi.models.repositories.IColeccionesRepository;
import ar.utn.ba.ddsi.models.repositories.IHechosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ColeccionService implements IColeccionService {
    @Autowired
    private IColeccionesRepository coleccionesRepository;

    @Autowired
    private IHechosRepository hechosRepository;

    @Override
    public void delete(String unHandle) {
        var coleccion = this.coleccionesRepository.findByHandle(unHandle);
        if (coleccion != null) {
            this.coleccionesRepository.delete(unHandle);
        }
    }

    @Override
    public ColeccionOutputDTO findByHandle(String unHandle) {
        var coleccion = this.coleccionesRepository.findByHandle(unHandle);
        if (coleccion != null) {
            return null;
        }
        return this.coleccionOutputDTO(coleccion);
    }

    @Override
    public void crear(ColeccionInputDTO unaColeccionInputDTO) {
        var coleccion = new Coleccion(
                unaColeccionInputDTO.getTitulo(),
                unaColeccionInputDTO.getDescripcion()
        );

        this.coleccionesRepository.save(coleccion);
    }

    public static void agregarHechoAColeccion(Coleccion coleccion, Hecho hecho, List<Criterio> criterios) {
        if (hecho == null || hecho.getSolicitudesDeEliminacion().stream().anyMatch(solicitud -> solicitud.getEstado() == EstadoDeSolicitudDeEliminacion.APROBADA)) {
            return;
        }

        for (Criterio criterio : criterios) {
            if (!criterio.cumple(hecho)) {
                return;
            }
        }

        coleccion.getHechos().add(hecho);
    }

    @Override
    public void actualizarColecciones() {
        for (Coleccion coleccion : coleccionesRepository.findAll()) {
            for (Hecho hechoIndice : hechosRepository.findAll()) {
              agregarHechoAColeccion(coleccion, hechoIndice, coleccion.getCriterioDePertenencia());
            }
        }
    }

    public List<Hecho> obtenerHechosFiltradosPorColeccion(Coleccion coleccion) {
        return hechosRepository.findAll().stream()
                .filter(hecho -> coleccion.cumpleCriterios(hecho,coleccion.getCriterioDePertenencia()))
                .collect(Collectors.toList());
    }

    private ColeccionOutputDTO coleccionOutputDTO(Coleccion unaColeccion) {
        ColeccionOutputDTO coleccionOutputDTO = new ColeccionOutputDTO();
        coleccionOutputDTO.setTitulo(unaColeccion.getTitulo());
        coleccionOutputDTO.setDescripcion(unaColeccion.getDescripcion());
        //coleccionOutputDTO.setHandle(unaColeccion.getHandle());
        // TODO: VER QUE LOS HECHOS TAMBIEN SEAN OUTPUTS: coleccionOutputDTO.setHechosOutputDtos(new ArrayList<>());
        return coleccionOutputDTO;
    }
}
