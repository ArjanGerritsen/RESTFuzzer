package nl.ou.se.rest.fuzzer.components.data.fuz.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzResponse;

public interface FuzResponseService extends CrudRepository<FuzResponse, Long> {

    @Query(value = "SELECT COUNT(r) FROM fuz_responses r WHERE r.project.id = :projectId AND r.request.path LIKE :path")
    long countByProjectIdAndPath(Long projectId, String path);

    @Query(value = "SELECT r FROM fuz_responses r WHERE r.project.id = :projectId AND r.request.path LIKE :path")
    List<FuzResponse> findByProjectIdAndPath(Long projectId, String path, Pageable pageable);
}