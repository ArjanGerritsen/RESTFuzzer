package nl.ou.se.fuzz.rest.service.projects;

import org.springframework.data.repository.CrudRepository;

import nl.ou.se.fuzz.rest.service.projects.domain.Project;

public interface  ProjectsService extends CrudRepository<Project, Integer>  {

}