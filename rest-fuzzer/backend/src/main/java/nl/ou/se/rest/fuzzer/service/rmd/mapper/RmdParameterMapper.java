package nl.ou.se.rest.fuzzer.service.rmd.mapper;

import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import nl.ou.se.rest.fuzzer.data.rmd.domain.RmdParameter;
import nl.ou.se.rest.fuzzer.service.rmd.domain.RmdParameterDto;

public class RmdParameterMapper {

    public static List<RmdParameterDto> toDtos(SortedSet<RmdParameter> parameters) {
        return parameters.stream().map(p -> RmdParameterMapper.toDto(p)).collect(Collectors.toList());
    }

    private static RmdParameterDto toDto(RmdParameter parameter) {
        RmdParameterDto dto = new RmdParameterDto();
        BeanUtils.copyProperties(parameter, dto);
        dto.setMetaDataTuplesJson(parameter.getMetaDataTuples().toString());
        return dto;
    }
}
