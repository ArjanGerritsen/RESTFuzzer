package nl.ou.se.rest.fuzzer.service.sut.mapper;

import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import nl.ou.se.rest.fuzzer.data.rmd.domain.Parameter;
import nl.ou.se.rest.fuzzer.service.sut.domain.ParameterDto;

public class ParameterMapper {

    public static List<ParameterDto> toDtos(SortedSet<Parameter> parameters) {
        return parameters.stream().map(p -> ParameterMapper.toDto(p)).collect(Collectors.toList());
    }

    private static ParameterDto toDto(Parameter parameter) {
        ParameterDto dto = new ParameterDto();
        BeanUtils.copyProperties(parameter, dto);
        dto.setMetaDataTuplesJson(parameter.getMetaDataTuples().toString());
        return dto;
    }
}
