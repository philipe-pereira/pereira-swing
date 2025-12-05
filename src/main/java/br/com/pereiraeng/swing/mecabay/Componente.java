package br.com.pereiraeng.swing.mecabay;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

import br.com.pereiraeng.math.Scale2DiOff;
import br.com.pereiraeng.swing.interfaces.Click;
import br.com.pereiraeng.swing.interfaces.DesG;
import br.com.pereiraeng.swing.interfaces.Selec;
import br.com.pereiraeng.swing.mecabay.Change.TipoAlteracao;
import br.com.pereiraeng.xml.XMLserializable;

public class Componente implements DesG, Click, Selec, XMLserializable {
	public enum TipoComponente {
		GRADE(TipoAlteracao.EDICAO, TipoAlteracao.TEXTO_VALOR_1),
		BUTAO(TipoAlteracao.TEXTO_VALOR_1, TipoAlteracao.ACTION, TipoAlteracao.IMAGE_1, TipoAlteracao.AV,
				TipoAlteracao.AH, TipoAlteracao.FONTE, TipoAlteracao.SIZE, TipoAlteracao.B, TipoAlteracao.I,
				TipoAlteracao.EDICAO),
		TOOGLE_BUTAO(TipoAlteracao.TEXTO_VALOR_1, TipoAlteracao.TEXTO_VALOR_2, TipoAlteracao.SELECTED,
				TipoAlteracao.ACTION, TipoAlteracao.IMAGE_1, TipoAlteracao.IMAGE_2, TipoAlteracao.AV, TipoAlteracao.AH,
				TipoAlteracao.FONTE, TipoAlteracao.SIZE, TipoAlteracao.B, TipoAlteracao.I, TipoAlteracao.EDICAO),
		LABEL(TipoAlteracao.TEXTO_VALOR_1, TipoAlteracao.IMAGE_1, TipoAlteracao.AV, TipoAlteracao.AH,
				TipoAlteracao.FONTE, TipoAlteracao.SIZE, TipoAlteracao.B, TipoAlteracao.I),
		TEXTO(TipoAlteracao.TEXTO_VALOR_1, TipoAlteracao.LINHAS, TipoAlteracao.COLUNAS, TipoAlteracao.FONTE,
				TipoAlteracao.SIZE, TipoAlteracao.B, TipoAlteracao.I, TipoAlteracao.EDICAO),
		CHECK(TipoAlteracao.TEXTO_VALOR_1, TipoAlteracao.SELECTED, TipoAlteracao.ACTION, TipoAlteracao.EDICAO),
		SLIDER(TipoAlteracao.ORIENT, TipoAlteracao.TEXTO_VALOR_1, TipoAlteracao.VALOR_MIN, TipoAlteracao.VALOR_MAX,
				TipoAlteracao.STEP),
		SPINNER(TipoAlteracao.TEXTO_VALOR_1, TipoAlteracao.VALOR_MIN, TipoAlteracao.VALOR_MAX, TipoAlteracao.STEP),
		SEPARADOR(TipoAlteracao.ORIENT);

		public TipoAlteracao[] argumentos;

		private TipoComponente(TipoAlteracao... argumentos) {
			this.argumentos = argumentos;
		}
	}

	private TipoComponente type;
	private String name;
	private int x, y, width, height;
	private HashMap<String, Change> changes;

	public Componente(String nome, TipoComponente tipo) {
		this(tipo, nome, 0, 0, 1, 1, null);
	}

	public Componente(TipoComponente type, String name, int x, int y, int width, int height,
			HashMap<String, Change> changes) {
		this.type = type;
		this.name = name;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		if (changes != null)
			this.changes = changes;
		else
			this.changes = new HashMap<String, Change>();
	}

	public String toString() {
		return String.format("[%s, (%d,%d,%d,%d)]", this.type.name().toLowerCase(), x, y, width, height);
	}

	// ------------------- setters and getters -------------------

	public TipoComponente getType() {
		return type;
	}

	public void setTipo(TipoComponente tipo) {
		this.type = tipo;
	}

	public String getNome() {
		return name;
	}

	public void setNome(String nome) {
		this.name = nome;
	}

	// ------------------- POSIÇÃO --------------------------

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public Point getLocation() {
		return new Point(getX(), getY());
	}

