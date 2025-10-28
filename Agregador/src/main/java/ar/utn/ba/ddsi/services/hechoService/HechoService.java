package ar.utn.ba.ddsi.services.hechoService;

import ar.utn.ba.ddsi.models.dtos.input.hecho.HechoInputPUTDTO;
import ar.utn.ba.ddsi.models.dtos.output.HechoFiltroDTO;
import ar.utn.ba.ddsi.models.dtos.output.HechoOutputDTO;
import ar.utn.ba.ddsi.models.entities.hecho.Categoria;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.hecho.Lugar;
import ar.utn.ba.ddsi.models.entities.hecho.filtros.*;
import ar.utn.ba.ddsi.models.repositories.ICategoriasRepository;
import ar.utn.ba.ddsi.models.repositories.IHechosRepository;
import ar.utn.ba.ddsi.services.hechoService.normalizador.NormalizadorHechos;
import ar.utn.ba.ddsi.services.mappers.HechoMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HechoService implements IHechoService {
    @Autowired
    private IHechosRepository hechosRepository;
    @Autowired
    private ICategoriasRepository categoriaRepository;
    @Autowired
    private NormalizadorHechos normalizadorHechos;
    @Autowired
    private HechoMapper hechoMapper;

    @Override
    public void registrarHechoDesdeFuente(Hecho unHecho) {
        Hecho hechoPosta = normalizadorHechos.normalizar(unHecho);

        if (hechoPosta.getCategoria() == null ||
                hechoPosta.getCategoria().getNombre() == null ||
                hechoPosta.getCategoria().getNombre().isBlank()) {
            throw new IllegalArgumentException("El hecho debe tener una categoría válida");
        }
        if(hechoPosta.getEsAnonimo()==null){
            hechoPosta.setEsAnonimo(true);
        }

        String nombreCategoria = hechoPosta.getCategoria().getNombre().trim();
        Categoria categoriaPersistida = categoriaRepository.findByNombre(nombreCategoria)
                .orElseGet(() -> categoriaRepository.saveAndFlush(new Categoria(nombreCategoria)));

        hechoPosta.setCategoria(categoriaPersistida);
        hechosRepository.saveAndFlush(hechoPosta);
    }

    @Override
    public List<HechoOutputDTO> obtenerHechos() {
        return this.hechosRepository.findAll().stream().filter(h -> !h.getFueEliminado()).map(h -> hechoMapper.toDTO(h)).collect(Collectors.toList());
    }

    @Override
    public List<HechoOutputDTO> obtenerHechosPendientes() {
        return this.hechosRepository.findAll().stream()
                .filter(h -> !h.getFueEliminado() && h.getPendiente() && !h.getFueAceptado())
                .map(hechoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<HechoOutputDTO> obtenerHechosVisibles() {
        return this.hechosRepository.findAll().stream()
                .filter(h -> !h.getFueEliminado() && h.getFueAceptado() && !h.getPendiente())
                .map(hechoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public HechoOutputDTO modificarHecho(Long idHecho, HechoInputPUTDTO hechoInputDTO) {
        Hecho hecho = this.hechosRepository.findById(idHecho).orElseThrow(() -> new IllegalArgumentException("Hecho no encontrado: " + idHecho));

        ZoneId zonaUTC3 = ZoneOffset.ofHours(-3);
        ZonedDateTime ahoraEnUTC3 = ZonedDateTime.now(zonaUTC3);
        LocalDateTime fechaCarga = hecho.getFechaDeCargaDelHecho();
        ZonedDateTime fechaCargaConZona = fechaCarga.atZone(zonaUTC3);

        if (fechaCarga == null) {
            throw new IllegalStateException("El hecho no tiene fecha de carga registrada");
        }

        Duration transcurrido = Duration.between(fechaCargaConZona, ahoraEnUTC3);
        if (transcurrido.compareTo(Duration.ofHours(1)) >= 0) {
            throw new IllegalStateException("El hecho solo puede modificarse dentro de la primera hora desde su carga");
        }
        System.out.println("CargaHecho: " + fechaCargaConZona + " Ahora" + ahoraEnUTC3);
        this.validarModificacion(hecho, hechoInputDTO);
        Hecho guardado = this.hechosRepository.save(hecho);
        return hechoMapper.toDTO(guardado);
    }

    @Override
    public void validarModificacion(Hecho hecho, HechoInputPUTDTO dto) {
        if (dto == null) throw new IllegalArgumentException("Body vacío.");

        boolean hayAlgoParaActualizar =
                dto.getTitulo() != null ||
                        dto.getDescripcion() != null ||
                        dto.getCategoria() != null ||
                        dto.getLatitud() != null ||
                        dto.getLongitud() != null ||
                        dto.getFechaAcontecimiento() != null ||
                        dto.getMultimedia() != null;

        if (!hayAlgoParaActualizar) {
            throw new IllegalArgumentException("No hay campos para modificar.");
        }

        if (dto.getTitulo() != null) {
            String titulo = dto.getTitulo().trim();
            if (titulo.isEmpty()) throw new IllegalArgumentException("El título no puede estar vacío.");
            if (titulo.length() > 255) throw new IllegalArgumentException("El título no puede superar 255 caracteres.");
            hecho.setTitulo(titulo);
        }

        if (dto.getDescripcion() != null) {
            String desc = dto.getDescripcion().trim();
            if (desc.isEmpty()) throw new IllegalArgumentException("La descripción no puede estar vacía.");
            hecho.setDescripcion(desc);
        }

        if (dto.getCategoria() != null) {
            String nombreCat = dto.getCategoria().trim();
            var categoria = categoriaRepository.findByNombre(nombreCat)
                    .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada: " + nombreCat));
            hecho.setCategoria(categoria);
        }

        // Fecha de acontecimiento (yyyy-MM-dd)
        if (dto.getFechaAcontecimiento() != null) {
            String raw = dto.getFechaAcontecimiento().trim();
            try {
                LocalDateTime fecha = LocalDateTime.parse(raw, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

                hecho.setFechaDeAcontecimiento(fecha);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("fechaAcontecimiento debe tener formato yyyy-MM-ddTHH:mm:ss.");
            }
        }


        // Lugar: latitud/longitud
        if (dto.getLatitud() != null || dto.getLongitud() != null) {
            Lugar lugar = hecho.getLugar();
            if (lugar == null) lugar = new Lugar();

            if (dto.getLatitud() != null) {
                String latStr = dto.getLatitud().trim();
                double lat;
                try { lat = Double.parseDouble(latStr); }
                catch (NumberFormatException e) { throw new IllegalArgumentException("Latitud inválida."); }
                if (lat < -90 || lat > 90) throw new IllegalArgumentException("Latitud fuera de rango (-90 a 90).");
            }

            if (dto.getLongitud() != null) {
                String lonStr = dto.getLongitud().trim();
                double lon;
                try { lon = Double.parseDouble(lonStr); }
                catch (NumberFormatException e) { throw new IllegalArgumentException("Longitud inválida."); }
                if (lon < -180 || lon > 180) throw new IllegalArgumentException("Longitud fuera de rango (-180 a 180).");
            }

            hecho.setLugar(lugar);
        }

        if (dto.getMultimedia() != null) {
            var lista = dto.getMultimedia().stream()
                    .filter(s -> s != null && !s.trim().isEmpty())
                    .map(String::trim)
                    .collect(Collectors.toList());
            hecho.setMultimedia(new java.util.ArrayList<>(lista));
        }
    }

    @Override
    public List<HechoOutputDTO> filtrarHechos(HechoFiltroDTO filtros) {
        List<HechoSpecification> listaFiltros = new ArrayList<>();
        listaFiltros.add(new FechaHechoSpecification(filtros.getFechaDesde(), filtros.getFechaHasta()));
        listaFiltros.add(new CategoriaHechoSpecification(filtros.getCategoriaId()));
        listaFiltros.add(new FuenteHechoSpecification(filtros.getTipoFuente()));
        listaFiltros.add(new UbicacionHechoSpecification(filtros.getProvincia()));

        Specification<Hecho> spec = HechoSpecifications.combinar(listaFiltros);

        return hechosRepository.findAll(spec)
                .stream()
                .filter(h -> !h.getFueEliminado())
                .map(hechoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<HechoOutputDTO> obtenerHechosDestacados() {
        List<Hecho> todos = hechosRepository.findAll();
        Collections.shuffle(todos);
        List<Hecho> seleccionados = todos.stream()
                .filter(h -> !h.getFueEliminado())
                .limit(6)
                .collect(Collectors.toList());
        return seleccionados.stream()
                .map(hechoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public HechoOutputDTO eliminarHecho(Long idHecho) {
        Hecho hecho = this.hechosRepository.findById(idHecho).orElseThrow(() -> new IllegalArgumentException("Hecho no encontrado: " + idHecho));
        this.hechosRepository.delete(hecho);
        return hechoMapper.toDTO(hecho);
    }

    @Transactional
    public void aprobarHecho(Long id) {
        Hecho hecho = hechosRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hecho no encontrado"));
        hecho.setPendiente(false);
        hecho.setFueAceptado(true);
    }
}
