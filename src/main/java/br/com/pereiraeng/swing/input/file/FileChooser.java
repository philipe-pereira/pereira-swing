package br.com.pereiraeng.swing.input.file;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import br.com.pereiraeng.io.FileType;
import br.com.pereiraeng.io.IOutils;

public class FileChooser {

	// ---------------- escolher arquivo a ser salvo ----------------

	/**
	 * Função que carrega o {@link JFileChooser} para que um arquivo seja criado. A
	 * pasta inicial é dada a partir de seu caminho e somente arquivos de uma dada
	 * extensão serão exibidos.
	 * 
	 * @param path      caminho da pasta inicial
	 * @param extension extensão dos arquivos
	 * @return arquivo selecionado
	 */
	public static File fileChooserSave(String path, String extension) {
		return fileChooserSave(path, extension, null);
	}

	/**
	 * Função que carrega o {@link JFileChooser} para que um arquivo seja criado. A
	 * pasta inicial é dada a partir de seu caminho e somente arquivos de uma dada
	 * extensão serão exibidos.
	 * 
	 * @param path      caminho da pasta inicial
	 * @param extension extensão dos arquivos
	 * @param parent    componente superior a ser associado o {@link JFileChooser}
	 * @return arquivo selecionado
	 */
	public static File fileChooserSave(String path, String extension, Component parent) {
		return fileChooserSave(path, extension, parent, null);
	}

	/**
	 * Função que carrega o {@link JFileChooser} para que um arquivo seja criado. A
	 * pasta inicial é dada a partir de seu caminho e somente arquivos de uma dada
	 * extensão serão exibidos.
	 * 
	 * @param path      caminho da pasta inicial
	 * @param extension extensão dos arquivos
	 * @param parent    componente superior a ser associado o {@link JFileChooser}
	 * @param title     texto da barra de título (<code>null</code> para o título
	 *                  &ldquo;Salvar&rdquo;)
	 * 
	 * @return arquivo selecionado
	 */
	public static File fileChooserSave(String path, String extension, Component parent, String title) {
		return fileChooserSave(path, extension, parent, title, null);
	}

	/**
	 * Função que carrega o {@link JFileChooser} para que um arquivo seja criado. A
	 * pasta inicial é dada a partir de seu caminho e somente arquivos de uma dada
	 * extensão serão exibidos.
	 * 
	 * @param path           caminho da pasta inicial
	 * @param extension      extensão dos arquivos
	 * @param parent         componente superior a ser associado o
	 *                       {@link JFileChooser}
	 * @param title          texto da barra de título (<code>null</code> para o
	 *                       título &ldquo;Salvar&rdquo;)
	 * @param actionListener escutador a ser adicionado ao {@link JFileChooser}
	 * 
	 * @return arquivo selecionado
	 */
	public static File fileChooserSave(String path, String extension, Component parent, String title,
			ActionListener actionListener) {
		JFileChooser fc = new JFileChooser(path == null ? System.getProperty("user.dir") : path);
		if (extension != null) {
			// remover filtro existente
			fc.removeChoosableFileFilter(fc.getChoosableFileFilters()[0]);
			// adicionar o filtro escolhido pelo usuário
			fc.addChoosableFileFilter(new FileFilterAdapter(extension));
			fc.setAcceptAllFileFilterUsed(false);
		}

		if (actionListener != null)
			fc.addActionListener(actionListener);

		if (title != null)
			fc.setDialogTitle(title);

		int opcao = fc.showSaveDialog(parent);

		if (opcao == JFileChooser.APPROVE_OPTION) {
			File f = fc.getSelectedFile();

			String ext = IOutils.getExtension(f);

			if (ext == null)
				f = new File(f.getAbsolutePath() + "." + extension);
			else if (!ext.equals(extension))
				f = new File(f.getAbsolutePath() + "." + extension);

			return f;
		} else
			return null;
	}

	// ---------------- escolher arquivo a ser carregado ----------------

	/**
	 * Função que carrega o {@link JFileChooser} para que um arquivo seja carregado.
	 * A pasta inicial é dada a partir de seu caminho e somente arquivos de uma dada
	 * extensão serão exibidos.
	 * 
	 * @param path   caminho da pasta inicial (<code>null</code> para
	 *               <code>System.getProperty("user.dir")</code>)
	 * @param filter objeto do tipo {@link FileFilterAdapter} para decidir quais
	 *               arquivos poderão ser escolhidos
	 * @return arquivo selecionado
	 */
	public static File fileChooserLoad(String path, FileFilterAdapter filter) {
		return fileChooserLoad(path, filter, null);
	}

