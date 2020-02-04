package nl.ou.se.rest.fuzzer.service.sut;

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

import nl.ou.se.rest.fuzzer.data.rmd.dao.SutService;
import nl.ou.se.rest.fuzzer.data.rmd.domain.Sut;
import nl.ou.se.rest.fuzzer.service.HttpResponseDto;
import nl.ou.se.rest.fuzzer.service.ValidatorUtil;
import nl.ou.se.rest.fuzzer.service.sut.domain.SutDto;
import nl.ou.se.rest.fuzzer.service.sut.mapper.SutMapper;

@RestController()
@RequestMapping("/rest/suts")
public class SutController {

	private Logger logger = LoggerFactory.getLogger(SutController.class);

	@Autowired
	SutService sutSerivce;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<SutDto> findAll() {
		List<Sut> suts = sutSerivce.findAll();
		return SutMapper.toDtos(suts);
	}

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<?> findById(@PathVariable(name = "id") Long id) {
        Optional<Sut> sut = sutSerivce.findById(id);
        if (!sut.isPresent()) {
            return ResponseEntity.badRequest().body(new SutDto());         
        }
        return ResponseEntity.ok(SutMapper.toDto(sut.get()));
    }	

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<?> add(@RequestBody SutDto sutDto) {
        Sut sut = SutMapper.toDomain(sutDto);
        sut.setCreatedAt(LocalDateTime.now());
        
    	List<String> violations = ValidatorUtil.getViolations(sut); 

		if (violations.isEmpty()) {
	        sut = sutSerivce.save(sut);
	        return ResponseEntity.ok(SutMapper.toDto(sut));
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
        Optional<Sut> sut = sutSerivce.findById(id);
        if (!sut.isPresent()) {
            return ResponseEntity.badRequest().body(new SutDto());         
        }
        sutSerivce.deleteById(id);
        return ResponseEntity.ok(SutMapper.toDto(sut.get()));
    }
}