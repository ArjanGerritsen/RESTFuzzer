package nl.ou.se.fuzz.rest.service.controller.rest.projects;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/rest/projects")
public class ProjectsController {

	private List<ProjectDto> projects = new ArrayList<ProjectDto>();

	private ProjectsController() {
		this.init();
	}
	
	private void init() {
		ProjectDto project = null;
		
		project = new ProjectDto();
		project.setCreatedAt(new Date().getTime());
		project.setDescription("Fuzzing van Wordpress (60min)");
		project.setId(1);
		project.setOasUrl("http://localhost/rest/swagger.json");
		project.setStatus("NIEUW");
		this.projects.add(project);

		project = new ProjectDto();
		project.setCreatedAt(new Date().getTime());
		project.setDescription("Fuzzing van Wordpress (10min)");
		project.setId(2);
		project.setOasUrl("http://localhost/rest/swagger.json");
		project.setStatus("NIEUW");
		this.projects.add(project);		
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<ProjectDto> list() {	
		return this.projects;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody ProjectDto add(@RequestBody ProjectDto projectDto) {
		ProjectDto project = new ProjectDto();
		project.setCreatedAt(new Date().getTime());
		project.setDescription(projectDto.getDescription());
		project.setId(this.projects.size() + 1);
		project.setOasUrl(projectDto.getOasUrl());
		project.setStatus("NIEUW");
		this.projects.add(project);

		return project;
	}
}