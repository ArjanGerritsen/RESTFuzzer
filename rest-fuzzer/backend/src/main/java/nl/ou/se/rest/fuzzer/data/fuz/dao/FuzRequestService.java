package nl.ou.se.rest.fuzzer.data.fuz.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import nl.ou.se.rest.fuzzer.data.fuz.domain.FuzRequest;

public interface FuzRequestService extends CrudRepository<FuzRequest, Long> {

	long countByProjectId(Long id);

    List<FuzRequest> findByProjectId(Long id);    

}