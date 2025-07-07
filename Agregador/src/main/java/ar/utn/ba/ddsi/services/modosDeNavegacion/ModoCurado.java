package ar.utn.ba.ddsi.services.modosDeNavegacion;

import ar.utn.ba.ddsi.models.entities.coleccion.Coleccion;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;

import java.util.List;

public class ModoCurado implements IModoDeNavegacion{
    @Override
    public List<Hecho> navegar(Coleccion unaColeccion) {
        return unaColeccion.getHechosConAlgotimoAplicado();
    }
}
