/**
 * 
 */
package ru.futurelink.rap.d3.charts;

import java.io.InputStream;

/**
 * @author pavlov
 *
 */
public class D3MultiLineChart extends D3ScaledChart {

	/**
	 * 
	 */
	public D3MultiLineChart() {
		super();
	}

	@Override
	public InputStream getD3StyleSheet() {
		return this.getClass().getResourceAsStream("/charts/multilinechart.css");
	}

	@Override
	public InputStream getD3FullScript() {
		return this.getClass().getResourceAsStream("/charts/multilinechart.js");
	}
	
}
