package nl.ou.se.rest.fuzzer.components.data.fuz.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzResponse;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.HttpMethod;

public interface FuzResponseService extends CrudRepository<FuzResponse, Long> {

    @Query(value = "SELECT COUNT(r) FROM fuz_responses r LEFT JOIN r.request req WHERE r.project.id = :projectId AND req.httpMethod IN (:httpMethods) AND r.statusCode IN (:statusCodes) AND r.request.path LIKE :path")
    Long countByFilter(Long projectId, List<HttpMethod> httpMethods, List<Integer> statusCodes, String path);

    @Query(value = "SELECT r FROM fuz_responses r LEFT JOIN FETCH r.request req WHERE r.project.id = :projectId AND req.httpMethod IN (:httpMethods) AND r.statusCode IN (:statusCodes) AND r.request.path LIKE :path")
    List<FuzResponse> findByFilter(Long projectId, List<HttpMethod> httpMethods, List<Integer> statusCodes, String path, Pageable pageable);

    @Query(value = "SELECT DISTINCT(r.statusCode) FROM fuz_responses r LEFT JOIN r.request req WHERE r.project.id = :projectId")
    List<Integer> findUniqueStatusCodesForProject(Long projectId);

    @Modifying
    @Query(value = "DELETE FROM fuz_responses r WHERE r.project.id = :projectId")
    void deleteByProjectId(Long projectId);

}