package br.com.pereiraeng.swing;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class HTMLselection implements Transferable {

	private static List<DataFlavor> htmlFlavors = new ArrayList<>(2);

	static {
		try {
			htmlFlavors.add(new DataFlavor("text/html;class=java.lang.String"));
			htmlFlavors.add(new DataFlavor("text/html;class=java.io.Reader"));
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}

	private final String html;

	public HTMLselection(String html) {
		this.html = html;
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return (DataFlavor[]) htmlFlavors.toArray(new DataFlavor[htmlFlavors.size()]);
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return htmlFlavors.contains(flavor);
	}

	@Override
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
		if (String.class.equals(flavor.getRepresentationClass()))
			return html;
		else if (Reader.class.equals(flavor.getRepresentationClass()))
			return new StringReader(html);
		throw new UnsupportedFlavorException(flavor);
	}
}
