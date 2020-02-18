package nl.ou.se.rest.fuzzer.data.fuz.domain;

import java.time.LocalDateTime;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.SortNatural;

import nl.ou.se.rest.fuzzer.Constants;
import nl.ou.se.rest.fuzzer.data.rmd.domain.RmdSut;

@Entity(name = "fuz_projects")
@NamedEntityGraph(
	name = Constants.ENTITY_GRAPH_FUZ_PROJECTS_ALL_RELATIONS, 
	attributeNodes = {
		@NamedAttributeNode(value = "sut", subgraph = "sut")
//		@NamedAttributeNode(value = "requests"),
//		@NamedAttributeNode(value = "responses")
	},
	subgraphs = {
		@NamedSubgraph(
			name = "sut",
			attributeNodes = {
				@NamedAttributeNode(value = "actions", subgraph = "sut.actions") 
			}
		), 	
		@NamedSubgraph(
			name = "sut.actions",
			attributeNodes = {
		        @NamedAttributeNode(value = "parameters"),
		        @NamedAttributeNode(value = "responses")						        
		    }
		)
	}
)
public class FuzProject implements Comparable<FuzProject> {

    // variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private FuzType type;

    @ManyToOne(optional = false)
    @JoinColumn(name = "sut_id")
    @NotNull
    private RmdSut sut;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id")
    @SortNatural
    private SortedSet<FuzRequest> requests = new TreeSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id")
    @SortNatural
    private SortedSet<FuzResponse> responses = new TreeSet<>();

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

	public SortedSet<FuzRequest> getRequests() {
		return requests;
	}

	public void setRequests(SortedSet<FuzRequest> requests) {
		this.requests = requests;
	}

	public SortedSet<FuzResponse> getResponses() {
		return responses;
	}

	public void setResponses(SortedSet<FuzResponse> responses) {
		this.responses = responses;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	// toString
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
