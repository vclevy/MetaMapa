package ar.utn.ba.ddsi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
		exclude = {
				org.springframework.boot.autoconfigure.couchbase.CouchbaseAutoConfiguration.class,
				org.springframework.boot.autoconfigure.data.couchbase.CouchbaseDataAutoConfiguration.class
		}
)
public class AgregadorApplication {
	public static void main(String[] args) {
		SpringApplication.run(AgregadorApplication.class, args);
	}
}
