package br.com.pereiraeng.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionResult {

	private final int id;

	private final ActionListener listener;

	private final Object source;

	public ActionResult(int id, ActionListener listener, int state, Object source) {
		this.id = id;
		this.listener = listener;
		this.state = state;
		this.source = source;
	}

	public int getId() {
		return id;
	}

	private transient int state;

	public void setState(int state) {
		this.state = state;
	}

	public int getState() {
		return state;
	}

	public Object getSource() {
		return source;
	}

	public void actionPerformed(ActionEvent event) {
		this.listener.actionPerformed(new ActionEvent(this, event.getID(), event.getActionCommand()));
	}
}
