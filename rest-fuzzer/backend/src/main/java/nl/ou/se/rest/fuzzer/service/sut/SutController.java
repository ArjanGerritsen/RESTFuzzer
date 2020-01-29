package nl.ou.se.rest.fuzzer.service.sut;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import nl.ou.se.rest.fuzzer.data.rmd.dao.SutService;
import nl.ou.se.rest.fuzzer.data.rmd.domain.Sut;

@RestController()
@RequestMapping("/rest/suts")
public class SutController {

	@Autowired
	SutService sutSerivce;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<SutDto> findAll() {
		List<Sut> suts = sutSerivce.findAll();
		return SutMapper.toDtos(suts);
	}

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<?> add(@RequestBody SutDto sutDto) {
        Sut sut = sutSerivce.save(SutMapper.toDomain(sutDto));
        return ResponseEntity.ok(SutMapper.toDto(sut));
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