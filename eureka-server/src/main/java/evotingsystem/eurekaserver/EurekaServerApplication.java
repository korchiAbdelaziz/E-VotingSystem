package evotingsystem.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Application principale du serveur Eureka.
 * 
 * Eureka Server est le service de découverte qui permet aux microservices
 * de s'enregistrer et de découvrir les autres services disponibles.
 * 
 * @author Équipe E-Voting System
 * @version 1.0.0
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

    /**
     * Point d'entrée principal de l'application Eureka Server.
     * 
     * @param args Arguments de la ligne de commande
     */
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}

