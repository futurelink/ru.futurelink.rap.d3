/**
 * 
 */

var margin, width, height;

var x0;
var x1;
var y;
var color;

var xAxis;
var yAxis;
var svg;
var labels = [];

var nodata;

function initChart() {
	var remoteMargins = getMargins();
	margin = {top: remoteMargins[0], left: remoteMargins[1], bottom: remoteMargins[2], right: remoteMargins[3]};

	width = window.innerWidth - margin.left - margin.right - 5;
	height = window.innerHeight - margin.top - margin.bottom - 5;
	
	x0 = d3.scale.ordinal().rangeRoundBands([0, width], .1);
	x1 = d3.scale.ordinal();
	y = d3.scale.linear().range([height, 0]);

	color = d3.scale.category10();

	xAxis = d3.svg.axis()
    	.scale(x0)
    	.orient("bottom");

	yAxis = d3.svg.axis()
    	.scale(y)
    	.orient("left")
    	.tickFormat(d3.format(".2s"));

	svg = d3.select("body").append("svg")
    	.attr("width", width + margin.left + margin.right)
    	.attr("height", height + margin.top + margin.bottom)
    	.append("g")
    		.attr("transform", "translate(" + margin.left + "," + margin.top + ")");	
}

function prepareData() {	
	data = getDataArray();
	/*var rowCount = getDataRowsCount();				
	for (var rowIndex = 0; rowIndex < rowCount; rowIndex++) {
		var dataRow = [];
		var dataItemsCount = getDataItemsCount(rowIndex);
		for (var i = 0; i < dataItemsCount; i++) {
			dataRow.push(getDataItem(rowIndex, i));
		}
		data.push(dataRow);
	}*/

	labels = getLabels();
}

function drawData() {

	prepareData();
	
	// Draw NODATA chart if data is empty
	if (d3.keys(data).length == 0) {			
		nodata = svg.append("text")
			.attr("class", "nodatatext")
			.attr("transform", function(d) { return "translate(" + (width/2) + ", " + (height/2) + ");" })
			.attr("text-anchor", "middle")
			.text(getNoDataText());		
		return;
	} else {
		nodata = null;
	}
	
	var rowLabelIndices = d3.keys(labels).filter(function(key) { return key !== "0"; });
	var columns = d3.keys(data[0]);
	
	data.forEach(function(d) {
	    d.values = rowLabelIndices.map(function(index) { return {title: index, value: +d[index]}; });
	});

	x0.domain(data.map(function(d) { return d[0]; })); 					// Main X scale
	x1.domain(rowLabelIndices).rangeRoundBands([0, x0.rangeBand()]);	// Second X scale
	y.domain([0, d3.max(data, function(d) { return d3.max(d.values, function(d) { return d.value; }); })]);
	
	svg.append("g")
	      .attr("class", "x axis")
	      .attr("transform", "translate(0," + height + ")")
	      .call(xAxis);
	
	svg.append("g")
		.attr("class", "y axis")
	    .call(yAxis)
	    .append("text")
	    	.attr("transform", "rotate(-90)")
	    	.attr("y", 6)
	    	.attr("dy", ".71em")
	    	.style("text-anchor", "end")
	    	.text(getYAxisTitle());
	
	// Create group of bars
	var state = svg.selectAll(".state")
	      .data(data)
	    .enter().append("g")
	      .attr("class", "g")
	      .attr("transform", function(d) { return "translate(" + x0(d[0]) + ",0)"; });
	
	// Create bars in group
	state.selectAll("rect")
	      .data(function(d) { return d.values; })
	    .enter().append("rect")
	      .attr("width", x1.rangeBand())
	      .attr("x", function(d) { return x1(d.title); })
	      .attr("y", function(d) { return y(d.value); })
	      .attr("height", function(d) { return height - y(d.value); })
	      .style("fill", function(d) { return color(d.title); });

	  // Draw legend
	  var legend = svg.selectAll(".legend")
	      .data(rowLabelIndices)
	      .enter().append("g")
	      	.attr("class", "legend")
	      	.attr("transform", function(d, i) { return "translate(0," + i * 20 + ")"; });

	  legend.append("rect")
	      .attr("x", width - 18)
	      .attr("width", 18)
	      .attr("height", 18)
	      .style("fill", function(d) { return color(d); });

	  legend.append("text")
	      .attr("x", width - 24)
	      .attr("y", 9)
	      .attr("dy", ".35em")
	      .style("text-anchor", "end")
	      .text(function(d) { return labels[d]; });	
}

function redrawData() {
	
}

