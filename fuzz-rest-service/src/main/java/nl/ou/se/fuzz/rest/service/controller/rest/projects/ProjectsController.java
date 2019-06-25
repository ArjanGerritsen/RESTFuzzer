package nl.ou.se.fuzz.rest.service.controller.rest.projects;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/rest/projects")
public class ProjectsController {

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<ProjectDto> list() {
		List<ProjectDto> projects = new ArrayList<ProjectDto>();
		ProjectDto project = null;
		
		project = new ProjectDto();
		project.setCreatedAt(new Date().getTime());
		project.setDescription("Fuzzing van Wordpress (60min)");
		project.setId(1);
		project.setOasUrl("http://localhost/rest/swagger.json");
		project.setStatus("NIEUW");
		projects.add(project);

		project = new ProjectDto();
		project.setCreatedAt(new Date().getTime());
		project.setDescription("Fuzzing van Wordpress (10min)");
		project.setId(2);
		project.setOasUrl("http://localhost/rest/swagger.json");
		project.setStatus("NIEUW");
		projects.add(project);
		
		return projects;
	}
}