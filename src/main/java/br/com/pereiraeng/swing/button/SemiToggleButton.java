package br.com.pereiraeng.swing.button;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.JToggleButton;

/**
 * Classe do objeto gráfico de um botão que, ao ser clicado com o botão esquerdo
 * do mouse se comporta como um <code>JButton</code>, mas que ao ser clicado com
 * o botão direito do mouse se comporta com um <code>JToggleButton</code>,
 * mudando de estado e de aparência
 * 
 * @author Philipe PEREIRA
 *
 */
public class SemiToggleButton extends JToggleButton implements MouseListener {
	private static final long serialVersionUID = 1L;

	private String command;

	public SemiToggleButton(Icon icon) {
		super(icon);
		addMouseListener(this);
	}

	/**
	 * Comando do evento gerado quando se clica com o botão direito (manter o botão
	 * pressionado)
	 * 
	 * @param command
	 *            comando do evento
	 */
	public void setToggleCommand(String command) {
		this.command = command;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// se clicou com o direito, troca o estado (pois não foi trocado)
		// se clicou com o esquerdo, destroca (já foi trocado)
		this.setSelected(!this.isSelected());
		if (e.getButton() == MouseEvent.BUTTON3)
			fireActionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, command));
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
