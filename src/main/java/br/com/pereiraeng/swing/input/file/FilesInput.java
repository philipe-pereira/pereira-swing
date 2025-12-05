package br.com.pereiraeng.swing.input.file;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import br.com.pereiraeng.io.FileType;
import br.com.pereiraeng.swing.button.CUDpanel;
import br.com.pereiraeng.swing.input.Input;

public class FilesInput extends JPanel implements Input<File[]>, ActionListener {
	private static final long serialVersionUID = 1L;

	private JList<File> list;
	private DefaultListModel<File> filesList;

	private FileType type;

	private String[] extensions;

	private String path;

	public FilesInput() {
		this((FileType) null);
	}

	public FilesInput(File[] files) {
		this(files, (FileType) null);
	}

	public FilesInput(FileType type) {
		this(null, type);
	}

	public FilesInput(String... extensions) {
		this(null, extensions);
	}

	public FilesInput(File[] files, FileType type) {
		this(files, type, (String[]) null);
	}

	public FilesInput(File[] files, String... extensions) {
		this(files, null, extensions);
	}

	private FilesInput(File[] files, FileType type, String... extensions) {
		super(new BorderLayout());
		this.type = type;
		this.extensions = extensions;

		list = new JList<>(filesList = new DefaultListModel<>());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		add(new JScrollPane(list), BorderLayout.CENTER);
		add(new CUDpanel(false, this), BorderLayout.SOUTH);

		set(files);
	}

	public void setPath(String path) {
		this.path = path;
	}

	// ------------------------ INPUT ------------------------

	@Override
	public File[] get() {
		File[] out = new File[filesList.size()];
		for (int i = 0; i < out.length; i++)
			out[i] = filesList.get(i);
		return out;
	}

	@Override
	public void set(File[] files) {
		if (files != null) {
			for (File f : files)
				filesList.addElement(f);
		} else
			filesList.clear();
	}

	@Override
	public Component getComponent() {
		return this;
	}

	public void clear() {
		set(null);
	}

	// ------------------------ LISTENER ------------------------

	@Override
	public void actionPerformed(ActionEvent event) {
		switch (event.getActionCommand()) {
		case CUDpanel.NEW:
			FileFilterAdapter ff = null;
			if (type != null)
				ff = new FileFilterAdapter(type);
			else
				ff = new FileFilterAdapter(extensions);
			File[] fs = FileChooser.filesChooserLoad(path, ff, this, null);
			if (fs != null)
				for (File f : fs)
					filesList.addElement(f);
			break;
		case CUDpanel.DELETE:
			int index = list.getSelectedIndex();
			if (index >= 0)
				filesList.removeElementAt(index);
			break;
		}
	}
}