package br.com.pereiraeng.swing.files;

import java.awt.CardLayout;
import java.awt.Component;
import java.io.File;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import br.com.pereiraeng.io.FileType;
import br.com.pereiraeng.io.IOutils;
import br.com.pereiraeng.swing.SwingUtils;
import br.com.pereiraeng.core.collections.ArrayUtils;

public class FileScreen extends JPanel {
	private static final long serialVersionUID = 1L;

	public FileScreen(FileType... types) {
		this(types, ArrayUtils.booleanVec(false, types.length));
	}

	public FileScreen(FileType[] types, boolean[] multi) {
		super(new CardLayout());

		super.add(new JPanel(), "BLANK");

		for (int i = 0; i < types.length; i++) {
			FileType ft = types[i];
			boolean m = multi[i];

			Component comp = null;
			switch (ft) {
			case IMAGENS:
				if (m)
					comp = new MultiImages();
				else
					comp = new ImageScreen();
				break;
//			case TEXTOS_PDF: TODO solução auto-incremental
//				if (m)
//					comp = new MultiPDF();
//				else
//					comp = new PDFscreen();
//				break;
			case TEXTO_AFN:
				if (m)
					; // TODO abas?
				else {
					JTextArea ta = new JTextArea();
					ta.setLineWrap(true);
					ta.setWrapStyleWord(true);
					comp = new JScrollPane(ta, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
							JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				}
				break;
			case WEBPAGES:
			case LINK:
				if (m)
					; // TODO abas?
				else
					comp = new JScrollPane(new JEditorPane(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
							JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				break;
			default:
				if (m)
					;
				else
					;

				// dados do arquivo (na forma de uma tabela)
				// e oferecer para abrir fora do programa
				// TODO
				break;
			}
			super.add(comp, ft.name());
		}
	}

	public void set(File file) {
		FileType fileType = null;

		if (file != null) {
			if (file.isDirectory()) {
				// se for diretório, que seus arquivos sejam analisados
				FileType[] fts = FileType.getFileTypesInDirectory(file);
				if (fts.length == 1)
					fileType = fts[0];
			} else {
				// se for arquivo, ver extensão
				String ext = IOutils.getExtension(file);
				if (ext != null)
					fileType = FileType.getType(ext);
			}
		}

		if (fileType != null) {
			((CardLayout) this.getLayout()).show(this, fileType.name());
			Component componenet = SwingUtils.getShowed(this);

			switch (fileType) {
			case IMAGENS:
				if (componenet instanceof ImageScreen && file.isFile())
					((ImageScreen) componenet).setImage(file);
				else if (componenet instanceof MultiImages)
					((MultiImages) componenet).setImage(file);
				break;
//			case TEXTOS_PDF: TODO solução auto-incremental
//				if (componenet instanceof PDFscreen && file.isFile())
//					((PDFscreen) componenet).setPdf(file);
//				else if (componenet instanceof MultiPDF && file.isDirectory())
//					((MultiPDF) componenet).setPDFs(file);
//				break;
			case TEXTO_AFN:
				((JTextArea) ((JScrollPane) componenet).getViewport().getView()).setText(IOutils.readFile2(file));
				break;
			case WEBPAGES:
				try {
					((JEditorPane) ((JScrollPane) componenet).getViewport().getView()).setPage(file.toURI().toURL());
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case LINK:
				try {
					((JEditorPane) ((JScrollPane) componenet).getViewport().getView()).setPage(IOutils.getURL(file));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			default:
				// dados do arquivo (na forma de uma tabela)
				// e oferecer para abrir fora do programa
				// TODO
				break;
			}
		} else {
			((CardLayout) this.getLayout()).show(this, "BLANK");
		}
	}
}
