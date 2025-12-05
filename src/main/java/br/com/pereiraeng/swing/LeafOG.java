package br.com.pereiraeng.swing;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JScrollPane;
import javax.swing.JViewport;

import br.com.pereiraeng.math.Scale2Di;
import br.com.pereiraeng.math.Scale2DiOff;
import br.com.pereiraeng.swing.interfaces.Click;
import br.com.pereiraeng.swing.interfaces.DesG;

/**
 * Painel na forma de grade sobre o qual serão desenhados objetos. Tais objetos
 * que representam os elementos deverão extender a classe {@link DesG} (ou seja,
 * ele deverão assumir posições discretas ao longo de uma grade). Faz parte do
 * grupo <strong>B</strong>lack<strong>b</strong>oar<strong>d</strong>
 * 
 * @author Philipe PEREIRA
 * 
 * @param <T> classe extensão de {@link DesG}
 */
public abstract class LeafOG<T extends DesG> extends LeafO<T> {
	private static final long serialVersionUID = 1L;

	protected Scale2DiOff scale;

	protected int dx, dy;

	/**
	 * Construtor do painel gradeado
	 * 
	 * @param background cor de fundo do painel
	 * @param dx         número de casas horizontais da grade
	 * @param dy         número de casas horizontais da grade
	 * @param opaque     opaque se <code>true</code> o painel será opaco, sem
	 *                   pintado com a cor estipulada no primeiro argumento
	 * @param scale      objeto {@link Scale2Di} com as dimensões, em pixels, do
	 *                   passo da grade
	 */
	public LeafOG(Color background, int dx, int dy, boolean opaque, Scale2Di scale) {
		super(background, dx * scale.getWidth(), dy * scale.getHeight(), opaque);

		if (scale instanceof Scale2DiOff)
			this.scale = (Scale2DiOff) scale;
		else
			this.scale = new Scale2DiOff(scale, new Point());

		this.dx = dx;
		this.dy = dy;
	}

	// ----------------------- GRADE, OFFSET E VIEWPOINT -----------------------

	public void setOffset(int x0, int y0) {
		this.scale.setOffset(x0, y0);
	}

	/**
	 * Altera as dimensões da grade devido a uma alteração do número de casas (ao
	 * contrário da função {@link LeafOG#setGrade(int, int)}, em que a alteração nas
	 * dimensões se dá por uma mudança no tamanho do passo da grade)
	 * 
	 * @param dx número de casas horizontais
	 * @param dy número de casas verticais
	 */
	public void setGradeSize(int dx, int dy) {
		adjustView(dx, this.dx, dy, this.dy);
		this.dx = dx;
		this.dy = dy;
		super.setPreferredSize(new Dimension(this.dx * scale.getWidth(), this.dy * scale.getHeight()));
		super.revalidate();
		super.repaint();

		// se ele estiver inserido em um JScrollPane, já atualiza as barras
		JScrollPane sp = SwingUtils.getScrollPane(this);
		if (sp != null) {
			sp.revalidate();
			sp.repaint();
		}
	}

	/**
	 * Função que altera o zoom da visão modificando o passo da grade. Há também uma
	 * alteração no tamanho total da grade.
	 * 
	 * @param stepX passo horizontal da grade
	 * @param stepY passo vertical da grade
	 */
	public void setGrade(int stepX, int stepY) {
		adjustView(stepX, scale.getWidth(), stepY, scale.getHeight());
		// alterar passo da grade
		this.scale.set(stepX, stepY);
		super.setPreferredSize(new Dimension(this.dx * scale.getWidth(), this.dy * scale.getHeight()));
		this.repaint();

		// se ele estiver inserido em um JScrollPane, já atualiza as barras
		JScrollPane sp = SwingUtils.getScrollPane(this);
		if (sp != null) {
			sp.revalidate();
			sp.repaint();
		}
	}

	/**
	 * Função que ajusta o ponto de vista do {@link JScrollPane} quando se altera a
	 * grade (passo ou o número de casas), de modo a manter o mesmo ponto central
	 * 
	 * @param x  nova dimensão característica em x
	 * @param x0 antiga dimensão característica em x
	 * @param y  nova dimensão característica em y
	 * @param y0 antiga dimensão característica em y
	 */
	private void adjustView(int x, int x0, int y, int y0) {
		// ajustar ponto de visto caso o mapa esteja localizado dentro de um
		// scroll Pane
		Container c = this.getParent();
		if (c instanceof JViewport) {
			JViewport s = (JViewport) c;
			Point p = s.getViewPosition();
			p.setLocation(p.x * ((double) x) / x0, p.y * ((double) y) / y0);
			s.setViewPosition(p);
		}
	}

