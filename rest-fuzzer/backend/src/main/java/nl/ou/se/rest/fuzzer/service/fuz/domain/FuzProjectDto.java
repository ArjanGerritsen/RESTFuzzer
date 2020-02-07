package nl.ou.se.rest.fuzzer.service.fuz.domain;

import java.time.LocalDateTime;

import nl.ou.se.rest.fuzzer.service.rmd.domain.RmdSutDto;

public class FuzProjectDto {

    // variables
    private Long id;
    private String type;
    private RmdSutDto sut;
    private LocalDateTime createdAt;

    // getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public RmdSutDto getSut() {
        return sut;
    }

    public void setSut(RmdSutDto sut) {
        this.sut = sut;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}