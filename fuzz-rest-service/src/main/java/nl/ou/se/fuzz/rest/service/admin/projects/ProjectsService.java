package nl.ou.se.fuzz.rest.service.admin.projects;

import org.springframework.data.repository.CrudRepository;

public interface  ProjectsService extends CrudRepository<Project, Integer>  {

}