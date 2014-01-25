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
	
	public void setMargins(Integer marginTop, Integer marginLeft, Integer marginBotton, Integer marginRight);
	public void setBrowser(Browser browser);
	public void initBrowserFunctions();

	public InputStream getD3StyleSheet();
	public InputStream getD3FullScript();
}
