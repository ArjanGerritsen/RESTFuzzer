package nl.ou.se.rest.fuzzer.service.fuz.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import nl.ou.se.rest.fuzzer.data.fuz.domain.FuzRequest;
import nl.ou.se.rest.fuzzer.service.fuz.domain.FuzRequestDto;

public class FuzRequestMapper {

    public static List<FuzRequestDto> toDtos(Set<FuzRequest> requests) {
        return requests.stream().map(r -> FuzRequestMapper.toDto(r)).collect(Collectors.toList());
    }

    public static FuzRequestDto toDto(FuzRequest request ) {
        return null;
    }
}
