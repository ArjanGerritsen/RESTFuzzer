package nl.ou.se.rest.fuzzer.components.service.fuz.domain;

import java.time.LocalDateTime;

import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzType;
import nl.ou.se.rest.fuzzer.components.service.rmd.domain.RmdSutDto;

public class FuzProjectDto {

	// variables
	private Long id;
	private FuzType type;
	private RmdSutDto sut;
	private Long requestsCount;
	private Long responsesCount; // TODO Gelijk trekken met Suts.
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

	public Long getRequestsCount() {
		return requestsCount;
	}

	public void setRequestsCount(Long requestsCount) {
		this.requestsCount = requestsCount;
	}

	public Long getResponsesCount() {
		return responsesCount;
	}

	public void setResponsesCount(Long responsesCount) {
		this.responsesCount = responsesCount;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}