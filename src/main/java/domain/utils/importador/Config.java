package domain.utils.importador;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final String ARCHIVO_CONFIG = "config.properties";

    public static String get(String clave) {
        Properties props = new Properties();
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream(ARCHIVO_CONFIG)) {
            if (input == null) {
                throw new RuntimeException("No se encontró el archivo de configuración.");
            }
            props.load(input);
            return props.getProperty(clave);
        } catch (IOException e) {
            throw new RuntimeException("Error leyendo archivo de configuración", e);
        }
    }
}
