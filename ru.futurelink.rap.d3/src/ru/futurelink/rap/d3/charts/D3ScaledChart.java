/**
 * 
 */
package ru.futurelink.rap.d3.charts;

import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;

import ru.futurelink.rap.d3.ID3Chart;

/**
 * @author pavlov
 *
 */
public abstract class D3ScaledChart implements ID3Chart {
	private String mYAxisTitle;
	private String mNoDataText = "No data for period";

	private Integer mMarginTop = 10;
	private Integer mMarginBottom = 10;
	private Integer mMarginLeft = 10;
	private Integer mMarginRight = 10;
	
	private Browser mBrowser;

	@Override
	public void setMargins(Integer marginTop, Integer marginLeft,
			Integer marginBotton, Integer marginRight) {
		mMarginTop = marginTop;
		mMarginBottom = marginBotton;
		mMarginLeft = marginLeft;
		mMarginRight = marginRight;
	}
	
	@Override
	public void setBrowser(Browser browser) {
		mBrowser = browser;
	}

	public void setYAxisTitle(String title) {
		mYAxisTitle = title;
	}

	public void setNoDataText(String text) {
		mNoDataText = text;
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
		
		new BrowserFunction(mBrowser, "getNoDataText") {
	        @Override
	        public Object function( Object[] arguments ) {
	        	return mNoDataText;
	        }
		};		
	}

}
