/**
 * 
 */
package ru.futurelink.rap.d3.charts;

import java.io.InputStream;

/**
 * @author pavlov
 *
 */
public class D3GroupedBarChart extends D3ScaledChart {

	@Override
	public InputStream getD3StyleSheet() {
		return this.getClass().getResourceAsStream("/charts/groupedbarchart.css");
	}

	@Override
	public InputStream getD3FullScript() {
		return this.getClass().getResourceAsStream("/charts/groupedbarchart.js");
	}

}
