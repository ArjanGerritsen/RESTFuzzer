package nl.ou.se.rest.fuzzer.components.data.rmd.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import nl.ou.se.rest.fuzzer.components.data.rmd.domain.RmdAction;

public interface RmdActionService extends CrudRepository<RmdAction, Long> {

    @Query(value = "SELECT COUNT(a) FROM rmd_actions a WHERE a.sut.id = :sutId AND a.path LIKE CONCAT('%', :path, '%')")
    long countBySutIdAndPath(Long sutId, String path);

    @Query(value = "SELECT a FROM rmd_actions a WHERE a.sut.id = :sutId AND a.path LIKE CONCAT('%', :path, '%')")
    List<RmdAction> findBySutIdAndPath(Long sutId, String path, Pageable pageable);
}