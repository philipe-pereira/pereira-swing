package br.com.pereiraeng.swing.theme;

import java.awt.Color;

import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;

public class Tema2 extends DefaultMetalTheme {

	protected ColorUIResource getPrimary1() {
		return new ColorUIResource(Color.BLACK);
	}

	protected ColorUIResource getPrimary2() {
		return new ColorUIResource(Color.BLACK);
	}

	protected ColorUIResource getPrimary3() {
		return new ColorUIResource(Color.BLUE);
	}

	protected ColorUIResource getSecondary1() {
		return new ColorUIResource(Color.YELLOW);
	}

	protected ColorUIResource getSecondary2() {
		return new ColorUIResource(Color.ORANGE);
	}

	protected ColorUIResource getSecondary3() {
		return new ColorUIResource(Color.BLACK);
	}

	protected ColorUIResource getBlack() {
		return new ColorUIResource(Color.WHITE);
	}

	protected ColorUIResource getWhite() {
		return new ColorUIResource(Color.BLACK);
	}
}