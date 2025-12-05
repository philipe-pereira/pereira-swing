package br.com.pereiraeng.swing.button;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import br.com.pereiraeng.icons.Icons;

/**
 * Classe do objeto gráfico de um painel com um par de botões
 * 
 * @author Philipe PEREIRA
 *
 */
public abstract class PairButtonsPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	/**
	 * Construtor do objeto gráfico de um par de botões
	 * 
	 * @param listener      escutador da ação de clicar sobre os butões
	 * @param prefixCommand prefixo
	 * @param left          endereço do arquivo da imagem do botão esquerdo
	 * @param leftA         ação do botão esquerdo
	 * @param right         endereço do arquivo da imagem do botão direito
	 * @param rightA        ação do botão direito
	 * @param dimension     tamanho dos botões
	 */
	protected PairButtonsPanel(ActionListener listener, String prefixCommand, String left, String leftA, String right,
			String rightA, Dimension dimension) {
		// zoom in
		JButton b = new JButton(Icons.loadIcon(left));
		if (dimension != null)
			b.setPreferredSize(dimension);
		b.setActionCommand(prefixCommand + leftA);
		add(b);

		// zoom out
		b = new JButton(Icons.loadIcon(right));
		if (dimension != null)
			b.setPreferredSize(dimension);
		b.setActionCommand(prefixCommand + rightA);
		add(b);

		if (listener != null)
			addActionListener(listener);
	}

	/**
	 * Função que adiciona um <code>ActionListener</code> aos dois butões
	 * 
	 * @param listener escutador a ser adicionado
	 */
	public void addActionListener(ActionListener listener) {
		((JButton) getComponent(0)).addActionListener(listener);
		((JButton) getComponent(1)).addActionListener(listener);
	}
}
