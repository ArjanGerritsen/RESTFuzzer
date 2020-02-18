package nl.ou.se.rest.fuzzer.service.fuz.mapper;

import java.util.List;
import java.util.stream.Collectors;

import nl.ou.se.rest.fuzzer.data.fuz.domain.FuzResponse;
import nl.ou.se.rest.fuzzer.service.fuz.domain.FuzResponseDto;

public class FuzResponseMapper {

    public static List<FuzResponseDto> toDtos(List<FuzResponse> responses) {
        return responses.stream().map(r -> FuzResponseMapper.toDto(r)).collect(Collectors.toList());
    }

    private static FuzResponseDto toDto(FuzResponse response) {
        return null;
    }

}
