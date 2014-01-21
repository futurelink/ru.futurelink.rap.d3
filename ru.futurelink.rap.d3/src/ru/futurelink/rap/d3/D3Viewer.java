/**
 * 
 */
package ru.futurelink.rap.d3;

import org.eclipse.jface.viewers.CellEditor.LayoutData;
import org.eclipse.jface.viewers.ContentViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * @author pavlov
 *
 */
public class D3Viewer 
	extends ContentViewer 
{
	private static final long serialVersionUID = 1L;
		
	private D3BrowserIntl			mBrowser;

	public D3Viewer(Composite composite, ID3Chart chart) {
		super();
		
		mBrowser = new D3BrowserIntl(composite, chart);	
	}
	
	public void setLayoutData(GridData layoutData) {
		getControl().setLayoutData(layoutData);
	}
	
	public void setLayoutData(LayoutData layoutData) {
		getControl().setLayoutData(layoutData);
	}

	@Override
	protected void inputChanged(Object input, Object oldInput) {
		super.inputChanged(input, oldInput);
		
		mBrowser.setInput(input);		
	}
	
	public void addDimension(String title, String label) {
		mBrowser.addDimension(title, label);
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
		mBrowser.refresh();
	}

	@Override
	public void setSelection(ISelection arg0, boolean arg1) {
		
	}	
}
