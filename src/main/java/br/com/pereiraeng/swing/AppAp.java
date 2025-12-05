package br.com.pereiraeng.swing;

import javax.swing.JApplet;

public abstract class AppAp extends JApplet implements App {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() {
		setSize(getWindowSize());
		start();
		build(this);
	}

	@Override
	public void destroy() {
		close();
	}
}
