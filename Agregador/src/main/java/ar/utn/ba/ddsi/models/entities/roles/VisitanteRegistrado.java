package ar.utn.ba.ddsi.models.entities.roles;

import ar.utn.ba.ddsi.models.entities.roles.roleDefinition.IRol;
import ar.utn.ba.ddsi.models.entities.roles.roleDefinition.Permisos;

import java.util.EnumSet;
import java.util.Set;

public class VisitanteRegistrado implements IRol {

    private static final Set<Permisos> permisos = EnumSet.of(
            Permisos.VER_HECHOS,
            Permisos.SUBIR_HECHO,
            Permisos.EDITAR_HECHO,
            Permisos.SOLICITAR_ELIMINACION,
            Permisos.VER_COLECCIONES
    );

    @Override
    public Boolean tenesPermiso(Permisos permiso) {
        return permisos.contains(permiso);
    }
}