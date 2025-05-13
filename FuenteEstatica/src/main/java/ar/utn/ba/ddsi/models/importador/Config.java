package ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteEstatica.models.importador;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final String ARCHIVO_CONFIG = "archivo.properties";
    private static final Properties props = new Properties();

    static {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream(ARCHIVO_CONFIG)) {
            if (input == null) {
                throw new RuntimeException("No se encontró el archivo de configuración en el classpath: " + ARCHIVO_CONFIG);
            }
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error leyendo archivo de configuración: " + ARCHIVO_CONFIG, e);
        }
    }

    public static String get(String clave) {
        String valor = props.getProperty(clave);
        if (valor == null) {
            throw new RuntimeException("No se encontró la clave '" + clave + "' en el archivo de configuración.");
        }
        return valor;
    }
}
