package nl.ou.se.fuzz.rest.service.admin.settings;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import nl.ou.se.fuzz.rest.service.admin.settings.domain.SettingDto;

@RestController()
@RequestMapping("/rest/admin/settings")
public class SettingsController {

	private SortedSet<SettingDto> settings = new TreeSet<>();

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Set<SettingDto> list() {
		return this.settings;
	}

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody SettingDto add(@RequestBody SettingDto settingDto) {
		this.settings.add(settingDto);
		return settingDto;
	}
}