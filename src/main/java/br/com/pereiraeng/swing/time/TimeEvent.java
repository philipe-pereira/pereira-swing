package br.com.pereiraeng.swing.time;

import java.util.Calendar;
import java.util.EventObject;

/**
 * TimeEvent is used to notify interested parties that a period of time has been
 * passed since another event has happened in the source.
 * 
 * @author Philipe PEREIRA
 *
 */
public class TimeEvent extends EventObject {
	private static final long serialVersionUID = 1L;

	private Calendar time;

	private String actionCommand;

	public TimeEvent(Object source, Calendar time, String actionCommand) {
		super(source);
		this.time = time;
		this.actionCommand = actionCommand;
	}

	public Calendar getTime() {
		return time;
	}

	public String getActionCommand() {
		return actionCommand;
	}
}
