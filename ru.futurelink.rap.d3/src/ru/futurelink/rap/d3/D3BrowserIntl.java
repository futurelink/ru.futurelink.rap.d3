/**
 * 
 */
package ru.futurelink.rap.d3;

import java.io.InputStream;
import java.util.Scanner;

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

	private D3DataAccessor				mAccessor;
	private ID3Chart					mChart;
	
	/**
	 * @param parent
	 */
	public D3BrowserIntl(Composite parent, ID3Chart chart) {
		super(parent, SWT.BORDER);

		setLayout(new FillLayout());
		mBrowser = new Browser(this, SWT.NONE);

		mChart = chart;
		mChart.setBrowser(mBrowser);
				
		mAccessor = new D3DataAccessor(this, mBrowser);
		
		mBrowser.addProgressListener(new ProgressListener() {			
			private static final long serialVersionUID = 1L;

			@Override
			public void completed(ProgressEvent arg0) {
				mInitialized = true;

				mChart.initBrowserFunctions();
				mAccessor.initDataAccessFunctions();

				if(getInput() != null) {
					mBrowser.evaluate("initChart();");
					mBrowser.evaluate("prepareData();");
					mBrowser.evaluate("drawData();");					
				}
			}
			
			@Override
			public void changed(ProgressEvent arg0) {}
		});
		
		mBrowser.setText(getInitialScript());		
	}

	@SuppressWarnings("resource")
	private String readScript() {
		InputStream s = mChart.getD3FullScript();
		return new Scanner(s,"UTF-8").useDelimiter("\\A").next();				
	}

	@SuppressWarnings("resource")
	private String readStyle() {
		InputStream s = mChart.getD3StyleSheet();
		return new Scanner(s,"UTF-8").useDelimiter("\\A").next();				
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
	
	protected String getInitialScript() {
		return "<!DOCTYPE html><meta charset=\"utf-8\">\n" +
				"<style>\n" +
					readStyle() +
				"</style>\n" +
				"</meta><body>\n" +
					"<script src=\"http://d3js.org/d3.v3.min.js\"></script>\n" +
					"<script>\n" +
						readScript() +
					"</script>\n" +
				"</body>\n";
	}

}
