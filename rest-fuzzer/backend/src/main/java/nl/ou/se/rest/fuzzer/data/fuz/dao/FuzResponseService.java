package nl.ou.se.rest.fuzzer.data.fuz.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import nl.ou.se.rest.fuzzer.data.fuz.domain.FuzResponse;

public interface FuzResponseService extends CrudRepository<FuzResponse, Long> {

	long countByProjectId(Long id);

    List<FuzResponse> findByProjectId(Long id);    

}