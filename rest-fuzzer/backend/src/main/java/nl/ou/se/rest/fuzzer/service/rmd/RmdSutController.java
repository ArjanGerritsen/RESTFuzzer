package nl.ou.se.rest.fuzzer.service.rmd;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nl.ou.se.rest.fuzzer.data.rmd.dao.RmdSutService;
import nl.ou.se.rest.fuzzer.data.rmd.domain.RmdSut;
import nl.ou.se.rest.fuzzer.service.HttpResponseDto;
import nl.ou.se.rest.fuzzer.service.ValidatorUtil;
import nl.ou.se.rest.fuzzer.service.rmd.domain.RmdSutDto;
import nl.ou.se.rest.fuzzer.service.rmd.mapper.RmdSutMapper;

@RestController()
@RequestMapping("/rest/suts")
public class RmdSutController {

	private Logger logger = LoggerFactory.getLogger(RmdSutController.class);

	@Autowired
	RmdSutService sutSerivce;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<RmdSutDto> findAll() {
		List<RmdSut> suts = sutSerivce.findAll();
		return RmdSutMapper.toDtos(suts);
	}

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<?> findById(@PathVariable(name = "id") Long id) {
        Optional<RmdSut> sut = sutSerivce.findById(id);
        if (!sut.isPresent()) {
            return ResponseEntity.badRequest().body(new RmdSutDto());         
        }
        return ResponseEntity.ok(RmdSutMapper.toDto(sut.get()));
    }	

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<?> add(@RequestBody RmdSutDto sutDto) {
        RmdSut sut = RmdSutMapper.toDomain(sutDto);
        sut.setCreatedAt(LocalDateTime.now());
        
    	List<String> violations = ValidatorUtil.getViolations(sut); 

		if (violations.isEmpty()) {
	        sut = sutSerivce.save(sut);
	        return ResponseEntity.ok(RmdSutMapper.toDto(sut));
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

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        Optional<RmdSut> sut = sutSerivce.findById(id);
        if (!sut.isPresent()) {
            return ResponseEntity.badRequest().body(new RmdSutDto());         
        }
        sutSerivce.deleteById(id);
        return ResponseEntity.ok(RmdSutMapper.toDto(sut.get()));
    }
}