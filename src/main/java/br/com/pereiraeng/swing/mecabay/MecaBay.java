package br.com.pereiraeng.swing.mecabay;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import br.com.pereiraeng.io.IOutils;
import br.com.pereiraeng.math.Scale2Di;
import br.com.pereiraeng.swing.App;
import br.com.pereiraeng.swing.Grade;
import br.com.pereiraeng.swing.LeafOG;
import br.com.pereiraeng.swing.SwingUtils;
import br.com.pereiraeng.swing.XMLmenuBarReader;
import br.com.pereiraeng.swing.input.file.FileChooser;
import br.com.pereiraeng.swing.input.file.FileFilterAdapter;
import br.com.pereiraeng.swing.interfaces.Click;
import br.com.pereiraeng.swing.interfaces.Selec;
import br.com.pereiraeng.swing.mecabay.Change.TipoAlteracao;
import br.com.pereiraeng.swing.mecabay.Componente.TipoComponente;
import br.com.pereiraeng.swing.scroll.JanelaRolavel;
import br.com.pereiraeng.swing.scroll.Quina;
import br.com.pereiraeng.swing.scroll.ReguaDiscreta;
import br.com.pereiraeng.swing.table.EditableTable;

public class MecaBay implements InterfaceGrade, App, ActionListener, ChangeListener, CaretListener, TableModelListener {
	private JInternalFrame frame;

	private File arquivoAberto;
	private HashMap<String, Componente> comps;

	public static final int GRADE = 25;
	public int rows = 10, columns = 10;
	private int top = 0, right = 0, bottom = 0, left = 0;

	private JComboBox<TipoComponente> components;
	private HashMap<String, Component> ref1, ref;

	public void build(Component c) {
		this.frame = (JInternalFrame) c;

		// barra de menu
		(new XMLmenuBarReader()).createMenu(frame, MecaBay.class.getResourceAsStream("MecaBayMenu.xml"), this);

		// aspecto geral do programa

		Grade controle = new Grade();
		ref1 = (new XMLgradeReader()).buildGrade(controle,
				ClassLoader.getSystemResource("constelacao/mecabay/MecaBay.xml").getFile());

		// caixa de componentes
		components = new JComboBox<TipoComponente>(TipoComponente.values());
		((Grade) controle).add(components, 0, 0, 5, 1);

		((JButton) ref1.get("new")).addActionListener(this);
		((JButton) ref1.get("delete")).addActionListener(this);

		((JSpinner) ref1.get("MCtop")).addChangeListener(this);
		((JSpinner) ref1.get("MCright")).addChangeListener(this);
		((JSpinner) ref1.get("MCbottom")).addChangeListener(this);
		((JSpinner) ref1.get("MCleft")).addChangeListener(this);
		((JSpinner) ref1.get("MCrows")).addChangeListener(this);
		((JSpinner) ref1.get("MCcolumns")).addChangeListener(this);

		Grade comp = buildTelaComponente();
		controle.add(comp, 0, 1, 6, 1);

		// painel de desenho
		FolhaComponentes f = new FolhaComponentes();
		Dimension d = f.getPreferredSize();

		this.janela = new JanelaRolavel(f, new Dimension(500, 400), new ReguaDiscreta(true, d.height, GRADE),
				new ReguaDiscreta(false, d.width, GRADE), new Quina(Color.RED), new Quina(Color.RED),
				new Quina(Color.RED));
		controle.add(this.janela, 6, 0, 1, 12);

		frame.getContentPane().add(controle);
	}

	// ----- App -----
	@Override
	public String getTitle() {
		return "Meca Bay 2.0";
	}

	@Override
	public boolean isResizable() {
		return true;
	}

	@Override
	public boolean isMaximizable() {
		return true;
	}

	@Override
	public Dimension getWindowSize() {
		return new Dimension(720, 460);
	}

	@Override
	public void open(String file) {
	}

	@Override
	public void start() {
	}

	@Override
	public void close() {
	}

	// FOLHA DE COMPONENTES

	private void set(String nome, int valor) {
		if (nome.endsWith("top")) {
			top = valor;
		} else if (nome.endsWith("right")) {
			right = valor;
		} else if (nome.endsWith("bottom")) {
			bottom = valor;
		} else if (nome.endsWith("left")) {
			left = valor;
		} else {
			if (nome.endsWith("rows")) {
				rows = valor;
				JSpinner spinner = (JSpinner) ref.get("ySpinner");
				((SpinnerNumberModel) spinner.getModel()).setMaximum(rows - 1);
			} else if (nome.endsWith("columns")) {
				columns = valor;
				JSpinner spinner = (JSpinner) ref.get("xSpinner");
				((SpinnerNumberModel) spinner.getModel()).setMaximum(columns - 1);
			}
			janela.setFolhaSize(new Dimension(GRADE * columns, GRADE * rows));
		}
	}

	private JanelaRolavel janela;
	private Selec selecionado1, selecionado2;

	private class FolhaComponentes extends LeafOG<Componente> {
		private static final long serialVersionUID = 1L;

