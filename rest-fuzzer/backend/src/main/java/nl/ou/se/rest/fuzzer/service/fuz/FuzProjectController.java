package nl.ou.se.rest.fuzzer.service.fuz;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nl.ou.se.rest.fuzzer.data.fuz.dao.FuzProjectService;
import nl.ou.se.rest.fuzzer.data.fuz.dao.FuzRequestService;
import nl.ou.se.rest.fuzzer.data.fuz.dao.FuzResponseService;
import nl.ou.se.rest.fuzzer.data.fuz.domain.FuzProject;
import nl.ou.se.rest.fuzzer.data.rmd.dao.RmdSutService;
import nl.ou.se.rest.fuzzer.data.rmd.domain.RmdSut;
import nl.ou.se.rest.fuzzer.service.HttpResponseDto;
import nl.ou.se.rest.fuzzer.service.ValidatorUtil;
import nl.ou.se.rest.fuzzer.service.fuz.domain.FuzProjectDto;
import nl.ou.se.rest.fuzzer.service.fuz.mapper.FuzProjectMapper;
import nl.ou.se.rest.fuzzer.service.fuz.mapper.FuzRequestMapper;
import nl.ou.se.rest.fuzzer.service.fuz.mapper.FuzResponseMapper;

@RestController()
@RequestMapping("/rest/projects")
public class FuzProjectController {

    // variables
    private Logger logger = LoggerFactory.getLogger(FuzProjectController.class);

    @Autowired
    FuzProjectService projectService;

    @Autowired
    FuzRequestService requestService;

    @Autowired
    FuzResponseService responseService;

    @Autowired
    RmdSutService sutService;

    // methods
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<FuzProjectDto> findAll() {
        List<FuzProject> projects = projectService.findAll();
        return FuzProjectMapper.toDtos(projects);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<?> findById(@PathVariable(name = "id") Long id) {
        Optional<FuzProject> project = projectService.findById(id);
        if (!project.isPresent()) {
            return ResponseEntity.badRequest().body(new FuzProjectDto());
        }
        return ResponseEntity.ok(FuzProjectMapper.toDto(project.get(), true));
    }

    @RequestMapping(path = "{id}/requests", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<?> findRequestsById(@PathVariable(name = "id") Long id,
            @RequestParam(name = "curPage") int curPage, @RequestParam(name = "perPage") int perPage,
            @RequestParam(name = "filter", required = false) String path) {
        return ResponseEntity.ok(FuzRequestMapper
                .toDtos(requestService.findByProjectIdAndPath(id, toLike(path), toPageRequest(curPage, perPage))));
    }

    @RequestMapping(path = "{id}/requests/count", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<?> countRequestsById(@PathVariable(name = "id") Long id,
            @RequestParam(name = "filter", required = false) String path) {
        return ResponseEntity.ok(requestService.countByProjectIdAndByPath(id, toLike(path)));
    }

    @RequestMapping(path = "{id}/responses", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<?> findResponsesById(@PathVariable(name = "id") Long id,
            @RequestParam(name = "curPage") int curPage, @RequestParam(name = "perPage") int perPage,
            @RequestParam(name = "filter", required = false) String path) {
        return ResponseEntity.ok(FuzResponseMapper
                .toDtos(responseService.findByProjectIdAndPath(id, toLike(path), toPageRequest(curPage, perPage))));
    }

    @RequestMapping(path = "{id}/responses/count", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<?> countResponsesById(@PathVariable(name = "id") Long id,
            @RequestParam(name = "filter", required = false) String path) {
        return ResponseEntity.ok(responseService.countByProjectIdAndByPath(id, toLike(path)));
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
            return ResponseEntity.ok(FuzProjectMapper.toDto(project, false));
        } else {
            String json = "";
            try {
                json = new ObjectMapper().writeValueAsString(new HttpResponseDto(violations));
            } catch (JsonProcessingException e) {
                logger.warn(e.getMessage());
            }
            return ResponseEntity.badRequest().body(json);
        }
    }

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        Optional<FuzProject> project = projectService.findById(id);
        if (!project.isPresent()) {
            return ResponseEntity.badRequest().body(new FuzProjectDto());
        }
        projectService.deleteById(id);
        return ResponseEntity.ok(FuzProjectMapper.toDto(project.get(), false));
    }

    private String toLike(String query) {
        if (StringUtils.isAllBlank(query)) {
            return "%";
        } else {
            return String.format("%%%s%%", query);
        }
    }

    private PageRequest toPageRequest(int curPage, int perPage) {
        return PageRequest.of(curPage - 1, perPage);
    }
}
