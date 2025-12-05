package br.com.pereiraeng.swing;

import java.awt.Component;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import br.com.pereiraeng.swing.longtask.SplashAdapter;
import br.com.pereiraeng.swing.longtask.Splashable;

public abstract class AbsAppAp extends AppAp implements Splashable {
	private static final long serialVersionUID = 9079195810636729157L;

	protected SplashAdapter splash;

	protected JInternalFrame frame1;

	protected JFrame frame2;

	protected JApplet frame3;

	@Override
	public void build(Component comp) {
		JPanel p = new JPanel();
		if (comp instanceof JInternalFrame) {
			JInternalFrame f = (JInternalFrame) comp;
			this.frame1 = f;
			this.frame1.setContentPane(p);
		} else if (comp instanceof JFrame) {
			JFrame f = (JFrame) comp;
			this.frame2 = f;
			this.frame2.setContentPane(p);
		} else if (comp instanceof JApplet) {
			JApplet f = (JApplet) comp;
			this.frame3 = f;
			this.frame3.setContentPane(p);
		}
		build(p);
	}

	public abstract void build(JPanel comp);

	@Override
	public void setSplash(SplashAdapter splash) {
		this.splash = splash;
	}
}
