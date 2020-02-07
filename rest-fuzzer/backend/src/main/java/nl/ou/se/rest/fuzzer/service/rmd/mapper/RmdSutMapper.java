package nl.ou.se.rest.fuzzer.service.rmd.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import nl.ou.se.rest.fuzzer.data.rmd.domain.RmdSut;
import nl.ou.se.rest.fuzzer.service.rmd.domain.RmdSutDto;

public abstract class RmdSutMapper {

	// methods
	public static List<RmdSutDto> toDtos(List<RmdSut> suts) {
		return suts.stream().map(s -> RmdSutMapper.toDto(s, false)).collect(Collectors.toList());
	}

	public static RmdSutDto toDto(RmdSut sut) {
		return toDto(sut, true);
	}

    public static RmdSut toDomain(RmdSutDto dto) {
        RmdSut sut = new RmdSut();
        BeanUtils.copyProperties(dto, sut);
        return sut;
    }

    private static RmdSutDto toDto(RmdSut sut, boolean mapRelations) {
        RmdSutDto dto = new RmdSutDto();
        BeanUtils.copyProperties(sut, dto);
        if (mapRelations) {
            dto.setActions(RmdActionMapper.toDtos(sut.getActions()));
        }
        return dto;
    }
}