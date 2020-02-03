package nl.ou.se.rest.fuzzer.data.rmd.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import nl.ou.se.rest.fuzzer.data.rmd.domain.Sut;

public interface SutService extends CrudRepository<Sut, Long> {

    List<Sut> findAll();
    
    @EntityGraph(value = "suts.all_relations")
    Optional<Sut> findById(Long id);    
}