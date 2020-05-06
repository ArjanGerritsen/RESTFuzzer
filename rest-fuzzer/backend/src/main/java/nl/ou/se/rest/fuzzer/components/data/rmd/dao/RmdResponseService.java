package nl.ou.se.rest.fuzzer.components.data.rmd.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import nl.ou.se.rest.fuzzer.components.data.rmd.domain.RmdResponse;

public interface RmdResponseService extends CrudRepository<RmdResponse, Long> {

    @Modifying
    @Query(value = "DELETE FROM rmd_responses r WHERE r.id IN (SELECT r.id FROM rmd_responses r LEFT JOIN r.action a LEFT JOIN a.sut s WHERE s.id = :sutId)")
    Integer deleteBySutId(Long sutId);

}