	@Override
	public void setPosition(int x, int y) {
		this.setX(x);
		this.setY(y);
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public void setGrade(Scale2DiOff grade) {
		// a grade não muda, logo este método não é utilizado
	}

	// -------------- ALTURA E LARGURA DO OBJETO ------------------

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	// --------------------------------------------------------------

	public void setAlteracoes(HashMap<String, Change> alteracoes) {
		this.changes = alteracoes;
	}

	public Change getChange(String string) {
		return changes.get(string);
	}

	public HashMap<String, Change> getChanges() {
		return changes;
	}

	// ------------------- interface Desenhável -------------------

	public void drawObject(Graphics2D g) {
		switch (this.type) {
		case GRADE:
			g.setColor(Color.BLUE);
			g.fillRect(MecaBay.GRADE * this.x, MecaBay.GRADE * this.y, width * MecaBay.GRADE, height * MecaBay.GRADE);
			break;
		case BUTAO:
			g.setColor(Color.GRAY);
			g.fillRect(2 + MecaBay.GRADE * this.x, 2 + MecaBay.GRADE * this.y, width * MecaBay.GRADE - 4,
					height * MecaBay.GRADE - 4);
			g.setColor(Color.BLACK);
			g.drawRect(2 + MecaBay.GRADE * this.x, 2 + MecaBay.GRADE * this.y, width * MecaBay.GRADE - 4,
					height * MecaBay.GRADE - 4);
			break;
		case CHECK:
			g.setColor(Color.WHITE);
			g.fillRect(2 + MecaBay.GRADE * this.x, 2 + MecaBay.GRADE * this.y, width * MecaBay.GRADE - 4,
					height * MecaBay.GRADE - 4);
			g.setColor(Color.BLACK);
			g.drawRect(2 + MecaBay.GRADE * this.x, 2 + MecaBay.GRADE * this.y, width * MecaBay.GRADE - 4,
					height * MecaBay.GRADE - 4);
			break;
		case LABEL:
			g.setColor(Color.GRAY);
			g.fillRect(MecaBay.GRADE * this.x, MecaBay.GRADE * this.y, width * MecaBay.GRADE, height * MecaBay.GRADE);
			break;
		case SEPARADOR:
			int x1 = (int) (MecaBay.GRADE * (this.x + 0.5f));
			int y1 = (int) (MecaBay.GRADE * (this.y + 0.5f));

			g.setColor(Color.BLACK);
			g.drawLine(x1, y1, x1 + MecaBay.GRADE * (this.width - 1), y1 + MecaBay.GRADE * (this.height - 1));
			break;
		case SPINNER:
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(MecaBay.GRADE * this.x, MecaBay.GRADE * this.y, width * MecaBay.GRADE / 2,
					height * MecaBay.GRADE);

			g.setColor(Color.GRAY);
			g.fillRect((int) (MecaBay.GRADE * (this.x + 0.5f)), MecaBay.GRADE * this.y, width * MecaBay.GRADE / 2,
					height * MecaBay.GRADE / 2);
			g.fillRect((int) (MecaBay.GRADE * (this.x + 0.5f)), (int) (MecaBay.GRADE * (this.y + 0.5f)),
					width * MecaBay.GRADE / 2, height * MecaBay.GRADE / 2);
			g.setColor(Color.BLACK);
			g.drawRect((int) (MecaBay.GRADE * (this.x + 0.5f)), MecaBay.GRADE * this.y, width * MecaBay.GRADE / 2,
					height * MecaBay.GRADE / 2);
			g.drawRect((int) (MecaBay.GRADE * (this.x + 0.5f)), (int) (MecaBay.GRADE * (this.y + 0.5f)),
					width * MecaBay.GRADE / 2, height * MecaBay.GRADE / 2);
			break;
		case TEXTO:
			g.setColor(Color.WHITE);
			g.fillRect(2 + MecaBay.GRADE * this.x, 2 + MecaBay.GRADE * this.y, width * MecaBay.GRADE - 4,
					height * MecaBay.GRADE - 4);
			g.setColor(Color.BLACK);
			g.drawRect(2 + MecaBay.GRADE * this.x, 2 + MecaBay.GRADE * this.y, width * MecaBay.GRADE - 4,
					height * MecaBay.GRADE - 4);
			break;
		case SLIDER:
			x1 = (int) (MecaBay.GRADE * (this.x + 0.5f));
			y1 = (int) (MecaBay.GRADE * (this.y + 0.5f));

			g.setColor(Color.BLACK);
			g.drawLine(x1, y1, x1 + MecaBay.GRADE * (this.width - 1), y1 + MecaBay.GRADE * (this.height - 1));
			g.setColor(Color.RED);
			g.fillOval(x1 - 4, y1 - 4, 8, 8);
			break;
		case TOOGLE_BUTAO:
			g.setColor(Color.GRAY);
			g.fillRect(2 + MecaBay.GRADE * this.x, 2 + MecaBay.GRADE * this.y, width * MecaBay.GRADE - 4,
					height * MecaBay.GRADE - 4);
			g.setColor(Color.BLACK);
			g.drawRect(2 + MecaBay.GRADE * this.x, 2 + MecaBay.GRADE * this.y, width * MecaBay.GRADE - 4,
					height * MecaBay.GRADE - 4);
			break;
		default:
			break;
		}
	}

	public void setDrawable(boolean drawable) {
	}

	public boolean isDrawable() {
		return true;
	}

	// interface Clicavel
	public boolean isOn(int x, int y) {
		return getClickableArea().contains(x, y);
	}

	public Area getClickableArea() {
		return new Area(new Rectangle2D.Float(MecaBay.GRADE * this.x, MecaBay.GRADE * this.y, width * MecaBay.GRADE,
				height * MecaBay.GRADE));
	}

	// ------------------- interface Selecionavel -------------------

	private boolean selecao = false;
	private Color color = Color.GREEN;

	public void setSelected(boolean on) {
		this.selecao = on;
	}

	public boolean isSelected() {
		return this.selecao;
	}

	public void drawSelection(Graphics2D g) {
		if (isSelected()) {
			g.setColor(color);
			g.drawRect(MecaBay.GRADE * this.x, MecaBay.GRADE * this.y, width * MecaBay.GRADE, height * MecaBay.GRADE);
		}
	}

	public void setCorDaSelecao(Color color) {
		this.color = color;
	}

	// -------------------------- XML --------------------------

	public String getXML() {
		String out = "<" + this.type.name().toLowerCase() + " nome=\"" + this.name + "\" x=\"" + this.x + "\" y=\""
				+ this.y + "\" w=\"" + this.width + "\" h=\"" + this.height + "\">\n";
		for (Change t : changes.values())
			out += t.getXML();
		out += "</" + this.type.name().toLowerCase() + ">\n";
		return out;
	}
}