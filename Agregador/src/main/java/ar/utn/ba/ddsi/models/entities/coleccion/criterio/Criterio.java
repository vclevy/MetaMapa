package ar.utn.ba.ddsi.models.entities.coleccion.criterio;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Criterio {
    private String atributo;
    private Operador operador;
    private Object valor;

    public Criterio(String atributo, Operador operador, Object valor) {
        this.atributo = atributo;
        this.operador = operador;
        this.valor = valor;
    }

    public boolean cumple(Hecho hecho) {
        Object valorHecho = hecho.getAtributoPorNombre(atributo);
        return operador.evaluar(valorHecho, valor);
    }
}
