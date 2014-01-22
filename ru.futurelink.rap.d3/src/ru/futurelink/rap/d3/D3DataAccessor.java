/**
 * 
 */
package ru.futurelink.rap.d3;

import java.util.ArrayList;
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
	        	return getDataRow((int)arguments[0]).toArray();
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
	        	return getDataItem((int)arguments[0], (int)arguments[1]).toArray();
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
		return ((List<?>)getViewer().getInput()).size();
	}
	
	/**
	 * Get data source row contents.
	 * 
	 * @param rowIndex
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<List<?>> getDataRow(int rowIndex) {
		return ((List<List<?>>)((List<?>)getViewer().getInput()).get(rowIndex));
	}

	/**
	 * Get data items count in a row.
	 * 
	 * @param row
	 * @return
	 */
	public Integer getDataItemsCount(int row) {
		if (row > getDataRowsCount()-1) {
			return Integer.valueOf(0);
		} else {
			return Integer.valueOf(getDataRow(row).size());
		}
	}

	/**
	 * Get data item contents from a row and with index.
	 * 
	 * @param rowIndex
	 * @param index
	 * @return
	 */
	public List<?> getDataItem(int rowIndex, int index) {
		if (rowIndex > getDataRowsCount()-1) {
			return new ArrayList<List<Object>>();
		} else {
			return ((List<?>)(getDataRow(rowIndex)).get(index));
		}
	}
	
}
