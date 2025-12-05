package br.com.pereiraeng.swing.time;

import java.util.EventListener;

/**
 * The listener interface for receiving time events. The class that is
 * interested in processing an time event implements this interface, and the
 * object created with that class is registered with a component, using the
 * component's addTimeListener method. When the time elapses, that object's
 * timeElapsed method is invoked.
 * 
 * @author Philipe PEREIRA
 *
 */
public interface TimeListener extends EventListener {

	/**
	 * Invoked when the time elapses.
	 */
	public void timeElapsed(TimeEvent event);
}
