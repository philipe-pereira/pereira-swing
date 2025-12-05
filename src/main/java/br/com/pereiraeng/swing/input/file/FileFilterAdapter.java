package br.com.pereiraeng.swing.input.file;

import java.io.File;
import java.util.Arrays;

import javax.swing.filechooser.FileFilter;

import br.com.pereiraeng.io.FileType;
import br.com.pereiraeng.io.IOutils;

/**
 * Adaptação da classe abstrata <code>FileFilter</code>
 * 
 * @author Philipe Pereira
 * 
 */
public class FileFilterAdapter extends FileFilter {

	private FileType type;
	private String[] extension;

	public FileFilterAdapter() {
		this.type = FileType.TODOS;
	}

	public FileFilterAdapter(FileType type) {
		this.type = type;
	}

	public FileFilterAdapter(FileType... type) {
		this(FileType.extensionList(type));
	}

	public FileFilterAdapter(String... extension) {
		this.extension = extension;
		for (int i = 0; i < this.extension.length; i++)
			this.extension[i] = this.extension[i].toUpperCase();
		// ordenar extensões (necessário pois depois vai se fazer uma busca
		// binária com as extensões)
		Arrays.sort(this.extension);
	}

	public FileType getType() {
		return this.type;
	}

	@Override
	public boolean accept(File f) {

		// se for para aceitar todos os arquivos
		if (type == FileType.TODOS)
			return true;

		// se o arquivo for um diretório, aceitar
		if (f.isDirectory()) {
			return true;
		} else if (type == FileType.DIRETORIO) {
			// se não for diretório e o usuário buscar diretórios, rejeitar
			return false;
		}

		String ext = IOutils.getExtension(f);
		if (ext == null) {
			return false;
		} else {
			if (type != null) {
				if (FileType.getType(ext) == type)
					return true;
				else
					return false;
			} else if (extension != null) {
				if (Arrays.binarySearch(extension, ext.toUpperCase()) >= 0)
					return true;
				else
					return false;
			} else
				return true;
		}
	}

	@Override
	public String getDescription() {
		if (type != null) {
			if (type == FileType.TODOS) {
				return "Todos os arquivos";
			} else {
				return "Tipo " + type.name().toLowerCase();
			}
		} else {
			return "Extensão " + Arrays.toString(extension);
		}
	}
}
