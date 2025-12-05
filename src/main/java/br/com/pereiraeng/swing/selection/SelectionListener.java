package br.com.pereiraeng.swing.selection;

import java.util.EventListener;

/**
 * The listener interface for receiving selection events. The class that is
 * interested in processing an action event implements this interface, and the
 * object created with that class is registered with a component, using the
 * component's addSelectionListener method. When the action event occurs, that
 * object's actionPerformed method is invoked.
 * 
 * @author Philipe PEREIRA
 *
 */
public interface SelectionListener extends EventListener {

	/**
	 * Invoked when an object is selected
	 * 
	 * @param event
	 */
	public void selectElement(SelectionEvent event);

	/**
	 * Invoked when an object is unselected
	 * 
	 * @param event
	 */
	public void deselectElement(SelectionEvent event);
}
