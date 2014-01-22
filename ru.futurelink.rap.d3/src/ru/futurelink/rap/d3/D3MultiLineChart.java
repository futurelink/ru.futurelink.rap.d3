/**
 * 
 */
package ru.futurelink.rap.d3;

/**
 * @author pavlov
 *
 */
public class D3MultiLineChart implements ID3Chart {

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

		"var x = d3.time.scale().range([0, width]);" +
		"var y = d3.scale.linear().range([height, 0]);" +
		"var color = d3.scale.category10();" +
		"var xAxis = d3.svg.axis().scale(x).orient(\"bottom\");" +
		"var yAxis = d3.svg.axis().scale(y).orient(\"left\");" +

		"var line = d3.svg.line().interpolate(\"basis\")" +
			".x(function(d) { return x(d["+mDataIndexForX+"]); })" +
			".y(function(d) { return y(d["+mDataIndexForY+"]); });" +

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
				"body {" +
					"font: 10px sans-serif;" +
				"}" +

				".axis path," +
				".axis line {" +
					"fill: none;" +
					"stroke: #000;" +
					"shape-rendering: crispEdges;" +
				"}" +

				".x.axis path {" +
					"display: none;" +
				"}" +

				".line {" +
					"fill: none;" +
					"stroke: steelblue;" +
					"stroke-width: 1.5px;" +
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

				"var city = svg.selectAll(\".city\")" +
					".data(lines)"+
					".enter().append(\"g\")" +
					".attr(\"class\",\"city\");" +

				"city.append(\"path\")" +
					".attr(\"class\", \"line\")" +
					".attr(\"d\", function(d) { return line(d.values); })" +
					".style(\"stroke\", function(d) { return color(d.name); });"
				;
	}

	@Override
	public String getD3PrepareData() {
		return 	"data = []; "
				+ "var rowCount = getDataRowsCount();"				
				+ "for (var rowIndex = 0; rowIndex < rowCount; rowIndex++) {"
					+ "var dataRow = [];"
					+ "var dataItemsCount = getDataItemsCount(rowIndex);"
					+ "for (var i = 0; i < dataItemsCount; i++) {"
						+ "dataRow.push(getDataItem(rowIndex, i));"
					+ "}"
					+ "data.push(dataRow);"
				+ "}"
				
				+ "var lines = color.domain().map(function(name) {"	
					+ "return {"
						+  "name: name,"
						+ "values: data.map(function(d) {"
							+  "return [date: d.date, temperature: +d[name]];"	
						+"})"
					+"};"
				+"});"					
		;		
	}

	@Override
	public String getD3RedrawData() {
		return "";	
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
