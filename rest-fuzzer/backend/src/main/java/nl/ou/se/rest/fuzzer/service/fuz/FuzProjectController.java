package nl.ou.se.rest.fuzzer.service.fuz;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nl.ou.se.rest.fuzzer.data.fuz.dao.FuzProjectService;
import nl.ou.se.rest.fuzzer.data.fuz.domain.FuzProject;
import nl.ou.se.rest.fuzzer.data.rmd.dao.RmdSutService;
import nl.ou.se.rest.fuzzer.data.rmd.domain.RmdSut;
import nl.ou.se.rest.fuzzer.service.HttpResponseDto;
import nl.ou.se.rest.fuzzer.service.ValidatorUtil;
import nl.ou.se.rest.fuzzer.service.fuz.domain.FuzProjectDto;
import nl.ou.se.rest.fuzzer.service.fuz.mapper.FuzProjectMapper;

@RestController()
@RequestMapping("/rest/projects")
public class FuzProjectController {

    private Logger logger = LoggerFactory.getLogger(FuzProjectController.class);

    @Autowired
    FuzProjectService projectService;

    @Autowired
    RmdSutService sutService;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<FuzProjectDto> findAll() {
        List<FuzProject> projects = projectService.findAll();
        return FuzProjectMapper.toDtos(projects);
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<?> add(@RequestBody FuzProjectDto projectDto) {
        FuzProject project = FuzProjectMapper.toDomain(projectDto);
        project.setCreatedAt(LocalDateTime.now());

        if (projectDto.getSut() != null && projectDto.getSut().getId() != null) {
            Optional<RmdSut> sut = sutService.findById(projectDto.getSut().getId());
            if (sut.isPresent()) {
                project.setSut(sut.get());
            }
        }

        List<String> violations = ValidatorUtil.getViolations(project);

        if (violations.isEmpty()) {
            project = projectService.save(project);
            return ResponseEntity.ok(FuzProjectMapper.toDto(project));
        } else {
            String json = "";
            try {
                json = new ObjectMapper().writeValueAsString(new HttpResponseDto(violations));
            } catch (JsonProcessingException e) {
                logger.warn(e.getMessage());
            }
            return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
        }
    }
}
