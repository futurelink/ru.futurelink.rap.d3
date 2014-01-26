/**
 * 
 */

var width, height, radius;

var color;
var arc;
var svg;

function initChart() {
	var remoteMargins = getMargins();

	margin = {top: remoteMargins[0], left: remoteMargins[1], bottom: remoteMargins[2], right: remoteMargins[3]};
	width = window.innerWidth - margin.left - margin.right - 5;
	height = window.innerHeight - margin.top - margin.bottom - 5;
	radius = Math.min(width, height) / 2;
	
	color = d3.scale.category10();
	arc = d3.svg.arc()
	    .outerRadius(radius - 20)
	    .innerRadius(radius / 2);
	
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

	var displayedRow = 1;

	var pie = d3.layout.pie()
    	.sort(null)
    	.value(function(d) { return d[displayedRow]; });
  
	var g = svg.selectAll(".arc")
    	.data(pie(data))
    	.enter().append("g")
    	.attr("class", "arc");

	g.append("path")
		.attr("d", arc)
		.style("fill", function(d) { return color(d.data[0]); });

	g.append("text")
		.attr("transform", function(d) { return "translate(" + arc.centroid(d) + ")"; })
		.attr("dy", ".15em")
		.style("text-anchor", "middle")
		.text(function(d) { return d.data[0]; });

}

function redrawData() {
	
}
