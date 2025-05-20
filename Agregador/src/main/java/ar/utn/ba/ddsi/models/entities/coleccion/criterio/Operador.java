package ar.utn.ba.ddsi.models.entities.coleccion.criterio;

public interface Operador {
    boolean evaluar(Object valorHecho, Object valorCriterio);
}