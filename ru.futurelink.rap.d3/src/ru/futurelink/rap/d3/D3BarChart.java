/**
 * 
 */
package ru.futurelink.rap.d3;

import java.io.InputStream;

import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;

/**
 * @author pavlov
 *
 */
public class D3BarChart implements ID3Chart {
	public D3BarChart() {}

	private String mYAxisTitle;

	private Integer mMarginTop = 10;
	private Integer mMarginBottom = 10;
	private Integer mMarginLeft = 10;
	private Integer mMarginRight = 10;
	
	private Browser mBrowser;

	@Override
	public InputStream getD3StyleSheet() {
		return this.getClass().getResourceAsStream("/resources/charts/barchart.css");
	}

	@Override
	public void setMargins(Integer marginTop, Integer marginLeft,
			Integer marginBotton, Integer marginRight) {
		mMarginTop = marginTop;
		mMarginBottom = marginBotton;
		mMarginLeft = marginLeft;
		mMarginRight = marginRight;
	}

	@Override
	public InputStream getD3FullScript() {
		return this.getClass().getResourceAsStream("/resources/charts/barchart.js");
	}

	@Override
	public void setBrowser(Browser browser) {
		mBrowser = browser;
	}

	public void setYAxisTitle(String title) {
		mYAxisTitle = title;
	}
	
	@Override
	public void initBrowserFunctions() {
		new BrowserFunction(mBrowser, "getYAxisTitle") {
	        @Override
	        public Object function( Object[] arguments ) {
	        	return mYAxisTitle;
	        }
		};
		
		new BrowserFunction(mBrowser, "getMargins") {
	        @Override
	        public Object function( Object[] arguments ) {

	        	Integer margins[] = new Integer[4];
	        	margins[0] = mMarginTop;
	        	margins[1] = mMarginLeft;
	        	margins[2] = mMarginBottom;
	        	margins[3] = mMarginRight;
	        	
	        	return margins;
	        }
		};
	}
}
