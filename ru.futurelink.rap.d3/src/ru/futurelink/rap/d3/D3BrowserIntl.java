/**
 * 
 */
package ru.futurelink.rap.d3;

import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * @author pavlov
 *
 */
class D3BrowserIntl  extends Composite 
{		
	private static final long serialVersionUID = 1L;

	private Browser						mBrowser;
	private Object						mData;
	private boolean					mInitialized;
	private HashMap<String, String>		mDimensions;

	private D3DataAccessor				mAccessor;
	private ID3Chart					mChart;
	
	/**
	 * @param parent
	 */
	public D3BrowserIntl(Composite parent, ID3Chart chart) {
		super(parent, SWT.BORDER);

		mChart = chart;
		
		mDimensions = new HashMap<String, String>();
				
		setLayout(new FillLayout());
		mBrowser = new Browser(this, SWT.NONE);
		mAccessor = new D3DataAccessor(this, mBrowser);
		
		mBrowser.addProgressListener(new ProgressListener() {			
			private static final long serialVersionUID = 1L;

			@Override
			public void completed(ProgressEvent arg0) {
				mInitialized = true;
				mAccessor.createDataAccessFunctions();
				if(getInput() != null) {
					mBrowser.evaluate("prepareData();");
					mBrowser.evaluate("drawData();");					
				}
			}
			
			@Override
			public void changed(ProgressEvent arg0) {}
		});
		
		mBrowser.setText(getInitialScript());		
	}
	
	protected Object getInput() {
		return mData;
	}
	
	public void setInput(Object data) {
		mData = data;
	}

	public void refresh() {
		if ((mBrowser == null) || mBrowser.isDisposed()) return;
		if (mInitialized) {
			mBrowser.evaluate("prepareData()");
			mBrowser.evaluate("redrawData();");
		}
	}
	
	/**
	 * Add chert dimension for data binding.
	 * 
	 * @param title
	 * @param field
	 */
	public void addDimension(String title, String field) {
		mDimensions.put(field, title);
	}
	
	protected String getInitialScript() {
		return "<!DOCTYPE html><meta charset=\"utf-8\">\n"+
					mChart.getD3StyleSheet()+
				"</meta><body>\n"+
					"<script src=\"http://d3js.org/d3.v3.min.js\"></script>\n" +
					"<script>"+
					getD3InternalScript() +
					"</script>"+
				"</body>\n";
	}

	private String getD3InternalScript() {
		return 
			mChart.getD3InitialScript()	// Insert initial script (chart initialization)
			+ "function drawData() {" 
			+ mChart.getD3DrawData()		// Insert data drawing script
			+ "}"
			+ "function prepareData() {"
			+ mChart.getD3PrepareData()	// Insert data preparing script
			+ "}"
			+ "function redrawData() {"
			+ mChart.getD3RedrawData()		// Insert data updating script (animations etc.)
			+ "}";
	}
}
