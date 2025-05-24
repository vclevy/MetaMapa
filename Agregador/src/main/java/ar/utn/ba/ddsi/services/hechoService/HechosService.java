package ar.utn.ba.ddsi.services.hechoService;

import ar.utn.ba.ddsi.models.dtos.output.HechoOutputDTO;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.repositories.IHechosRepository;
import ar.utn.ba.ddsi.services.fuentes.FuenteDeHechos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HechosService implements IHechosService {
    @Autowired
    private IHechosRepository hechosRepository;
    private final List<FuenteDeHechos> fuentesDeHechos;

    public HechosService(List<FuenteDeHechos> fuentes, IHechosRepository hechosRepository) {
        this.fuentesDeHechos = fuentes;
        this.hechosRepository = hechosRepository;
    }

    @Override
    public HechoOutputDTO findById(Integer id) {
        var hecho = this.hechosRepository.findById(id);
        if(hecho == null) {
            return null;
        }
        return hechoOutputDTO(hecho);
    }

    @Override
    public void eliminar(Integer id) {
        var hecho = this.hechosRepository.findById(id);
        if(hecho != null){
            this.hechosRepository.delete(hecho);
        }
    }

    public void obtenerTodosLosHechosDeTodasLasFuentes() {
        for (FuenteDeHechos fuenteIndice : fuentesDeHechos) {
            List<Hecho> hechos = fuenteIndice.obtenerHechos();
            for (Hecho hechoIndice : hechos) {
                this.hechosRepository.save(hechoIndice);
            }
        }
    }

    private HechoOutputDTO hechoOutputDTO(Hecho unHecho) {
        HechoOutputDTO hechoOutputDTO = new HechoOutputDTO();
        hechoOutputDTO.setTitulo(unHecho.getTitulo());
        hechoOutputDTO.setDescripcion(unHecho.getDescripcion());
        hechoOutputDTO.setCategoria(unHecho.getCategoria());
        hechoOutputDTO.setFechaDeAcontecimiento(unHecho.getFechaDeAcontecimiento());
        hechoOutputDTO.setLugar(unHecho.getLugar());
        hechoOutputDTO.setSolicitudesDeEliminacion(unHecho.getSolicitudesDeEliminacion());
        hechoOutputDTO.setEtiquetas(unHecho.getEtiquetas());
        hechoOutputDTO.setMultimedia(unHecho.getMultimedia());
        hechoOutputDTO.setContribuyente(unHecho.getContribuyente());
        return hechoOutputDTO;
    }
}
