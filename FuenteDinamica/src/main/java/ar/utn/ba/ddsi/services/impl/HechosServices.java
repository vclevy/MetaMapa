package ar.utn.ba.ddsi.services.impl;

import ar.utn.ba.ddsi.models.dtos.output.HechoOutputDTO;
import ar.utn.ba.ddsi.models.dtos.input.HechoInputDTO;
import ar.utn.ba.ddsi.models.entities.Usuario;
import ar.utn.ba.ddsi.models.entities.hecho.EstadoRevision;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.hecho.Revision;
import ar.utn.ba.ddsi.models.entities.roles.Permisos;
import ar.utn.ba.ddsi.models.repositories.IHechosRepository;
import ar.utn.ba.ddsi.services.IHechosServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class HechosServices implements IHechosServices {
    @Autowired
    private IHechosRepository repositorioDeHechos;

    @Value("${hechos.upload.dir}")
    private String uploadDir;

    private HechoOutputDTO hechoOutputDTO(Hecho hecho) {
        HechoOutputDTO hechoOutputDTO = new HechoOutputDTO();
        hechoOutputDTO.setId(hecho.getId());
        hechoOutputDTO.setTitulo((hecho.getTitulo()));
        hechoOutputDTO.setDescripcion(hecho.getDescripcion());
        hechoOutputDTO.setCategoria(hecho.getCategoria());
        hechoOutputDTO.setFechaDeAcontecimiento(hecho.getFechaDeAcontecimiento());
        hechoOutputDTO.setLugar(hecho.getLugar());
        hechoOutputDTO.setOrigen(hecho.getOrigen());
        hechoOutputDTO.setSolicitudesDeEliminacion(hecho.getSolicitudesDeEliminacion());
        hechoOutputDTO.setEtiquetas(hecho.getEtiquetas());
        hechoOutputDTO.setFueEliminado(hecho.getFueEliminado());
        hechoOutputDTO.setEsEditable(hecho.esEditable());
        return hechoOutputDTO;
    }

    public HechoInputDTO hechoInputDTO(Hecho hecho) {
        HechoInputDTO dto = new HechoInputDTO();
        dto.setTitulo(hecho.getTitulo());
        dto.setDescripcion(hecho.getDescripcion());
        dto.setFechaDeAcontecimiento(hecho.getFechaDeAcontecimiento());
        dto.setLugar(hecho.getLugar());
        dto.setEtiquetas(hecho.getEtiquetas());
        dto.setMultimedia(hecho.getMultimedia());

        return dto;
    }

    public HechoOutputDTO findById(Integer id) {
        return hechoOutputDTO(repositorioDeHechos.findById(id));
    }

    public Hecho inputDTOAHecho(HechoInputDTO dto) {
        Hecho hecho = new Hecho();

        hecho.setTitulo(dto.getTitulo());
        hecho.setDescripcion(dto.getDescripcion());
        hecho.setFechaDeAcontecimiento(dto.getFechaDeAcontecimiento());
        hecho.setLugar(dto.getLugar());
        hecho.setEtiquetas(dto.getEtiquetas());
        hecho.setMultimedia(dto.getMultimedia());
        hecho.setFueEliminado(false);
        hecho.setEsAnonimo(hecho.esAnonimo());
        hecho.setSolicitudesDeEliminacion(new ArrayList<>());
        hecho.setContribuyente(null);
        return hecho;
    }

    public void subirHecho(HechoInputDTO hecho, MultipartFile[] archivos) {
        Hecho nuevoHecho = inputDTOAHecho(hecho);
        nuevoHecho.setFechaDeCarga(LocalDateTime.now());

        List<String> rutasMultimedia = guardarArchivos(archivos);

        if (hecho.getMultimedia() != null) {
            rutasMultimedia.addAll(hecho.getMultimedia());
        }

        nuevoHecho.setMultimedia(rutasMultimedia);
        repositorioDeHechos.save(nuevoHecho);
    }

    private List<String> guardarArchivos(MultipartFile[] archivos) {
        List<String> rutas = new ArrayList<>();

        if (archivos == null) return rutas;

        for (MultipartFile archivo : archivos) {
            if (!archivo.isEmpty()) {
                try {
                    String nombreArchivo = UUID.randomUUID() + "_" + archivo.getOriginalFilename();
                    Path destino = Paths.get(uploadDir).resolve(nombreArchivo);
                    Files.createDirectories(destino.getParent());
                    Files.write(destino, archivo.getBytes());
                    rutas.add("/uploads/" + nombreArchivo); // Ruta accesible desde navegador
                } catch (IOException e) {
                    throw new RuntimeException("Error al guardar archivo multimedia", e);
                }
            }
        }

        return rutas;
    }

    public void editarHecho(int id, HechoInputDTO hechoModificado) {
        Hecho hechoOriginal = repositorioDeHechos.findById(id);
        if (hechoOriginal == null) {
            throw new RuntimeException("Hecho no encontrado");
        }

        if (hechoOriginal.getContribuyente() == null) {
            throw new RuntimeException("Un usuario anónimo no puede editar hechos");
        }

        if (!hechoOriginal.esEditable()) {
            throw new RuntimeException("Este hecho ya no puede ser editado o no sos su autor");
        }

        hechoOriginal.setTitulo(hechoModificado.getTitulo());
        hechoOriginal.setDescripcion((hechoModificado.getDescripcion()));
        hechoOriginal.setFechaDeAcontecimiento(hechoModificado.getFechaDeAcontecimiento());
        hechoOriginal.setLugar((hechoModificado.getLugar()));
        hechoOriginal.setEtiquetas(hechoModificado.getEtiquetas());
        hechoOriginal.setMultimedia(hechoModificado.getMultimedia());
        repositorioDeHechos.save(hechoOriginal);
    }

    public void revisarHecho(int id, EstadoRevision nuevoEstado, String comentario, Usuario admin) {
        if (!admin.getRol().tenesPermiso(Permisos.REVISAR_HECHO)) {
            throw new RuntimeException("No tenés permiso para revisar hechos");
        }

        Hecho hecho = repositorioDeHechos.findById(id);
        if (hecho == null) {
            throw new RuntimeException("Hecho no encontrado");
        }

        Revision nuevaRevision = new Revision(nuevoEstado, comentario);
        hecho.setRevision(nuevaRevision);
        repositorioDeHechos.save(hecho);
    }
}