	/**
	 * Função que carrega o {@link JFileChooser} para que um arquivo seja carregado.
	 * A pasta inicial é dada a partir de seu caminho e somente arquivos de uma dada
	 * extensão serão exibidos.
	 * 
	 * @param path   caminho da pasta inicial (<code>null</code> para
	 *               <code>System.getProperty("user.dir")</code>)
	 * @param filter objeto do tipo {@link FileFilterAdapter} para decidir quais
	 *               arquivos poderão ser escolhidos
	 * @param parent componente superior a ser associado o {@link JFileChooser}
	 * @return arquivo selecionado
	 */
	public static File fileChooserLoad(String path, FileFilterAdapter filter, Component parent) {
		return fileChooserLoad(path, filter, parent, null);
	}

	/**
	 * Função que carrega o {@link JFileChooser} para que um arquivo seja carregado.
	 * A pasta inicial é dada a partir de seu caminho e somente arquivos de uma dada
	 * extensão serão exibidos.
	 * 
	 * @param path   caminho da pasta inicial (<code>null</code> para
	 *               <code>System.getProperty("user.dir")</code>)
	 * @param filter objeto do tipo {@link FileFilterAdapter} para decidir quais
	 *               arquivos poderão ser escolhidos
	 * @param parent componente superior a ser associado o {@link JFileChooser}
	 * @param title  texto da barra de título (<code>null</code> para o título
	 *               &ldquo;Abrir&rdquo;)
	 * @return arquivo selecionado
	 */
	public static File fileChooserLoad(String path, FileFilterAdapter filter, Component parent, String title) {
		return fileChooserLoad(path, filter, parent, title, null);
	}

	/**
	 * Função que carrega o {@link JFileChooser} para que um arquivo seja carregado.
	 * A pasta inicial é dada a partir de seu caminho e somente arquivos de uma dada
	 * extensão serão exibidos.
	 * 
	 * @param path           caminho da pasta inicial (<code>null</code> para
	 *                       <code>System.getProperty("user.dir")</code>)
	 * @param filter         objeto do tipo {@link FileFilterAdapter} para decidir
	 *                       quais arquivos poderão ser escolhidos
	 * @param parent         componente superior a ser associado o
	 *                       {@link JFileChooser}
	 * @param title          texto da barra de título (<code>null</code> para o
	 *                       título &ldquo;Abrir&rdquo;)
	 * @param actionListener escutador a ser adicionado ao {@link JFileChooser}
	 * @return arquivo selecionado
	 */
	public static File fileChooserLoad(String path, FileFilterAdapter filter, Component parent, String title,
			ActionListener actionListener) {
		JFileChooser fc = new JFileChooser(path == null ? System.getProperty("user.dir") : path);
		if (filter != null) {
			// remover filtro existente
			fc.removeChoosableFileFilter(fc.getChoosableFileFilters()[0]);
			// adicionar o filtro escolhido pelo usuário
			fc.addChoosableFileFilter(filter);
			fc.setAcceptAllFileFilterUsed(false);

			if (filter.getType() == FileType.DIRETORIO)
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		}

		if (actionListener != null)
			fc.addActionListener(actionListener);

		if (title != null)
			fc.setDialogTitle(title);

		int option = fc.showOpenDialog(parent);

		if (option == JFileChooser.APPROVE_OPTION)
			return fc.getSelectedFile();
		else
			return null;
	}

	// ---------------- escolher arquivos a serem carregados ----------------

	/**
	 * Função que carrega o {@link JFileChooser} para que um ou mais arquivos sejam
	 * carregado. A pasta inicial é dada a partir de seu caminho e somente arquivos
	 * de uma dada extensão serão exibidos.
	 * 
	 * @param path   caminho da pasta inicial
	 * @param filter objeto do tipo {@link FileFilterAdapter} para decidir quais
	 *               arquivos poderão ser escolhidos
	 * @return vetor com os arquivos selecionados
	 */
	public static File[] filesChooserLoad(String path, FileFilterAdapter filter) {
		return filesChooserLoad(path, filter, null);
	}

	/**
	 * Função que carrega o {@link JFileChooser} para que um ou mais arquivos sejam
	 * carregado. A pasta inicial é dada a partir de seu caminho e somente arquivos
	 * de uma dada extensão serão exibidos.
	 * 
	 * @param path   caminho da pasta inicial
	 * @param filter objeto do tipo {@link FileFilterAdapter} para decidir quais
	 *               arquivos poderão ser escolhidos
	 * @param parent componente superior a ser associado o {@link JFileChooser}
	 * @return vetor com os arquivos selecionados
	 */
	public static File[] filesChooserLoad(String path, FileFilterAdapter filter, Component parent) {
		return filesChooserLoad(path, filter, parent, null);
	}

