package ar.utn.ba.ddsi.services.impl;

import ar.utn.ba.ddsi.models.dtos.output.HechoOutputDTO;
import ar.utn.ba.ddsi.models.dtos.input.HechoInputDTO;
import ar.utn.ba.ddsi.models.entities.Usuario;
import ar.utn.ba.ddsi.models.entities.hecho.Categoria;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.hecho.Lugar;
import ar.utn.ba.ddsi.models.entities.hecho.OrigenDelHecho;
import ar.utn.ba.ddsi.models.repositories.ICategoriaRepository;
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

    @Autowired
    private ICategoriaRepository categoriaRepository;

    @Autowired
    private NormalizadorHechos normalizadorHechos;

    @Value("${hechos.upload.dir}")
    private String uploadDir;

    private HechoOutputDTO hechoOutputDTO(Hecho hecho) {
        HechoOutputDTO dto = new HechoOutputDTO();
        dto.setId(hecho.getId());
        dto.setTitulo(hecho.getTitulo());
        dto.setDescripcion(hecho.getDescripcion());
        dto.setCategoria(hecho.getCategoria());
        dto.setFechaDeAcontecimiento(hecho.getFechaDeAcontecimiento());
        dto.setLugar(hecho.getLugar());
        dto.setOrigen(hecho.getOrigen());
        dto.setSolicitudesDeEliminacion(hecho.getSolicitudesDeEliminacion());
        dto.setEtiquetas(hecho.getEtiquetas());
        dto.setFueEliminado(hecho.getFueEliminado());
        dto.setEsEditable(hecho.esEditable());
        return dto;
    }

    public Hecho inputDTOAHecho(HechoInputDTO dto) {

        Hecho hecho = new Hecho();
        hecho.setTitulo(dto.getTitulo());
        hecho.setDescripcion(dto.getDescripcion());
        hecho.setFechaDeAcontecimiento(dto.getFechaDeAcontecimiento());
        hecho.setMultimedia(dto.getMultimedia());
        hecho.setFueEliminado(false);
        hecho.setEsAnonimo(hecho.esAnonimo());
        hecho.setSolicitudesDeEliminacion(new ArrayList<>());
        hecho.setContribuyente(null); // TODO: asignar contribuyente real

        Lugar lugar = new Lugar();
        lugar.setLatitud(dto.getLugar().getLatitud());
        lugar.setLongitud(dto.getLugar().getLongitud());
        hecho.setLugar(lugar);

        Categoria categoriaProvisional = new Categoria();
        categoriaProvisional.setNombre(dto.getCategoria().getNombre());
        hecho.setCategoria(categoriaProvisional);

        hecho = normalizadorHechos.normalizar(hecho);
        String nombreCategoria = hecho.getCategoria().getNombre();

        Categoria categoriaPersistida = categoriaRepository
                .findByNombre(hecho.getCategoria().getNombre())
                .orElseGet(() -> {
                    Categoria nueva = new Categoria();
                    nueva.setNombre(nombreCategoria);
                    return categoriaRepository.save(nueva);
                });

        hecho.setCategoria(categoriaPersistida);

        return hecho;
    }



    public void subirHecho(HechoInputDTO hechoDto, MultipartFile[] archivos) {
        Hecho nuevoHecho = inputDTOAHecho(hechoDto);
        nuevoHecho.setFechaDeCarga(LocalDateTime.now());
        nuevoHecho.setOrigen(OrigenDelHecho.CONTRIBUYENTE);

        List<String> rutasMultimedia = guardarArchivos(archivos);
        if (hechoDto.getMultimedia() != null) {
            rutasMultimedia.addAll(hechoDto.getMultimedia());
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
                    rutas.add("/uploads/" + nombreArchivo);
                } catch (IOException e) {
                    throw new RuntimeException("Error al guardar archivo multimedia", e);
                }
            }
        }
        return rutas;
    }

    public void editarHecho(Long id, HechoInputDTO hechoModificado, Usuario usuario) {
        Hecho hechoOriginal = repositorioDeHechos.findById(id)
                .orElseThrow(() -> new RuntimeException("Hecho no encontrado"));

        if (hechoOriginal.getContribuyente() == null) {
            throw new RuntimeException("Un usuario anónimo no puede editar hechos");
        }

        if (!hechoOriginal.esEditable()) {
            throw new RuntimeException("Este hecho ya no puede ser editado o no sos su autor");
        }

        // TODO: comparar cambios y registrar
        // compararYRegistrarCambios(hechoOriginal, hechoModificado, usuario);

        repositorioDeHechos.save(hechoOriginal);
    }

    public List<HechoOutputDTO> obtenerHechos() {
        return repositorioDeHechos.findAll()
                .stream()
                .map(this::hechoOutputDTO)
                .toList();
    }
}
