package evotingsystem.voteservice;

import evotingsystem.voteservice.entities.Vote;
import evotingsystem.voteservice.repositories.VoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Application principale du service de vote.
 * 
 * Ce service gère les votes des électeurs et communique avec voter-service
 * pour vérifier les informations des électeurs.
 * 
 * @author Équipe E-Voting System
 * @version 1.0.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@Slf4j
@RequiredArgsConstructor
public class VoteServiceApplication implements CommandLineRunner {

    private final VoteRepository voteRepository;

    /**
     * Point d'entrée principal de l'application.
     * 
     * @param args Arguments de la ligne de commande
     */
    public static void main(String[] args) {
        SpringApplication.run(VoteServiceApplication.class, args);
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
        log.info("Démarrage de l'insertion des données de test pour Vote Service...");

        // Vérifier si des données existent déjà
        if (voteRepository.count() > 0) {
            log.info("Des votes existent déjà dans la base de données. Aucune insertion de données de test.");
            return;
        }

        try {
            // Vote 1 : Électeur 1 vote pour le candidat 1
            Vote vote1 = new Vote();
            vote1.setElectorId(1L);
            vote1.setCandidateId(1L);
            voteRepository.save(vote1);
            log.info("Vote de test créé : Électeur ID {} → Candidat ID {}", vote1.getElectorId(), vote1.getCandidateId());

            // Vote 2 : Électeur 2 vote pour le candidat 1
            Vote vote2 = new Vote();
            vote2.setElectorId(2L);
            vote2.setCandidateId(1L);
            voteRepository.save(vote2);
            log.info("Vote de test créé : Électeur ID {} → Candidat ID {}", vote2.getElectorId(), vote2.getCandidateId());

            // Vote 3 : Électeur 3 vote pour le candidat 2
            Vote vote3 = new Vote();
            vote3.setElectorId(3L);
            vote3.setCandidateId(2L);
            voteRepository.save(vote3);
            log.info("Vote de test créé : Électeur ID {} → Candidat ID {}", vote3.getElectorId(), vote3.getCandidateId());

            // Vote 4 : Électeur 4 vote pour le candidat 2
            Vote vote4 = new Vote();
            vote4.setElectorId(4L);
            vote4.setCandidateId(2L);
            voteRepository.save(vote4);
            log.info("Vote de test créé : Électeur ID {} → Candidat ID {}", vote4.getElectorId(), vote4.getCandidateId());

            // Vote 5 : Électeur 5 vote pour le candidat 3
            Vote vote5 = new Vote();
            vote5.setElectorId(5L);
            vote5.setCandidateId(3L);
            voteRepository.save(vote5);
            log.info("Vote de test créé : Électeur ID {} → Candidat ID {}", vote5.getElectorId(), vote5.getCandidateId());

            // Vote 6 : Électeur 7 vote pour le candidat 1
            Vote vote6 = new Vote();
            vote6.setElectorId(7L);
            vote6.setCandidateId(1L);
            voteRepository.save(vote6);
            log.info("Vote de test créé : Électeur ID {} → Candidat ID {}", vote6.getElectorId(), vote6.getCandidateId());

            // Vote 7 : Électeur 8 vote pour le candidat 3
            Vote vote7 = new Vote();
            vote7.setElectorId(8L);
            vote7.setCandidateId(3L);
            voteRepository.save(vote7);
            log.info("Vote de test créé : Électeur ID {} → Candidat ID {}", vote7.getElectorId(), vote7.getCandidateId());

            log.info("Insertion des données de test terminée avec succès. {} votes créés.", voteRepository.count());

        } catch (Exception e) {
            log.error("Erreur lors de l'insertion des données de test : {}", e.getMessage(), e);
        }
    }
}