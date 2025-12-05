package br.com.pereiraeng.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.ButtonGroup;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;

import br.com.pereiraeng.swing.interfaces.DesM;
import br.com.pereiraeng.swing.interfaces.TakePoint;
import br.com.pereiraeng.swing.interfaces.WL;

/**
 * Painel sobre o qual serão desenhados objetos de tamanho e posição variável.
 * Faz parte do grupo
 * <strong>B</strong>lack<strong>b</strong>oar<strong>d</strong>
 * 
 * @author Philipe Pereira
 * 
 * @param <T> subclasse de {@link DesM DesM} indicando o tipo de objeto a ser
 *            desenhado
 */
public abstract class LeafOM<T extends DesM> extends LeafO<T> implements MouseWheelListener, KeyListener, WL {
	private static final long serialVersionUID = 1L;

	public static Color black = Color.BLACK, white = Color.WHITE, lightGray = Color.LIGHT_GRAY, gray = Color.GRAY;

	/**
	 * Coordenadas, na unidade de medida relativa ao desenho, da quina superior
	 * esquerda da janela
	 */
	protected transient Point2D.Float corner;

	/**
	 * comprimento, na unidade de medida relativa ao desenho, da janela
	 */
	protected transient float dx = Float.NaN;

	/**
	 * altura, na unidade de medida relativa ao desenho, da janela
	 */
	protected transient float dy = Float.NaN;

	/**
	 * maior abscissa dos objetos nesta janela, na unidade de medida relativa ao
	 * desenho
	 */
	private transient float xM = Float.NEGATIVE_INFINITY;

	/**
	 * menor abscissa dos objetos nesta janela, na unidade de medida relativa ao
	 * desenho
	 */
	private transient float xm = Float.POSITIVE_INFINITY;

	/**
	 * maior ordenada dos objetos nesta janela, na unidade de medida relativa ao
	 * desenho
	 */
	private transient float yM = Float.NEGATIVE_INFINITY;

	/**
	 * menor ordenada dos objetos nesta janela, na unidade de medida relativa ao
	 * desenho
	 */
	private transient float ym = Float.POSITIVE_INFINITY;

	/**
	 * Se <code>true</code>, a razão de pixels por faixa de coordenada é igual para
	 * ambos os eixos
	 */
	private boolean proportional;

	/**
	 * Se <code>true</code>, é possível alterar a razão de pixels por faixa de
	 * coordenada (sendo possível aumentar ou diminiur o zoom). Se
	 * <code>false</code> isto não é possível
	 */
	private boolean zoomable;

	/**
	 * Se <code>true</code>, a área mostrada na tela inclui o zero do mapa
	 */
	private boolean showZero;

	/**
	 * se <code>true</code>, é possível mudar a forma como o zoom é modificado. Se
	 * <code>false</code>, o zoom é modifidado sempre da mesma maneira
	 */
	private boolean changeAxisZoom;

	/**
	 * Se <code>true</code>, a troca entre os possíveis {@link #axisZoom zooms} se
	 * dá através de um pop-up. Se <code>false</code>, a cada clique alterna entre
	 * uma modalidade de zoom. Só faz diferença se {@link #changeAxisZoom} for
	 * <code>true</code>
	 */
	private boolean zoomPopUp = false;

	/**
	 * Zoom altera:
	 * <ol start="0">
	 * <li>os dois eixos;</i>
	 * <li>somente o horizontal;</i>
	 * <li>somente o vertical.</i>
	 * </ol>
	 * 
	 * Só pode ser alterado se {@link #changeAxisZoom} for <code>true</code>
	 */
	private int axisZoom = 0;

	/**
	 * Porcentagem de acréscimo da área mostrada em comparação com o mínimo que
	 * engloba os objetos.
	 */
	public float drawMargin = 0.1f;

	private static final float ZOOM_SPEED = 0.03f;

