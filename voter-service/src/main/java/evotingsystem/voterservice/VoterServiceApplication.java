package evotingsystem.voterservice;

import evotingsystem.voterservice.entities.Elector;
import evotingsystem.voterservice.repositories.ElectorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Application principale du service de gestion des électeurs (Voter Service).
 * 
 * Ce service est responsable de la gestion des informations des électeurs dans le système de vote électronique.
 * Il fournit des fonctionnalités pour créer, récupérer et lister les électeurs.
 * 
 * @author Équipe E-Voting System
 * @version 1.0.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
@RequiredArgsConstructor
public class VoterServiceApplication implements CommandLineRunner {

    private final ElectorRepository electorRepository;

    /**
     * Point d'entrée principal de l'application.
     * 
     * @param args Arguments de la ligne de commande
     */
    public static void main(String[] args) {
        SpringApplication.run(VoterServiceApplication.class, args);
    }

    /**
     * Méthode exécutée après le démarrage de l'application.
     * Insère des données de test par défaut pour faciliter les tests.
     * 
     * @param args Arguments de la ligne de commande
     * @throws Exception En cas d'erreur lors de l'insertion des données
     */
    @Override
    public void run(String... args) throws Exception {
        log.info("Démarrage de l'insertion des données de test...");

        // Vérifier si des données existent déjà
        if (electorRepository.count() > 0) {
            log.info("Des électeurs existent déjà dans la base de données. Aucune insertion de données de test.");
            return;
        }

        try {
            // Création d'un format de date pour parser les dates
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            // Électeur 1 : Jean Dupont
            Elector elector1 = new Elector(
                    "Dupont",
                    "Jean",
                    dateFormat.parse("1990-05-15"),
                    "ID001234"
            );
            electorRepository.save(elector1);
            log.info("Électeur de test créé : {} {} (ID: {})", 
                    elector1.getPrenom(), elector1.getNom(), elector1.getIdElector());

            // Électeur 2 : Marie Martin
            Elector elector2 = new Elector(
                    "Martin",
                    "Marie",
                    dateFormat.parse("1985-08-22"),
                    "ID002345"
            );
            electorRepository.save(elector2);
            log.info("Électeur de test créé : {} {} (ID: {})", 
                    elector2.getPrenom(), elector2.getNom(), elector2.getIdElector());

            // Électeur 3 : Pierre Bernard
            Elector elector3 = new Elector(
                    "Bernard",
                    "Pierre",
                    dateFormat.parse("1992-03-10"),
                    "ID003456"
            );
            electorRepository.save(elector3);
            log.info("Électeur de test créé : {} {} (ID: {})", 
                    elector3.getPrenom(), elector3.getNom(), elector3.getIdElector());

            // Électeur 4 : Sophie Dubois
            Elector elector4 = new Elector(
                    "Dubois",
                    "Sophie",
                    dateFormat.parse("1988-11-30"),
                    "ID004567"
            );
            electorRepository.save(elector4);
            log.info("Électeur de test créé : {} {} (ID: {})", 
                    elector4.getPrenom(), elector4.getNom(), elector4.getIdElector());

            // Électeur 5 : Thomas Moreau
            Elector elector5 = new Elector(
                    "Moreau",
                    "Thomas",
                    dateFormat.parse("1995-07-18"),
                    "ID005678"
            );
            electorRepository.save(elector5);
            log.info("Électeur de test créé : {} {} (ID: {})", 
                    elector5.getPrenom(), elector5.getNom(), elector5.getIdElector());

            // Électeur 6 : Julie Petit (a déjà voté)
            Elector elector6 = new Elector(
                    "Petit",
                    "Julie",
                    dateFormat.parse("1991-01-25"),
                    "ID006789"
            );
            elector6.setAVote(true);
            electorRepository.save(elector6);
            log.info("Électeur de test créé : {} {} (ID: {}) - A déjà voté", 
                    elector6.getPrenom(), elector6.getNom(), elector6.getIdElector());

            // Électeur 7 : Lucas Roux
            Elector elector7 = new Elector(
                    "Roux",
                    "Lucas",
                    dateFormat.parse("1987-09-12"),
                    "ID007890"
            );
            electorRepository.save(elector7);
            log.info("Électeur de test créé : {} {} (ID: {})", 
                    elector7.getPrenom(), elector7.getNom(), elector7.getIdElector());

            // Électeur 8 : Emma Laurent
            Elector elector8 = new Elector(
                    "Laurent",
                    "Emma",
                    dateFormat.parse("1993-04-05"),
                    "ID008901"
            );
            electorRepository.save(elector8);
            log.info("Électeur de test créé : {} {} (ID: {})", 
                    elector8.getPrenom(), elector8.getNom(), elector8.getIdElector());

            log.info("Insertion des données de test terminée avec succès. {} électeurs créés.", 
                    electorRepository.count());

        } catch (Exception e) {
            log.error("Erreur lors de l'insertion des données de test : {}", e.getMessage(), e);
        }
    }
}

