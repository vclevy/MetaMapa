package ar.utn.ba.ddsi.models.entities.roles;

import ar.utn.ba.ddsi.models.entities.roles.roleDefinition.IRol;
import ar.utn.ba.ddsi.models.entities.roles.roleDefinition.Permisos;

import java.util.EnumSet;
import java.util.Set;

public class Revisor implements IRol {
    private static final Set<Permisos> permisos = EnumSet.of(
            Permisos.VER_HECHOS,
            Permisos.ELIMINAR_HECHO,
            Permisos.IMPORTAR_HECHOS,
            Permisos.VER_COLECCIONES,
            Permisos.CREAR_COLECCION,
            Permisos.EDITAR_COLECCION,
            Permisos.ELIMINAR_HECHO,
            Permisos.GESTIONAR_ETIQUETAS,
            Permisos.VER_SOLICITUDES,
            Permisos.GESTIONAR_SOLICITUDES,
            Permisos.REVISAR_HECHO
    );

    public Boolean tenesPermiso(Permisos permiso) {
        return permisos.contains(permiso);
    }
}
