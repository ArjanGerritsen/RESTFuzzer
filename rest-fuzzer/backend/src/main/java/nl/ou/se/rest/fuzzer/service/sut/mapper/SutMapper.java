package nl.ou.se.rest.fuzzer.service.sut.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import nl.ou.se.rest.fuzzer.data.rmd.domain.Sut;
import nl.ou.se.rest.fuzzer.service.sut.domain.SutDto;

public abstract class SutMapper {

	// methods
	public static List<SutDto> toDtos(List<Sut> suts) {
		return suts.stream().map(s -> SutMapper.toDto(s)).collect(Collectors.toList());
	}

	public static SutDto toDto(Sut sut) {
		SutDto dto = new SutDto();
		BeanUtils.copyProperties(sut, dto);
		dto.setActions(ActionMapper.toDtos(sut.getActions()));
		return dto;
	}

    public static Sut toDomain(SutDto dto) {
        Sut sut = new Sut();
        BeanUtils.copyProperties(dto, sut);
        return sut;
    }	
}