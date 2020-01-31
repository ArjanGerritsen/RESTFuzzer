package nl.ou.se.rest.fuzzer.service.sut.mapper;

import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import nl.ou.se.rest.fuzzer.data.rmd.domain.Response;
import nl.ou.se.rest.fuzzer.service.sut.domain.ResponseDto;

public class ResponseMapper {

    public static List<ResponseDto> toDtos(SortedSet<Response> responses) {
        return responses.stream().map(r -> ResponseMapper.toDto(r)).collect(Collectors.toList());
    }

    private static ResponseDto toDto(Response response) {
        ResponseDto dto = new ResponseDto();
        BeanUtils.copyProperties(response, dto);
        return dto;
    }
}