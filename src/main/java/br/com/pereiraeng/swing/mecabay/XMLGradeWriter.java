package br.com.pereiraeng.swing.mecabay;

import java.util.Collection;

import br.com.pereiraeng.xml.XMLserializable;

public class XMLGradeWriter<T extends XMLserializable> {
	public String getText(int top, int right, int bottom, int left, Collection<T> lista) {
		String out = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n<raiz top=\"" + top + "\" right=\"" + right
				+ "\" bottom=\"" + bottom + "\" left=\"" + left + "\">\n";
		for (T t : lista)
			out += t.getXML();
		out += "</raiz>";
		return out;
	}
}