	/**
	 * Construtor do painel de objetos de tamanho e posições variáveis
	 * 
	 * @param background   cor de fundo
	 * @param width        largura, em pixels
	 * @param height       altura, em pixels
	 * @param opaque       se <code>true</code>, o painel é opaco
	 * @param translation  se <code>true</code>, o painel pode ser deslocado por
	 *                     arrasto do mouse
	 * @param proportional se <code>true</code>, a razão de pixels por faixa de
	 *                     coordenada é igual para ambos os eixos
	 * @param zoomable     se <code>true</code>, é possível alterar a razão de
	 *                     pixels por faixa de coordenada (não sendo possível
	 *                     aumentar ou diminiur o zoom). Se <code>false</code> isto
	 *                     não é possível
	 */
	public LeafOM(Color background, int width, int height, boolean opaque, boolean translation, boolean proportional,
			boolean zoomable) {
		this(background, width, height, opaque, translation, proportional, zoomable, false, false);
	}

	/**
	 * Construtor do painel de objetos de tamanho e posições variáveis
	 * 
	 * @param background     cor de fundo
	 * @param width          largura, em pixels
	 * @param height         altura, em pixels
	 * @param opaque         se <code>true</code>, o painel é opaco
	 * @param translation    se <code>true</code>, o painel pode ser deslocado por
	 *                       arrasto do mouse
	 * @param proportional   se <code>true</code>, a razão de pixels por faixa de
	 *                       coordenada é igual para ambos os eixos
	 * @param zoomable       se <code>true</code>, é possível alterar a razão de
	 *                       pixels por faixa de coordenada (não sendo possível
	 *                       aumentar ou diminiur o zoom). Se <code>false</code>
	 *                       isto não é possível
	 * @param showZero       se <code>true</code>, a área mostrada na tela inclui o
	 *                       zero do mapa
	 * @param changeAxisZoom se <code>true</code>, é possível mudar a forma como o
	 *                       zoom é modificado. Se <code>false</code>, o zoom é
	 *                       modifidado sempre da mesma maneira
	 */
	public LeafOM(Color background, int width, int height, boolean opaque, boolean translation, boolean proportional,
			boolean zoomable, boolean showZero, boolean changeAxisZoom) {
		super(background, width, height, opaque);

		this.addMouseWheelListener(this);
		this.addKeyListener(this);

		this.translation = translation;
		this.proportional = proportional;
		this.zoomable = zoomable;

		this.showZero = showZero;
		this.setChangeAxisZoom(changeAxisZoom);
	}

	public void setProportional(boolean proportional) {
		this.proportional = proportional;
	}

	/**
	 * Função que determina como se altera o modo de se alterar o zoom com a roda do
	 * mouse
	 * 
	 * @param zoomPopUp <code>true</code> para que apareça uma caixa pop-up com as
	 *                  opções de alteração de zoom, <code>false</code> as
	 *                  {@link #axisZoom alterações cíclicas} com o botão direito
	 */
	public void setZoomPopUp(boolean zoomPopUp) {
		this.zoomPopUp = zoomPopUp;
	}

	/**
	 * 
	 * @param changeAxisZoom se <code>true</code>, é possível mudar a forma como o
	 *                       zoom é modificado. Se <code>false</code>, o zoom é
	 *                       modifidado sempre da mesma maneira
	 */
	public void setChangeAxisZoom(boolean changeAxisZoom) {
		this.changeAxisZoom = changeAxisZoom;
	}

	/**
	 * 
	 * @param axisZoom
	 *                 <ol start="0">
	 *                 <li>os dois eixos;</i>
	 *                 <li>somente o horizontal;</i>
	 *                 <li>somente o vertical.</i>
	 *                 </ol>
	 */
	public void setAxisZoom(int axisZoom) {
		this.axisZoom = axisZoom;
	}

	protected boolean isVerticalZoomEnable() {
		return axisZoom == 0 || axisZoom == 2;
	}

	public boolean isShowZero() {
		return this.showZero;
	}

	public float getxM() {
		return xM;
	}

	public void setxM(float xM) {
		this.xM = xM;
	}

	public float getxm() {
		return xm;
	}

	public void setxm(float xm) {
		this.xm = xm;
	}

	public void setDrawMargin(float drawMargin) {
		this.drawMargin = drawMargin;
	}

	// -------------------------- DRAWER --------------------------

	@Override
	protected void drawBackground(Graphics2D g) {
		drawBackground(g, this);
	}

