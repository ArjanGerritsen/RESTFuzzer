package nl.ou.se.rest.fuzzer.service.fuz;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import nl.ou.se.rest.fuzzer.data.fuz.dao.FuzProjectService;
import nl.ou.se.rest.fuzzer.data.fuz.domain.FuzProject;
import nl.ou.se.rest.fuzzer.service.fuz.domain.FuzProjectDto;
import nl.ou.se.rest.fuzzer.service.fuz.mapper.FuzProjectMapper;

@RestController()
@RequestMapping("/rest/projects")
public class FuzProjectController {

    private Logger logger = LoggerFactory.getLogger(FuzProjectController.class);

    @Autowired
    FuzProjectService projectService;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<FuzProjectDto> findAll() {
        List<FuzProject> projects = projectService.findAll();
        return FuzProjectMapper.toDtos(projects);
    }
}
