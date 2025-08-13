package ar.utn.ba.ddsi.services.mappers;

import ar.utn.ba.ddsi.models.dtos.output.ColeccionOutputDTO;
import ar.utn.ba.ddsi.models.dtos.output.HechoOutputDTO;
import ar.utn.ba.ddsi.models.entities.coleccion.Coleccion;
import ar.utn.ba.ddsi.models.entities.hecho.Categoria;
import ar.utn.ba.ddsi.models.entities.hecho.Etiqueta;
import ar.utn.ba.ddsi.models.entities.hecho.Lugar;
import ar.utn.ba.ddsi.models.entities.hecho.Multimedia;
import ar.utn.ba.ddsi.models.entities.solicitud.Solicitud;
import ar.utn.ba.ddsi.models.entities.usuario.Usuario;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ColeccionMapper {
    private final HechoMapper hechoMapper;

    public ColeccionMapper(HechoMapper hechoMapper) {
        this.hechoMapper = hechoMapper;
    }

    public ColeccionOutputDTO toDTO(Coleccion coleccion) {
        ColeccionOutputDTO dto = new ColeccionOutputDTO();
        dto.setTitulo(coleccion.getTitulo());
        dto.setDescripcion(coleccion.getDescripcion());

        //List<HechoOutputDTO> hechos = coleccion.getHechos().stream()
          //      .map(hechoMapper::toDTO)
          //      .collect(Collectors.toList());

        HechoOutputDTO hecho1 = new HechoOutputDTO();
        hecho1.setTitulo("Inauguración del parque central");
        hecho1.setDescripcion("Se inauguró el nuevo parque central con actividades para toda la familia.");
        hecho1.setCategoria(new Categoria("Eventos públicos"));
        hecho1.setFechaDeAcontecimiento(LocalDate.of(2025, 5, 10));
        hecho1.setLugar(new Lugar(-34.6037, -58.3816));
        hecho1.setSolicitudesDeEliminacion(List.of());
        hecho1.setEtiquetas(List.of(new Etiqueta("inauguración"), new Etiqueta("parque")));
        hecho1.setContribuyente(new Usuario("Juan Pérez"));


        HechoOutputDTO hecho2 = new HechoOutputDTO();
        hecho2.setTitulo("Corte de luz en el barrio norte");
        hecho2.setDescripcion("Un apagón afectó a más de 500 viviendas durante tres horas.");
        hecho2.setCategoria(new Categoria("Servicios"));
        hecho2.setFechaDeAcontecimiento(LocalDate.of(2025, 8, 2));
        hecho2.setLugar(new Lugar(-34.5875, -58.3925));
        hecho2.setEtiquetas(List.of(new Etiqueta("corte"), new Etiqueta("energía")));
        hecho2.setContribuyente(new Usuario("María Gómez"));

        HechoOutputDTO hecho3 = new HechoOutputDTO();
        hecho3.setTitulo("Festival gastronómico local");
        hecho3.setDescripcion("El festival reunió a más de 20 food trucks con comidas típicas.");
        hecho3.setCategoria(new Categoria("Gastronomía"));
        hecho3.setFechaDeAcontecimiento(LocalDate.of(2025, 6, 18));
        hecho3.setLugar(new Lugar(-34.6011, -58.3750));
        hecho3.setSolicitudesDeEliminacion(List.of());
        hecho3.setEtiquetas(List.of(new Etiqueta("festival"), new Etiqueta("comida")));
        hecho3.setContribuyente(new Usuario("Carlos López"));

        List<HechoOutputDTO> hechos = new ArrayList<HechoOutputDTO>();
        hechos.add(hecho1);
        hechos.add(hecho2);
        hechos.add(hecho3);

        dto.setHechosDeLaColeccion(hechos);
        return dto;
    }
}