	protected abstract void drawBackground(Graphics2D g, WL wl);

	@Override
	protected void drawForeground(Graphics2D g) {
		drawForeground(g, this);
	}

	protected abstract void drawForeground(Graphics2D g, WL wl);

	@Override
	public void setDrawInfo(Collection<T> list) {
		super.setDrawInfo(list);
		for (DesM d : list)
			if (d.isDrawable()) // repassa dados da tela para o objeto
				d.setWL(this);
	}

	// -------------------------- TRANSFORMATION --------------------------

	// quina do desenho

	@Override
	public float getX0() {
		if (corner != null)
			return corner.x;
		else
			return 0f;
	}

	@Override
	public float getY0() {
		if (corner != null)
			return corner.y;
		else
			return 0f;
	}

	// largura do desenho

	@Override
	public float getDx() {
		if (proportional) {
			if (getSize().height != 0) {
				float ndx = dy * getSize().width / getSize().height;
				if (ndx > dx)
					dx = ndx;
			}
		}
		return dx;
	}

	@Override
	public float getDy() {
		if (proportional) {
			if (getSize().width != 0) {
				float ndy = dx * getSize().height / getSize().width;
				if (ndy > dy)
					dy = ndy;
			}
		}
		return dy;
	}

	// relação de pixels por unidade do desenho

	@Override
	public float getPPH() {
		return this.getSize().width / getDx();
	}

	@Override
	public float getPPV() {
		return this.getSize().height / getDy();
	}

	// janela máxima

	/**
	 * Função que estipula o valor mínimo da dimensão numérica horizontal da janela
	 * 
	 * @return valor máximo da dimensão horizontal visível
	 */
	protected abstract float getMinDx();

	/**
	 * Função que estipula o valor mínimo da dimensão numérica vertical da janela
	 * 
	 * @return valor máximo da dimensão vertical visível
	 */
	protected abstract float getMaxDx();

	// cálculos - ponto simples

	/**
	 * Função que calcula as coordenadas x e y (dadas em unidades do gráfico) em
	 * função das coordenadas dadas em pixels
	 * 
	 * @param x  coordenada X, em pixels, do ponto
	 * @param y  coordenada Y, em pixels, do ponto
	 * @param wl interface que comunica com a tela de desenho de modo a obter os
	 *           parâmetros da transformação linear
	 * @return coordenadas x e y nas unidades do gráfico
	 */
	public static Point2D.Float getInversePoint(int x, int y, WL wl) {
		return new Point2D.Float(wl.getX0() + x / wl.getPPH(), wl.getY0() - y / wl.getPPV());
	}

	private static float getTranformedXF(float xr, float x, WL wl) {
		return (x - xr) * wl.getPPH();
	}

	private static float getTranformedYF(float yr, float y, WL wl) {
		return (-y + yr) * wl.getPPV();
	}

	/**
	 * Função que calcula as coordenadas em <strong>pixels decimais</strong> onde
	 * deve ser desenhado um dado ponto representado pelas suas coordenadas x e y
	 * (dadas em unidades do gráfico)
	 * 
	 * @param xr offset da abscissa
	 * @param yr offset da ordenada
	 * @param x  abscissa do ponto
	 * @param y  ordenada do ponto
	 * @param wl interface que comunica com a tela de desenho de modo a obter os
	 *           parâmetros da transformação linear
	 * @return coordenadas em {@link Point2D.Float pixels decimais} do ponto
	 */
	public static Point2D.Float getTranformedPointF(float xr, float yr, float x, float y, WL wl) {
		return new Point2D.Float(getTranformedXF(xr, x, wl), getTranformedYF(yr, y, wl));
	}

	/**
	 * Função que calcula as coordenadas em <strong>pixels decimais</strong> onde
	 * deve ser desenhado um dado ponto representado pelas suas coordenadas x e y
	 * (dadas em unidades do gráfico)
	 * 
	 * @param x  abscissa do ponto
	 * @param y  ordenada do ponto
	 * @param wl interface que comunica com a tela de desenho de modo a obter os
	 *           parâmetros da transformação linear
	 * @return coordenadas em pixels decimais do ponto
	 */
	public static Point2D.Float getTranformedPointF(float x, float y, WL wl) {
		return getTranformedPointF(wl.getX0(), wl.getY0(), x, y, wl);
	}

