package nl.ou.se.fuzz.rest.service.settings;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import nl.ou.se.fuzz.rest.service.settings.domain.SettingDto;
import nl.ou.se.fuzz.rest.service.settings.domain.SettingJpa;

public class SettingsMapper {

//	public static Set<SettingJpa> mapToDomain(Iterable<SettingDto> settingDtos) {
//		Set<SettingJpa> settingJpas = new TreeSet<>();
//		settingDtos.forEach(s -> settingJpas.add(cop(s)));
//		return settingJpas;
//	}

	public static SettingJpa copyPropsToDomain(SettingJpa jpa, SettingDto dto) {
		Date now = new Date();

		jpa.setCreatedAt(dto.getCreatedAt() == null ? now : new Date(dto.getCreatedAt()));
		jpa.setDescription(dto.getDescription());
		jpa.setId(dto.getId());
		jpa.setKey(dto.getKey());
		jpa.setScope(dto.getScope());
		jpa.setType(dto.getType());
		jpa.setUpdatedAt(dto.getUpdatedAt() == null ? now : new Date(dto.getUpdatedAt()));
		jpa.setValue(dto.getValue());
		return jpa;
	}

	public static Set<SettingDto> mapToDtos(Iterable<SettingJpa> settingJpas) {
		Set<SettingDto> settingDtos = new TreeSet<>();
		settingJpas.forEach(s -> settingDtos.add(mapToDto(s)));
		return settingDtos;
	}
	
	public static SettingDto mapToDto(SettingJpa jpa) {
		SettingDto s = new SettingDto();		
		s.setCreatedAt(jpa.getCreatedAt().getTime());
		s.setDescription(jpa.getDescription());
		s.setId(jpa.getId());
		s.setKey(jpa.getKey());
		s.setScope(jpa.getScope());
		s.setType(jpa.getType());
		s.setUpdatedAt(jpa.getUpdatedAt().getTime());
		s.setValue(jpa.getValue());
		return s;
	}	
}