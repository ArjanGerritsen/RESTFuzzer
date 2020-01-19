package nl.ou.se.rest.fuzzer.data.job.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity(name="jobs")
public class Job {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@NotEmpty
	private String className;

	@Temporal(TemporalType.TIMESTAMP)
	private Date startedAt;

	@Temporal(TemporalType.TIMESTAMP)
	private Date finishedAt;
}