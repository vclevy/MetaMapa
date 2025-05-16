package services.impl;

import models.entities.Hecho;
import models.repositories.impl.ProxyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProxyServices {

        @Autowired
        private ProxyRepository fuenteProxyRepository;

        public List<Hecho> obtenerTodosLosHechos(Map<String, String> filtros) {
            return fuenteProxyRepository.obtenerHechos(filtros);
        }

        public List<Hecho> obtenerHechosDeColeccion(String identificador, Map<String, String> filtros) {
            return fuenteProxyRepository.obtenerHechosDeColeccion(identificador, filtros);
        }
}