	/**
	 * Função que calcula as coordenadas em pixels onde deve ser desenhado um dado
	 * ponto representado pelas suas coordenadas x e y (dadas em unidades do
	 * gráfico)
	 * 
	 * @param xr offset da abscissa
	 * @param yr offset da ordenada
	 * @param x  abscissa do ponto
	 * @param y  ordenada do ponto
	 * @param wl interface que comunica com a tela de desenho de modo a obter os
	 *           parâmetros da transformação linear
	 * @return coordenadas em pixels do ponto
	 */
	public static Point getTranformedPoint(float xr, float yr, float x, float y, WL wl) {
		int px, py;
		if (Float.isNaN(y)) {
			px = (int) getTranformedXF(xr, x, wl);
			py = Integer.MIN_VALUE;
		} else {
			Point2D.Float p = getTranformedPointF(xr, yr, x, y, wl);
			px = (int) p.x;
			py = (int) p.y;
		}
		return new Point(px, py);
	}

	/**
	 * Função que calcula as coordenadas em pixels onde deve ser desenhado um dado
	 * ponto representado pelas suas coordenadas x e y (dadas em unidades do
	 * gráfico)
	 * 
	 * @param x  abscissa do ponto
	 * @param y  ordenada do ponto
	 * @param wl interface que comunica com a tela de desenho de modo a obter os
	 *           parâmetros da transformação linear
	 * @return coordenadas em pixels do ponto
	 */
	public static Point getTranformedPoint(float x, float y, WL wl) {
		return getTranformedPoint(wl.getX0(), wl.getY0(), x, y, wl);
	}

	// cálculos - conjunto de pontos - todos

	public static int[][] getPoints(Collection<? extends Point2D> points, WL wl) {
		int xy[][] = new int[2][points.size()], i = 0;

		for (Point2D pf : points) {
			Point p = LeafOM.getTranformedPoint((float) pf.getX(), (float) pf.getY(), wl);
			xy[0][i] = p.x;
			xy[1][i] = p.y;
			i++;
		}
		return xy;
	}

	public static float[][] getPointsF(Collection<? extends Point2D.Float> points, WL wl) {
		float xy[][] = new float[2][points.size()];
		int i = 0;

		for (Point2D.Float pf : points) {
			Point2D.Float p = LeafOM.getTranformedPointF((float) pf.getX(), (float) pf.getY(), wl);
			xy[0][i] = p.x;
			xy[1][i] = p.y;
			i++;
		}
		return xy;
	}

	// cálculos - conjunto de pontos - filtra pontos em função da distância

	public static int[][] getPoints(Collection<? extends Point2D.Float> points, WL wl, int resolution) {
		LinkedList<Integer> xs = new LinkedList<>();
		LinkedList<Integer> ys = new LinkedList<>();

		Point2D.Float pf = null;
		Point previous = null;
		Iterator<? extends Point2D.Float> it = points.iterator();

		if (it.hasNext()) {
			// primeiro ponto
			pf = it.next();
			previous = LeafOM.getTranformedPoint((float) pf.getX(), (float) pf.getY(), wl);

			xs.add(previous.x);
			ys.add(previous.y);
		}

		while (it.hasNext()) {
			// demais pontos
			pf = it.next();
			Point point = LeafOM.getTranformedPoint((float) pf.getX(), (float) pf.getY(), wl);

			int d = Math.max(Math.abs(previous.x - point.x), Math.abs(previous.y - point.y));
			if (d > resolution) {
				xs.add(point.x);
				ys.add(point.y);
				previous = point;
			}
		}

		int[] xa = new int[xs.size()];
		int i = 0;
		for (Integer x : xs)
			xa[i++] = x;

		int[] ya = new int[ys.size()];
		i = 0;
		for (Integer y : ys)
			ya[i++] = y;

		return new int[][] { xa, ya };
	}

