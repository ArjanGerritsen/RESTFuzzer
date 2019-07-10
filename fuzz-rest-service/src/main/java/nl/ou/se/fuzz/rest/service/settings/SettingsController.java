package nl.ou.se.fuzz.rest.service.settings;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import nl.ou.se.fuzz.rest.service.settings.domain.SettingDto;
import nl.ou.se.fuzz.rest.service.settings.domain.SettingJpa;
import nl.ou.se.fuzz.rest.service.shared.JpaUtil;

@RestController()
@RequestMapping("/rest/admin/settings")
public class SettingsController {

	@Autowired
	private SettingsService settingsService;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Set<SettingDto> list() {
		return SettingsMapper.mapToDtos(this.settingsService.findAll());
	}

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> add(@RequestBody SettingDto settingDto) {
		SettingJpa settingJpa = SettingsMapper.mapToDomain(settingDto);
		String violationString = JpaUtil.getViolations(settingJpa); 

		if (violationString.isBlank()) {
			this.settingsService.save(settingJpa);
			return ResponseEntity.ok(settingDto);			
		} else {
			return new ResponseEntity<>(violationString, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(path = "{id}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
		SettingJpa settingJpa = this.settingsService.findById(id).get();
		this.settingsService.deleteById(id);
		return ResponseEntity.ok(SettingsMapper.mapToDto(settingJpa));
	}
}