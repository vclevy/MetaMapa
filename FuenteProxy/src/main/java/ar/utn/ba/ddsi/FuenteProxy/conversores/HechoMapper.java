package ar.utn.ba.ddsi.FuenteProxy.conversores;

import ar.utn.ba.ddsi.FuenteProxy.models.dtos.input.HechoInputDTO;
import ar.utn.ba.ddsi.FuenteProxy.models.dtos.output.HechoOutputDTO;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.Categoria;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.hecho.Lugar;
import ar.utn.ba.ddsi.FuenteProxy.models.repositories.ICategoriaRepository;
import ar.utn.ba.ddsi.FuenteProxy.normalizador.NormalizadorHechos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HechoMapper {
    @Autowired
    private ICategoriaRepository categoriaRepository;

    @Autowired
    private NormalizadorHechos normalizadorHechos;

    public Hecho adaptar(HechoInputDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("El DTO no puede ser nulo");
        }

        Hecho hecho = new Hecho();
        hecho.setId(dto.getId());
        hecho.setTitulo(dto.getTitulo());
        hecho.setDescripcion(dto.getDescripcion());
        hecho.setFechaDeAcontecimiento(dto.getFechaHecho());
        hecho.setCreatedAt(dto.getCreatedAt());
        hecho.setUpdatedAt(dto.getUpdatedAt());

        // Normalizar la categoría antes de asignarla
        if (dto.getCategoria() != null && !dto.getCategoria().isBlank()) {
            // Crear una categoría temporal para normalizar
            Categoria categoriaTemporal = new Categoria();
            categoriaTemporal.setNombre(dto.getCategoria().trim());
            hecho.setCategoria(categoriaTemporal);

            // Normalizar la categoría usando NormalizadorHechos
            hecho = normalizadorHechos.normalizar(hecho);

            // Obtener el nombre normalizado
            String nombreCategoriaNormalizado = hecho.getCategoria().getNombre();

            // Buscar o persistir la categoría normalizada
            Categoria categoriaPersistida = categoriaRepository
                    .findByNombre(nombreCategoriaNormalizado)
                    .orElseGet(() -> {
                        Categoria nueva = new Categoria();
                        nueva.setNombre(nombreCategoriaNormalizado);
                        return categoriaRepository.save(nueva);
                    });

            hecho.setCategoria(categoriaPersistida);
        } else {
            // Opcional: asignar una categoría por defecto o dejar null
            hecho.setCategoria(null);
        }

        // Asignar lugar si las coordenadas están presentes
        if (dto.getLatitud() != null || dto.getLongitud() != null) {
            Lugar lugar = new Lugar();
            lugar.setLatitud(dto.getLatitud());
            lugar.setLongitud(dto.getLongitud());
            hecho.setLugar(lugar);
        }

        return hecho;
    }

    public HechoOutputDTO aOutputDTO(Hecho hecho) {
        HechoOutputDTO dto = new HechoOutputDTO();
        dto.setId(hecho.getId());
        dto.setTitulo(hecho.getTitulo());
        dto.setDescripcion(hecho.getDescripcion());
        dto.setCategoria(hecho.getCategoria() != null ? hecho.getCategoria().getNombre() : null);
        dto.setFechaHecho(hecho.getFechaDeAcontecimiento());
        dto.setCreatedAt(hecho.getCreatedAt());
        dto.setUpdatedAt(hecho.getUpdatedAt());

        if (hecho.getLugar() != null) {
            dto.setLatitud(hecho.getLugar().getLatitud());
            dto.setLongitud(hecho.getLugar().getLongitud());
        }

        return dto;
    }
}