	public static float[][] getPointsF(Collection<? extends Point2D.Float> points, WL wl, float resolution) {
		LinkedList<Float> xs = new LinkedList<>();
		LinkedList<Float> ys = new LinkedList<>();

		Point2D.Float pf = null;
		Point2D.Float previous = null;
		Iterator<? extends Point2D.Float> it = points.iterator();

		if (it.hasNext()) {
			// primeiro ponto
			pf = it.next();
			previous = LeafOM.getTranformedPointF((float) pf.getX(), (float) pf.getY(), wl);

			xs.add(previous.x);
			ys.add(previous.y);
		}

		while (it.hasNext()) {
			// demais pontos
			pf = it.next();
			Point.Float point = LeafOM.getTranformedPointF((float) pf.getX(), (float) pf.getY(), wl);

			float d = Math.max(Math.abs(previous.x - point.x), Math.abs(previous.y - point.y));
			if (d > resolution) {
				xs.add(point.x);
				ys.add(point.y);
				previous = point;
			}
		}

		float[] xa = new float[xs.size()];
		int i = 0;
		for (Float x : xs)
			xa[i++] = x;

		float[] ya = new float[ys.size()];
		i = 0;
		for (Float y : ys)
			ya[i++] = y;

		return new float[][] { xa, ya };
	}

	// -------------------------- PANEL BORDERS --------------------------

	/**
	 * Função que recalcula as bordas da painel de desenho
	 * 
	 * @param newObj se for um objeto, atualiza-se as bordas em função em função da
	 *               adição deste no painel. Se for <code>null</code>, recalcula-se
	 *               a partir de todos os objetos (se a lista estiver vazia, bordas
	 *               padrão [-1;1]x[-1;1]).
	 */
	protected void calculateView(DesM newObj) {
		if (newObj != null) {
			// novo objeto adicionado
			if (newObj.isDrawable()) {
				if (getList().size() == 1) // se esse é o 1o objeto que foi adicionado a tela, zera-se as bordas
					zeraBordas();
				setNewBorder(newObj);
			}
		} else {
			// objeto removido: recalcula a posição para todos os objetos

			if (getList().size() != 0) {
				// se há objetos, começa-se zerando as bordas...
				zeraBordas();
				// ... para em seguida eles determinem as novas bordas
				for (DesM d : getList())
					if (d.isDrawable())
						setNewBorder(d);
			} else // se não há objetos, bordas padrão
				bordasDefault();
		}
		this.refreshIntervals();
	}

	protected void zeraBordas() {
		this.xM = Float.NEGATIVE_INFINITY;
		this.xm = Float.POSITIVE_INFINITY;
		this.yM = Float.NEGATIVE_INFINITY;
		this.ym = Float.POSITIVE_INFINITY;
	}

	protected void bordasDefault() {
		this.xM = 1f;
		this.xm = -1f;
		this.yM = 1f;
		this.ym = -1f;
	}

	protected void setNewBorder(DesM d) {
		if (d instanceof Point2D.Float) {
			Point2D.Float p = (Point2D.Float) d;
			this.extendBorder(p.x, p.x, p.y, p.y);
		} else // se for uma área
			this.extendBorder(d.getMax().x, d.getMin().x, d.getMax().y, d.getMin().y);
	}

	protected void refreshIntervals() {
		if (this.showZero)
			this.extendBorder(0f, 0f, 0f, 0f);

		this.dx = this.xM - this.xm;
		this.dy = this.yM - this.ym;

		if (proportional) {
			// caso seja proporcional, corrigir dx e dy de modo que PPH e PPV sejam iguais
			getDx();
			getDy();
		}

		this.setCorner(new Point2D.Float(xm - this.dx * drawMargin / 2, yM + this.dy * drawMargin / 2));

		float margin = (1f + drawMargin);
		this.dx *= margin;
		this.dy *= margin;
	}

	private void extendBorder(float xMax, float xMin, float yMax, float yMin) {
		if (xMax > this.xM)
			this.xM = xMax;
		if (xMin < this.xm)
			this.xm = xMin;
		if (yMax > this.yM)
			this.yM = yMax;
		if (yMin < this.ym)
			this.ym = yMin;
	}

	// -------------------------- MOUSE --------------------------

	// função 'drag-and-drop'
	private boolean translation = false;

