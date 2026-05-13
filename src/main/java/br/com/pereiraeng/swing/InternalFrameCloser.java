package br.com.pereiraeng.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class InternalFrameCloser extends InternalFrameAdapter {

	public static final String CLOSED = "CLOSED";

	private final ActionListener listener;

	public InternalFrameCloser(ActionListener listener) {
		this.listener = listener;
	}

	@Override
	public void internalFrameClosed(InternalFrameEvent e) {
		this.listener.actionPerformed(new ActionEvent(e.getInternalFrame(), ActionEvent.ACTION_PERFORMED, "CLOSED"));
	}

}
