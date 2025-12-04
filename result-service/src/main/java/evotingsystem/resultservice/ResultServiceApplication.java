package evotingsystem.resultservice;

import evotingsystem.resultservice.entities.Result;
import evotingsystem.resultservice.repositories.ResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Application principale du service de résultats.
 * 
 * Ce service calcule et publie les résultats des votes en communiquant
 * avec vote-service pour récupérer les votes.
 * 
 * @author Équipe E-Voting System
 * @version 1.0.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@Slf4j
@RequiredArgsConstructor
public class ResultServiceApplication implements CommandLineRunner {

    private final ResultRepository resultRepository;

    /**
     * Point d'entrée principal de l'application.
     * 
     * @param args Arguments de la ligne de commande
     */
    public static void main(String[] args) {
        SpringApplication.run(ResultServiceApplication.class, args);
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
        log.info("Démarrage de l'insertion des données de test pour Result Service...");

        // Vérifier si des données existent déjà
        if (resultRepository.count() > 0) {
            log.info("Des résultats existent déjà dans la base de données. Aucune insertion de données de test.");
            return;
        }

        try {
            // Résultat 1 : Candidat 1 avec 3 votes (sera recalculé depuis vote-service)
            Result result1 = new Result();
            result1.setCandidateId(1L);
            result1.setTotalVotes(3L);
            resultRepository.save(result1);
            log.info("Résultat de test créé : Candidat ID {} - {} votes", result1.getCandidateId(), result1.getTotalVotes());

            // Résultat 2 : Candidat 2 avec 2 votes
            Result result2 = new Result();
            result2.setCandidateId(2L);
            result2.setTotalVotes(2L);
            resultRepository.save(result2);
            log.info("Résultat de test créé : Candidat ID {} - {} votes", result2.getCandidateId(), result2.getTotalVotes());

            // Résultat 3 : Candidat 3 avec 2 votes
            Result result3 = new Result();
            result3.setCandidateId(3L);
            result3.setTotalVotes(2L);
            resultRepository.save(result3);
            log.info("Résultat de test créé : Candidat ID {} - {} votes", result3.getCandidateId(), result3.getTotalVotes());

            log.info("Insertion des données de test terminée avec succès. {} résultats créés.", resultRepository.count());

        } catch (Exception e) {
            log.error("Erreur lors de l'insertion des données de test : {}", e.getMessage(), e);
        }
    }
}