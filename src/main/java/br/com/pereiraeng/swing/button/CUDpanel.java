package br.com.pereiraeng.swing.button;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import br.com.pereiraeng.icons.Icons;
import br.com.pereiraeng.icons.PereiraIcon;

/**
 * Componente que contém os três butões associados às operações básicas de
 * alteração de base de dados (<strong>C</strong>reate, <strong>U</strong>pdate
 * e <strong>D</strong>elete)
 * 
 * @author Philipe Pereira
 * 
 */
public class CUDpanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public static final String NEW = "NEW", EDIT = "EDIT", DELETE = "DELETE";

	private JButton create, update, delete;

	/**
	 * Construtor do componente
	 */
	public CUDpanel() {
		this(null);
	}

	/**
	 * Construtor do componente onde já se adiciona aos butões um escutador para
	 * seus butões
	 * 
	 * @param listener objeto <code>ActionListener</code> para os butões
	 */
	public CUDpanel(ActionListener listener) {
		this(true, listener);
	}

	/**
	 * Construtor do componente onde já se adiciona aos butões um escutador para
	 * seus butões e seleciona se queremos ou não o botão de edição
	 * 
	 * @param edition  se <code>true</code> o botão de edição é exibido
	 * @param listener objeto <code>ActionListener</code> para os butões
	 */
	public CUDpanel(boolean edition, ActionListener listener) {
		this(edition, listener, false);
	}

	/**
	 * Construtor do componente onde já se adiciona aos butões um escutador para
	 * seus butões e seleciona se queremos ou não o botão de edição
	 * 
	 * @param edition  edition se <code>true</code> o botão de edição é exibido
	 * @param listener objeto <code>ActionListener</code> para os butões
	 * @param mini     <code>true</code> para botões com 20 pixels de lado,
	 *                 <code>false</code> para 34 pixels
	 */
	public CUDpanel(boolean edition, ActionListener listener, boolean mini) {
		super(new FlowLayout(FlowLayout.CENTER, 0, 0));

		create = new JButton(PereiraIcon.NEW.create());
		create.setActionCommand(NEW);
		create.setPreferredSize(mini ? Icons.DIM_BUTTON_VERT_SMALL_ICON : Icons.DIM_BUTTON_ICON);
		add(create);

		update = new JButton(PereiraIcon.EDIT.create());
		update.setActionCommand(EDIT);
		update.setPreferredSize(mini ? Icons.DIM_BUTTON_VERT_SMALL_ICON : Icons.DIM_BUTTON_ICON);
		add(update);
		setEditable(edition);

		delete = new JButton(PereiraIcon.DELETE.create());
		delete.setActionCommand(DELETE);
		delete.setPreferredSize(mini ? Icons.DIM_BUTTON_VERT_SMALL_ICON : Icons.DIM_BUTTON_ICON);
		add(delete);

		if (listener != null)
			addActionListener(listener);
	}

	@Override
	public void setEnabled(boolean e) {
		this.create.setEnabled(e);
		if (this.update.isVisible())
			this.update.setEnabled(e);
		this.delete.setEnabled(e);
	}

	public void setCreateButtonEnabled(boolean e) {
		this.create.setVisible(e);
	}

	public void setEditable(boolean edition) {
		update.setVisible(edition);
	}

	/**
	 * Função que adiciona um <code>ActionListener</code> a todos os três butões
	 * 
	 * @param listener escutador a ser adicionado
	 */
	public void addActionListener(ActionListener listener) {
		create.addActionListener(listener);
		if (update.isVisible())
			update.addActionListener(listener);
		delete.addActionListener(listener);
	}

	/**
	 * Função que determina se um dado evento foi gerado ou não por um dos butões
	 * desse componente gráfico
	 * 
	 * @param event evento analisado
	 * @return <code>true</code> se o evento foi gerado por um dos botões desse
	 *         componente
	 */
	public boolean isSource(ActionEvent event) {
		Object source = event.getSource();
		if (source != null)
			return source.equals(create) || source.equals(update) || source.equals(delete);
		else
			return false;
	}

	public void addPrefixActionCommand(String actionCommand) {
		create.setActionCommand(actionCommand + NEW);
		if (update.isVisible())
			update.setActionCommand(actionCommand + EDIT);
		delete.setActionCommand(actionCommand + DELETE);
	}
}
