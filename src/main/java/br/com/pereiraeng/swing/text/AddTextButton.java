package br.com.pereiraeng.swing.text;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.text.JTextComponent;

import br.com.pereiraeng.core.StringUtils;

/**
 * Classe do objeto gráfico na forma de um botão que abre uma caixa de pop-up
 * para inserção de expressões de sintaxe de linguagens de marcação
 * 
 * @author Philipe Pereira
 *
 */
public class AddTextButton extends JButton implements ActionListener {
	private static final long serialVersionUID = 1L;

	private JTextComponent target;

	private String[] groups;

	private String[][][] options;

	/**
	 * Construtor do botão que vai inserir expressões de sintaxe de linguagens de
	 * marcação
	 * 
	 * @param target  caixa de texto alvo
	 * @param icon    ícone do botão
	 * @param groups  vetor de sequências de caracteres com os nomes dos grupos de
	 *                expressões a serem inseridas
	 * @param options vetor de três dimensões, organizado da seguinte forma:
	 *                <ol start="0">
	 *                <li>diferentes grupos;</i>
	 *                <li>diferentes expressões;</i>
	 *                <li>expressão:
	 *                <ol start="0">
	 *                <li>nome da expressão;</i>
	 *                <li>o que acontece o conteúdo;</i>
	 *                <li>o que sucede o conteúdo.</i>
	 *                </ol>
	 *                </i>define-se conteúdo como aquilo que está selecionado na
	 *                caixa de texto.
	 *                </ol>
	 */
	public AddTextButton(JTextComponent target, Icon icon, String[] groups, String[][][] options) {
		super(icon);
		this.addActionListener(this);
		this.setActionCommand("O");
		this.target = target;
		this.groups = groups;
		this.options = options;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();

		if ("O".equals(command)) {
			JPopupMenu pm = new JPopupMenu();

			for (int i = 0; i < groups.length; i++) {
				String group = groups[i];

				JMenu g = new JMenu(group);

				for (int j = 0; j < options[i].length; j++) {
					String[] ss = options[i][j];

					JMenuItem option = new JMenuItem(ss[0]);
					option.addActionListener(this);
					option.setActionCommand(String.format("%d;%d", i, j));

					g.add(option);

				}
				pm.add(g);
			}

			pm.show(this.getParent(), this.getX(), this.getY() + this.getHeight());
		} else {
			// alterar o texto
			int[] pos = StringUtils.parseInts(command.split(";"));
			int s = target.getSelectionStart();
			int e = target.getSelectionEnd();

			String text = target.getText();
			if (s == e) // insert
				text = text.substring(0, s) + options[pos[0]][pos[1]][1] + options[pos[0]][pos[1]][2]
						+ text.substring(e);
			else // embrace
				text = text.substring(0, s) + options[pos[0]][pos[1]][1] + text.substring(s, e)
						+ options[pos[0]][pos[1]][2] + text.substring(e);

			target.setText(text);
		}
	}
}
