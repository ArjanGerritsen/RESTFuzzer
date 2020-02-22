<template>
  <div>
    <div class="row">
      <div class="col-8">
        <b-card header-tag="header">
          <span slot="header">
            <b-icon icon="list-task" font-scale="1"></b-icon>
            &nbsp;About
          </span>
          <b-card-text>
            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque dapibus tellus ut leo rhoncus vestibulum. Integer vel facilisis nibh. Ut ut diam a lacus sollicitudin posuere a vel arcu. Sed vehicula, enim sed bibendum ullamcorper, justo nisl finibus velit, eget hendrerit nunc felis et massa. Phasellus dignissim magna et turpis aliquam, vel facilisis purus viverra. Nunc volutpat libero id tortor pharetra, id tincidunt nisl egestas. Aenean nec bibendum odio. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Fusce mollis odio ut est blandit lobortis. Maecenas vitae nisl at elit fringilla auctor. Phasellus fermentum eros vel dolor sodales blandit.</p>
            <p>In sapien arcu, egestas et placerat eu, mattis sit amet libero. Nam tincidunt viverra pharetra. Aliquam at diam arcu. Aliquam eget diam vel odio scelerisque congue vitae ac lectus. Fusce aliquam metus elit, ut pulvinar turpis eleifend id. Integer ac tellus sagittis urna pharetra consequat a eget ligula. Nam congue quam eros, eu commodo orci commodo non. Praesent malesuada a ipsum ut rhoncus. In sed mi sed nunc dictum consequat sit amet non arcu. In et libero ut sem blandit mollis. Vestibulum nunc erat, molestie ut ullamcorper eu, eleifend nec nunc. Duis iaculis, odio non ultricies fringilla, dolor quam suscipit quam, vitae fringilla lorem nisl eget arcu.</p>
            <p>Interdum et malesuada fames ac ante ipsum primis in faucibus. Suspendisse potenti. Vivamus eget nunc quis quam dapibus laoreet at ut eros. Suspendisse pharetra interdum sem tincidunt bibendum. Fusce vel cursus mauris. Etiam blandit velit ac lorem euismod sodales id non magna. Donec turpis lacus, condimentum vel massa vulputate, facilisis auctor libero. In vel maximus dolor. Curabitur at sagittis erat.</p>
          </b-card-text>
        </b-card>        
      </div>
      <div class="col-4">
        <b-card header-tag="header">
          <span slot="header">
            <b-icon icon="list-task" font-scale="1"></b-icon>
            &nbsp;Changelog
          </span>
          <b-card-text>
            <b-list-group>
            <b-list-group-item href="#" disabled class="flex-column align-items-start">
            <div class="d-flex w-100 justify-content-between">
              <h5 class="mb-1">Release v0.03</h5>
              <small class="text-muted">xx-03-2020</small>
            </div>
            <p class="mb-1">
              <ul>
                <li>TODO Finialize (simple) fuzzing task (displaying requests and responses).</li>
                <li>TODO Start Creating dependencies between REST actions (graph)</li>
                <li>TODO Fix: Display Fuzzing project detail correctly.</li>
                <li>TODO Fix: Use of EntityGraph for FuzProject.</li>
              </ul>
            </p>
          </b-list-group-item>
          <b-list-group-item href="#" disabled class="flex-column align-items-start">
          <div class="d-flex w-100 justify-content-between">
            <h5 class="mb-1">Release v0.02</h5>
            <small class="text-muted">19-02-2020</small>
          </div>
          <p class="mb-1">
            <ul>
              <li>Display REST model description information.</li>
              <li>Backend validations.</li>
              <li>Start (simple) fuzzing task.</li>
              <li>Fix: Delete SUT.</li>
              <li>Fix: Lazy loading for relations.</li>
              <li>Fix: Apply store pattern correctly.</li>
            </ul>
          </p>
        </b-list-group-item>
              <b-list-group-item href="#" disabled class="flex-column align-items-start">
                <div class="d-flex w-100 justify-content-between">
                  <h5 class="mb-1">Release v0.01</h5>
                  <small class="text-muted">29-01-2020</small>
                </div>
                <p class="mb-1">
                  <ul>
                    <li>Show SUTs overview + Start task.</li>
                    <li>Extractor task (Parse OAS information, save to MySQL database).</li>
                    <li>Background task system.</li>
                  </ul>
                </p>
              </b-list-group-item>
            </b-list-group>              
          </b-card-text>
        </b-card>
      </div>     
    </div>
  </div>
</template>