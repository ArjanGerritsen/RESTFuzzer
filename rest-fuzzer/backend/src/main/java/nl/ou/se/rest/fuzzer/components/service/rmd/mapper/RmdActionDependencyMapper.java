package nl.ou.se.rest.fuzzer.components.service.rmd.mapper;

import java.util.List;
import java.util.stream.Collectors;

import nl.ou.se.rest.fuzzer.components.data.rmd.domain.RmdActionDependency;
import nl.ou.se.rest.fuzzer.components.service.rmd.domain.RmdActionDependencyDto;

public class RmdActionDependencyMapper {

    public static List<RmdActionDependencyDto> toDtos(List<RmdActionDependency> actionDependencies) {
        return actionDependencies.stream().map(a -> RmdActionDependencyMapper.toDto(a)).collect(Collectors.toList());
    }

    public static RmdActionDependencyDto toDto(RmdActionDependency actionDependency) {
        RmdActionDependencyDto dto = new RmdActionDependencyDto();
        dto.setId(actionDependency.getId());
        dto.setActionId(actionDependency.getAction().getId());
        dto.setDependsOnActionId(actionDependency.getActionDependsOn().getId());
        return dto;
    }
}