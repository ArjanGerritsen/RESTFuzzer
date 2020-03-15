package nl.ou.se.rest.fuzzer.components.data.fuz.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzDictionary;

public interface FuzDictionaryService extends CrudRepository<FuzDictionary, Long> {

	List<FuzDictionary> findAll();

    Optional<FuzDictionary> findById(Long id);

}