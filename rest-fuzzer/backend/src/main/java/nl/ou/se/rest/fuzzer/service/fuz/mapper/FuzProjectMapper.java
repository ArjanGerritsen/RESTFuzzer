package nl.ou.se.rest.fuzzer.service.fuz.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import nl.ou.se.rest.fuzzer.data.fuz.domain.FuzProject;
import nl.ou.se.rest.fuzzer.service.fuz.domain.FuzProjectDto;

public class FuzProjectMapper {

    // methods
    public static List<FuzProjectDto> toDtos(List<FuzProject> tasks) {
        return tasks.stream().map(ft -> FuzProjectMapper.toDto(ft, false)).collect(Collectors.toList());
    }

    public static FuzProjectDto toDto(FuzProject fuzTask) {
        return toDto(fuzTask, true);
    }

    public static FuzProject toDomain(FuzProjectDto dto) {
        FuzProject task = new FuzProject();
        BeanUtils.copyProperties(dto, task);
        task.setType(dto.getType());
        return task;
    }

    private static FuzProjectDto toDto(FuzProject task, boolean mapRelations) {
        FuzProjectDto dto = new FuzProjectDto();
        BeanUtils.copyProperties(task, dto);
        if (mapRelations) {
            // TODO
        }
        return dto;
    }
}
