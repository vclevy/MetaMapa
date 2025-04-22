package domain.coleccion;

import domain.hecho.Hecho;

import java.time.LocalDate;
import java.util.List;

public interface CriterioDePertenencia {
    public boolean cumple(Hecho unHecho);
}


