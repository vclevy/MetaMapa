package ar.utn.ba.ddsi.services.impl;

import ar.utn.ba.ddsi.models.repositories.ISolicitudesRepository;
import ar.utn.ba.ddsi.services.ISolcitudesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SolicitudesService implements ISolcitudesService {
    @Autowired
    private ISolicitudesRepository solicitudesRepository;
}
