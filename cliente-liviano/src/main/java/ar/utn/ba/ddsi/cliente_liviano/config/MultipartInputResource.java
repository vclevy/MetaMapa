package ar.utn.ba.ddsi.cliente_liviano.config;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
public class MultipartInputResource extends ByteArrayResource {

    private final String filename;

    public MultipartInputResource(byte[] byteArray, String filename) {
        super(byteArray);
        this.filename = filename;
    }

    @Override
    public String getFilename() {
        return this.filename;
    }
}

