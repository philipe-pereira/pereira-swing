package br.com.pereiraeng.swing.list.filter;

public class DefaultListFilter implements ListFilter {

	private transient String filter;

	private boolean caseSensitive = false;

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public void clearFilter() {
		this.filter = null;
	}

	public void setCaseSensitive(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

	@Override
	public boolean accept(Object element) {
		if (filter == null ? true : "".equals(filter))
			return true;
		if (caseSensitive)
			return element.toString().contains(filter);
		else
			return element.toString().toLowerCase().contains(filter.toLowerCase());
	}
}
