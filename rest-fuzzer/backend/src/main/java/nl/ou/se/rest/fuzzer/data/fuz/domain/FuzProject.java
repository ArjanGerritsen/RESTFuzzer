package nl.ou.se.rest.fuzzer.data.fuz.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import nl.ou.se.rest.fuzzer.data.rmd.domain.RmdSut;

@Entity(name = "fuz_tasks")
public class FuzProject implements Comparable<FuzProject> {

    // variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    private FuzType type;

    @ManyToOne(optional = false)
    @JoinColumn(name = "sut_id")
    private RmdSut sut;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    // methods
    public int compareTo(FuzProject other) {
        return this.getId().compareTo(other.getId());
    }

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

    public RmdSut getSut() {
        return sut;
    }

    public void setSut(RmdSut sut) {
        this.sut = sut;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}