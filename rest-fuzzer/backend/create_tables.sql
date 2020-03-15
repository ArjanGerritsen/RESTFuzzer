--------------------------- creating all --------------------

CREATE TABLE IF NOT EXISTS tasks (
  id INT AUTO_INCREMENT PRIMARY KEY,
  canonical_name VARCHAR(255) NOT NULL,
  meta_data_tuples_json TEXT, 
  started_at TIMESTAMP NULL,
  crashed_at TIMESTAMP NULL,
  finished_at TIMESTAMP NULL
) ENGINE=INNODB;



CREATE TABLE IF NOT EXISTS rmd_suts (
  id INT AUTO_INCREMENT PRIMARY KEY,
  location VARCHAR(255) NOT NULL,
  title VARCHAR(64) NULL,
  description VARCHAR(128) NULL,  
  host VARCHAR(255) NULL,
  base_path VARCHAR(255) NULL,
  created_at TIMESTAMP NULL
) ENGINE=INNODB;


CREATE TABLE IF NOT EXISTS rmd_actions (
  id INT AUTO_INCREMENT PRIMARY KEY,
  path VARCHAR(255) NOT NULL,
  http_method ENUM('GET', 'POST', 'PATCH', 'PUT', 'DELETE') NOT NULL,
  sut_id INTEGER
) ENGINE=INNODB;

CREATE INDEX idx_rmd_actions_sut_id ON rmd_actions (sut_id); 

CREATE TABLE IF NOT EXISTS rmd_parameters (
  id INT AUTO_INCREMENT PRIMARY KEY,
  position INT NOT NULL,
  name VARCHAR(64) NOT NULL,
  required BOOLEAN NOT NULL,
  description VARCHAR(255) NOT NULL,
  type ENUM('BOOLEAN', 'STRING', 'INTEGER', 'ARRAY') NOT NULL,
  context ENUM('FORMDATA', 'HEADER', 'PATH', 'QUERY') NOT NULL,
  meta_data_tuples_json TEXT,
  action_id INT
) ENGINE=INNODB;

 
CREATE INDEX idx_rmd_parameters_action_id ON rmd_parameters (action_id);

CREATE TABLE IF NOT EXISTS rmd_responses (
  id INT AUTO_INCREMENT PRIMARY KEY,
  status_code INT NOT NULL,
  description VARCHAR(255) NOT NULL,
  action_id INT
) ENGINE=INNODB;


CREATE INDEX idx_rmd_responses_action_id ON rmd_responses (action_id);


CREATE TABLE IF NOT EXISTS fuz_projects (
  id INT AUTO_INCREMENT PRIMARY KEY,
  type VARCHAR(64) NOT NULL,
  meta_data_tuples_json TEXT, 
  sut_id INT,
  created_at TIMESTAMP NULL
) ENGINE=INNODB;

CREATE INDEX idx_fuz_projects_sut_id ON fuz_projects (sut_id);

CREATE TABLE IF NOT EXISTS fuz_requests (
  id INT AUTO_INCREMENT PRIMARY KEY,
  path VARCHAR(255) NOT NULL,
  http_method ENUM('GET', 'POST', 'PATCH', 'PUT', 'DELETE') NOT NULL,
  formdata_parameters_json TEXT,
  header_parameters_json TEXT,
  path_parameters_json TEXT,
  query_parameters_json TEXT,
  project_id INT,
  created_at TIMESTAMP NULL  
) ENGINE=INNODB;

CREATE INDEX idx_fuz_requests_project_id ON fuz_requests (project_id);


CREATE TABLE IF NOT EXISTS fuz_responses (
  id INT AUTO_INCREMENT PRIMARY KEY,
  status_code INT,
  status_description VARCHAR(255),
  body TEXT,
  failure_reason VARCHAR(255),
  project_id INT,
  request_id INT,
  created_at TIMESTAMP NULL
) ENGINE=INNODB;

CREATE INDEX idx_fuz_responses_project_id ON fuz_responses (project_id);
CREATE INDEX idx_fuz_responses_request_id ON fuz_responses (request_id);

CREATE TABLE IF NOT EXISTS fuz_dictionaries (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255),
  items_csv TEXT
) ENGINE=INNODB;

--------------------------- dropping all --------------------

drop table fuz_responses;
drop table fuz_requests;
drop table fuz_projects;
drop table fuz_dictionaries;

drop table rmd_responses;
drop table rmd_parameters;
drop table rmd_actions;
drop table rmd_suts;

drop table tasks;

--------------------------- other ---------------------------

truncate tasks;
truncate rmd_suts;
truncate rmd_actions;
truncate rmd_parameters;
truncate rmd_responses;

insert into rmd_suts values (1, "http://localhost/wordpress/rest-api/schema", null, null, null, null, null);
insert into rmd_suts values (1, "/ws/git/ou-prototype/rest-fuzzer/backend/src/main/resources/schema.json", null, null, null, null);

insert into tasks values (1, "nl.ou.se.rest.fuzzer.extractor.ExtractorTask", "{\"sut_id\":1}", NULL, NULL, NULL);