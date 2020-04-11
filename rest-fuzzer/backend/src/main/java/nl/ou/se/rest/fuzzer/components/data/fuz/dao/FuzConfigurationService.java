package nl.ou.se.rest.fuzzer.components.data.fuz.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzConfiguration;

public interface FuzConfigurationService extends CrudRepository<FuzConfiguration, Long> {

	List<FuzConfiguration> findAll();

    Optional<FuzConfiguration> findById(Long id);

}