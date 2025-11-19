package ar.utn.ba.ddsi.gateway.schedulers;

import ar.utn.ba.ddsi.gateway.services.coleccionService.IColeccionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ColeccionesScheduler {

    private static final Logger log = LoggerFactory.getLogger(ColeccionesScheduler.class);

    private final IColeccionService coleccionService;

    public ColeccionesScheduler(IColeccionService coleccionService) {
        this.coleccionService = coleccionService;
    }

    @Scheduled(fixedRate = 10000)
    public void refrescar() {
        log.info("⏳ [Scheduler] Iniciando refresco de colecciones…");

        try {
            coleccionService.refrescarColecciones();
            log.info("✅ [Scheduler] Refresco de colecciones finalizado correctamente.");
        } catch (Exception e) {
            log.error("❌ [Scheduler] Error al refrescar colecciones", e);
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void aplicarAlgoritmos() {
        log.info("🧠 [Scheduler] Aplicando algoritmos de consenso a todas las colecciones…");

        try {
            coleccionService.aplicarAlgoritmosAColecciones();
            log.info("✅ [Scheduler] Algoritmos aplicados correctamente.");
        } catch (Exception e) {
            log.error("❌ [Scheduler] Error al aplicar algoritmos", e);
        }
    }
}
