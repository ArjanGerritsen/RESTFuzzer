package nl.ou.se.rest.fuzzer.data.rmd.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import nl.ou.se.rest.fuzzer.Constants;
import nl.ou.se.rest.fuzzer.data.rmd.domain.RmdSut;

public interface RmdSutService extends CrudRepository<RmdSut, Long> {

    List<RmdSut> findAll();
    
    @EntityGraph(value = Constants.ENTITY_GRAPH_RMD_SUTS_ALL_RELATIONS)
    Optional<RmdSut> findById(Long id);
}