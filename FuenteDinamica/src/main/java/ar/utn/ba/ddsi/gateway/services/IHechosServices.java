package ar.utn.ba.ddsi.gateway.services;

import ar.utn.ba.ddsi.gateway.models.dtos.input.HechoInputDTO;
import ar.utn.ba.ddsi.gateway.models.dtos.output.HechoOutputDTO;
import ar.utn.ba.ddsi.gateway.models.entities.Usuario;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IHechosServices {
    void subirHecho(HechoInputDTO hecho, MultipartFile[] archivos);
    void editarHecho(Long id, HechoInputDTO hechoDto, MultipartFile[] archivos);
    List<HechoOutputDTO> obtenerHechos();
}
