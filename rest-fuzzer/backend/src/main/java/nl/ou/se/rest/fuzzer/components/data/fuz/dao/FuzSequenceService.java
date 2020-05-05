package nl.ou.se.rest.fuzzer.components.data.fuz.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzSequence;

public interface FuzSequenceService extends CrudRepository<FuzSequence, Long> {

    @Query(value = "SELECT COUNT(s) FROM fuz_sequences s WHERE s.project.id = :projectId")
    Long countByProjectId(Long projectId);

    @Query(value = "SELECT s FROM fuz_sequences s WHERE s.project.id = :projectId")
    List<FuzSequence> findByProjectId(Long projectId, Pageable pageable);

    @Modifying
    @Query(value = "DELETE FROM fuz_sequences s WHERE s.project.id = :projectId")
    void deleteByProjectId(Long projectId);

}