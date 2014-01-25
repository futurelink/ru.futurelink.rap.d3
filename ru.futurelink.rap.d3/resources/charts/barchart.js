var data;

var margin;
var width, height;

var x,y;
var xAxis, yAxis;

var svg = d3.select("body").append("svg")
	.attr("width", width + margin.left + margin.right)
	.attr("height", height + margin.top + margin.bottom)
	.append("g")
	.attr("transform", "translate(" + margin.left + "," + margin.top + ")");

var bars;

function initChart() {
	var remoteMargins = getMargins();
	margin = {top: remoteMargins[0], left: remoteMargins[1], bottom: remoteMargins[2], right: remoteMargins[3]};
	
	width = window.innerWidth - margin.left - margin.right - 5;
	height = window.innerHeight - margin.top - margin.bottom - 5;

	x = d3.scale.ordinal();
	y = d3.scale.linear().range([height, 0]);
	
	xAxis = d3.svg.axis().scale(x).orient("bottom");
	yAxis = d3.svg.axis().scale(y).orient("left");
}

function prepareData() {
	data = [];
	var rowCount = getDataRowsCount();				
	for (var rowIndex = 0; rowIndex < rowCount; rowIndex++) {
		var dataRow = [];
		var dataItemsCount = getDataItemsCount(rowIndex);
		for (var i = 0; i < dataItemsCount; i++) {
			dataRow.push(getDataItem(rowIndex, i)[1]);
		}
		data.push(dataRow);
	}

    // Create bar data
    bars = d3.keys(data)
    	.filter(function(key) { return key !== "0"; })
    	.map(function(rowNumber) {
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
    x.domain(data[0]).rangeRoundBands([0, width], .1);

    var linearRange = [0, width];
    //x.domain(d3.extent(data[0], function(d) { return d; })).range(linearRange);
   
    y.domain([
		0,
		d3.max(bars[0].points, function(v) { return v.y; })
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
	
	svg.selectAll(".bar")
		.data(bars[0].points)
		.enter().append("rect")
		.attr("class", "bar")
		.attr("x", function(d) { return x(d.x); })
		.attr("width", x.rangeBand())
		.attr("y", function(d) { alert("y: " + y(d.y)+", height: "+(height-y(d.y))); return y(d.y); })
		.attr("height", function(d) { return height - y(d.y); });
}

function redrawData() {
	// Recalculate domain
    y.domain([0, d3.max(bars[0].points, function(v) { return v.y; })]);

    // Modify axis
	svg.selectAll("y axis")
		.attr("class", "y axis")
		.call(yAxis)
		.append("text")
		.attr("transform", "rotate(-90)")
		.attr("y", 6)
		.attr("dy", ".71em")
		.style("text-anchor", "end")
		.text("Values");

	// Modify data
	svg.selectAll("rect")
		.data(bars[0].points)
		.transition()
		.duration(500)
		.attr("class", "bar")
		.attr("x", function(d) { return x(d.x); })
		.attr("width", x.rangeBand())
		.attr("y", function(d) { return y(d.y); })
		.attr("height", function(d) { return height - y(d.y); });		
}
