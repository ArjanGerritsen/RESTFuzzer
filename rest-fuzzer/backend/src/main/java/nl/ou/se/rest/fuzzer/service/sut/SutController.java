package nl.ou.se.rest.fuzzer.service.sut;

import java.util.List;
import java.util.SortedSet;

import org.springframework.beans.factory.annotation.Autowired;
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
		SortedSet<Sut> suts = sutSerivce.findAll();
		return SutMapper.toDtos(suts);
	}
}