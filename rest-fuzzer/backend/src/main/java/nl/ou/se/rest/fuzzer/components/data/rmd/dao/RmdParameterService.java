package nl.ou.se.rest.fuzzer.components.data.rmd.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import nl.ou.se.rest.fuzzer.components.data.rmd.domain.RmdParameter;

public interface RmdParameterService  extends CrudRepository<RmdParameter, Long> {

    @Query(value = "SELECT p FROM rmd_parameters p WHERE p.action.id = :actionId ORDER BY p.position ASC")
    List<RmdParameter> findByActionId(Long actionId);

}