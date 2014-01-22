/**
 * 
 */
package ru.futurelink.rap.d3;

import java.util.List;

import org.eclipse.jface.viewers.IContentProvider;

/**
 * @author pavlov
 *
 */
public interface ID3ContentProvider extends IContentProvider {
	/**
	 * Get list of rows used to display data, containing list of data arrays.
	 * This structure is only used to access data correctly.
	 * 
	 * @return
	 */
	public List<List<List<Object>>> getDataRows();
}
