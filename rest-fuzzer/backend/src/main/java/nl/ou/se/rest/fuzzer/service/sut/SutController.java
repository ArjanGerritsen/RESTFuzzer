package nl.ou.se.rest.fuzzer.service.sut;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public @ResponseBody List<SutDto> list() {
		List<Sut> suts = sutSerivce.findAll();
		return SutMapper.toDtos(suts);
	}

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody SutDto add(@RequestBody SutDto sutDto) {
        Sut sut = sutSerivce.save(SutMapper.toDomain(sutDto));
        return SutMapper.toDto(sut);
    }
}