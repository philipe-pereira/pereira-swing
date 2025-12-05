package br.com.pereiraeng.swing.time;

import java.awt.Color;

import br.com.pereiraeng.core.TimeRefresh;

public class Chronometre extends ClockPanel {
	private static final long serialVersionUID = -4383555020609011486L;

	public Chronometre() {
		super(Color.WHITE, 150, 39, "%tT.%<tL");
		tm.setStep(50L);
	}

	@Override
	public int getUnit() {
		return TimeRefresh.NOT;
	}

	@Override
	public int getWindow() {
		return TimeRefresh.NOT;
	}
}
