package nl.ou.se.rest.fuzzer.components.service.fuz.mapper;

import java.util.List;
import java.util.stream.Collectors;

import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzDictionary;
import nl.ou.se.rest.fuzzer.components.service.fuz.domain.FuzDictionaryDto;

public class FuzDictionaryMapper {

	public static List<FuzDictionaryDto> toDtos(List<FuzDictionary> dictionaries) {
		return dictionaries.stream().map(d -> FuzDictionaryMapper.toDto(d)).collect(Collectors.toList());
	}

	public static FuzDictionaryDto toDto(FuzDictionary dictionary) {
		// TODO Auto-generated method stub
		return null;
	}

	public static FuzDictionary toDomain(FuzDictionaryDto dictionaryDto) {
		FuzDictionary dictionary = new FuzDictionary(dictionaryDto.getName(), dictionaryDto.getItems());
		return dictionary;
	}
}