/**
 * 
 */
package ru.futurelink.rap.d3;

/**
 * @author pavlov
 *
 */
public class D3BarChart implements ID3Chart {
	public D3BarChart() {}

	private Integer mMarginTop = 10;
	private Integer mMarginBottom = 10;
	private Integer mMarginLeft = 10;
	private Integer mMarginRight = 10;
	
	@SuppressWarnings("unused")
	private String mXAxisText = "X axis";
	private String mYAxisText = "Y axis";
	
	private Integer mDataIndexForX = 0;
	private Integer mDataIndexForY = 1;
	
	@Override
	public String getD3InitialScript() {
		return "var data; " +
		"var margin = {top: "+mMarginTop+", right: "+mMarginRight+", bottom: "+mMarginBottom+", left: "+mMarginLeft+"};" +
		"var width = window.innerWidth - margin.left - margin.right - 5;" +
		"var height = window.innerHeight - margin.top - margin.bottom - 5;" +

		"var x = d3.scale.ordinal()" +
			".rangeRoundBands([0, width], .1);" +

		"var y = d3.scale.linear()"+
			".range([height, 0]);"+

		"var xAxis = d3.svg.axis()"+
			".scale(x)"+
			".orient(\"bottom\");"+

		"var yAxis = d3.svg.axis()"+
			".scale(y)"+
			".orient(\"left\")"+
			".ticks(10, \"%\");"+

		"var svg = d3.select(\"body\").append(\"svg\")"+
			".attr(\"width\", width + margin.left + margin.right)"+
			".attr(\"height\", height + margin.top + margin.bottom)"+
			".append(\"g\")"+
			".attr(\"transform\", \"translate(\" + margin.left + \",\" + margin.top + \")\");"+

		"\n\n";
	}

	@Override
	public String getD3StyleSheet() {
		return "<style>" +
				".bar {" +
					"fill: steelblue;" +
				"}" +
				".bar:hover {"+
					"fill: brown;"+
				"}"+
				".axis {" +
					"font: 10px sans-serif;" +
				"}" +
				".axis path," +
				".axis line {" +
					"fill: none;" +
					"stroke: #000;" +
					"shape-rendering: crispEdges;"+
				"}" +
				".x.axis path {" +
					"display: none;" +
				"}" +
				"</style>\n\n";
	}

	@Override
	public String getD3DrawData() {
		return

				"x.domain(data.map(function(d) { return d["+mDataIndexForX+"]; }));"+
				"y.domain([0, d3.max(data, function(d) { return d["+mDataIndexForY+"]; })]);"+
		
  				"svg.append(\"g\")"+
  					".attr(\"class\", \"x axis\")"+
  					".attr(\"transform\", \"translate(0,\" + height + \")\")"+
  					".call(xAxis);"+

  				"svg.append(\"g\")"+
  					".attr(\"class\", \"y axis\")"+
  					".call(yAxis)"+
  					".append(\"text\")"+
  					".attr(\"transform\", \"rotate(-90)\")"+
  					".attr(\"y\", 6)"+
  					".attr(\"dy\", \".71em\")"+
  					".style(\"text-anchor\", \"end\")"+
  					".text(\""+mYAxisText+"\");"+

  				"svg.selectAll(\".bar\")"+
  					".data(data)"+
  					".enter().append(\"rect\")"+
  					".attr(\"class\", \"bar\")"+
  					".attr(\"x\", function(d) { return x(d["+mDataIndexForX+"]); })"+
  					".attr(\"width\", x.rangeBand())"+
  					".attr(\"y\", function(d) { return y(d["+mDataIndexForY+"]); })"+
  					".attr(\"height\", function(d) { return height - y(d["+mDataIndexForY+"]); });";	
	}

	@Override
	public String getD3PrepareData() {
		return 	"data = [];"				
				+ "var dataItemsCount = getDataItemsCount(0);"
				+ "for (var i = 0; i < dataItemsCount; i++) {"
				+ "data.push(getDataItem(0, i));"
				+ "}"
				;		
	}

	@Override
	public String getD3RedrawData() {
		return 	"svg.selectAll(\"rect\")"+
	  			".data(data)"+
	  			".transition()"+
	  			".duration(500)"+
	  			".attr(\"class\", \"bar\")"+
	  			".attr(\"x\", function(d) { return x(d["+mDataIndexForX+"]); })"+
	  			".attr(\"width\", x.rangeBand())"+
	  			".attr(\"y\", function(d) { return y(d["+mDataIndexForY+"]); })"+
	  			".attr(\"height\", function(d) { return height - y(d["+mDataIndexForY+"]); });";	
	}

	@Override
	public void setMargins(Integer marginTop, Integer marginLeft,
			Integer marginBotton, Integer marginRight) {
		mMarginTop = marginTop;
		mMarginBottom = marginBotton;
		mMarginLeft = marginLeft;
		mMarginRight = marginRight;
	}
	
	public void setYAxisText(String text) {
		mYAxisText = text;
	}
	
	public void setXAxisText(String text) {
		mXAxisText = text;
	}
}
