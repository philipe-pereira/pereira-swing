package br.com.pereiraeng.swing.longtask;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.SplashScreen;

public class SplashAdapter {

	private SplashScreen splash;

	private Graphics2D g;

	public SplashAdapter(SplashScreen splash) {
		this.splash = splash;
		if (this.splash != null)
			this.g = splash.createGraphics();
	}

	public void update(String item) {
		if (splash != null ? splash.isVisible() : false) {
			g.setComposite(AlphaComposite.Clear);
			g.fillRect(120, 160, 200, 40);
			g.setPaintMode();
			g.setColor(Color.BLACK);
			g.drawString("Carregando " + item + "...", 120, 170);
			splash.update();
		}
	}

	public void close() {
		if (splash != null ? splash.isVisible() : false)
			splash.close();
	}
}
