package ar.utn.ba.ddsi.gateway.cliente_liviano.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${hechos.upload.dir}")
    private String uploadDir;

    @Value("${hechos.upload.url-path}")
    private String uploadUrlPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();

        registry.addResourceHandler(uploadUrlPath)
                .addResourceLocations("file:" + uploadPath.toUri().getPath());
    }
}
