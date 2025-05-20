package ar.utn.ba.ddsi.models.entities.coleccion.criterio.impl;

import ar.utn.ba.ddsi.models.entities.coleccion.criterio.Operador;

public class OperadorIgual implements Operador {
    @Override
    public boolean evaluar(Object valorHecho, Object valorCriterio) {
        return valorHecho != null && valorHecho.equals(valorCriterio);
    }
}