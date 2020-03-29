<template>
  <div>
    <svg width="720" height="500" />
  </div>
</template>

<script>
import * as d3 from "d3";

export default {
  data() {
    return {};
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
      console.log(this.nodes);
      console.log(this.links);

      // console.log(this.nodes.length);
      // console.log(this.links.length);

      var svg = d3.select("svg"),
        width = +svg.attr("width"),
        height = +svg.attr("height");

      var color = d3.scaleOrdinal(d3["schemeDark2"]);

      var simulation = d3
        .forceSimulation()
        .force(
          "link",
          d3.forceLink().id(function(d) {
            return d.id;
          })
        )
        .force("charge", d3.forceManyBody())
        .force("center", d3.forceCenter(width / 2, height / 2));

      var all_links = svg
        .append("g")
        .attr("class", "links")
        .selectAll("line")
        .data(this.links)
        .enter()
        .append("line")
        .attr("stroke-width", function(d) {
          return Math.sqrt(d.value);
        });

      console.log(all_links);

      var all_nodes = svg
        .append("g")
        .attr("class", "nodes")
        .selectAll("g")
        .data(this.nodes)
        .enter()
        .append("g");

      console.log(all_nodes);

      var circles = all_nodes
        .append("circle")
        .attr("r", 5)
        .attr("fill", function(d) {
          return color(1); // TODO
        })
        .call(
          d3
            .drag()
            .on("start", dragstarted)
            .on("drag", dragged)
            .on("end", dragended)
        );

      var lables = all_nodes
        .append("text")
        .text(function(d) {
          return d.id;
        })
        .attr("x", 6)
        .attr("y", 3);

      all_nodes.append("title").text(function(d) {
        return d.id;
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

        all_nodes.attr("transform", function(d) {
          return "translate(" + d.x + "," + d.y + ")";
        });
      }

      function dragstarted(d) {
        if (!d3.event.active) simulation.alphaTarget(0.3).restart();
        d.fx = d.x;
        d.fy = d.y;
      }

      function dragged(d) {
        d.fx = d3.event.x;
        d.fy = d3.event.y;
      }

      function dragended(d) {
        if (!d3.event.active) simulation.alphaTarget(0);
        d.fx = null;
        d.fy = null;
      }

      console.log("DONE");
    }
  },
  mounted: function() {
    setTimeout(() => {
      this.drawIt();
    }, 7500);
  }
};
</script>

<style scoped>
.links line {
  stroke: #999;
  stroke-opacity: 0.6;
}

.nodes circle {
  stroke: #fff;
  stroke-width: 1.5px;
}

text {
  font-family: sans-serif;
  font-size: 10px;
}

#relations {
  border: 1px solid #dddddd;
  width: 720px;
  height: 500px;
  margin: auto;
}
</style>