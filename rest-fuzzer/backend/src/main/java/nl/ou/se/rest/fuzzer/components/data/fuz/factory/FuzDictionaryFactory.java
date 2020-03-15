package nl.ou.se.rest.fuzzer.components.data.fuz.factory;

import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzDictionary;

public class FuzDictionaryFactory {

	private FuzDictionary dictionary;
	
	public FuzDictionaryFactory create(String name, String itemsCsv) {
		dictionary = new FuzDictionary(name, itemsCsv);	
		return this;
	}
	
	public FuzDictionary build() {
		return this.dictionary;
	}
}