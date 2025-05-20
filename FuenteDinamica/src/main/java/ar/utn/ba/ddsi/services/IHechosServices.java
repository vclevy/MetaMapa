package ar.utn.ba.ddsi.services;

import ar.utn.ba.ddsi.models.dtos.input.HechoInputDTO;
import ar.utn.ba.ddsi.models.dtos.output.HechoOutputDTO;
import ar.utn.ba.ddsi.models.entities.Usuario;

public interface IHechosServices {

    public void subirHecho(HechoInputDTO hecho);
    public void editarHecho(int id, HechoInputDTO hechoModificado);
}
