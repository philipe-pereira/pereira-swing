package br.com.pereiraeng.swing.input.file;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;

import br.com.pereiraeng.io.FileType;
import br.com.pereiraeng.swing.input.Input;
import br.com.pereiraeng.core.LocaleConfig;


/**
 * Botão que quando clicado abre uma caixa de diálogo de escolha de arquivos
 * 
 * @author Philipe Pereira
 * 
 */
public class FileInput extends JButton implements Input<File>, ActionListener {
	private static final long serialVersionUID = 1L;

	private String extension;

	private FileType type;

	/**
	 * este objeto tanto pode ser o arquivo selecionado quanto o diretório
	 * selecionado (se {@link #type} indicar que pode ser um diretório)
	 */
	private File fileOrFolder;

	public FileInput(FileType type) {
		this(null, type);
	}

	public FileInput(String extension) {
		this(null, extension);
	}

	/**
	 * 
	 * @param file <code>null</code> para desktop, <code>new File("/")</code> para
	 *             raiz da aplicação, diretório para diretório inicial (e objeto
	 *             final, caso type=={@link FileType#DIRETORIO} )
	 */
	public FileInput(File file) {
		this(file, null, null);
	}

	public FileInput(File file, FileType type) {
		this(file, type, null);
	}

	public FileInput(File file, String extension) {
		this(file, null, extension);
	}

	private FileInput(File file, FileType type, String extension) {
		this.type = type;
		this.extension = extension;
		super.addActionListener(this);
		this.set(file);
		super.setPreferredSize(new Dimension(84, 26));
	}

	private void changeButton() {
		if (hasFile())
			setText(this.fileOrFolder.getPath());
		else
			setText(LocaleConfig.hasConfig() ? LocaleConfig.getString("chooseFile") : "Escolher arquivo");
	}

	public boolean hasFile() {
		return this.get() != null;
	}

	// ------------------------- INPUT -------------------------

	@Override
	public void set(File file) {
		if (file != null) {
			String completePath = file.toString();
			if (file.isDirectory()) {
				if (type != FileType.DIRETORIO)
					if ("\\".equals(file.getPath()))
						completePath = System.getProperty("user.dir");
			} else {
				if (extension != null)
					if (!completePath.toLowerCase().endsWith(extension.toLowerCase()))
						completePath += "." + extension;
			}
			this.fileOrFolder = new File(completePath);
		} else
			this.fileOrFolder = new File(System.getProperty("user.home") + "/Desktop");
		changeButton();
	}

	@Override
	public File get() {
		if (!fileOrFolder.isDirectory())
			return fileOrFolder;
		else if (type != null) {
			if (type == FileType.DIRETORIO)
				return fileOrFolder;
			else
				return null;
		} else
			return null;
	}

	@Override
	public Component getComponent() {
		return this;
	}

	// ------------------------- LISTENER -------------------------

	private ActionListener listener;

	private String command;

	@Override
	public void actionPerformed(ActionEvent event) {
		String path = fileOrFolder.getAbsolutePath();

		File f1 = null;
		if (extension != null && type == null)
			// se definiu-se uma extensão ao invés de um tipo
			f1 = FileChooser.fileChooserLoad(path, new FileFilterAdapter(extension), this);
		else
			f1 = FileChooser.fileChooserLoad(path, type != null ? new FileFilterAdapter(type) : null, this);
		// ao se apertar o botão "Cancel", a função chooserLoad retorna null,
		// porém nesse caso não se deve "apagar" o conteúdo do input, mas sim
		// deixá-lo como está
		if (f1 != null) {
			this.set(f1);
			if (listener != null)
				this.listener.actionPerformed(
						new ActionEvent(this, event.getID(), command == null ? event.getActionCommand() : command));
		}
	}

	@Override
	public void addActionListener(ActionListener listener) {
		this.listener = listener;
	}

	public void setActionCommand2(String command) {
		this.command = command;
	}
}