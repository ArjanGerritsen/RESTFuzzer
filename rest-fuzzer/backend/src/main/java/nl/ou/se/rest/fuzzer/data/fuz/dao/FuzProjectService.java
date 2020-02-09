package nl.ou.se.rest.fuzzer.data.fuz.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import nl.ou.se.rest.fuzzer.Constants;
import nl.ou.se.rest.fuzzer.data.fuz.domain.FuzProject;

public interface FuzProjectService extends CrudRepository<FuzProject, Long> {

    List<FuzProject> findAll();

    @EntityGraph(value = Constants.ENTITY_GRAPH_FUZ_PROJECTS_ALL_RELATIONS)
    Optional<FuzProject> findById(Long id);    

}