package br.com.pereiraeng.swing.interfaces;

/**
 * Classe abstrata dos objetos gráficos desenhados usando uma tabela html como
 * grade
 * 
 * @author Philipe PEREIRA
 *
 */
public abstract class AbstractDesHT implements DesHT {

	protected final int w, h;

	protected int x, y;

	protected boolean drawable = true;

	public AbstractDesHT(int w, int h) {
		this.w = w;
		this.h = h;
	}

	@Override
	public void setDrawable(boolean drawable) {
		this.drawable = drawable;
	}

	@Override
	public boolean isDrawable() {
		return drawable;
	}

	public void setX(int x) {
		this.x = x;
	}

	@Override
	public int getX() {
		return x;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public int getW() {
		return w;
	}

	@Override
	public int getH() {
		return h;
	}

	@Override
	public int compareTo(DesHT o) {
		int out = o.getY() - this.getY();
		if (out == 0)
			return o.getX() - this.getX();
		else
			return out;
	}

	// auxiliar

	public static final String BORDER = "%cborder-%s: 1px solid black";

	public static String getBorder(boolean... dirs) {
		StringBuilder out = new StringBuilder("style=");
		for (int i = 0; i < dirs.length; i++)
			if (dirs[i])
				out.append(String.format(BORDER, out.length() == 6 ? '\"' : ';',
						i == 0 ? "right" : (i == 1 ? "top" : (i == 2 ? "left" : "bottom"))));
		out.append('\"');
		return out.toString();
	}
}
