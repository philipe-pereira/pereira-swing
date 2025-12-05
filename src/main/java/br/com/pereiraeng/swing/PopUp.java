package br.com.pereiraeng.swing;

import javax.swing.JPopupMenu;

public class PopUp extends JPopupMenu {
	private static final long serialVersionUID = 1L;

	private boolean closeable;

	public void close() {
		closeable = true;
		setVisible(false);
	}

	public void setVisible(boolean b) {
		if (b || closeable) {
			super.setVisible(b);
			closeable = false;
		}
	}
}