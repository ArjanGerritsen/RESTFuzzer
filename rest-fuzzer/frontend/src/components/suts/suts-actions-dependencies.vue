<template>
  <svg id="relations" width="960" height="600" />
</template>

<script>
import * as d3 from "d3";

export default {
  data() {
    return {
      svg_links: null
    };
  },
  computed: {
    nodes: function() {
      return this.$store.getters.sutNodes;
    },
    links: function() {
      return this.$store.getters.sutLinks;
    }
  },
  methods: {
    drawIt: function() {
      var svg = d3.select("#relations");

      var width = 720;
      var height = 600;
      var radius = 6;

      var color = d3.scaleOrdinal(d3["schemeDark2"]);

      svg.attr("viewBox", [0, 0, width, height]);
      svg.call(
        d3
          .zoom()
          .extent([
            [0, 0],
            [width, height]
          ])
          .scaleExtent([1, 8])
          .on("zoom", zoomed)
      );

      const g = svg.append("g").attr("cursor", "grab");

      let simulation = d3
        .forceSimulation()
        .force(
          "link",
          d3.forceLink().id(function(d) {
            return d.id;
          })
        )
        .force("charge", d3.forceManyBody().strength(-50))
        .force("x", d3.forceX(width / 2).strength(0.05))
        .force("y", d3.forceY(height / 2).strength(0.05))
        .force("center", d3.forceCenter(width / 2 - 50, height / 2));

      var all_links = g
        .append("g")
        .attr("class", "links")
        .selectAll("line")
        .data(this.links)
        .enter()
        .append("line")
        .attr("stroke", d3.rgb(0, 0, 0));

      var all_nodes = g
        .append("g")
        .attr("class", "nodes")
        .selectAll("g")
        .data(this.nodes)
        .enter()
        .append("g");

      var circles = all_nodes
        .append("circle")
        .attr("r", radius)
        .attr("fill", function(d) {
          return colorForHttpMethod(d.httpMethod);
        })
        .call(
          d3
            .drag()
            .on("start", dragstarted)
            .on("drag", dragged)
            .on("end", dragended)
        );

      var labels = all_nodes
        .append("text")
        .text(function(d) {
          return d.title;
        })
        .attr("x", 6)
        .attr("y", 3);

      all_nodes.append("title").text(function(d) {
        return `hoi ${d.id}`;
      });

      simulation.nodes(this.nodes).on("tick", ticked);
      simulation.force("link").links(this.links);

      function ticked() {
        all_links
          .attr("x1", function(d) {
            return d.source.x;
          })
          .attr("y1", function(d) {
            return d.source.y;
          })
          .attr("x2", function(d) {
            return d.target.x;
          })
          .attr("y2", function(d) {
            return d.target.y;
          });

        all_nodes
          .attr("transform", function(d) {
            return "translate(" + d.x + "," + d.y + ")";
          })
          .attr("cx", function(d) {
            return (d.x = Math.max(radius, Math.min(width - radius, d.x)));
          })
          .attr("cy", function(d) {
            return (d.y = Math.max(radius, Math.min(height - radius, d.y)));
          });
      }

      function dragstarted(d) {
        // if (!d3.event.active) simulation.alphaTarget(0.3).restart();
        d.fx = d.x;
        d.fy = d.y;
      }

      function dragged(d) {
        d.fx = d3.event.x;
        d.fy = d3.event.y;
      }

      function dragended(d, simulation) {
        // if (!d3.event.active) simulation.alphaTarget(0);
        d.fx = null;
        d.fy = null;
      }

      function colorForHttpMethod(httpMethod) {
        switch (httpMethod) {
          case "GET":
            return d3.rgb(0, 255, 0);
          case "POST":
            return d3.rgb(0, 255, 255);
          case "PATCH":
            return d3.rgb(0, 128, 128);
          case "PUT":
            return d3.rgb(0, 255, 128);
          case "DELETE":
            return d3.rgb(255, 0, 0);
          default:
            return d3.rgb(255, 255, 0);
        }
      }

      function zoomed() {
        g.attr("transform", d3.event.transform);
      }
    }
  },
  mounted: function() {
    setTimeout(() => {
      this.drawIt();
    }, 1500);
  }
};
</script>

<style>
#relation rect .get {
  stroke: #000;
  stroke-width: 1;
}

#relations {
  font-family: "Courier New", Courier, monospace;
  font-size: 0.8em;
  border: 1px solid #dddddd;
  width: 720px;
  height: 600px;
  margin: auto;
}
</style>