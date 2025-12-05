package br.com.pereiraeng.swing.mecabay;

import java.awt.Component;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JComponent;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import br.com.pereiraeng.swing.Grade;
import br.com.pereiraeng.swing.mecabay.Change.AlinHrz;
import br.com.pereiraeng.swing.mecabay.Change.AlinVrt;
import br.com.pereiraeng.swing.mecabay.Change.Fonte;
import br.com.pereiraeng.swing.mecabay.Change.Orientacao;
import br.com.pereiraeng.swing.mecabay.Change.TipoAlteracao;
import br.com.pereiraeng.swing.mecabay.Componente.TipoComponente;
import br.com.pereiraeng.xml.XMLadapter;

public class XMLgradeReader extends XMLadapter {

	private Grade grid;

	private HashMap<String, Componente> list;
	private HashMap<String, Component> referencias;

	private int[] root = new int[4];

	private int xMax;

	private int yMax;

	public HashMap<String, Component> buildGrade(Grade grade, String filename) {
		this.grid = grade;
		this.referencias = new HashMap<String, Component>();

		parse(filename);

		return this.referencias;
	}

	public void getList(File file, InterfaceGrade destino) {
		this.list = new HashMap<String, Componente>();
		this.xMax = 0;
		this.yMax = 0;

		parse(file);

		destino.add(this.list);
		destino.setXYMax(xMax, yMax);
		destino.setDistancia(root[0], root[1], root[2], root[3]);
	}

	// =============================== LEITURA ===============================

	private transient String value;

	private transient String componentName;

	private transient TipoComponente type;

	private transient TipoAlteracao prop;

	private transient int[] position = new int[4];

	private HashMap<String, Change> properties;

	@Override
	public void startDocument() throws SAXException {
	}

	@Override
	public void startElement(String qName, Attributes atts) {
		TipoComponente tipo = null;
		TipoAlteracao p = null;
		try {
			tipo = TipoComponente.valueOf(qName.toUpperCase());
		} catch (IllegalArgumentException exception) {
			try {
				p = TipoAlteracao.valueOf(qName.toUpperCase());
			} catch (IllegalArgumentException exception2) {
			}
		}

		if (tipo != null && this.type == null) {
			this.type = tipo;
			this.properties = new HashMap<String, Change>();

			componentName = atts.getValue("nome");
			position[0] = Integer.parseInt(atts.getValue("x"));
			position[1] = Integer.parseInt(atts.getValue("y"));
			position[2] = Integer.parseInt(atts.getValue("w"));
			position[3] = Integer.parseInt(atts.getValue("h"));
		} else if (p != null && this.type != null && this.prop == null) {
			if (Arrays.asList(this.type.argumentos).contains(p))
				this.prop = p;
		} else if (qName.equals("raiz")) {
			root[0] = Integer.parseInt(atts.getValue("top"));
			root[1] = Integer.parseInt(atts.getValue("right"));
			root[2] = Integer.parseInt(atts.getValue("bottom"));
			root[3] = Integer.parseInt(atts.getValue("left"));

			if (this.grid != null)
				this.grid.setSeparation(root[0], root[1], root[2], root[3]);
		}
	}

	@Override
	public void characters(String s) {
		this.value = s;
	}

	@Override
	public void endElement(String qName) {
		if (this.prop != null) {
			properties.put(this.prop.name(), new Change(this.prop, getValue(this.prop.classeValor, this.value)));
			this.prop = null;
		} else if (this.type != null) {
			if (grid != null && this.list == null) { // se uma grade está
														// sendo carregada
				JComponent c = AssemblyPlant.addComponent(this.grid, type, componentName, position[0], position[1],
						position[2], position[3], properties);

				if (!componentName.equals(""))
					this.referencias.put(componentName, c);
				else
					System.err.println("Componente sem nome");
			} else if (this.list != null && grid == null) { // se uma lista
															// dos
															// componente da
															// grade está
															// sendo
															// carregada
															// pelo MecaBay
				this.xMax = (this.xMax > position[0] + position[2] ? this.xMax : position[0] + position[2]);
				this.yMax = (this.yMax > position[1] + position[3] ? this.yMax : position[1] + position[3]);

				this.list.put(componentName, new Componente(type, componentName, position[0], position[1], position[2],
						position[3], properties));
			}
			this.type = null;
			this.properties = null;
		}
	}

	@Override
	public void endDocument() throws SAXException {
	}

	// ----------------------------- AUXILIARES -----------------------------

	public static Object getValue(Class<?> classe, String value) {
		if (classe.equals(String.class)) {
			return value;
		} else if (classe.equals(File.class)) {
			return new File(value);
		} else if (classe.equals(AlinHrz.class)) {
			return AlinHrz.valueOf(value.toUpperCase());
		} else if (classe.equals(AlinVrt.class)) {
			return AlinVrt.valueOf(value.toUpperCase());
		} else if (classe.equals(Fonte.class)) {
			return Fonte.valueOf(value.toUpperCase());
		} else if (classe.equals(Integer.class)) {
			return Integer.parseInt(value);
		} else if (classe.equals(Boolean.class)) {
			return Boolean.parseBoolean(value);
		} else if (classe.equals(Orientacao.class)) {
			return Orientacao.valueOf(value.toUpperCase());
		} else
			return null;
	}
}
