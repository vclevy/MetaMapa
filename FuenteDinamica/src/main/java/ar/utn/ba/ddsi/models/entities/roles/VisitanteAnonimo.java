package ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteDinamica.models.entities.roles;

import java.util.Set;

import java.util.Set;
import java.util.EnumSet;

public class VisitanteAnonimo implements Rol {

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
