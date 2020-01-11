package nl.ou.se.fuzz.rest.service.settings;

import java.util.List;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nl.ou.se.fuzz.rest.service.settings.domain.SettingDto;
import nl.ou.se.fuzz.rest.service.settings.domain.SettingJpa;
import nl.ou.se.fuzz.rest.service.shared.JpaUtil;
import nl.ou.se.fuzz.rest.service.shared.ResponseDto;

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
		return addOrUpdate(null, settingDto);
	}

	@RequestMapping(path = "{id}", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody SettingDto settingDto) {
		return addOrUpdate(id, settingDto);
	}

	@RequestMapping(path = "{id}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
		if (id == null || id < 0) {
			return new ResponseEntity<>(new ResponseDto(null), HttpStatus.BAD_REQUEST);			
		}

		SettingJpa settingJpa = this.settingsService.findById(id).get();
		this.settingsService.deleteById(id);
		return ResponseEntity.ok(SettingsMapper.mapToDto(settingJpa));
	}

	private @ResponseBody ResponseEntity<?> addOrUpdate(Long id, SettingDto settingDto) {
		SettingJpa settingJpa = new SettingJpa();
		if (id != null && id > 0) {
			settingJpa = this.settingsService.findById(id).get();
		}
		SettingsMapper.copyPropsToDomain(settingJpa, settingDto);
		List<String> violations = JpaUtil.getViolations(settingJpa); 

		if (violations.isEmpty()) {
			this.settingsService.save(settingJpa);
			return ResponseEntity.ok(settingDto);			
		} else {
			String json = "";
			try {
				json = new ObjectMapper().writeValueAsString(new ResponseDto(violations));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				// TODO
			}
			return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
		}		
	}	
}