	/**
	 * Função que ajusta o ponto de vista do {@link JScrollPane} para que um dado
	 * ponto da grade esteja no centro
	 * 
	 * @param x coordenada x do ponto da grade a se situar no centro da visão
	 * @param y coordenada y do ponto da grade a se situar no centro da visão
	 */
	public void putAtCenter(int x, int y) {
		Container c = this.getParent();
		if (c instanceof JViewport) {
			JViewport s = (JViewport) c;
			Dimension d = s.getSize();
			int x0 = (int) (x * scale.getWidth() - d.width / 2), y0 = (int) (y * scale.getHeight() - d.height / 2);
			if (x0 < 0)
				x0 = 0;
			if (y0 < 0)
				y0 = 0;
			s.setViewPosition(new Point(x0, y0));
		}
		this.repaint();
	}

	/**
	 * Função que altera o zoom da visão modificando o passo da grade
	 * 
	 * @param d fator multiplicativo do passo da grade
	 */
	public void setGrade(float d) {
		this.setGrade(Math.round(d * this.scale.getWidth()), Math.round(d * this.scale.getHeight()));
	}

	// -------------------------- DRAWER --------------------------

	@Override
	public void setDrawInfo(Collection<T> list) {
		super.setDrawInfo(list);
		for (DesG d : list)
			if (d.isDrawable()) // repassa dados da grade para o objeto
				d.setGrade(scale);
	}

	// -------------------------- DRAG N'DROP --------------------------

	@Override
	protected void setDragPostion(Click c, int x, int y) {
		((DesG) c).setPosition(Math.round((float) x / scale.getWidth()) + scale.getX(),
				Math.round((float) y / scale.getHeight()) + scale.getY());
	}

	// -------------------------- AUXILIAR --------------------------

	/**
	 * Função que reduz as dimensões da grade de modo que ela tenha o menor tamanho
	 * que ainda seja suficiente para acomodar todos os elementos
	 * 
	 * @param ds elementos que são (ou serão) mostrados na grade
	 */
	public Dimension fitLeaf(Collection<? extends DesG> ds) {
		Dimension[] mM = getExtend(ds);
		setGradeSize(mM[1].width - mM[0].width + 2, mM[1].height - mM[0].height + 2);
		setOffset(mM[0].width - 1, mM[0].height - 1);
		return mM[1];
	}

	/**
	 * Função que retorna as coordenadas máximas e mínimas ocupadas pelos
	 * {@link DesG objetos} exibidos na grade
	 * 
	 * @param ds elementos que são mostrados na grade
	 * @return vetor com duas posições, a primeira sendo ocupada por um
	 *         {@link Dimension objeto indicando} as coordenadas mínimas e a segunda
	 *         ocupada por um indicando as coordenadas máximas
	 */
	public static Dimension[] getExtend(Collection<? extends DesG> ds) {
		Dimension min = new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
		Dimension max = new Dimension(Integer.MIN_VALUE, Integer.MIN_VALUE);

		for (DesG v : ds) {
			Point xy = v.getLocation();
			if (xy != null) {
				if (xy.x < min.width)
					min.width = xy.x;
				if (xy.y < min.height)
					min.height = xy.y;
				if (xy.x > max.width)
					max.width = xy.x;
				if (xy.y > max.height)
					max.height = xy.y;
			}
		}
		return new Dimension[] { min, max };
	}

	/**
	 * Função que indica a posição da grade respectiva as coordenadas, em pixels, de
	 * um ponto na janela de desenho
	 * 
	 * @param x abscissa, em pixels
	 * @param y coordenadas, em pixels
	 * @return ponto na grade
	 */
	public Point getGridPoint(int x, int y) {
		return new Point((x + scale.getWidth() / 2) / scale.getWidth() + scale.getX(),
				(y + scale.getHeight() / 2) / scale.getHeight() + scale.getY());
	}

	/**
	 * Função que converte a tabela com coordenadas decimais das posições em uma
	 * tabela com coordenadas inteiras
	 * 
	 * @param preferable tabela que associa para cada identificador do objeto a sua
	 *                   posição preferida, em coordenadas decimais
	 * @param dx         número de casas horizontais
	 * @param dy         número de casas verticais
	 * @return tabela que associa para cada identificador do objeto a sua posição,
	 *         em coordenadas inteiras preferida
	 */
	public static <K> Map<K, Point> adjust(Map<K, Point2D.Float> preferable, int dx, int dy) {
		if (preferable == null)
			return null;
		// função afim

		Point2D.Float min = new Point2D.Float(Float.MAX_VALUE, Float.MAX_VALUE);
		Point2D.Float max = new Point2D.Float(Float.MIN_VALUE, Float.MIN_VALUE);

		for (Point2D.Float p : preferable.values()) {
			min.x = Math.min(min.x, p.x);
			min.y = Math.min(min.y, p.y);
			max.x = Math.max(max.x, p.x);
			max.y = Math.max(max.y, p.y);
		}

		float a = Math.min(dx / (max.x - min.x), dy / (max.y - min.y));

		Map<K, Point> out = new HashMap<>();
		for (Entry<K, Point2D.Float> e : preferable.entrySet()) {
			Point2D.Float p = e.getValue();
			out.put(e.getKey(), new Point((int) ((p.x - min.x) * a), (int) ((p.y - min.y) * a)));
		}
		return out;
	}
}