package clustering.components;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * Classe principale dell'applicazione Spring Boot per il server REST.
 */
@SpringBootApplication
public class RestServerApplication {
    /**
     * Metodo main che rappresenta il punto di ingresso dell'applicazione REST.
     *
     * @param args eventuali argomenti da linea di comando.
     */
    public static void main(String[] args) {
        SpringApplication.run(RestServerApplication.class, args);
    }
}
