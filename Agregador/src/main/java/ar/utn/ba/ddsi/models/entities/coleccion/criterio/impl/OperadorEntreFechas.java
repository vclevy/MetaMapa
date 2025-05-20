package ar.utn.ba.ddsi.models.entities.coleccion.criterio.impl;

import ar.utn.ba.ddsi.models.entities.coleccion.criterio.Operador;

import java.time.LocalDateTime;
import java.util.List;

public class OperadorEntreFechas implements Operador {
    @Override
    public boolean evaluar(Object valorHecho, Object valorCriterio) {
        if (!(valorHecho instanceof LocalDateTime) || !(valorCriterio instanceof List))
            return false;

        List<?> valores = (List<?>) valorCriterio;
        LocalDateTime desde = (LocalDateTime) valores.get(0);
        LocalDateTime hasta = (LocalDateTime) valores.get(1);

        LocalDateTime valor = (LocalDateTime) valorHecho;
        return !valor.isBefore(desde) && !valor.isAfter(hasta);
    }
}