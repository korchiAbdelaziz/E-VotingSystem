package evotingsystem.resultservice.repositories;

import evotingsystem.resultservice.entities.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {
    Optional<Result> findByCandidateId(Long candidateId);
}