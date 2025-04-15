package domain.utils.importador; //Justificaciones de diseño: Patron Strategy e inyector de dependencias

import com.opencsv.exceptions.CsvValidationException;
import domain.coleccion.Coleccion;

import java.io.IOException;

public interface Importador {

    public void importarHechos(String archivo, Coleccion coleccion) throws IOException, CsvValidationException;
}