	// função 'coleta ponto'
	private TakePoint tp;

	private boolean taking = false;

	public void setTakePoint(TakePoint tp) {
		this.tp = tp;
	}

	public void setTaking(boolean taking) {
		this.taking = taking;
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		super.mouseClicked(event);

		// solicitar o foco eventos do teclado
		super.requestFocus();

		// se for clique direito, troca o zoom (caso seja permitido)
		if (event.getButton() == MouseEvent.BUTTON3 && changeAxisZoom) {
			if (zoomPopUp) {
				// abre a caixa de escolha dos zooms
				JPopupMenu popup = new JPopupMenu();

				JMenuItem option = new JMenuItem("Ajustar ao conteúdo");
				option.addActionListener(this);
				option.setActionCommand("ZR");
				popup.add(option);

				popup.addSeparator();

				ButtonGroup bg = new ButtonGroup();

				option = new JRadioButtonMenuItem("Zoom nos dois eixos", this.axisZoom == 0);
				bg.add(option);
				option.addActionListener(this);
				option.setActionCommand("Z0");
				popup.add(option);

				option = new JRadioButtonMenuItem("Zoom no eixo horizontal", this.axisZoom == 1);
				bg.add(option);
				option.addActionListener(this);
				option.setActionCommand("Z1");
				popup.add(option);

				option = new JRadioButtonMenuItem("Zoom no eixo vertical", this.axisZoom == 2);
				bg.add(option);
				option.addActionListener(this);
				option.setActionCommand("Z2");
				popup.add(option);

				popup.show(this, event.getX(), event.getY());
			} else // para cada clique, um modo diferente
				setAxisZoom((axisZoom + 1) % 3);
		}

		// se estiver sendo coletando medições
		if (tp != null && taking)
			tp.clickOver(getInversePoint(event.getX(), event.getY(), this));
	}

	@Override
	public void mousePressed(MouseEvent event) {
		// drag
		super.mousePressed(event);

		if (super.drag == null) {
			// translation OR areaSelection
			super.x_mouse = event.getX();
			super.y_mouse = event.getY();
		}
	}

