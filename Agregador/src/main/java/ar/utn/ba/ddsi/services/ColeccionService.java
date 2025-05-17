package ar.utn.ba.ddsi.services;

import ar.utn.ba.ddsi.models.entities.coleccion.Coleccion;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ColeccionService {
    private List<Coleccion> colecciones = new ArrayList<>();
    private List<Hecho> hechos = new ArrayList<>();

    public void addColeccion(Coleccion coleccion){
        colecciones.add(coleccion);
    }

    public void removeColeccion(Coleccion coleccion){
        colecciones.remove(coleccion);
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

    public void actualizarHechosColeccion(String handle, List<Hecho> nuevosHechos){
        //TODO CronJobs
    }
}
