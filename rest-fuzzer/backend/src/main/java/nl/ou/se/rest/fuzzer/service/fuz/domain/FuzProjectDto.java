package nl.ou.se.rest.fuzzer.service.fuz.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import nl.ou.se.rest.fuzzer.data.fuz.domain.FuzType;
import nl.ou.se.rest.fuzzer.service.rmd.domain.RmdSutDto;

public class FuzProjectDto {

	// variables
	private Long id;
	private FuzType type;
	private RmdSutDto sut;
	private List<FuzRequestDto> requests = new ArrayList<>();
	private List<FuzResponseDto> responses = new ArrayList<>();
	private LocalDateTime createdAt;

	// getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FuzType getType() {
		return type;
	}

	public void setType(FuzType type) {
		this.type = type;
	}

	public RmdSutDto getSut() {
		return sut;
	}

	public void setSut(RmdSutDto sut) {
		this.sut = sut;
	}

	public List<FuzRequestDto> getRequests() {
		return requests;
	}

	public void setRequests(List<FuzRequestDto> requests) {
		this.requests = requests;
	}

	public List<FuzResponseDto> getResponses() {
		return responses;
	}

	public void setResponses(List<FuzResponseDto> responses) {
		this.responses = responses;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}