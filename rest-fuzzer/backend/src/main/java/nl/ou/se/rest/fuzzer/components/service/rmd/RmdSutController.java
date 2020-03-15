package nl.ou.se.rest.fuzzer.components.service.rmd;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import nl.ou.se.rest.fuzzer.components.data.rmd.dao.RmdActionService;
import nl.ou.se.rest.fuzzer.components.data.rmd.dao.RmdSutService;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.RmdSut;
import nl.ou.se.rest.fuzzer.components.service.rmd.domain.RmdSutDto;
import nl.ou.se.rest.fuzzer.components.service.rmd.mapper.RmdActionMapper;
import nl.ou.se.rest.fuzzer.components.service.rmd.mapper.RmdSutMapper;
import nl.ou.se.rest.fuzzer.components.service.util.ValidatorUtil;
import nl.ou.se.rest.fuzzer.components.shared.QueryUtil;

@RestController()
@RequestMapping("/rest/suts")
public class RmdSutController {

    private Logger logger = LoggerFactory.getLogger(RmdSutController.class);

    @Autowired
    RmdSutService sutService;

    @Autowired
    RmdActionService actionService;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<RmdSutDto> findAll() {
        List<RmdSut> suts = sutService.findAll();
        return RmdSutMapper.toDtos(suts);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<?> findById(@PathVariable(name = "id") Long id) {
        Optional<RmdSut> sut = sutService.findById(id);
        if (!sut.isPresent()) {
            return ResponseEntity.badRequest().body(new RmdSutDto());
        }
        return ResponseEntity.ok(RmdSutMapper.toDto(sut.get(), true));
    }

    @RequestMapping(path = "{id}/actions", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<?> findActionsById(@PathVariable(name = "id") Long id,
            @RequestParam(name = "curPage") int curPage, @RequestParam(name = "perPage") int perPage,
            @RequestParam(name = "filter", required = false) String path) {
        return ResponseEntity.ok(RmdActionMapper.toDtos(actionService.findBySutIdAndPath(id, QueryUtil.toLike(path),
                QueryUtil.toPageRequest(curPage, perPage))));
    }

    @RequestMapping(path = "{id}/actions/count", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<?> countActionsByIdId(@PathVariable(name = "id") Long id,
            @RequestParam(name = "filter", required = false) String path) {
        return ResponseEntity.ok(actionService.countBySutIdAndPath(id, QueryUtil.toLike(path)));
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<?> add(@RequestBody RmdSutDto sutDto) {
        RmdSut sut = RmdSutMapper.toDomain(sutDto);
        sut.setCreatedAt(LocalDateTime.now());

        List<String> violations = ValidatorUtil.getViolations(sut);

        if (violations.isEmpty()) {
            sut = sutService.save(sut);
            return ResponseEntity.ok(RmdSutMapper.toDto(sut, false));
        } else {
        	return ValidatorUtil.getResponseForViolations(violations);
        }
    }

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        Optional<RmdSut> sut = sutService.findById(id);
        if (!sut.isPresent()) {
            return ResponseEntity.badRequest().body(new RmdSutDto());
        }
        sutService.deleteById(id);
        return ResponseEntity.ok(RmdSutMapper.toDto(sut.get(), false));
    }
}