package ar.utn.ba.ddsi.services;

import ar.utn.ba.ddsi.models.dtos.output.HechoOutputDTO;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;

import java.util.List;

public interface IHechosService {
    public HechoOutputDTO findById(Integer id);
    public void eliminar(Integer id);
    public List<HechoOutputDTO> findAll();
}
