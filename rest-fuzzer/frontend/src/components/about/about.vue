<template>
  <div>
    <div class="row">
      <div class="col-6">
        <b-card header-tag="header">
          <span slot="header">
            <b-icon icon="list-task" font-scale="1"></b-icon>&nbsp;About
          </span>
          <b-card-text>
            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque dapibus tellus ut leo rhoncus vestibulum. Integer vel facilisis nibh. Ut ut diam a lacus sollicitudin posuere a vel arcu. Sed vehicula, enim sed bibendum ullamcorper, justo nisl finibus velit, eget hendrerit nunc felis et massa. Phasellus dignissim magna et turpis aliquam, vel facilisis purus viverra. Nunc volutpat libero id tortor pharetra, id tincidunt nisl egestas. Aenean nec bibendum odio. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Fusce mollis odio ut est blandit lobortis. Maecenas vitae nisl at elit fringilla auctor. Phasellus fermentum eros vel dolor sodales blandit.</p>
            <p>In sapien arcu, egestas et placerat eu, mattis sit amet libero. Nam tincidunt viverra pharetra. Aliquam at diam arcu. Aliquam eget diam vel odio scelerisque congue vitae ac lectus. Fusce aliquam metus elit, ut pulvinar turpis eleifend id. Integer ac tellus sagittis urna pharetra consequat a eget ligula. Nam congue quam eros, eu commodo orci commodo non. Praesent malesuada a ipsum ut rhoncus. In sed mi sed nunc dictum consequat sit amet non arcu. In et libero ut sem blandit mollis. Vestibulum nunc erat, molestie ut ullamcorper eu, eleifend nec nunc. Duis iaculis, odio non ultricies fringilla, dolor quam suscipit quam, vitae fringilla lorem nisl eget arcu.</p>
            <p>Interdum et malesuada fames ac ante ipsum primis in faucibus. Suspendisse potenti. Vivamus eget nunc quis quam dapibus laoreet at ut eros. Suspendisse pharetra interdum sem tincidunt bibendum. Fusce vel cursus mauris. Etiam blandit velit ac lorem euismod sodales id non magna. Donec turpis lacus, condimentum vel massa vulputate, facilisis auctor libero. In vel maximus dolor. Curabitur at sagittis erat.</p>
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
                <b-tab title="v0.09 | xx-08-2020">
                  <b-card-text>
                    <ul>
                      <li>Fix: Update report when task is finished / refresh list.</li>
                      <li>Fix: SUT selection is empty when adding new fuzzing projects.</li>
                      <li>Fix: Clear results for fuzzing project doesn't work correct.</li>
                      <li>TODO Refactor createdAt to constructors instead of factories.</li>
                    </ul>
                  </b-card-text>
                </b-tab>
                <b-tab title="v0.08 | 22-07-2020" active>
                  <b-card-text>
                    <ul>
                      <li>Add/execute initial version dictionary fuzzing.</li>
                      <li>Add/execute initial version model-based dictionary fuzzing.</li>                      
                    </ul>
                  </b-card-text>
                </b-tab>
                <b-tab title="v0.07 | 01-07-2020">
                  <b-card-text>
                    <ul>
                      <li>Add CRUD for reports (code coverage and responses status codes).</li>
                      <li>Relation between request and action to display action details with request.</li>
                      <li>Add favicon for the application.</li>
                    </ul>
                  </b-card-text>
                </b-tab>
                <b-tab title="v0.06 | 13-06-2020">
                  <b-card-text>
                    <ul>
                      <li>View all information with sequences for projects.</li>
                      <li>Improve (make consistent) viewing all request, response and sequence information with projects/SUTs.</li>
                      <li>Add isRequired (true/false/*) for parameters in configuration.</li>
                      <li>Default values in configuration.</li>
                      <li>Relation between request and action to display action details with request.</li>
                      <li>Use values from dependencies in sequence.</li>
                      <li>Fix. Sequences are not always valid.</li>
                    </ul>
                  </b-card-text>
                </b-tab>
                <b-tab title="v0.05 | 13-05-2020">
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
                <b-tab title="v0.04 | 22-04-2020">
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
                <b-tab title="v0.03 | 23-03-2020">
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
                <b-tab title="v0.02 | 19-02-2020">
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
                <b-tab title="v0.01 | 29-01-2020">
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