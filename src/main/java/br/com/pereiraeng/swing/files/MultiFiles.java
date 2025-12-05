package br.com.pereiraeng.swing.files;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.io.File;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import br.com.pereiraeng.io.FileType;

public class MultiFiles extends JPanel implements ListSelectionListener {
	private static final long serialVersionUID = 1L;

	private FileScreen fileShower;

	private JList<File> list;
	private DefaultListModel<File> listModel;

	public MultiFiles(FileType... types) {
		super(new BorderLayout());

		fileShower = new FileScreen(types);
		add(fileShower, BorderLayout.CENTER);

		list = new JList<File>(listModel = new DefaultListModel<>());
		list.setCellRenderer(new DefaultListCellRenderer() {
			private static final long serialVersionUID = 1L;

			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				return super.getListCellRendererComponent(list, ((File) value).getName(), index, isSelected,
						cellHasFocus);
			}
		});
		list.addListSelectionListener(this);
		JScrollPane sp = new JScrollPane(list);
		sp.setPreferredSize(new Dimension(150, 800));
		add(sp, BorderLayout.EAST);
	}

	public void setFiles(File[] files) {
		listModel.clear();
		if (files != null)
			for (File file : files)
				listModel.addElement(file);
	}

	public void clear() {
		setFiles(null);
	}

	// ------------------------------- LISTENER -------------------------------

	@Override
	public void valueChanged(ListSelectionEvent event) {
		File file = list.getSelectedValue();
		fileShower.set(file);
	}
}