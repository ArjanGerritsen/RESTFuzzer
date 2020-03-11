package nl.ou.se.rest.fuzzer.components.data.fuz.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzProject;

public interface FuzProjectService extends CrudRepository<FuzProject, Long> {

    List<FuzProject> findAll();

//    @Query("SELECT f FROM fuz_projects f "
//            + "JOIN fetch f.sut s "
//            + "JOIN FETCH s.actions a "
//            + "JOIN FETCH a.parameters "
//            + "JOIN FETCH f.requests q " // TODO Anders? Is nu zo gedaan voor Generator + Executor
//            + "WHERE f.id = :id ")
//    Optional<FuzProject> findByIdWithRelations(Long id);

    Optional<FuzProject> findById(Long id);

}