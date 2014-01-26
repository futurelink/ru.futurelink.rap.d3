/**
 * 
 */
package ru.futurelink.rap.d3.charts;

import java.io.InputStream;

/**
 * @author pavlov
 *
 */
public class D3PieChart extends D3ScaledChart {

	/**
	 * 
	 */
	public D3PieChart() {
		super();
	}

	@Override
	public InputStream getD3StyleSheet() {
		return this.getClass().getResourceAsStream("/charts/piechart.css");
	}

	@Override
	public InputStream getD3FullScript() {
		return this.getClass().getResourceAsStream("/charts/piechart.js");
	}

}
