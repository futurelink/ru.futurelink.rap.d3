/**
 * 
 */
package ru.futurelink.rap.d3.charts;

import java.io.InputStream;

import org.eclipse.swt.browser.BrowserFunction;

/**
 * @author pavlov
 *
 */
public class D3PieChart extends D3ScaledChart {

	private String mCenterText = "";
	
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

	public void setCenterText(String centerText) {
		mCenterText = centerText;
	}
	
	@Override
	public void initBrowserFunctions() {
		super.initBrowserFunctions();
	
		new BrowserFunction(mBrowser, "getCenterText") {
	        @Override
	        public Object function( Object[] arguments ) {
	        	return mCenterText;
	        }
		};				
	}
}
