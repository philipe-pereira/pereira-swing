package br.com.pereiraeng.swing.dialog;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import br.com.pereiraeng.swing.DialogBox;
import br.com.pereiraeng.swing.Grade;
import br.com.pereiraeng.swing.button.CoCaPanel;
import br.com.pereiraeng.core.LocaleConfig;
import br.com.pereiraeng.core.Password;
import br.com.pereiraeng.core.User;

public class PasswordLogin extends DialogBox implements ActionListener, KeyListener {
	private static final long serialVersionUID = 1L;

	private static enum TypeBox {
		PASSWORD(1), LOGIN(2), CHANGE(3), CREATE(4);

		private int fields;

		private TypeBox(int fields) {
			this.fields = fields;
		}
	}

	/**
	 * Função que exibe uma caixa de dialógo com um campo de texto onde se insere
	 * uma senha que será checada
	 * 
	 * @param root     janela a qual está subordinada a caixa de diálogo
	 * @param password senha correta que será comparado com a inserida
	 * @return <code>true</code> se a senha é compatível, <code>false</code> senão
	 */
	public static boolean password(Frame root, String password) {
		PasswordLogin dialog = new PasswordLogin(root, TypeBox.PASSWORD);

		String newPassword = dialog.getPassword();

		if (newPassword == null ? true : newPassword.length() == 0) {
			// ao se apertar CANCELAR (ou ESC) ou ao se apertar OK com nenhuma senha
			return false;
		} else {
			if (password.equals(newPassword)) {
				return true;
			} else {
				JOptionPane.showMessageDialog(null,
						LocaleConfig.hasConfig() ? LocaleConfig.getString("wrongPassword") : "Senha incorreta",
						LocaleConfig.hasConfig() ? LocaleConfig.getString("error") : "Erro", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
	}

	/**
	 * Função que exibe uma caixa de dialógo com dois campos de texto, um para o
	 * identificador do usuário e outro para a senha
	 * 
	 * @param root  janela a qual está subordinada a caixa de diálogo
	 * @param users lista de usuário permitidos
	 * @return {@link User objeto do usuário} selecionado, ou <code>null</code> caso
	 *         o usuário não exista ou caso a senha não esteja correta
	 */
	public static User login(Frame root, Collection<User> users) {
		PasswordLogin dialog = new PasswordLogin(root, TypeBox.LOGIN);

		String login = dialog.getLogin(), newPassword = dialog.getPassword();

		User user = null;
		if (newPassword.length() == 0) {
			return null;
		} else {
			user = searchUser(login, users);
			if (user == null) {
				JOptionPane.showMessageDialog(null, login + " : "
						+ (LocaleConfig.hasConfig() ? LocaleConfig.getString("wrongId") : "Usuário inexistente"),
						LocaleConfig.hasConfig() ? LocaleConfig.getString("error") : "Erro", JOptionPane.ERROR_MESSAGE);
				return null;
			} else {
				if (!user.getPassword().equals(newPassword)) {
					JOptionPane.showMessageDialog(null,
							LocaleConfig.hasConfig() ? LocaleConfig.getString("wrongPassword") : "Senha incorreta",
							LocaleConfig.hasConfig() ? LocaleConfig.getString("error") : "Erro",
							JOptionPane.ERROR_MESSAGE);
					return null;
				}
			}
		}
		return user;
	}

	/**
	 * Função que exibe uma caixa de dialógo com três campos de texto, um para a
	 * antiga senha do usuário, e duas para a nova senha (uma delas é de
	 * confirmação)
	 * 
	 * @param root janela a qual está subordinada a caixa de diálogo
	 * @param user usuário que terá a senha alterada caso a senha antiga seja
	 *             corretamente digitada e caso a nova senha seja validada na caixa
	 *             de confirmação
	 */
	public static void changePassword(Frame root, User user) {
		PasswordLogin dialog = new PasswordLogin(root, TypeBox.CHANGE);

		String password = dialog.getPassword(), newPassword1 = dialog.getNewPassword1(),
				newPassword2 = dialog.getNewPassword2();

		if (password.length() != 0) {
			if (!user.getPassword().equals(password)) {
				JOptionPane.showMessageDialog(null,
						LocaleConfig.hasConfig() ? LocaleConfig.getString("wrongPassword") : "Senha incorreta",
						LocaleConfig.hasConfig() ? LocaleConfig.getString("error") : "Erro", JOptionPane.ERROR_MESSAGE);
			} else {
				if (!newPassword1.equals(newPassword2))
					JOptionPane.showMessageDialog(null,
							LocaleConfig.hasConfig() ? LocaleConfig.getString("differentPassword")
									: "Senhas digitadas diferentes",
							LocaleConfig.hasConfig() ? LocaleConfig.getString("error") : "Erro",
							JOptionPane.ERROR_MESSAGE);
				else
					user.setSenha(new Password(newPassword1));
			}
		}
	}

	/**
	 * Função que exibe uma caixa de dialógo com quatro campos de texto, um para a
	 * antiga senha do usuário, e duas para a nova senha (uma delas é de
	 * confirmação)
	 * 
	 * @param root     janela a qual está subordinada a caixa de diálogo
	 * @param name     nome do novo usuário
	 * @param newLogin identificação do novo usuário
	 * @param users    lista de usuário que podem cadastrar um novo usuário
	 * @return novo {@link User objeto do usuário}, ou <code>null</code> caso não
	 *         tenha sido possível criar o novo cadastro
	 */
	public static User newUser(Frame root, String name, String newLogin, ArrayList<User> users) {
		PasswordLogin dialog = new PasswordLogin(root, TypeBox.CREATE);

		String login = dialog.getLogin(), password = dialog.getPassword(), newPassword1 = dialog.getNewPassword1(),
				newPassword2 = dialog.getNewPassword2();

		User validUser = null, newUser = null;
		if (password.length() == 0) {
			return null;
		} else {
			validUser = searchUser(login, users);
			if (validUser == null) {
				JOptionPane.showMessageDialog(null, login + " : "
						+ (LocaleConfig.hasConfig() ? LocaleConfig.getString("wrongId") : "Usuário inexistente"),
						LocaleConfig.hasConfig() ? LocaleConfig.getString("error") : "Erro", JOptionPane.ERROR_MESSAGE);
				return null;
			} else {
				if (!validUser.getPassword().equals(password)) {
					JOptionPane.showMessageDialog(null,
							LocaleConfig.hasConfig() ? LocaleConfig.getString("wrongPassword") : "Senha incorreta",
							LocaleConfig.hasConfig() ? LocaleConfig.getString("error") : "Erro",
							JOptionPane.ERROR_MESSAGE);
					return null;
				} else {
					if (!newPassword1.equals(newPassword2)) {
						JOptionPane.showMessageDialog(null,
								LocaleConfig.hasConfig() ? LocaleConfig.getString("differentPassword")
										: "Senhas digitadas diferentes",
								LocaleConfig.hasConfig() ? LocaleConfig.getString("error") : "Erro",
								JOptionPane.ERROR_MESSAGE);
						return null;
					} else {
						newUser = new User(name, newLogin, new Password(newPassword1));
					}
				}
			}
		}
		return newUser;
	}

	private static User searchUser(String login, Collection<User> users) {
		User user = null;
		for (User u : users) {
			if (u.getLogin().equals(login)) {
				user = u;
				break;
			}
		}
		return user;
	}

	/**
	 * Classe do objeto gráfico que representa uma caixa de diálogo para manipulação
	 * e leitura de senhas
	 * 
	 * @author Philipe PEREIRA
	 *
	 */

	private JTextField loginField;
	private JPasswordField passField, passN1field, passN2field;

	private String login = null, password = null, newPassword1 = null, newPassword2 = null;

	private PasswordLogin(Frame root, TypeBox type) {
		super(root, LocaleConfig.hasConfig() ? LocaleConfig.getString("password") : "Senha de acesso",
				new Dimension(200, (type.fields + 2) * 30), true);

		Grade g = new Grade();

		// login
		JLabel label = new JLabel("Login");
		label.setVisible(type == TypeBox.LOGIN || type == TypeBox.CREATE);
		g.add(label, 0, 0, 1, 1);

		loginField = new JTextField(15);
		loginField.addKeyListener(this);
		loginField.setVisible(type == TypeBox.LOGIN || type == TypeBox.CREATE);
		g.add(loginField, 1, 0, 1, 1);

		// senha
		g.add(new JLabel(LocaleConfig.hasConfig() ? LocaleConfig.getString("password") : "Senha de acesso"), 0, 1, 1,
				1);

		passField = new JPasswordField(15);
		passField.addKeyListener(this);
		g.add(passField, 1, 1, 1, 1);

		if (type == TypeBox.CREATE || type == TypeBox.CHANGE)
			g.add(new JSeparator(JSeparator.HORIZONTAL), 0, 2, 2, 1);

		// senha nova 1
		label = new JLabel(LocaleConfig.hasConfig() ? LocaleConfig.getString("newPass") : "Nova senha");
		label.setVisible(type == TypeBox.CREATE || type == TypeBox.CHANGE);
		g.add(label, 0, 3, 1, 1);

		passN1field = new JPasswordField(15);
		passN1field.setVisible(type == TypeBox.CREATE || type == TypeBox.CHANGE);
		passN1field.addKeyListener(this);
		g.add(passN1field, 1, 3, 1, 1);

		// senha nova 2
		label = new JLabel(LocaleConfig.hasConfig() ? LocaleConfig.getString("ok") : "Confirmar");
		label.setVisible(type == TypeBox.CREATE || type == TypeBox.CHANGE);
		g.add(label, 0, 4, 1, 1);

		passN2field = new JPasswordField(15);
		passN2field.setVisible(type == TypeBox.CREATE || type == TypeBox.CHANGE);
		passN2field.addKeyListener(this);
		g.add(passN2field, 1, 4, 1, 1);

		// butões
		CoCaPanel p = new CoCaPanel();
		p.addActionListener(this);
		g.add(p, 0, 5, 2, 1);

		setContentPane(g);
		showFrame(false);
	}

	private void gatherContent() {
		this.login = loginField.getText();
		this.password = String.valueOf(passField.getPassword());
		this.newPassword1 = String.valueOf(passN1field.getPassword());
		this.newPassword2 = String.valueOf(passN2field.getPassword());
	}

	private String getLogin() {
		return login;
	}

	private String getPassword() {
		return password;
	}

	private String getNewPassword1() {
		return newPassword1;
	}

	private String getNewPassword2() {
		return newPassword2;
	}

	// ------------------------ LISTENER ------------------------

	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();

		switch (command) {
		case CoCaPanel.EDITION_OK:
			// se clicar em OK, recolhe os valores digitados
			gatherContent();
			break;
		case CoCaPanel.EDITION_CANCEL:
			// se clicar em CANCELAR, retorna vetor vazio
			break;
		}
		this.dispose();
	}

	@Override
	public void keyTyped(KeyEvent event) {
		char c = event.getKeyChar();

		switch (c) {
		case KeyEvent.VK_ENTER:
			// se apertar ENTER, recolhe os valores digitados
			gatherContent();
			this.dispose();
			break;
		case KeyEvent.VK_ESCAPE:
			// se apertar ESC, retorna vetor vazio
			this.dispose();
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