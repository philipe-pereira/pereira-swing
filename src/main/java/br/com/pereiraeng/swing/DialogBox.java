package br.com.pereiraeng.swing;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JRootPane;
import javax.swing.WindowConstants;

/**
 * Classe que extende a {@link JDialog}, adicionando-lhe funções que facilitam
 * seu manuseio
 * 
 * @author Philipe Pereira
 * 
 */
public class DialogBox extends JDialog {
	private static final long serialVersionUID = 1L;

	/**
	 * Contrutor da classe com o qual se cria uma caixa de diálogo com um dado
	 * título e um dado tamanho, posicionada no centro da tela.
	 * 
	 * @param root      janela superior a ser associado à caixa de diálogo
	 * @param title     título da caixa de diálogo a ser exibido na barra superior
	 * @param size      objeto {@link Dimension} com as dimensões, em pixels, da
	 *                  caixa de diálogo
	 * @param dependent se <code>true</code>, o usuário não poderá executar ações
	 *                  sobre o componente superior a ele sem que antes a caixa de
	 *                  diálogo esteja fechada
	 */
	public DialogBox(Window root, String title, Dimension size, boolean dependent) {
		super(root);
		super.setTitle(title);
		super.setModalityType(dependent ? ModalityType.APPLICATION_MODAL : ModalityType.MODELESS);

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

		super.setLocation((screen.width - size.width) / 2, (screen.height - size.height) / 2);
		super.setSize(size);
	}

	/**
	 * Contrutor da classe com o qual se cria uma caixa de diálogo com um dado
	 * título e um dado tamanho, posicionada numa dada coordenada a partir do canto
	 * da tela.
	 * 
	 * @param root      janela superior a ser associado à caixa de diálogo
	 * @param title     título da caixa de diálogo a ser exibido na barra superior
	 * @param size      objeto {@link Dimension} com as dimensões, em pixels,
	 *                  da caixa de diálogo
	 * @param position  coordenadas, em pixels, indicando onde será posicionada a
	 *                  caixa de diálogo.
	 * @param dependent se <code>true</code>, o usuário não poderá executar ações
	 *                  sobre a janela superior a ele sem que antes a caixa de
	 *                  diálogo esteja fechada
	 */
	public DialogBox(Window root, String title, Dimension size, Point position, boolean dependent) {
		super(root);
		super.setTitle(title);
		super.setModalityType(dependent ? ModalityType.APPLICATION_MODAL : ModalityType.MODELESS);

		super.setLocation(position.x, position.y);
		super.setSize(size);
	}

	/**
	 * Contrutor da classe com o qual se cria uma caixa de diálogo com um dado
	 * título, centrada na tela e com as dimensões dadas como porcentagem do tamanho
	 * desta.
	 * 
	 * @param root      janela superior a ser associado à caixa de diálogo
	 * @param title     título da caixa de diálogo a ser exibido na barra superior
	 * @param width     número indicando o tamanho relativo entre a largura da caixa
	 *                  de diálogo e a da tela
	 * @param height    número indicando o tamanho relativo entre a altura da caixa
	 *                  de diálogo e a da tela
	 * @param dependent se <code>true</code>, o usuário não poderá executar ações
	 *                  sobre a janela superior a ele sem que antes a caixa de
	 *                  diálogo esteja fechada
	 */
	public DialogBox(Window root, String title, double width, double height, boolean dependent) {
		super(root);
		super.setTitle(title);
		super.setModalityType(dependent ? ModalityType.APPLICATION_MODAL : ModalityType.MODELESS);

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension size = new Dimension((int) (width * screen.width), (int) (height * screen.height));

		super.setLocation((screen.width - size.width) / 2, (screen.height - size.height) / 2);
		super.setSize(size);
	}

	/**
	 * Contrutor da classe com o qual se cria uma caixa de diálogo com um dado
	 * título, dimensões dadas como porcentagem do tamanho desta e posicionada com
	 * relação ao centro
	 * 
	 * @param root      janela superior a ser associado à caixa de diálogo
	 * @param title     título da caixa de diálogo a ser exibido na barra superior
	 * @param width     número indicando o tamanho relativo entre a largura da caixa
	 *                  de diálogo e a da tela
	 * @param height    número indicando o tamanho relativo entre a altura da caixa
	 *                  de diálogo e a da tela
	 * @param x         posição horizontal relativa ao centro onde a caixa de
	 *                  diálogo será posicionada (-1f para a janela ser posicionada
	 *                  à esquerda, 1f para ser posicionada à direita)
	 * @param y         posição vertical relativa ao centro onde a caixa de diálogo
	 *                  será posicionada (-1f para a janela ser posicionada abaixo,
	 *                  1f para ser posicionada acima)
	 * @param dependent se <code>true</code>, o usuário não poderá executar ações
	 *                  sobre a janela superior a ele sem que antes a caixa de
	 *                  diálogo esteja fechada
	 */
	public DialogBox(Window root, String title, double width, double height, double x, double y, boolean dependent) {
		super(root);
		super.setTitle(title);
		super.setModalityType(dependent ? ModalityType.APPLICATION_MODAL : ModalityType.MODELESS);

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension size = new Dimension((int) (width * screen.width), (int) (height * screen.height));

		super.setLocation((int) ((screen.width - size.width) * (x / 2 + 0.5)),
				(int) ((screen.height - size.height) * (-y / 2 + 0.5)));
		super.setSize(size);
	}

	/**
	 * Função que faz aparecer a caixa de diálogo. Esta, por sua vez, poderá ser
	 * redimensionada ou não, em função do parâmetro <code>resizable</code>.
	 * 
	 * @param resizable se <code>true</code>, a caixa de diálogo poderá ser
	 *                  redimensionada pelo usuário
	 */
	public void showFrame(boolean resizable) {
		super.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		super.setResizable(resizable);
		if (resizable)
			super.setMinimumSize(this.getSize());
		super.setVisible(true);
	}

	// --------------------- AUXILIAR ---------------------

	public static Component getComponent(Dialog dialog, int n) {
		return ((JRootPane) dialog.getComponent(0)).getContentPane().getComponent(n);
	}
}