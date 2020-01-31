package nl.ou.se.rest.fuzzer.service.sut.mapper;

import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import nl.ou.se.rest.fuzzer.data.rmd.domain.Action;
import nl.ou.se.rest.fuzzer.service.sut.domain.ActionDto;

public class ActionMapper {

    public static List<ActionDto> toDtos(SortedSet<Action> actions) {
        return actions.stream().map(a -> ActionMapper.toDto(a)).collect(Collectors.toList());
    }

    public static ActionDto toDto(Action action) {
        ActionDto dto = new ActionDto();
        BeanUtils.copyProperties(action, dto);
        dto.setParameters(ParameterMapper.toDtos(action.getParameters()));
        dto.setResponses(ResponseMapper.toDtos(action.getResponses()));
        return dto;
    }
}