		public FolhaComponentes() {
			super(Color.WHITE, 10, 10, true, new Scale2Di(GRADE, GRADE));
			comps = new HashMap<>();
		}

		public Collection<Componente> getList() {
			return comps.values();
		}

		// ------------------ MOUSE ------------------

		public void mouseClicked(MouseEvent event) {
			// selecao de um elemento
			if (selecionado1 != null) {
				if (!selecionado1.equals(selecionado2)) {
					if (selecionado2 != null) {
						if (selecionado2 instanceof Componente) {
							Componente c = (Componente) selecionado2;
							c.setCorDaSelecao(Color.GREEN);
							c.setSelected(false);
						}
					}
					selecionado2 = selecionado1;
					if (selecionado2 instanceof Componente) {
						Componente c = (Componente) selecionado2;
						c.setCorDaSelecao(Color.BLUE);
						c.setSelected(true);

						set(c);
					}
				}
			} else {
				if (selecionado2 != null) {
					if (selecionado2 instanceof Componente) {
						Componente c = (Componente) selecionado2;
						c.setCorDaSelecao(Color.GREEN);
						c.setSelected(false);
						selecionado2 = null;
					}
				}
				set(null);
			}
		}

		@Override
		public void mouseMoved(MouseEvent event) {
			super.mouseMoved(event);

			// atualizaçao da posicao das réguas
			janela.setMousePosition(event.getPoint());

			Click mouseSobre = null;

			if (super.underPointer.size() > 0)
				mouseSobre = super.underPointer.get(0);

			if (mouseSobre != null) {
				if (selecionado1 instanceof Click ? !mouseSobre.equals((Click) selecionado1) : true) {
					if (selecionado1 != null)
						if (!selecionado1.equals(selecionado2))
							selecionado1.setSelected(false);

					selecionado1 = (Selec) mouseSobre;
					selecionado1.setSelected(true);
				}
			} else {
				if (selecionado1 != null)
					if (!selecionado1.equals(selecionado2))
						selecionado1.setSelected(false);
			}

			// repintar
			repaint();
		}

		@Override
		public void mouseExited(MouseEvent event) {
			super.mouseExited(event);

			// atualizaçao da posicao das réguas
			janela.setMousePosition(new Point(super.x_mouse, super.y_mouse));
		}

		@Override
		protected void drawBackground(Graphics2D g) {
		}

		@Override
		protected void drawForeground(Graphics2D g) {
		}

		@Override
		protected boolean isDragable() {
			return false;
		}

		@Override
		protected void setDragPostion(Click c, int x, int y) {
		}
	}

	// ------- IO -------

	private void newFile() {
		set(null);
		this.arquivoAberto = null;
		this.janela.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(""),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
	}

