drop table tasks;

CREATE TABLE IF NOT EXISTS tasks (
  id INT AUTO_INCREMENT PRIMARY KEY,
  canonical_name VARCHAR(255) NOT NULL,
  meta_data_tuples_json TEXT, 
  started_at TIMESTAMP NULL,
  crashed_at TIMESTAMP NULL,
  finished_at TIMESTAMP NULL
) ENGINE=INNODB;

drop table rmd_responses;
drop table rmd_parameters;
drop table rmd_actions;
drop table rmd_suts;

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

ALTER TABLE rmd_actions ADD CONSTRAINT rmd_actions_fk_sut FOREIGN KEY (sut_id) REFERENCES rmd_suts(id); 

CREATE TABLE IF NOT EXISTS rmd_parameters (
  id INT AUTO_INCREMENT PRIMARY KEY,
  position INT NOT NULL,
  name VARCHAR(64) NOT NULL,
  required BOOLEAN NOT NULL,
  description VARCHAR(255) NOT NULL,
  type ENUM('BOOLEAN', 'STRING', 'INTEGER', 'ARRAY') NOT NULL,
  context ENUM('FORMDATA', 'HEADER', 'PATH', 'QUERY') NOT NULL,
  meta_data_tuples_json TEXT,
  action_id INTEGER
) ENGINE=INNODB;

ALTER TABLE rmd_parameters ADD CONSTRAINT rmd_actions_fk_action FOREIGN KEY (action_id) REFERENCES rmd_actions(id); 

CREATE TABLE IF NOT EXISTS rmd_responses (
  id INT AUTO_INCREMENT PRIMARY KEY,
  status_code INT NOT NULL,
  description VARCHAR(255) NOT NULL,
  action_id INT
) ENGINE=INNODB;

ALTER TABLE rmd_responses ADD CONSTRAINT rmd_responses_fk_action FOREIGN KEY (action_id) REFERENCES rmd_actions(id);

truncate tasks;
truncate rmd_suts;
truncate rmd_actions;
truncate rmd_parameters;
truncate rmd_responses;

insert into rmd_suts values (1, "http://localhost/wordpress/rest-api/schema", null, null, null, null, null);
insert into rmd_suts values (1, "/ws/git/ou-prototype/rest-fuzzer/backend/src/main/resources/schema.json", null, null, null, null);

insert into tasks values (1, "nl.ou.se.rest.fuzzer.extractor.ExtractorTask", "{\"sut_id\":1}", NULL, NULL, NULL);