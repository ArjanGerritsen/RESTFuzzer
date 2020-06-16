package nl.ou.se.rest.fuzzer.components.service.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/rest/reports")
public class ReportController {

    // variable(s)
    private Logger logger = LoggerFactory.getLogger(this.getClass());

}