	private void load(boolean start) {
		if (start) {

		} else {
			if (this.arquivoAberto != null)
				set(null);

			this.arquivoAberto = FileChooser.fileChooserLoad(null, new FileFilterAdapter("xml"),
					SwingUtils.getWindow(frame));
			if (this.arquivoAberto != null) {
				(new XMLgradeReader()).getList(this.arquivoAberto, this);
				this.janela.repaint();
				this.janela.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createTitledBorder(this.arquivoAberto.getName()),
						BorderFactory.createEmptyBorder(5, 5, 5, 5)));
			}
		}
	}

	private void save(boolean newFile) {
		if (newFile) {
			File novo = FileChooser.fileChooserSave(null, "xml", SwingUtils.getWindow(frame));
			if (novo != null) {
				IOutils.writeFile(novo,
						(new XMLGradeWriter<Componente>()).getText(top, right, bottom, left, comps.values()));
				if (this.arquivoAberto == null)
					this.arquivoAberto = novo;
			}
		} else {
			if (this.arquivoAberto == null)
				this.save(true);
			else
				IOutils.writeFile(this.arquivoAberto,
						(new XMLGradeWriter<Componente>()).getText(top, right, bottom, left, comps.values()));
		}
	}

	// ----- InterfaceGrade -----

	public void add(HashMap<String, Componente> lista) {
		comps.putAll(lista);
	}

	public void setDistancia(int top, int right, int bottom, int left) {
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		this.left = left;
	}

	public void setXYMax(int xMax, int yMax) {
		((JSpinner) ref1.get("MCcolumns")).setValue(xMax);
		((JSpinner) ref1.get("MCrows")).setValue(yMax);
	}

	// COMPONENTE

	private EditableTable changes;
	private Componente object;

	private Grade buildTelaComponente() {
		Grade g = new Grade();
		this.ref = (new XMLgradeReader()).buildGrade(g,
				ClassLoader.getSystemResource("constelacao/mecabay/Componente.xml").getFile());
		changes = new EditableTable();
		changes.setPreferredSize(new Dimension(200, 100));
		changes.setRowHeight(20);
		changes.setRowHeaderWidth(60);
		changes.setColumnsWidth(200);
		changes.addTableModelListener(this);

		g.add(changes, 0, 6, 5, 1);

		((JSpinner) ref.get("xSpinner")).addChangeListener(this);
		((JSpinner) ref.get("ySpinner")).addChangeListener(this);
		((JSpinner) ref.get("wSpinner")).addChangeListener(this);
		((JSpinner) ref.get("hSpinner")).addChangeListener(this);
		((JTextField) ref.get("nome")).addCaretListener(this);

		this.set(this.object);

		return g;
	}

	private boolean internalChanges = false;

	public void get() {
		if (object != null) {
			object.setPosition((Integer) ((JSpinner) ref.get("xSpinner")).getValue(),
					(Integer) ((JSpinner) ref.get("ySpinner")).getValue());
			object.setWidth((Integer) ((JSpinner) ref.get("wSpinner")).getValue());
			object.setHeight((Integer) ((JSpinner) ref.get("hSpinner")).getValue());
		}
	}

	public void get(String nome) {
		if (this.object != null) {
			if (nome.equals("xSpinner")) {
				object.setX((Integer) ((JSpinner) ref.get(nome)).getValue());
			} else if (nome.equals("ySpinner")) {
				object.setY((Integer) ((JSpinner) ref.get(nome)).getValue());
			} else if (nome.equals("wSpinner")) {
				object.setWidth((Integer) ((JSpinner) ref.get(nome)).getValue());
			} else if (nome.equals("hSpinner")) {
				object.setHeight((Integer) ((JSpinner) ref.get(nome)).getValue());
			} else if (nome.equals("nome")) {
				object.setNome(((JTextField) ref.get(nome)).getText());
			}
		}
	}

	public void set(Componente componente) {
		this.object = componente;
		internalChanges = true;
		if (this.object != null) {
			((JLabel) ref.get("tipo")).setText(object.getType().name());
			((JTextField) ref.get("nome")).setText(object.getNome());
			((JSpinner) ref.get("xSpinner")).setValue(object.getX());
			((JSpinner) ref.get("ySpinner")).setValue(object.getY());
			((JSpinner) ref.get("wSpinner")).setValue(object.getWidth());
			((JSpinner) ref.get("hSpinner")).setValue(object.getHeight());
			loadTabela();
		} else {
			((JLabel) ref.get("tipo")).setText("");
			((JTextField) ref.get("nome")).setText("");
			((JSpinner) ref.get("xSpinner")).setValue(0);
			((JSpinner) ref.get("ySpinner")).setValue(0);
			((JSpinner) ref.get("wSpinner")).setValue(1);
			((JSpinner) ref.get("hSpinner")).setValue(1);
		}
		internalChanges = false;
	}

	public void loadTabela() {
		Object[][] values = new Object[object.getType().argumentos.length][];
		String[] properties = new String[object.getType().argumentos.length];

		for (int i = 0; i < object.getType().argumentos.length; i++) {
			TipoAlteracao p = object.getType().argumentos[i];
			// row header
			properties[i] = p.name();

			// cell
			Change c = object.getChange(p.name());
			Object value = null;
			if (c != null)
				value = c.getValue();
			else
				value = Change.getParDefault(p);

			values[i][0] = value;
		}

		changes.setDataVector(values, new String[] { "Valor" });
		changes.setRowIdentifiers(properties);
	}

	// ----- LISTENER -----

	private int numeracao = 0;

	public void actionPerformed(ActionEvent event) {
		String action = event.getActionCommand();
		if (action.equals("new")) {
			newFile();
		} else if (action.equals("open")) {
			load(false);
		} else if (action.equals("save")) {
			save(false);
		} else if (action.equals("saveAs")) {
			save(true);
		} else if (action.equals("exit")) {
			frame.dispose();
		} else if (action.equals("newComponent")) {
			TipoComponente t = (TipoComponente) components.getSelectedItem();
			String nome = "novo " + (numeracao++);
			Componente c = new Componente(nome, t);
			comps.put(nome, c);
		} else if (action.equals("delete")) {
			if (selecionado2 instanceof Componente) {
				Componente c = (Componente) selecionado2;
				comps.remove(c.getNome());
				set(null);
			}
		}
	}

	public void stateChanged(ChangeEvent event) {
		JSpinner spinner = (JSpinner) event.getSource();
		String name = spinner.getName();
		if (name.startsWith("MC")) {
			set(name, (Integer) spinner.getValue());
			janela.repaint();
		} else {
			if (this.object != null && !this.internalChanges) {
				get(((JSpinner) event.getSource()).getName());
			}
		}
	}

	public void caretUpdate(CaretEvent event) {
		if (this.object != null && !this.internalChanges) {
			get(((JTextField) event.getSource()).getName());
		}
	}

	public void tableChanged(TableModelEvent event) {
		if (event.getSource() instanceof AbstractTableModel) {
			AbstractTableModel mdt = (AbstractTableModel) event.getSource();
			int i = event.getFirstRow();
			Change p = null;
			if (i >= 0 && i < mdt.getRowCount())
				p = (Change) mdt.getValueAt(event.getFirstRow(), 0);
			if (p != null)
				this.object.getChanges().put(((TipoAlteracao) p.getType()).name(), p);
		}
	}
}