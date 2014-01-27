/**
 * 
 */

var width, height, radius;

var color;
var arc, pie;
var svg;

var displayedRow = 1;
var path;

var labelRadius;
var nodata;

function initChart() {
	var remoteMargins = getMargins();

	margin = {top: remoteMargins[0], left: remoteMargins[1], bottom: remoteMargins[2], right: remoteMargins[3]};
	width = window.innerWidth - margin.left - margin.right - 5;
	height = window.innerHeight - margin.top - margin.bottom - 5;
	radius = Math.min(width, height) / 2;
	
	color = d3.scale.category10();
	arc = d3.svg.arc()
	    .outerRadius(1)
	    .innerRadius(1);
	
	labelRadius = radius - 10;

	svg = d3.select("body").append("svg")
		.attr("width", width)
		.attr("height", height)
		.append("g")
			.attr("transform", "translate(" + width / 2 + "," + height / 2 + ")");
}

function prepareData() {
	data = [];
	var rowCount = getDataRowsCount();				
	for (var rowIndex = 0; rowIndex < rowCount; rowIndex++) {
		var dataRow = [];
		var dataItemsCount = getDataItemsCount(rowIndex);
		for (var i = 0; i < dataItemsCount; i++) {
			dataRow.push(getDataItem(rowIndex, i));
		}
		data.push(dataRow);
	}

	labels = getLabels();		
}

function drawData() {

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
	
	displayedRow = 1;

	pie = d3.layout.pie()
    	.sort(null)
    	.value(function(d) { return d[displayedRow]; });
  
	var g = svg.selectAll(".arc")
    	.data(pie(data))
    	.enter().append("g")
    	.attr("class", "arc");

	path = g.append("path")	
		.attr({
			"d": arc, 
			"fill": function(d,i) { return color(d.data[0]); }
		});
    
	path.transition().duration(500).ease("bounce")
		.attr({
			"d" : arc.outerRadius(radius - 20).innerRadius(radius / 2)
		});

	g.append("text")
		.attr("transform", function(d) { 
			var c = arc.centroid(d); var x = c[0]; var y = c[1]; var h = Math.sqrt(x*x + y*y);
			return "translate(" + (x/h * labelRadius) +  ',' + (y/h * labelRadius) +  ")"; 
        }) 
		.attr("dy", ".15em")
		.attr("text-anchor", function(d) { return (d.endAngle + d.startAngle)/2 > Math.PI ? "end" : "start"; })
		.text(function(d) { return d.data[0]; });

}

function redrawData() {
	if (path != null) {
		path.transition()
			.duration(100)
			.attr({
				"d" : arc.outerRadius(1).innerRadius(1)
			})
			.remove();
	}

	svg.selectAll(".nodatatext").remove();
	svg.selectAll(".arc").data([]).exit().remove();

	drawData();
}

