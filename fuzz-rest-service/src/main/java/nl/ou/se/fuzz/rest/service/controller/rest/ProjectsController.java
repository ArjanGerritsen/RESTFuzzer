package nl.ou.se.fuzz.rest.service.controller.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/projects")
public class ProjectsController {

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody String list() {
		return "TESTJE";
	}

}