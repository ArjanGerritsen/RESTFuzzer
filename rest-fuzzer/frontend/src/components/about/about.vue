<template>
  <div>
    <div class="row">
      <div class="col-6">
        <b-card header-tag="header">
          <span slot="header">
            <b-icon icon="list-task" font-scale="1"></b-icon>&nbsp;About
          </span>
          <b-card-text>
            <p>
              This web application is a prototype fuzzer, capable of basic, model-based, dictionary, and model-based dictionary fuzzing.
              It is developed as graduation assignment for the education Master Software Engineering on the <a href="http://www.ou.nl">Open Universiteit</a>.
            </p>
            <p>            
              For more information please contact the researcher: <a href="mailto:research@arjangerritsen.nl">Arjan Gerritsen</a>.
            </p>
          </b-card-text>
        </b-card>
      </div>
      <div class="col-6">
        <b-card header-tag="header">
          <span slot="header">
            <b-icon icon="list-task" font-scale="1"></b-icon>&nbsp;Releases
          </span>
          <b-card-text>
            <b-card no-body>
              <b-tabs card pills vertical>
                <b-tab title="v1.0.1 | 01-10-2020" active>
                  <b-card-text>
                    <ul>
                      <li>Verify/cleanup TODOs.</li>
                    </ul>
                  </b-card-text>
                </b-tab>
                <b-tab title="v1.0.0 | 01-09-2020">
                  <b-card-text>
                    <ul>
                      <li>TODO: Aanvullen</li>
                      <li>TODO Refactor createdAt to constructors instead of factories.</li>
                    </ul>
                  </b-card-text>
                </b-tab>
                <b-tab title="v0.0.9 | 26-08-2020">
                  <b-card-text>
                    <ul>
                      <li>Allow limiting all fuzzers to a maximum number of requests.</li>
                    </ul>
                    <ul>                      
                      <li>Fix: Update report when task is finished / refresh list.</li>
                      <li>Fix: SUT selection is empty when adding new fuzzing projects.</li>
                      <li>Fix: Clear results for fuzzing project doesn't work correct.</li>
                    </ul>
                  </b-card-text>
                </b-tab>
                <b-tab title="v0.0.8 | 22-07-2020">
                  <b-card-text>
                    <ul>
                      <li>Add/execute initial version dictionary fuzzing.</li>
                      <li>Add/execute initial version model-based dictionary fuzzing.</li>                      
                    </ul>
                  </b-card-text>
                </b-tab>
                <b-tab title="v0.0.7 | 01-07-2020">
                  <b-card-text>
                    <ul>
                      <li>Add CRUD for reports (code coverage and responses status codes).</li>
                      <li>Relation between request and action to display action details with request.</li>
                      <li>Add favicon for the application.</li>
                    </ul>
                  </b-card-text>
                </b-tab>
                <b-tab title="v0.0.6 | 13-06-2020">
                  <b-card-text>
                    <ul>
                      <li>View all information with sequences for projects.</li>
                      <li>Improve (make consistent) viewing all request, response and sequence information with projects/SUTs.</li>
                      <li>Add isRequired (true/false/*) for parameters in configuration.</li>
                      <li>Default values in configuration.</li>
                      <li>Relation between request and action to display action details with request.</li>
                      <li>Use values from dependencies in sequence.</li>
                    </ul>
                    <ul>                      
                      <li>Fix. Sequences are not always valid.</li>
                    </ul>
                  </b-card-text>
                </b-tab>
                <b-tab title="v0.0.5 | 13-05-2020">
                  <b-card-text>
                    <ul>
                      <li>Create sequences for MB fuzzing (implementing in fuzzing process + overview with fuzzing projects).</li>
                      <li>Enable basic authentication via configuration.</li>
                      <li>Improve correctness values assigned to arrays.</li>
                      <li>Dependency overview for SUTs.</li>
                      <li>Adding/removing manual dependencies for SUTs.</li>
                    </ul>
                    <ul>
                      <li>Fix: Delete fuzzing project fails.</li>
                      <li>Fix: Delete SUT fails.</li>
                    </ul>
                  </b-card-text>
                </b-tab>
                <b-tab title="v0.0.4 | 22-04-2020">
                  <b-card-text>
                    <ul>
                      <li>Configuration maintenance and selecting configuration for fuzzing projects.</li>
                      <li>Sepearate page for Tasks.</li>
                      <li>Tasks: Progress percentage.</li>
                      <li>Change layout Systems under test and Fuzzing projects (remove tasks progress).</li>
                    </ul>
                    <ul>
                      <li>Fix: Ids in URLs for all entities + deletion of item (update URL without id).</li>
                    </ul>
                  </b-card-text>
                </b-tab>
                <b-tab title="v0.0.3 | 23-03-2020">
                  <b-card-text>
                    <ul>
                      <li>Parameters for Fuzzing projects.</li>
                      <li>First version Dictionaries - CRUD.</li>
                      <li>Finialize (simple) fuzzing task (displaying requests and responses + repetitions).</li>
                      <li>Display Fuzzing project detail correctly.</li>
                      <li>Delete SUT is only possible if not connected to FuzProject.</li>
                    </ul>
                    <ul>
                      <li>Fix: Pagination requests/responses with filter.</li>
                    </ul>
                  </b-card-text>
                </b-tab>
                <b-tab title="v0.0.2 | 19-02-2020">
                  <b-card-text>
                    <ul>
                      <li>Display REST model description information.</li>
                      <li>Backend validations.</li>
                      <li>Start (simple) fuzzing task.</li>
                    </ul>
                    <ul>
                      <li>Fix: Delete SUT.</li>
                      <li>Fix: Lazy loading for relations.</li>
                      <li>Fix: Apply store pattern correctly.</li>
                    </ul>
                  </b-card-text>
                </b-tab>
                <b-tab title="v0.0.1 | 29-01-2020">
                  <b-card-text>
                    <ul>
                      <li>Project setup.</li>
                      <li>Show SUTs overview + Start task.</li>
                      <li>Extractor task (Parse OAS information, save to MySQL database).</li>
                      <li>Background task system.</li>
                    </ul>
                  </b-card-text>
                </b-tab>
              </b-tabs>
            </b-card>
          </b-card-text>
        </b-card>
      </div>
    </div>
  </div>
</template>