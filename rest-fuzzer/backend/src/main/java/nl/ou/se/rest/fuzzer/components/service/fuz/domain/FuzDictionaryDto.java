package nl.ou.se.rest.fuzzer.components.service.fuz.domain;

public class FuzDictionaryDto {

	// variables
	private Long id;
	private String name;
	private String items;

	// getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}
}