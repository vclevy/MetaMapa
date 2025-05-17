package ar.utn.ba.ddsi.services.impl;

import ar.utn.ba.ddsi.models.dtos.input.ColeccionInputDTO;
import ar.utn.ba.ddsi.models.dtos.output.ColeccionOutputDTO;
import ar.utn.ba.ddsi.models.entities.coleccion.Coleccion;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.repositories.IColeccionesRepository;
import ar.utn.ba.ddsi.models.repositories.IHechosRepository;
import ar.utn.ba.ddsi.services.IColeccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ColeccionService implements IColeccionService {
    @Autowired
    private IColeccionesRepository coleccionesRepository;

    @Autowired
    private IHechosRepository hechosRepository;

    public void crearColeccion(Coleccion unaColeccion) {
        coleccionesRepository.save(unaColeccion);
    }

    public void eliminar(String unHandle) {
        var coleccion = this.coleccionesRepository.findByHandle(unHandle);
        if (coleccion != null) {
            this.coleccionesRepository.delete(coleccion);
        }
    }

    public ColeccionOutputDTO buscarPorHandle(String unHandle) {
        var coleccion = this.coleccionesRepository.findByHandle(unHandle);
        if (coleccion != null) {
            return null;
        }
        return this.coleccionOutputDTO(coleccion);
    }

    public void crear(ColeccionInputDTO unaColeccionInputDTO) {
        var coleccion = new Coleccion(
                unaColeccionInputDTO.getTitulo(),
                unaColeccionInputDTO.getDescripcion(),
                unaColeccionInputDTO.getHandle()
        );

        this.coleccionesRepository.save(coleccion);
    }

    @Override
    public void actualizarHechosPertenecientes() {
        // TODO: CRON JOBS CADA UNA HORA Y ADEMAS HACER LA CONEXION ENTRE MODULOS PARA OBTENER LOS HECHOS DE DISTINTAS FUENTES
    }



    private ColeccionOutputDTO coleccionOutputDTO(Coleccion unaColeccion) {
        ColeccionOutputDTO coleccionOutputDTO = new ColeccionOutputDTO();
        coleccionOutputDTO.setTitulo(unaColeccion.getTitulo());
        coleccionOutputDTO.setDescripcion(unaColeccion.getDescripcion());
        coleccionOutputDTO.setHandle(unaColeccion.getHandle());
        // TODO: VER QUE LOS HECHOS TAMBIEN SEAN OUTPUTS: coleccionOutputDTO.setHechosOutputDtos(new ArrayList<>());
        return coleccionOutputDTO;
    }

    /*
    public void addColeccion(Coleccion coleccion){
        colecciones.add(coleccion);
    }

    public List<Coleccion> getColecciones(){
        return colecciones;
    }

    //Optional -> Puede traer un valor o traer NULL
    public Optional<Coleccion> findByHandle(String handleAEncontrar){
        return colecciones.stream().filter(c -> c.getHandle().equals(handleAEncontrar)).findFirst();
    }

    public List<Hecho> getHechosColeccion(String handle){
        return hechos.stream()
                .filter(hecho ->hecho.getHandlesColecciones().contains(handle))
                .toList();
    }
     */
}
