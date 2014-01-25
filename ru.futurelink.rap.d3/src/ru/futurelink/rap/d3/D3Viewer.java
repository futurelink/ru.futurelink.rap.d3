/**
 * 
 */
package ru.futurelink.rap.d3;

import java.util.List;

import org.eclipse.jface.viewers.CellEditor.LayoutData;
import org.eclipse.jface.viewers.ContentViewer;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import ru.futurelink.rap.d3.internal.D3BrowserIntl;

/**
 * This is a base viewer class to display D3 charts.
 * 
 * @author pavlov
 *
 */
public class D3Viewer 
	extends ContentViewer 
{
	private static final long serialVersionUID = 1L;
		
	private D3BrowserIntl				mBrowser;
	private List<List<List<Object>>>	mViewData;

	public D3Viewer(Composite composite, ID3Chart chart) {
		super();
		
		mBrowser = new D3BrowserIntl(composite, chart);	
	}
	
	@Override
	public void setContentProvider(IContentProvider contentProvider) {
		if (ID3ContentProvider.class.isAssignableFrom(contentProvider.getClass())) {
			super.setContentProvider(contentProvider);
		} else {
			throw new UnsupportedOperationException("Can't use plain IContentProvider in D3Viewer, use ID3ContentProvider instead.");
		}
	}

	/**
	 * Sets layout data to assigned control element.
	 * 
	 * @param layoutData
	 */
	public void setLayoutData(GridData layoutData) {
		getControl().setLayoutData(layoutData);
	}
	
	/**
	 * Sets layout data to assigned control element.
	 * 
	 * @param layoutData
	 */
	public void setLayoutData(LayoutData layoutData) {
		getControl().setLayoutData(layoutData);
	}

	@Override
	protected void inputChanged(Object input, Object oldInput) {
		super.inputChanged(input, oldInput);
		
		mViewData = ((ID3ContentProvider)getContentProvider()).getDataRows();		
		mBrowser.setInput(mViewData);		
	}
	
	@Override
	public Control getControl() {
		return mBrowser;
	}


	@Override
	public ISelection getSelection() {
		return null;
	}


	@Override
	public void refresh() {
		mViewData = ((ID3ContentProvider)getContentProvider()).getDataRows();
		mBrowser.refresh();
	}

	@Override
	public void setSelection(ISelection arg0, boolean arg1) {
		
	}	
}
