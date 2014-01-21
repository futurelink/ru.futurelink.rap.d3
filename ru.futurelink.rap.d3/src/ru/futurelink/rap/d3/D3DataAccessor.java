/**
 * 
 */
package ru.futurelink.rap.d3;

import java.util.List;

import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;

/**
 * Abstract data accessor to D3 component.
 * 
 * @author pavlov
 *
 */
public class D3DataAccessor {
	private D3BrowserIntl 	mViewer; 
	private Browser			mBrowser;
	
	/**
	 * 
	 */
	public D3DataAccessor(D3BrowserIntl viewer, Browser browser) {
		mViewer = viewer;
		mBrowser = browser;
	}
	
	protected D3BrowserIntl getViewer() {
		return mViewer;
	}
	
	public void createDataAccessFunctions() {

		new BrowserFunction(mBrowser, "getDataRow") {
	        @Override
	        public Object function( Object[] arguments ) {
	        	return getDataRow((int)arguments[0]);
	        }
		};

		new BrowserFunction(mBrowser, "getDataRowsCount") {
	        @Override
	        public Object function( Object[] arguments ) {
	        	return getDataRowsCount();
	        }
		};

		/**
		 * Get data element from data input array, usage:
		 * getDataItem(row, index)
		 */
		new BrowserFunction(mBrowser, "getDataItem") {
	        @Override
	        public Object function( Object[] arguments ) {
	        	return getDataItem((int)arguments[0], (int)arguments[1]);
	        }
		};

		new BrowserFunction(mBrowser, "getDataItemsCount") {
	        @Override
	        public Object function( Object[] arguments ) {
	        	return getDataItemsCount((int)arguments[0]);
	        }
		};		
	}
	
	/**
	 * Get data source row count.
	 * 
	 * @return
	 */
	public Integer getDataRowsCount() {
		return Integer.valueOf(1);
	}
	
	/**
	 * Get data source row contents.
	 * 
	 * @param rowIndex
	 * @return
	 */
	public Object getDataRow(int rowIndex) {
		return ((List<?>)getViewer().getInput()).toArray();
	}

	/**
	 * Get data items count in a row.
	 * 
	 * @param row
	 * @return
	 */
	public Integer getDataItemsCount(int row) {
		return Integer.valueOf(((List<?>)getViewer().getInput()).size());
	}

	/**
	 * Get data item contents from a row and with index.
	 * 
	 * @param rowIndex
	 * @param index
	 * @return
	 */
	public Object getDataItem(int rowIndex, int index) {
		return ((List<?>)((List<?>)getViewer().getInput()).get(index)).toArray();
	}
	
}
