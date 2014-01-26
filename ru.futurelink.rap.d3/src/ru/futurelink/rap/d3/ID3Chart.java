/**
 * 
 */
package ru.futurelink.rap.d3;

import java.io.InputStream;

import org.eclipse.swt.browser.Browser;

/**
 * @author pavlov
 *
 */
public interface ID3Chart {

	// D3 color scale definitions
	public static String D3_COLOR_SCALE_CATEGORY10 = "Category10";
	public static String D3_COLOR_SCALE_CATEGORY20 = "Category20";
	public static String D3_COLOR_SCALE_CATEGORY20B = "Category20b";
	public static String D3_COLOR_SCALE_CATEGORY20C = "Category20c";

	public void setMargins(Integer marginTop, Integer marginLeft, Integer marginBotton, Integer marginRight);
	public void setBrowser(Browser browser);
	public void initBrowserFunctions();

	public InputStream getD3StyleSheet();
	public InputStream getD3FullScript();
}
