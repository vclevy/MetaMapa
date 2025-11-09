package ar.utn.ba.ddsi.services;

import ar.utn.ba.ddsi.models.dtos.input.HechoInputDTO;
import ar.utn.ba.ddsi.models.dtos.output.HechoOutputDTO;
import ar.utn.ba.ddsi.models.entities.Usuario;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IHechosServices {
    void subirHecho(HechoInputDTO hecho, MultipartFile[] archivos);
    void editarHecho(Long id, HechoInputDTO hechoDto, MultipartFile[] archivos);
    List<HechoOutputDTO> obtenerHechos();
}
