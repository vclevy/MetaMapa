package ar.utn.ba.ddsi.cliente_liviano.config;

import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class MultipartInputResource extends InputStreamResource {
    private final String filename;

    public MultipartInputResource(MultipartFile file) throws IOException {
        super(file.getInputStream());
        this.filename = file.getOriginalFilename();
    }

    @Override
    public String getFilename() {
        return this.filename;
    }

    @Override
    public long contentLength() {
        return -1;
    }
}

