package nl.ou.se.rest.fuzzer.components.data.fuz.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzRequest;

public interface FuzRequestService extends CrudRepository<FuzRequest, Long> {

    @Query(value = "SELECT COUNT(r) FROM fuz_requests r WHERE r.project.id = :projectId AND r.path LIKE CONCAT('%', :path, '%')")
	long countByProjectIdAndPath(Long projectId, String path);

    @Query(value = "SELECT r FROM fuz_requests r WHERE r.project.id = :projectId AND r.path LIKE CONCAT('%', :path, '%')")
    List<FuzRequest> findByProjectIdAndPath(Long projectId, String path, Pageable pageable);
}