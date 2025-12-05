package br.com.pereiraeng.swing.selection;

import java.util.Collection;
import java.util.EventObject;

import br.com.pereiraeng.swing.interfaces.Click;

/**
 * SelectionEvent is used to notify interested parties that an object has been
 * selected in the event source.
 * 
 * @author Philipe PEREIRA
 */
public class SelectionEvent extends EventObject {
	private static final long serialVersionUID = 1L;

	private boolean multiSelection = false;

	private Click selected;

	private Collection<? extends Click> selecteds;

	private int gx;

	private int gy;

	private final int modifiers;

	public SelectionEvent(Object source, Click selected) {
		this(source, selected, 0);
	}

	public SelectionEvent(Object source, Click selected, int modifiers) {
		super(source);
		this.selected = selected;
		this.modifiers = modifiers;
	}

	public SelectionEvent(Object source, int gx, int gy) {
		this(source, gx, gy, 0);
	}

	public SelectionEvent(Object source, int gx, int gy, int modifiers) {
		super(source);
		this.gx = gx;
		this.gy = gy;
		this.modifiers = modifiers;
	}

	public SelectionEvent(Object source, Collection<? extends Click> selecteds) {
		super(source);
		this.multiSelection = true;
		this.selecteds = selecteds;
		this.modifiers = 0;
	}

	/**
	 * Função que retorna o objeto que foi selecionado
	 * 
	 * @return objeto selecionado
	 */
	public Click getSelected() {
		return selected;
	}

	/**
	 * Função que indica se foram selecionadas um ou vários objetos
	 * 
	 * @return <code>true</code> para vários objetos, <code>false</code> para
	 *         somente um
	 */
	public boolean isMultiSelection() {
		return multiSelection;
	}

	/**
	 * Função que retorna os objeto que foram selecionados
	 * 
	 * @return objetos selecionados
	 */
	public Collection<? extends Click> getSelecteds() {
		return selecteds;
	}

	/**
	 * Função que retorna a abscissa, em pixels, do ponto em que foi clicado para se
	 * desselecionar um objeto
	 * 
	 * @return abscissa, em pixels, do ponto onde foi clicado
	 */
	public int getGx() {
		return gx;
	}

	/**
	 * Função que retorna a abscissa, em pixels, do ponto em que foi clicado para se
	 * desselecionar um objeto
	 * 
	 * @return ordenada, em pixels, do ponto onde foi clicado
	 */
	public int getGy() {
		return gy;
	}

	public int getModifiers() {
		return this.modifiers;
	}
}