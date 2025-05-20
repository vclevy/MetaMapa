package ar.utn.ba.ddsi.models.importador; //Justificaciones de diseño: Patron Strategy e inyector de dependencias


import ar.utn.ba.ddsi.models.entities.Hecho;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.util.List;

public interface Importador {

    public List<Hecho> importarHechos(String archivo) throws IOException, CsvValidationException;
}
