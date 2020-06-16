package nl.ou.se.rest.fuzzer.components.data.report.dao;

import org.springframework.data.repository.CrudRepository;

import nl.ou.se.rest.fuzzer.components.data.report.domain.Report;

public interface ReportSerivce extends CrudRepository<Report, Long> {

}
