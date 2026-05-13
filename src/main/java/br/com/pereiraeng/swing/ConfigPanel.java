package br.com.pereiraeng.swing;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;

import br.com.pereiraeng.icons.PereiraIcon;

public class ConfigPanel extends Grade implements ActionListener {
	private static final long serialVersionUID = 1L;

	public static final char CHANGE = 'P';

	public static final char DELETE = 'E';

	private JPasswordField passField1, passField2, passField3;

	protected ActionListener listener;

	/**
	 * 
	 * @param listener escutador que vai notificar ações com os seguintes comandos
	 *                 <ul>
	 *                 <li>{@link #DELETE E}: apagar conta;</i>
	 *                 <li>{@link #LOGIN L} user password: login;</i>
	 *                 <li>{@link #NEW_USER U} user password: novo usuário.</i>
	 *                 </ul>
	 * @param command
	 */
	public ConfigPanel(ActionListener listener, String command) {
		super(2, 0, 2, 0);
		this.listener = listener;

		int i = 0;

		JButton b = new JButton("Excluir conta", PereiraIcon.CLOSE.create());
		b.setActionCommand(String.valueOf(DELETE));
		b.addActionListener(this.listener);
		add(b, 0, i++, 2, 1);

		add(new JSeparator(), 0, i++, 2, 1);

		add(new JLabel("Alterar senha"), 0, i++, 2, 1);

		add(new JLabel("Senha atual"), 0, i, 1, 1);
		passField1 = new JPasswordField(15);
		add(passField1, 1, i++, 1, 1);

		add(new JLabel("Nova senha"), 0, i, 1, 1);
		passField2 = new JPasswordField(15);
		add(passField2, 1, i++, 1, 1);

		add(new JLabel("Confirmar senha"), 0, i, 1, 1);
		passField3 = new JPasswordField(15);
		add(passField3, 1, i++, 1, 1);

		b = new JButton("OK");
		b.addActionListener(this);
		add(b, 0, i++, 2, 1);

		add(new JSeparator(), 0, i++, 2, 1);

		b = new JButton(PereiraIcon.RETURN.create());
		b.setActionCommand(command);
		b.addActionListener(this.listener);
		add(b, 0, 50, 2, 1);
	}

	public void setPassWrong() {
		passField1.setBackground(Color.RED);
		passField3.setBackground(Color.WHITE);
	}

	public void setPass3Wrong() {
		passField1.setBackground(Color.WHITE);
		passField3.setBackground(Color.RED);
	}

	public void confirmChange() {
		passField1.setBackground(Color.WHITE);
		passField3.setBackground(Color.WHITE);
		passField1.setText("");
		passField2.setText("");
		passField3.setText("");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// troca de senha
		String p2 = String.valueOf(passField2.getPassword());
		String p3 = String.valueOf(passField3.getPassword());
		if (!p2.equals(p3))
			setPass3Wrong();
		else
			this.listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, String
					.format(LoginPanel.FORMAT, CHANGE, String.valueOf(passField1.getPassword()), p2, LoginPanel.SEP)));
	}
}
