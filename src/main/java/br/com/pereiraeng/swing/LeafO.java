package br.com.pereiraeng.swing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import br.com.pereiraeng.swing.interfaces.AreaSelection;
import br.com.pereiraeng.swing.interfaces.Click;
import br.com.pereiraeng.swing.interfaces.Clock;
import br.com.pereiraeng.swing.interfaces.Des;
import br.com.pereiraeng.swing.interfaces.Prop;
import br.com.pereiraeng.swing.interfaces.Selec;
import br.com.pereiraeng.swing.selection.SelectionEvent;
import br.com.pereiraeng.swing.selection.SelectionListener;

/**
 * Painel sobre o qual serão desenhados objetos. Faz parte do grupo
 * <strong>B</strong>lack<strong>b</strong>oar<strong>d</strong>
 * 
 * @author Philipe PEREIRA
 * 
 * @param <T> subclasse de {@link Des Des} indicando o tipo de objeto a ser
 *            desenhado
 */
public abstract class LeafO<T extends Des> extends Leaf
		implements ActionListener, MouseListener, MouseMotionListener, AreaSelection {
	private static final long serialVersionUID = 1L;

	/**
	 * Construtor da tela de desenhos, onde estes são feitos por objetos
	 * 
	 * @param background cor de fundo do painel, visível somente se o parâmetro
	 *                   <code>opaque</code> for <code>true</code>
	 * @param width      largura do painel, em pixels
	 * @param height     altura do painel, em pixels
	 * @param opaque     se <code>true</code> o painel será opaco, sem pintado com a
	 *                   cor estipulada no primeiro argumento
	 */
	public LeafO(Color background, int width, int height, boolean opaque) {
		super(background, width, height, opaque);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	/**
	 * Função que retorna ao componente gráfico a lista de objetos a serem
	 * desenhados
	 * 
	 * @return relação de objetos a serem desenhados sobre o painel
	 */
	public abstract Collection<T> getList();

	/**
	 * Função que retorna ao componente gráfico a lista de todos os objetos,
	 * desenhados ou invisíveis. Essa função alimenta o
	 * {@link #getObjectsAt(int, int) mecanismo de busca} dos objetos sobre um dado
	 * ponto
	 * 
	 * @return relação de todos os objetos clicáveis
	 */
	protected Collection<Click> getClickList() {
		Collection<Click> out = null;
		Collection<T> ds = getList();
		if (ds != null) {
			if (!sent)
				setDrawInfo(ds);
			out = getClicks(ds);
		}
		return out;
	}

	protected static <T extends Des> Collection<Click> getClicks(Collection<T> ds) {
		Collection<Click> out = new LinkedList<Click>();
		for (Des d : ds)
			if (d.isDrawable() ? d instanceof Click : false)
				out.add((Click) d);
		return out;
	}

	// -------------------------- DRAWER --------------------------

	public final void draw(Graphics2D g) {
		this.drawBackground(g);

		this.drawObjects(g);

		if (this.track != null)
			drawTracking(g);

		if (this.selectedExtreme != null)
			drawSelectionRect(g);

		this.drawForeground(g);
	}

	/**
	 * Função que coordena o processo de desenho atrás (ou seja, esta é a primeira
	 * função a ser chamada)
	 * 
	 * @param g objeto gráfico
	 */
	protected abstract void drawBackground(Graphics2D g);

	protected final void drawObjects(Graphics2D g) {
		try {
			Collection<T> ds = getList();
			if (ds != null) {
				if (!sent)
					setDrawInfo(ds);
				for (Des d : ds)
					if (shouldDraw(d))
						this.drawObject(g, d);
				sent = false;
			}
		} catch (ConcurrentModificationException c) {
			System.out.println("!");
		}
	}

	protected final void drawObject(Graphics2D g, Des d) {
		d.drawObject(g);
		if (d instanceof Selec) {
			Selec s = (Selec) d;
			if (s.isSelected())
				s.drawSelection(g);
		}
	}

	/**
	 * Função que coordena o processo de desenho na frente (ou seja, esta é a última
	 * função a ser chamada)
	 * 
	 * @param g objeto gráfico
	 */
	protected abstract void drawForeground(Graphics2D g);

	// métodos "sobrescrevíveis"

	/**
	 * <code>true</code> se objetos a serem desenhados já receberam os dados
	 * referentes ao processo de desenho, <code>false</code> senão
	 */
	protected transient boolean sent = false;

	/**
	 * Função que envia para os objetos as informações necessária para que eles se
	 * desenhem.<br>
	 * Nesta classe, essa função simplesmente indica que o envio foi feito, porém
	 * alguns das suas subclasses (principalmente
	 * {@link LeafOG#setDrawInfo(Collection)} e
	 * {@link LeafOM#setDrawInfo(Collection)}) <strong>devem sobreescrever esse
	 * método</strong> repassando para seus objetos as informações para que eles se
	 * desenhem
	 * 
	 * @param objs coleção de objetos a serem desenhados
	 */
	protected void setDrawInfo(Collection<T> list) {
		this.sent = true;
		// objetos Prop
		Dimension t = new Dimension(this.getWidth(), this.getHeight());
		for (Des d : list)
			if (d instanceof Prop)
				((Prop) d).leafSize(t);
		// objetos Clock
		if (tm != null) {
			t = new Dimension(tm.getT(), tm.getTmax());
			for (Des d : list)
				if (d instanceof Clock)
					((Clock) d).setT(t);
		}
	}

	/**
	 * Função que avalia se um dado objeto desenhável deve ser desenhado ou não
	 * 
	 * @param d objeto a ser avaliado
	 * @return <code>true</code> se ele vai ser desenhado, <code>false</code> senão
	 */
	protected boolean shouldDraw(Des d) {
		return d.isDrawable();
	}

	// -------------------------- TRACK -------------------------

	protected transient Color track = null;

	protected static final BasicStroke TRACK_STROKE = new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
			10f, new float[] { 10f }, 0f);

	public void setTrack(Color track) {
		this.track = track;
	}

	protected void drawTracking(Graphics2D g) {
		if (x_mouse != -1 && y_mouse != -1) {
			g.setStroke(TRACK_STROKE);
			g.setColor(track);

			g.drawLine(0, y_mouse, this.getWidth(), y_mouse);
			g.drawLine(x_mouse, 0, x_mouse, this.getHeight());

			g.setStroke(new BasicStroke());
		}
	}

	// ----------------------- DRAG N' DROP, AREA SELECTION -----------------------

	protected transient Click drag;

	protected abstract boolean isDragable();

	protected Point selectedExtreme = null;

	protected AreaSelection as;

	protected void setAreaSelection(AreaSelection as) {
		this.as = as;
	}

	@Override
	public void setSelectedArea(Point2D.Float min, Point2D.Float max) {
		Collection<Click> ds = getClickList();
		if (ds != null) {
			List<Click> inTheBox = new LinkedList<>();
			Rectangle2D r = new Rectangle2D.Float(min.x, min.y, max.x - min.x, max.y - min.y);
			for (Click c : ds) {
				Area a = c.getClickableArea();
				if (a.intersects(r))
					inTheBox.add(c);
			}
			if (inTheBox.size() > 0)
				fireSelectionPerformed(true, new SelectionEvent(this, inTheBox));
			else
				fireSelectionPerformed(false, new SelectionEvent(this, (int) max.x, (int) max.y));
		}
	}

	/**
	 * Função que muda a posição de um objeto {@link Click arrastável}
	 * 
	 * @param c objeto a ser arrastado
	 * @param x nova abscissa, em pixels
	 * @param y nova ordenada, em pixels
	 */
	protected abstract void setDragPostion(Click c, int x, int y);

	@Override
	public void mousePressed(MouseEvent event) {
		if (underPointer.size() > 0 && isDragable())
			drag = (Click) underPointer.get(0);
	}

	@Override
	public void mouseDragged(MouseEvent event) {
		if (drag != null) {
			setDragPostion(drag, event.getX(), event.getY());
			super.repaint();
		} else if (SwingUtilities.isRightMouseButton(event) && as != null) {
			selectedExtreme = new Point(event.getX(), event.getY());
			super.repaint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		if (drag != null)
			drag = null;
		else if (as != null) {
			// area selection (se já não foi chamada em LeafOM e LeafOG)
			if (selectedExtreme != null) {
				as.setSelectedArea(
						new Point2D.Float(Math.min(x_mouse, selectedExtreme.x), Math.min(y_mouse, selectedExtreme.y)),
						new Point2D.Float(Math.max(x_mouse, selectedExtreme.x), Math.max(y_mouse, selectedExtreme.y)));
				selectedExtreme = null;
				super.repaint();
			}
		}
	}

	private void drawSelectionRect(Graphics2D g) {
		g.drawRect(Math.min(x_mouse, selectedExtreme.x), Math.min(y_mouse, selectedExtreme.y),
				Math.abs(x_mouse - selectedExtreme.x), Math.abs(y_mouse - selectedExtreme.y));
	}

	// -------------------------- MOUSE --------------------------

	// fonctions 'mouvement'
	protected transient int x_mouse, y_mouse;

	/**
	 * Lista de objetos do tipo {@link Click} que estão debaixo do ponteiro do mouse
	 */
	protected transient LinkedList<Click> underPointer;

	protected transient Cursor currentCursor = Cursor.getDefaultCursor();

	@Override
	public void mouseEntered(MouseEvent event) {
		this.mouseMoved(event);
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		this.x_mouse = event.getX();
		this.y_mouse = event.getY();

		// selecao
		this.underPointer = this.getObjectsAt(x_mouse, y_mouse);

		super.setCursor(this.underPointer.size() > 0 ? Cursor.getPredefinedCursor(Cursor.HAND_CURSOR) : currentCursor);

		if (track != null)
			repaint();
	}

	/**
	 * Retorna o objeto que foi desenhado por último sobre um determinado ponto
	 * 
	 * @return objeto por cima do ponto onde está o mouse
	 */
	public Click getInFront() {
		Click out = null;
		if (underPointer != null ? underPointer.size() > 0 : false)
			out = underPointer.getLast();
		return out;
	}

	@Override
	public void mouseExited(MouseEvent event) {
		this.x_mouse = -1;
		this.y_mouse = -1;
		super.repaint();
	}

	/**
	 * Função que retorna a lista de objetos que foram desenhados sobre um
	 * determinado ponto
	 * 
	 * @param x coordenada x
	 * @param y coordenada y
	 * @return lista de objetos sobre o ponto
	 */
	protected LinkedList<Click> getObjectsAt(int x, int y) {
		LinkedList<Click> out = new LinkedList<Click>();
		try {
			Collection<Click> ds = getClickList();
			if (ds != null)
				for (Click c : ds)
					if (c.isOn(x, y))
						out.add(c);
			// 28/11/2020 sent = false;
			// 03/02/2021
			sent = false;
		} catch (ConcurrentModificationException e) {
			System.out.println("!");
		}
		return out;
	}

	// -------------------------- SELECTION --------------------------

	private transient boolean hasSelectionListener = false;

	public void addSelectionListener(SelectionListener selectionListener) {
		if (selectionListener != null) {
			super.listenerList.add(SelectionListener.class, selectionListener);
			this.hasSelectionListener = true;
		}
	}

	public static final int SINGLE_SELECTION = 1;

	public static final int MULTI_SELECTION = 2;

	public void setSelectionMode(int mode) {
		switch (mode) {
		case SINGLE_SELECTION:
			setAreaSelection(null);
			break;
		case MULTI_SELECTION:
			setAreaSelection(this);
			break;
		}
	}

	private transient LinkedList<Click> popupItems;

	@Override
	public void mouseClicked(MouseEvent e) {
		if (hasSelectionListener) {
			// se deseja-se tratar os eventos de seleção de objetos...
			if (this.underPointer != null ? this.underPointer.size() > 0 : false) {
				// se há algum elemento sob o ponteiro do mouse...
				if (this.underPointer.size() == 1) // se só houver só um elemento, ele é selecionado
					fireSelectionPerformed(true, new SelectionEvent(this, this.getInFront(), e.getModifiersEx()));
				else {
					// se houver mais de um elemento, então abre a caixa pop-up
					// para decidir quais será selecionado
					JPopupMenu popup = new JPopupMenu();

					// cria-se uma lista de objetos que estão no local
					if (popupItems == null)
						popupItems = new LinkedList<>();
					else
						popupItems.clear();

					int j = 0;
					for (Click d : this.underPointer) {
						popupItems.add(d);

						JMenuItem option = new JMenuItem(d.toString());
						option.addActionListener(this);
						option.setActionCommand(String.valueOf(j++));
						popup.add(option);
					}
					popup.show(this, e.getX(), e.getY());
				}
			} else // ... se não houver nenhum objeto sob o ponteiro
				fireSelectionPerformed(false, new SelectionEvent(this, e.getX(), e.getY()));
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		fireSelectionPerformed(true, new SelectionEvent(this,
				this.popupItems.get(Integer.parseInt(event.getActionCommand())), event.getModifiers()));
		popupItems.clear();
	}

	protected void fireSelectionPerformed(boolean selected, SelectionEvent event) {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == SelectionListener.class) {
				SelectionListener sl = (SelectionListener) listeners[i + 1];
				if (selected)
					sl.selectElement(event);
				else
					sl.deselectElement(event);
			}
		}
	}
}