package nl.ou.se.fuzz.rest.service.settings;

import org.springframework.data.repository.CrudRepository;

import nl.ou.se.fuzz.rest.service.settings.domain.SettingJpa;

public interface SettingsService extends CrudRepository<SettingJpa, Long>  {

}