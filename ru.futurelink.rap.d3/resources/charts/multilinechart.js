var data;

var margin, width, height;
var x,y,color,xAxis,yAxis;

var lines;

var line, svg;

function initChart() {
	var remoteMargins = getMargins();

	margin = {top: remoteMargins[0], left: remoteMargins[1], bottom: remoteMargins[2], right: remoteMargins[3]};
	width = window.innerWidth - margin.left - margin.right - 5;
	height = window.innerHeight - margin.top - margin.bottom - 5;

	x = d3.scale.ordinal();
	y = d3.scale.linear().range([height, 0]);
	color = d3.scale.category10();
	xAxis = d3.svg.axis().scale(x).orient("bottom");
	yAxis = d3.svg.axis().scale(y).orient("left");

	line = d3.svg.line().interpolate("basis")
		.x(function(d) { return x(d.x); })
		.y(function(d) { return y(d.y); });

	svg = d3.select("body").append("svg")
		.attr("width", width + margin.left + margin.right)
		.attr("height", height + margin.top + margin.bottom)
		.append("g")
		.attr("transform", "translate(" + margin.left + "," + margin.top + ")");	
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

	// Create color domain from data rows except header row
    color.domain(d3.keys(data).filter(function(key) { return key !== "0"; }));
 
    // Create line data
    lines = color.domain().map(function(rowNumber) {
    	return {
    		rowNumber: rowNumber,
    		points: d3.keys(data[0]).map(function(d) {
    			return {x: d3.values(data[0])[d], y: data[rowNumber][d]};
    		})
    	};
    });
}

function drawData() {
    // Create X domain from row indices
    var ordinalRange = d3.range(0, width, width / d3.set(data[0]).size());
    x.domain(data[0]).range(ordinalRange);

    var linearRange = [0, width];
    //x.domain(d3.extent(data[0], function(d) { return d; })).range(linearRange);
    
    y.domain([
		d3.min(lines, function(c) { return d3.min(c.points, function(v) { return v.y; }); }),
		d3.max(lines, function(c) { return d3.max(c.points, function(v) { return v.y; }); })
	]);

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

	svg.selectAll(".city")
		.data(lines)
		.enter().append("g")
		.attr("class","city")				
		.append("path")
			.attr("class", "line")
			.attr("d", function(d) { return line(d.points); })
			.style("stroke", function(d) { return color(d.rowNumber); });
}

function redrawData() {

}
