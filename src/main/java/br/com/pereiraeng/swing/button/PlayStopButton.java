package br.com.pereiraeng.swing.button;

import javax.swing.ImageIcon;

import br.com.pereiraeng.icons.Icons;
import br.com.pereiraeng.swing.SwingUtils;

public class PlayStopButton extends ChangeableButton {
	private static final long serialVersionUID = 1L;

	public static final String STOP = "Stop", PLAY = "Play";

	public PlayStopButton() {
		super(new ImageIcon[] { Icons.loadUtilsIcon(PLAY + "24.gif"), Icons.loadUtilsIcon(STOP + "24.gif") });
		setPreferredSize(SwingUtils.DIM_BUTTON_ICON);
	}
}
