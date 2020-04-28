package nl.ou.se.rest.fuzzer.components.service.rmd.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import nl.ou.se.rest.fuzzer.components.data.rmd.domain.RmdAction;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.RmdActionDependency;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.RmdParameter;
import nl.ou.se.rest.fuzzer.components.service.rmd.domain.RmdActionDependencyDto;

public class RmdActionDependencyMapper {

    public static List<RmdActionDependencyDto> toDtos(List<RmdActionDependency> actionDependencies) {
        return actionDependencies.stream().map(a -> RmdActionDependencyMapper.toDto(a)).collect(Collectors.toList());
    }

    public static RmdActionDependencyDto toDto(RmdActionDependency actionDependency) {
        RmdActionDependencyDto dto = new RmdActionDependencyDto();
        BeanUtils.copyProperties(actionDependency, dto);
        dto.setParameter(RmdParameterMapper.toDto(actionDependency.getParameter()));
        dto.setAction(RmdActionMapper.toDto(actionDependency.getAction()));
        dto.setActionDependsOn(RmdActionMapper.toDto(actionDependency.getActionDependsOn()));
        return dto;
    }

    public static RmdActionDependency toDomain(RmdActionDependencyDto dto, RmdParameter parameter, RmdAction action,
            RmdAction actionDependsOn) {
        RmdActionDependency actionDependency = new RmdActionDependency();
        BeanUtils.copyProperties(dto, actionDependency);
        actionDependency.setParameter(parameter);
        actionDependency.setAction(action);
        actionDependency.setActionDependsOn(actionDependsOn);
        return actionDependency;
    }
}