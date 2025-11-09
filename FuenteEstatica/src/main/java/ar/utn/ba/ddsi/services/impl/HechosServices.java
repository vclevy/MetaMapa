package ar.utn.ba.ddsi.services.impl;

import ar.utn.ba.ddsi.conversores.HechoMapper;
import ar.utn.ba.ddsi.models.dtos.HechoInputDTO;
import ar.utn.ba.ddsi.models.dtos.HechoOutputDTO;
import ar.utn.ba.ddsi.models.entities.hecho.Categoria;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.hecho.Lugar;
import ar.utn.ba.ddsi.models.entities.importador.Importador;
import ar.utn.ba.ddsi.models.entities.importador.ImportadorCSV;
import ar.utn.ba.ddsi.models.repositories.ICategoriaRepository;
import ar.utn.ba.ddsi.models.repositories.IHechosRepository;
import ar.utn.ba.ddsi.normalizador.NormalizadorHechos;
import ar.utn.ba.ddsi.services.IHechosServices;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import static ar.utn.ba.ddsi.conversores.HechoMapper.*;

@Service
public class HechosServices implements IHechosServices {

    @Autowired
    private IHechosRepository repositorioDeHechos;
    private Importador importadorCSV = new ImportadorCSV();
    @Autowired
    private ICategoriaRepository categoriaRepository;
    @Autowired
    private HechoMapper hechoMapper;
    @Autowired
    private NormalizadorHechos normalizadorHechos;

    @Override
    public void importarHechos(List<String> unosArchivos) throws IOException, CsvValidationException {
        for (String archivoIndice : unosArchivos) {
            for (HechoInputDTO hechoInputIndice : importadorCSV.importarHechos(archivoIndice)) {
                Hecho hechoConvertido = this.inputDTOAHecho(hechoInputIndice);
                this.repositorioDeHechos.save(hechoConvertido);
            }
        }
    }

    public Hecho inputDTOAHecho(HechoInputDTO unHechoInput) {
        Hecho hecho = toHecho(unHechoInput);

        hecho = normalizadorHechos.normalizar(hecho);

        if (hecho.getCategoria() != null && !hecho.getCategoria().getNombre().isBlank()) {
            String nombreCategoria = hecho.getCategoria().getNombre().trim();

            Categoria categoriaPersistida = categoriaRepository
                    .findByNombre(nombreCategoria)
                    .orElseGet(() -> {
                        Categoria nueva = new Categoria();
                        nueva.setNombre(nombreCategoria);
                        return categoriaRepository.save(nueva);
                    });

            hecho.setCategoria(categoriaPersistida);
        }
        return hecho;
    }

    @Override
    public List<HechoOutputDTO> getHechos() {
        return repositorioDeHechos.findAll()
                .stream()
                .map(hechoMapper::toOutputDTO)
                .toList();
    }
    @Override
    public void editarHechoEstatica(Long id, HechoInputDTO hechoDto) {
        // 1️⃣ Buscar hecho existente
        Hecho hechoExistente = repositorioDeHechos.findById(id)
                .orElseThrow(() -> new RuntimeException("Hecho no encontrado con id: " + id));

        // 2️⃣ Actualizar campos solo si no son null
        if (hechoDto.getTitulo() != null) {
            hechoExistente.setTitulo(hechoDto.getTitulo());
        }
        if (hechoDto.getDescripcion() != null) {
            hechoExistente.setDescripcion(hechoDto.getDescripcion());
        }

        if (hechoDto.getFechaDelHecho() != null) {
            hechoExistente.setFechaDeAcontecimiento(hechoDto.getFechaDelHecho());
        }
        if (hechoDto.getLugar() != null) {
            Lugar lugarExistente = hechoExistente.getLugar();
            if (lugarExistente == null) {
                hechoExistente.setLugar(hechoDto.getLugar());
            } else {
                if (hechoDto.getLugar().getLatitud() != null) {
                    lugarExistente.setLatitud(hechoDto.getLugar().getLatitud());
                }
                if (hechoDto.getLugar().getLongitud() != null) {
                    lugarExistente.setLongitud(hechoDto.getLugar().getLongitud());
                }
            }
        }
        if (hechoDto.getNombreArchivo() != null) {
            hechoExistente.setNombreArchivo(hechoDto.getNombreArchivo());
        }

        // 3️⃣ Guardar cambios
        repositorioDeHechos.save(hechoExistente);
    }

}