	/**
	 * Função que carrega o {@link JFileChooser} para que um ou mais arquivos sejam
	 * carregado. A pasta inicial é dada a partir de seu caminho e somente arquivos
	 * de uma dada extensão serão exibidos.
	 * 
	 * @param path   caminho da pasta inicial
	 * @param filter objeto do tipo {@link FileFilterAdapter} para decidir quais
	 *               arquivos poderão ser escolhidos
	 * @param parent componente superior a ser associado o {@link JFileChooser}
	 * @param title  texto da barra de título (<code>null</code> para o título
	 *               &ldquo;Abrir&rdquo;)
	 * 
	 * @return vetor com os arquivos selecionados
	 */
	public static File[] filesChooserLoad(String path, FileFilterAdapter filter, Component parent, String title) {
		return filesChooserLoad(path, filter, parent, title, null);
	}

	/**
	 * Função que carrega o {@link JFileChooser} para que um ou mais arquivos sejam
	 * carregado. A pasta inicial é dada a partir de seu caminho e somente arquivos
	 * de uma dada extensão serão exibidos.
	 * 
	 * @param path           caminho da pasta inicial
	 * @param filter         objeto do tipo {@link FileFilterAdapter} para decidir
	 *                       quais arquivos poderão ser escolhidos
	 * @param parent         componente superior a ser associado o
	 *                       {@link JFileChooser}
	 * @param title          texto da barra de título (<code>null</code> para o
	 *                       título &ldquo;Abrir&rdquo;)
	 * @param actionListener escutador a ser adicionado ao {@link JFileChooser}
	 * 
	 * @return vetor com os arquivos selecionados
	 */
	public static File[] filesChooserLoad(String path, FileFilterAdapter filter, Component parent, String title,
			ActionListener actionListener) {
		JFileChooser fc = new JFileChooser(path == null ? System.getProperty("user.dir") : path);
		if (filter != null) {
			// remover filtro existente
			fc.removeChoosableFileFilter(fc.getChoosableFileFilters()[0]);
			// adicionar o filtro escolhido pelo usuário
			fc.addChoosableFileFilter(filter);
			fc.setAcceptAllFileFilterUsed(false);
		}
		fc.setMultiSelectionEnabled(true);

		if (actionListener != null)
			fc.addActionListener(actionListener);

		if (title != null)
			fc.setDialogTitle(title);

		int opcao = fc.showOpenDialog(parent);
		if (opcao == JFileChooser.APPROVE_OPTION)
			return fc.getSelectedFiles();
		else
			return null;
	}

	// ---------------- abrir ou mostrar um arquivo ----------------

	private static final String[] OPTIONS = { "Ver o arquivo", "Abrir o arquivo", "Prosseguir" };

	/**
	 * Função que abre uma caixa de diálogo que pergunta o que fazer após a criação
	 * de um arquivo:
	 * 
	 * <ul>
	 * <li>mostrar o arquivo na pasta;</i>
	 * <li>abrir o arquivo;</i>
	 * <li>fazer nada.</i>
	 * </ul>
	 * 
	 * @param file    arquivo criado
	 * @param message mensagem a ser exibida (e.g., 'Planilha criada.')
	 * @param warning <code>true</code> para uma mensagem de alarme,
	 *                <code>false</code> para uma mensagem interregotiva
	 */
	public static void openCreatedFile(File file, String message, boolean warning) {
		int n = JOptionPane.showOptionDialog(null, "<html>" + message + "<br>O que deseja a seguir?</html>",
				"Arquivo criado", JOptionPane.YES_NO_CANCEL_OPTION,
				warning ? JOptionPane.WARNING_MESSAGE : JOptionPane.QUESTION_MESSAGE, null, OPTIONS, OPTIONS[2]);
		try {
			switch (n) {
			case 0:
				Runtime.getRuntime().exec("explorer.exe /select," + file.getAbsolutePath());
				break;
			case 1:
				if (Desktop.isDesktopSupported())
					Desktop.getDesktop().open(file);
				break;
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"Não foi possível " + (n == 0 ? "mostrar" : "abrir") + " a planilha devido a algum erro.",
					"Erro ao " + (n == 0 ? "mostrar" : "abrir") + " a planilha", JOptionPane.ERROR_MESSAGE);
		}
	}
}
