package ar.utn.ba.ddsi.services.hechoService;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;

public interface IHechoService {
    public void registrarHechoDesdeFuente(Hecho unHecho);
    public Long definirId();
}
