package ar.utn.ba.ddsi.gateway.ServicioEstadisticas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ServicioEstadisticasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicioEstadisticasApplication.class, args);
	}

}
