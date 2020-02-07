package nl.ou.se.rest.fuzzer.service.rmd.mapper;

import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import nl.ou.se.rest.fuzzer.data.rmd.domain.RmdAction;
import nl.ou.se.rest.fuzzer.service.rmd.domain.RmdActionDto;

public class RmdActionMapper {

    public static List<RmdActionDto> toDtos(SortedSet<RmdAction> actions) {
        return actions.stream().map(a -> RmdActionMapper.toDto(a)).collect(Collectors.toList());
    }

    public static RmdActionDto toDto(RmdAction action) {
        RmdActionDto dto = new RmdActionDto();
        BeanUtils.copyProperties(action, dto);
        dto.setParameters(RmdParameterMapper.toDtos(action.getParameters()));
        dto.setResponses(RmdResponseMapper.toDtos(action.getResponses()));
        return dto;
    }
}
