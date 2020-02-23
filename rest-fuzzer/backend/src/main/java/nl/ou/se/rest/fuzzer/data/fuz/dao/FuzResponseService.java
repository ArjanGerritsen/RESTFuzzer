package nl.ou.se.rest.fuzzer.data.fuz.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import nl.ou.se.rest.fuzzer.data.fuz.domain.FuzResponse;

public interface FuzResponseService extends CrudRepository<FuzResponse, Long> {

    @Query(value = "SELECT COUNT(r) FROM fuz_responses r WHERE r.project.id = :project_id AND r.request.path LIKE :path")
    long countByProjectIdAndByPath(Long project_id, String path);

    @Query(value = "SELECT r FROM fuz_responses r WHERE r.project.id = :project_id AND r.request.path LIKE :path")
    List<FuzResponse> findByProjectIdAndPath(Long project_id, String path, Pageable pageable);
}