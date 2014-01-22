/**
 * 
 */
package ru.futurelink.rap.d3;

/**
 * @author pavlov
 *
 */
public interface ID3Chart {
	
	public void setMargins(Integer marginTop, Integer marginLeft, Integer marginBotton, Integer marginRight);
	
	public String getD3PrepareData();	
	public String getD3DrawData();
	public String getD3RedrawData();
	public String getD3InitialScript();
	public String getD3StyleSheet();
}