	@Override
	public void mouseDragged(MouseEvent event) {
		// drag
		super.mouseDragged(event);

		if (translation && drag == null && selectedExtreme == null) {
			// translation
			this.screenTranslation(super.x_mouse - event.getX(), super.y_mouse - event.getY());
			super.x_mouse = event.getX();
			super.y_mouse = event.getY();
			super.repaint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		// area selection
		if (selectedExtreme != null) {
			int minX = Math.min(selectedExtreme.x, x_mouse);
			int minY = Math.max(selectedExtreme.y, y_mouse);
			int maxX = Math.max(selectedExtreme.x, x_mouse);
			int maxY = Math.min(selectedExtreme.y, y_mouse);

			Point2D.Float min = getInversePoint(minX, minY, this);
			Point2D.Float max = getInversePoint(maxX, maxY, this);

			super.as.setSelectedArea(min, max);
			selectedExtreme = null;
		}
		// drag
		super.mouseReleased(event);
		// translation
		super.repaint();
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		this.setZoom(ZOOM_SPEED * e.getWheelRotation());
		if (zoomable)
			super.repaint();
		else // se o zoom não puder ser mudado, usar o mouse o JScrollPane
			getParent().dispatchEvent(e);
	}

	// -------------------------- TECLADO --------------------------

	@Override
	public void keyPressed(KeyEvent k) {
		keyEvent(k);
	}

	@Override
	public void keyReleased(KeyEvent k) {
	}

	@Override
	public void keyTyped(KeyEvent k) {
		keyEvent(k);
	}

	/**
	 * <ol start="0">
	 * <li>nada;</i>
	 * <li>TTI;</i>
	 * <li>CS.</i>
	 * </ol>
	 */
	private static final int MODE = 2;

	private void keyEvent(KeyEvent k) {
		switch (MODE) {
		case 1: // TTI
			switch (k.getKeyCode()) {
			case KeyEvent.VK_UP:
				this.screenTranslation(0, -50);
				break;
			case KeyEvent.VK_DOWN:
				this.screenTranslation(0, 50);
				break;
			case KeyEvent.VK_RIGHT:
				this.screenTranslation(50, 0);
				break;
			case KeyEvent.VK_LEFT:
				this.screenTranslation(-50, 0);
				break;
			case KeyEvent.VK_Q:
				this.setZoom(-ZOOM_SPEED);
				break;
			case KeyEvent.VK_A:
				this.setZoom(ZOOM_SPEED);
				break;
			}
			break;
		case 2: // CS
			switch (k.getKeyCode()) {
			case KeyEvent.VK_W:
				this.screenTranslation(0, -50);
				break;
			case KeyEvent.VK_S:
				this.screenTranslation(0, 50);
				break;
			case KeyEvent.VK_A:
				this.screenTranslation(50, 0);
				break;
			case KeyEvent.VK_D:
				this.screenTranslation(-50, 0);
				break;
			case KeyEvent.VK_UP:
				this.setZoom(-ZOOM_SPEED);
				break;
			case KeyEvent.VK_DOWN:
				this.setZoom(ZOOM_SPEED);
				break;
			}
			break;
		}

		super.repaint();
	}

	// --------------- TRANSLATION AND ZOOM ---------------

	/**
	 * Função que faz com que o centro da janela de desenho seja uma dada coordenada
	 * 
	 * @param center coordenadas (nas unidades do desenho) que estarão no centro da
	 *               tela
	 */
	public void putAtCenter(Point2D.Float center) {
		Point2D.Float newCorner = new Point2D.Float(center.x, center.y);
		newCorner.x -= this.dx / 2;
		newCorner.y += this.dy / 2;
		setCorner(newCorner);
		this.repaint();
	}

	protected void setCorner(Point2D.Float corner) {
		this.corner = corner;
	}

	protected void screenTranslation(int dx, int dy) {
		this.setCorner(getInversePoint(dx, dy, this));
	}

	private void setZoom(float ratio) {
		if (zoomable) {
			float dx0 = this.dx * (1 + ratio);
			if (dx0 > getMinDx() && dx0 < getMaxDx())
				setZoomRatio(ratio);
		}
	}

	protected void setZoomRatio(float ratio) {
		Point2D.Float m = getInversePoint(x_mouse, y_mouse, this);
		float ratioX = 0f, ratioY = 0f;
		switch (axisZoom) {
		case 0:
			ratioX = ratio;
			ratioY = ratio;
			break;
		case 1:
			ratioX = ratio;
			break;
		case 2:
			ratioY = ratio;
			break;
		}
		this.dx *= (1 + ratioX);
		this.dy *= (1 + ratioY);
		this.setCorner(new Point2D.Float(getX0() - ratioX * (m.x - getX0()), getY0() - ratioY * (m.y - getY0())));
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		if (command.charAt(0) == 'Z') {
			// pop-up do zoom
			char c = command.charAt(1);
			if (c == 'R') // reset
				calculateView(null);
			else // mudar zoom
				setAxisZoom(c - 48);
		} else // pop-up ao se selecionar objetos sobrepostos
			super.actionPerformed(event);
	}

	// -------------------------- AUXILIAR --------------------------

	/**
	 * Função que retorna as coordenadas máximas e mínimas ocupadas pelos
	 * {@link DesM objetos} exibidos na grade
	 * 
	 * @param ds elementos que são mostrados na grade
	 * @return vetor com duas posições, a primeira sendo ocupada por um
	 *         {@link Dimension objeto indicando} as coordenadas mínimas e a segunda
	 *         ocupada por um indicando as coordenadas máximas
	 */
	public static Point2D.Float[] getExtend(Collection<? extends DesM> ds) {
		Point2D.Float min = new Point2D.Float(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
		Point2D.Float max = new Point2D.Float(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);

		for (DesM v : ds) {
			Point2D.Float m = v.getMin();
			if (m.x < min.x)
				min.x = m.x;
			if (m.y < min.y)
				min.y = m.y;
			m = v.getMax();
			if (m.x > max.x)
				max.x = m.x;
			if (m.y > max.y)
				max.y = m.y;
		}
		return new Point2D.Float[] { min, max };
	}
}