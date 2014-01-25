/**
 * 
 */
package ru.futurelink.rap.d3;

import java.util.List;

import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;

import ru.futurelink.rap.d3.internal.D3BrowserIntl;

/**
 * Data accessor to D3 component.
 * 
 * This accessor contains functions:
 *  - getDataRow
 *  - getDataRowCount
 *  - getDataItem
 *  - getDataItemCount
 * 
 * It provides access to plain two-dimensional data arrays only.
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
	
	public void initDataAccessFunctions() {
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
		return Integer.valueOf(((List<?>)getViewer().getInput()).size());
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
	public Object getDataItem(int rowIndex, int index) {
		if (rowIndex > getDataRowsCount()-1) {
			return Integer.valueOf(0);
		} else {
			return getDataRow(rowIndex).get(index);
		}
	}
	
}
