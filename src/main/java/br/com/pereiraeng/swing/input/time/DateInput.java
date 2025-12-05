package br.com.pereiraeng.swing.input.time;

import java.awt.Color;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import br.com.pereiraeng.core.TimeUtils;
import br.com.pereiraeng.swing.Grade;
import br.com.pereiraeng.swing.input.Input;

/**
 * Classe do objeto gráfico de um botão que, ao ser clicado, abre um
 * {@link JPopupMenu pop-up} que permite a seleção de um dia do calendário.
 * 
 * @author Philipe PEREIRA
 *
 */
public class DateInput extends JButton implements ActionListener, Input<Date> {
	private static final long serialVersionUID = 1L;

	private int day, month, year;

	public DateInput() {
		this((Date) null);
	}

	public DateInput(Calendar calendar) {
		this(calendar.getTime());
	}

	public DateInput(Date date) {
		super.setFont(new Font("Courier New", Font.BOLD, super.getFont().getSize()));
		super.addActionListener(this);

		this.set(date);
	}

	@Override
	public Date get() {
		if (day == -1 && month == -1 && year == -1)
			return null;
		else
			return new GregorianCalendar(year, month, day).getTime();
	}

	@Override
	public void set(Date date) {
		if (date != null) {
			Calendar c = TimeUtils.date2Calendar(date);
			this.day = c.get(Calendar.DAY_OF_MONTH);
			this.month = c.get(Calendar.MONTH);
			this.year = c.get(Calendar.YEAR);
			super.setText(String.format("%1$td/%1$tm/%1$tY", c));
		} else {
			this.day = -1;
			this.month = -1;
			this.year = -1;
			super.setText("--/--/----");
		}
	}

	@Override
	public Component getComponent() {
		return this;
	}

	/**
	 * Sets the font for this component, creating a new Font object by replicating
	 * the current Font object and applying a new size to it.
	 * 
	 * @param size the size for the new Font.
	 */
	public void deriveFont(float size) {
		this.setFont(this.getFont().deriveFont(size));
	}

	// ---------------------------- LISTENER ----------------------------

	private transient boolean closed = true;

	@Override
	public void actionPerformed(ActionEvent event) {
		if (closed) {
			closed = false;
			if (day == -1 || month == -1 || year == -1)
				this.set(Calendar.getInstance().getTime());
			DatePopup d = new DatePopup();
			d.deriveFont(this.getFont().getSize());
			d.show(this.getParent(), this.getX(), this.getY());
		} else {
			closed = true;
			fireChangePerformed(new ChangeEvent(DateInput.this));
		}
	}

	protected EventListenerList listenerList2 = new EventListenerList();

	@Override
	public void addChangeListener(ChangeListener l) {
		if (listenerList2 == null)
			super.addChangeListener(l);
		else
			listenerList2.add(ChangeListener.class, l);
	}

	@Override
	public void removeChangeListener(ChangeListener l) {
		if (listenerList2 == null)
			super.removeChangeListener(l);
		else
			listenerList2.remove(ChangeListener.class, l);
	}

