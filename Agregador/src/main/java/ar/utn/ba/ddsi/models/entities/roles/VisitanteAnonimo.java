package ar.utn.ba.ddsi.models.entities.roles;

import ar.utn.ba.ddsi.models.entities.roles.roleDefinition.IRol;
import ar.utn.ba.ddsi.models.entities.roles.roleDefinition.Permisos;

import java.util.Set;
import java.util.EnumSet;

public class VisitanteAnonimo implements IRol {

    private static final Set<Permisos> permisos = EnumSet.of(
            Permisos.VER_HECHOS,
            Permisos.SUBIR_HECHO,
            Permisos.SOLICITAR_ELIMINACION,
            Permisos.VER_COLECCIONES
    );

    @Override
    public Boolean tenesPermiso(Permisos permiso) {
        return permisos.contains(permiso);
    }
}