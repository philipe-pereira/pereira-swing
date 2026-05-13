package br.com.pereiraeng.swing;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import br.com.pereiraeng.core.LocaleConfig;
import br.com.pereiraeng.swing.button.CoCaPanel;

/**
 * Classe dos objetos gráficos representando uma tela de acesso
 * 
 * @author Philipe PEREIRA
 *
 */
public class LoginPanel extends Grade implements ActionListener, KeyListener {
	private static final long serialVersionUID = 1L;

	// TODO na hora de cadastrar usuário e senha, rejeitar o separador
	public static final String SEP = " ";

	public static final char LOGIN = 'L';

	public static final char NEW_USER = 'U';

	public static final char FREE_LOGIN = 'F';

	public static final char DIFF_MODE = 'M';

	public static final String FORMAT = "%c%4$s%s%4$s%s";

	private JComboBox<String> modesCb;

	private JTextField loginField;
	private JPasswordField passField1, passField2;

	private final ActionListener listener;

	/**
	 * Construtor do objeto gráfico do painel de login
	 * 
	 * @param listener escutador que vai notificar ações com os seguintes comandos
	 *                 <ul>
	 *                 <li>{@link #FREE_LOGIN F}: acesso livre (ver
	 *                 {@link #enableFreeAccess(boolean)});</i>
	 *                 <li>{@link #LOGIN L} user password: login;</i>
	 *                 <li>{@link #NEW_USER U} user password: novo usuário.</i>
	 *                 </ul>
	 */
	public LoginPanel(ActionListener listener, String... modes) {
		super(2, 0, 2, 0);
		int i = 0;
		this.listener = listener;

		if (modes.length > 0) { // diferentes modos
			this.modesCb = new JComboBox<>();
			for (int j = 0; j < modes.length; j++) {
				String mode = modes[j];
				this.modesCb.addItem(mode);
			}
			this.modesCb.setSelectedIndex(0);
			this.modesCb.setActionCommand(String.valueOf(DIFF_MODE));
			this.modesCb.addActionListener(listener);
			i++;
		}

		// login
		JLabel label = new JLabel("Login");
		add(label, 0, i, 1, 1);

		loginField = new JTextField(15);
		loginField.addKeyListener(this);
		add(loginField, 1, i++, 1, 1);

		// senha
		add(new JLabel(LocaleConfig.hasConfig() ? LocaleConfig.getString("password") : "Senha de acesso"), 0, i, 1, 1);

		passField1 = new JPasswordField(15);
		passField1.addKeyListener(this);
		add(passField1, 1, i++, 1, 1);

		// confirmar senha (novo usuário)
		JLabel l = new JLabel(LocaleConfig.hasConfig() ? LocaleConfig.getString("password2") : "Repetir senha");
		l.setVisible(false);
		add(l, 0, i, 1, 1);

		passField2 = new JPasswordField(15);
		passField2.addKeyListener(this);
		passField2.setVisible(false);
		add(passField2, 1, i++, 1, 1);

		// butões
		CoCaPanel p = new CoCaPanel();
		p.addActionListener(this);
		add(p, 0, i++, 2, 1);

		JButton b = new JButton("Novo usuário");
		b.setActionCommand("NEW_USER_1");
		b.addActionListener(this);
		add(b, 0, i++, 2, 1);

		b = new JButton("Acessar sem cadastrar");
		b.setActionCommand(String.valueOf(FREE_LOGIN));
		b.addActionListener(this.listener);
		add(b, 0, i++, 2, 1);

		if (this.modesCb != null)
			add(this.modesCb, 0, 0, 2, 1);
	}

	public void clearPassword() {
		this.passField1.setText("");
	}

	public void setNothingWrong() {
		this.loginField.setBackground(Color.WHITE);
		this.passField1.setBackground(Color.WHITE);
		this.passField2.setBackground(Color.WHITE);
	}

	public void setUserWrong() {
		this.loginField.setBackground(Color.RED);
		this.passField1.setBackground(Color.WHITE);
		this.passField2.setBackground(Color.WHITE);
	}

	public void setPasswordWrong() {
		this.loginField.setBackground(Color.WHITE);
		this.passField1.setBackground(Color.RED);
		this.passField2.setBackground(Color.WHITE);
	}

	public void setPassword2Wrong() {
		this.loginField.setBackground(Color.WHITE);
		this.passField1.setBackground(Color.WHITE);
		this.passField2.setBackground(Color.RED);
	}

	public void enableFreeAccess(boolean free) {
		JButton b = (JButton) this.getComponent(8);
		b.setVisible(free);
		b.setEnabled(free);
	}

	public void newUser(boolean visible) {
		setNothingWrong();
		((JLabel) this.getComponent(4)).setVisible(visible);
		((JPasswordField) this.getComponent(5)).setVisible(visible);
		((JButton) this.getComponent(7)).setVisible(!visible);
		JButton b = (JButton) this.getComponent(8);
		if (b.isEnabled())
			b.setVisible(!visible);
	}

	private boolean isNewUser() {
		return ((JPasswordField) this.getComponent(5)).isVisible();
	}

	// ------------------------ LISTENER ------------------------

	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();

		switch (command) {
		case CoCaPanel.EDITION_OK:
			// se clicar em OK, recolhe os valores digitados

			String p1 = String.valueOf(passField1.getPassword());
			if ("".equals(p1))
				return;

			if (isNewUser()) { // novo usuário
				String p2 = String.valueOf(passField2.getPassword());
				if (!p1.equals(p2))
					setPassword2Wrong();
				else {
					String l = loginField.getText();
					if ("".equals(l)) // vazio
						return;
					if (l.contains(SEP)) // caracter inválido
						setUserWrong();
					else
						this.listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
								String.format(FORMAT, NEW_USER, l, p1, SEP)));
				}
			} else { // login
				String l = loginField.getText();
				if ("".equals(l)) // vazio
					return;
				this.listener.actionPerformed(
						new ActionEvent(this, ActionEvent.ACTION_PERFORMED, String.format(FORMAT, LOGIN, l, p1, SEP)));
			}
			break;
		case CoCaPanel.EDITION_CANCEL: // se apertar ESC, limpa
			if (isNewUser())
				newUser(false);
			else {
				loginField.setText("");
				passField1.setText("");
			}
			break;
		case "NEW_USER_1":
			newUser(true);
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent event) {
		char c = event.getKeyChar();

		switch (c) {
		case KeyEvent.VK_ENTER:
			// se apertar ENTER, recolhe os valores digitados
			this.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, CoCaPanel.EDITION_OK));
			break;
		case KeyEvent.VK_ESCAPE:
			// se apertar ESC, limpa
			this.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, CoCaPanel.EDITION_CANCEL));
			break;
		}
	}

	@Override
	public void keyPressed(KeyEvent event) {
	}

	@Override
	public void keyReleased(KeyEvent event) {
	}
}
