package ar.utn.ba.ddsi.services.hechoService;

import ar.utn.ba.ddsi.models.dtos.output.HechoOutputDTO;

import java.util.List;

public interface IHechosService {
    public HechoOutputDTO findById(Integer id);
    public void eliminar(Integer id);
    public List<HechoOutputDTO> findAll();
    public void actualizarHechosDeTodasLasFuentes();
}
