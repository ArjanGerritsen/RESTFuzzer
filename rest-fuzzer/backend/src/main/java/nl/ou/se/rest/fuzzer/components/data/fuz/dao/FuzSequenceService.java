package nl.ou.se.rest.fuzzer.components.data.fuz.dao;

import org.springframework.data.repository.CrudRepository;

import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzSequence;

public interface FuzSequenceService extends CrudRepository<FuzSequence, Long> {

}