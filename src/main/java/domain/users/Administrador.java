package domain.users;
import com.opencsv.exceptions.CsvValidationException;
import domain.coleccion.*;
import domain.hecho.solicitudes.GestorDeSolicitudes;
import domain.hecho.solicitudes.Solicitud;
import domain.utils.importador.Config;
import domain.utils.importador.Importador;
import lombok.Getter;
import lombok.Setter;
import java.io.IOException;

@Setter
@Getter
public class Administrador {
    private String nombre;
    private GestorDeSolicitudes gestorDeSolicitudes = new GestorDeSolicitudes();
    private Importador importador;

    public void elegirImportador(Importador importador) {
        this.importador = importador;
    }


    public void aprobarSolicitud(Solicitud unaSolicitud) {
        this.gestorDeSolicitudes.aprobarSolicitud(unaSolicitud);
    }

    public void rechazarSolicitud(Solicitud unaSolicitud) {
        this.gestorDeSolicitudes.rechazarSolicitud(unaSolicitud);
    }
}


