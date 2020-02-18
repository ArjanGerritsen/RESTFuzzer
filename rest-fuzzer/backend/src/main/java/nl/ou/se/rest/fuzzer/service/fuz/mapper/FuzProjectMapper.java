package nl.ou.se.rest.fuzzer.service.fuz.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import nl.ou.se.rest.fuzzer.data.fuz.domain.FuzProject;
import nl.ou.se.rest.fuzzer.service.fuz.domain.FuzProjectDto;
import nl.ou.se.rest.fuzzer.service.rmd.mapper.RmdSutMapper;

public class FuzProjectMapper {

	// methods
	public static List<FuzProjectDto> toDtos(List<FuzProject> tasks) {
		return tasks.stream().map(ft -> FuzProjectMapper.toDto(ft, true)).collect(Collectors.toList());
	}

	public static FuzProjectDto toDto(FuzProject project, boolean mapRelations) {
		FuzProjectDto dto = new FuzProjectDto();
		BeanUtils.copyProperties(project, dto);
		if (mapRelations) {
			dto.setSut(RmdSutMapper.toDto(project.getSut(), false));
		}
		return dto;
	}

	public static FuzProject toDomain(FuzProjectDto dto) {
		FuzProject project = new FuzProject();
		BeanUtils.copyProperties(dto, project);
		project.setType(dto.getType());
		return project;
	}
}
