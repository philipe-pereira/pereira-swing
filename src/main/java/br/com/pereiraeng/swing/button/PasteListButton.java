package br.com.pereiraeng.swing.button;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;

import br.com.pereiraeng.icons.PereiraIcon;
import br.com.pereiraeng.swing.SwingUtils;
import br.com.pereiraeng.swing.list.model.SharedCollectionModel;

/**
 * Classe do objeto gráfico do botão que ao ser pressionado busca os dados da
 * área de transferência e repassa ao modelo de uma lista
 * 
 * @author Philipe PEREIRA
 *
 * @param <V> classe do objeto do modelo da lista
 */
public class PasteListButton<V> extends JButton implements ActionListener {
	private static final long serialVersionUID = 1L;

	private final AbstractListModel<V> listModel;

	/**
	 * <ol>
	 * <li>para {@link Double#valueOf(String) converter} o texto em um número
	 * decimal longo;</i>
	 * <li>para {@link Integer#parseInt(String) converter} o texto em um número
	 * inteiro;</i>
	 * <li>para {@link Float#parseFloat(String) converter} o texto em um número
	 * decimal;</i>
	 * <li>para {@link Long#parseLong(String) converter} o texto em um número
	 * inteiro longo.</i>
	 * </ol>
	 * 
	 * Qualquer outro valor para manter a sequência de caracteres
	 */
	private final int d;

	/**
	 * Construtor do objeto gráfico do botão que cola o conteúdo na área de
	 * transferência na tabela
	 * 
	 * @param listModel modela da tabela
	 */
	public PasteListButton(AbstractListModel<V> listModel) {
		this(listModel, 0);
	}

	/**
	 * Construtor do objeto gráfico do botão que cola o conteúdo na área de
	 * transferência na tabela
	 * 
	 * @param listModel modela da tabela
	 * @param d
	 *                  <ol>
	 *                  <li>para {@link Double#valueOf(String) converter} o texto em
	 *                  um número decimal longo;</i>
	 *                  <li>para {@link Integer#parseInt(String) converter} o texto
	 *                  em um número inteiro;</i>
	 *                  <li>para {@link Float#parseFloat(String) converter} o texto
	 *                  em um número decimal;</i>
	 *                  <li>para {@link Long#parseLong(String) converter} o texto em
	 *                  um número inteiro longo.</i>
	 *                  </ol>
	 * 
	 *                  Qualquer outro valor para manter a sequência de caracteres
	 */
	public PasteListButton(AbstractListModel<V> listModel, int d) {
		super(PereiraIcon.PASTE.create());
		addActionListener(this);
		this.listModel = listModel;
		this.d = d;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent event) {
		String content = SwingUtils.getClipBoardContent(SwingUtils.getWindow(this));
		if (content != null) {
			String[] rows = content.split("\n");

			for (int r = 0; r < rows.length; r++) {
				String cell = rows[r];

				Object o = null;
				switch (this.d) {
				case 1:
					try {
						o = Double.parseDouble(cell.replace(',', '.'));
					} catch (NumberFormatException e) {
					}
					break;
				case 2:
					try {
						o = Integer.parseInt(cell);
					} catch (NumberFormatException e) {
					}
					break;
				case 3:
					try {
						o = Float.parseFloat(cell.replace(',', '.'));
					} catch (NumberFormatException e) {
					}
					break;
				case 4:
					try {
						o = Long.parseLong(cell);
					} catch (NumberFormatException e) {
					}
					break;
				default:
					o = cell;
					break;
				}

				if (o == null)
					return;

				if (listModel instanceof DefaultListModel) {
					DefaultListModel<V> dlm = (DefaultListModel<V>) listModel;
					dlm.addElement((V) o);
				} else if (listModel instanceof SharedCollectionModel) {
					SharedCollectionModel<V> scm = (SharedCollectionModel<V>) listModel;
					scm.addElement((V) o);
				}
			}
		}
	}
}
