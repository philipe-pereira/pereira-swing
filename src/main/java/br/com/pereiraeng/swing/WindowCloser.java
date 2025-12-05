package br.com.pereiraeng.swing;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class WindowCloser extends InternalFrameAdapter implements WindowListener {
	private App app;

	public WindowCloser(App app) {
		this.app = app;
	}

	public void internalFrameClosed(InternalFrameEvent event) {
		this.app.close();
	}

	@Override
	public void windowClosed(WindowEvent event) {
		this.app.close();
	}

	@Override
	public void windowActivated(WindowEvent event) {
	}

	@Override
	public void windowClosing(WindowEvent event) {
	}

	@Override
	public void windowDeactivated(WindowEvent event) {
	}

	@Override
	public void windowDeiconified(WindowEvent event) {
	}

	@Override
	public void windowIconified(WindowEvent event) {
	}

	@Override
	public void windowOpened(WindowEvent event) {
	}
}