package br.com.pereiraeng.swing.button;

import javax.swing.ImageIcon;

import br.com.pereiraeng.icons.Icons;
import br.com.pereiraeng.icons.PereiraIcon;

public class PlayStopButton extends ChangeableButton {
	private static final long serialVersionUID = 1L;

	public static final String STOP = "Stop", PLAY = "Play";

	public PlayStopButton() {
		super(new ImageIcon[] { PereiraIcon.PLAY.create(), PereiraIcon.STOP.create() });
		setPreferredSize(Icons.DIM_BUTTON_ICON);
	}
}
