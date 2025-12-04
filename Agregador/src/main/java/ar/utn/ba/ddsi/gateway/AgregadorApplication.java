package ar.utn.ba.ddsi.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(
		exclude = {
				org.springframework.boot.autoconfigure.couchbase.CouchbaseAutoConfiguration.class,
				org.springframework.boot.autoconfigure.data.couchbase.CouchbaseDataAutoConfiguration.class
		}
)
@EnableScheduling
public class AgregadorApplication {
	public static void main(String[] args) {
		SpringApplication.run(AgregadorApplication.class, args);
	}
}
