/**
 * 
 */
package ru.futurelink.rap.d3.charts;

import java.io.InputStream;

/**
 * @author pavlov
 *
 */
public class D3BarChart extends D3ScaledChart {
	public D3BarChart() {
		super();
	}

	@Override
	public InputStream getD3StyleSheet() {
		return this.getClass().getResourceAsStream("/charts/barchart.css");
	}

	@Override
	public InputStream getD3FullScript() {
		return this.getClass().getResourceAsStream("/charts/barchart.js");
	}

}