	private void fireChangePerformed(ChangeEvent event) {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList2.getListenerList();
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ChangeListener.class) {
				ChangeListener cl = (ChangeListener) listeners[i + 1];
				cl.stateChanged(event);
			}
		}
	}

	// ------------------------------ POP-UP ------------------------------

	private class DatePopup extends JPopupMenu implements MouseListener, PopupMenuListener {
		private static final long serialVersionUID = 1L;

		private Grade grade;
		private JLabel monthYearLabel;

		public DatePopup() {
			this.addPopupMenuListener(this);
			grade = new Grade(1, 1, 1, 1);

			// mês anterior
			JLabel label = new JLabel("\u25C4", SwingConstants.CENTER);
			label.setName("p");
			label.addMouseListener(this);
			label.setOpaque(true);
			label.setBackground(Color.LIGHT_GRAY);
			grade.add(label, 0, 0, 1, 1);

			// mês e ano
			monthYearLabel = new JLabel("", SwingConstants.CENTER);
			monthYearLabel.setOpaque(true);
			monthYearLabel.setBackground(Color.GRAY);
			grade.add(monthYearLabel, 1, 0, 5, 1);

			// próximo mês
			label = new JLabel("\u25BA", SwingConstants.CENTER);
			label.setName("n");
			label.addMouseListener(this);
			label.setOpaque(true);
			label.setBackground(Color.LIGHT_GRAY);
			grade.add(label, 6, 0, 1, 1);

			// dias da semana
			Calendar roll = Calendar.getInstance();
			roll.setFirstDayOfWeek(Calendar.SUNDAY);
			roll.set(Calendar.DAY_OF_WEEK, 1);
			for (int i = 0; i < 7; i++) {
				JLabel l = new JLabel(String.format("%ta", roll).substring(0, 1).toUpperCase(), SwingConstants.CENTER);
				grade.add(l, i, 1, 1, 1);
				roll.add(Calendar.DAY_OF_WEEK, 1);
			}
			add(grade);
			build();
		}

		private void build() {
			Calendar roll = Calendar.getInstance();
			roll.setFirstDayOfWeek(Calendar.SUNDAY);
			roll.set(Calendar.DAY_OF_MONTH, 1);
			roll.set(Calendar.MONTH, DateInput.this.month);
			roll.set(Calendar.YEAR, DateInput.this.year);

			// caso o mês seja negativo ou maior que 11 (retroceder ou avançar de ano),
			// atualizar os valores
			DateInput.this.month = roll.get(Calendar.MONTH);
			DateInput.this.year = roll.get(Calendar.YEAR);

			monthYearLabel.setText(String.format("%1$tB - %1$tY", roll));
			grade.setSeparation(1, 5, 1, 5);

			int i = roll.get(Calendar.DAY_OF_WEEK) - 1;
			while (roll.get(Calendar.MONTH) == DateInput.this.month) {
				JLabel l = new JLabel(String.format("%td", roll));
				l.setOpaque(true);
				if (roll.get(Calendar.DAY_OF_MONTH) == DateInput.this.day)
					l.setBackground(Color.GRAY);
				l.addMouseListener(this);

				grade.add(l, i % 7, i / 7 + 2, 1, 1);

				roll.add(Calendar.DAY_OF_MONTH, 1);
				i++;
			}

			deriveFont(this.getFont().getSize());

			this.repaint();
		}

		public void deriveFont(float size) {
			this.setFont(this.getFont().deriveFont(size));
			for (int i = 0; i < grade.getComponentCount(); i++) {
				Component c = grade.getComponent(i);
				c.setFont(c.getFont().deriveFont(size));
			}
		}

		@Override
		public void mouseClicked(MouseEvent event) {
			JLabel l = (JLabel) event.getSource();
			String name = l.getName();
			boolean next = "n".equals(name);
			boolean prev = "p".equals(name);
			if (next || prev) {
				// se o clique for sobre as setas de alteração de mês

				// apaga-se todos os dias do calendário
				int comps = DatePopup.this.grade.getComponentCount();
				for (int i = 10; i < comps; i++)
					DatePopup.this.grade.remove(10);

				// avança-se ou retira-se uma unidade...
				int step = 0;
				if (next)
					step = 1;
				else
					step = -1;

				// ... dos anos ou dos meses
				if (event.getButton() == MouseEvent.BUTTON3)
					DateInput.this.year += step;
				else
					DateInput.this.month += step;

				// reconstrói-se o calendário
				build();
			} else {
				DateInput.this.day = Integer.parseInt(l.getText());
				DateInput.super.setText(String.format("%1$td/%1$tm/%1$tY", get()));

				DatePopup.super.setVisible(false);
				DateInput.this.actionPerformed(new ActionEvent(DatePopup.this, ActionEvent.ACTION_PERFORMED, ""));
			}
		}

		@Override
		public void mouseEntered(MouseEvent event) {
		}

		@Override
		public void mouseExited(MouseEvent event) {
		}

		@Override
		public void mousePressed(MouseEvent event) {
		}

		@Override
		public void mouseReleased(MouseEvent event) {
		}

		@Override
		public void popupMenuCanceled(PopupMenuEvent event) {
			DateInput.this.closed = true;
		}

		@Override
		public void popupMenuWillBecomeInvisible(PopupMenuEvent event) {
		}

		@Override
		public void popupMenuWillBecomeVisible(PopupMenuEvent event) {
		}
	}
}