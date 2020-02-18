package nl.ou.se.rest.fuzzer.service.fuz.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import nl.ou.se.rest.fuzzer.data.fuz.domain.FuzRequest;
import nl.ou.se.rest.fuzzer.service.fuz.domain.FuzRequestDto;

public class FuzRequestMapper {

    public static List<FuzRequestDto> toDtos(List<FuzRequest> requests) {
        return requests.stream().map(r -> FuzRequestMapper.toDto(r)).collect(Collectors.toList());
    }

    public static FuzRequestDto toDto(FuzRequest request) {
        FuzRequestDto dto = new FuzRequestDto();
        BeanUtils.copyProperties(request, dto);
        return dto;
    }
}
