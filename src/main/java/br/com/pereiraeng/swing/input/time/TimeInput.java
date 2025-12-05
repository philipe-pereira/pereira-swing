package br.com.pereiraeng.swing.input.time;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import br.com.pereiraeng.core.TimeUtils;
import br.com.pereiraeng.icons.Icons;
import br.com.pereiraeng.swing.Grade;
import br.com.pereiraeng.swing.SwingUtils;
import br.com.pereiraeng.swing.input.Input;

/**
 * Classe do objeto gráfico que contém os meios para se inserir uma data e hora
 * 
 * 
 * @author Philipe PEREIRA
 *
 */
public class TimeInput extends Grade implements ChangeListener, Input<Calendar>, ActionListener {
	private static final long serialVersionUID = 1L;

	private DateInput d;
	private JSpinner h;

	private int shift;

	private JRadioButton atu, sel;

	/**
	 * Construtor do objeto gráfico de inserção de datas. Este é iniciado com os
	 * valores de data e horas atuais
	 */
	public TimeInput() {
		this(null);
	}

	public TimeInput(Calendar time) {
		this(time, true);
	}

	/**
	 * Construtor do objeto gráfico de inserção de datas
	 * 
	 * @param time     data e hora iniciais
	 * @param showHour se <code>true</code> o objeto gráfico exibe o selecionador de
	 *                 horas, senão este é ignorado (nos casos em que tanto faz a
	 *                 hora, em que se quer escolher apenas os dias)
	 */
	public TimeInput(Calendar time, boolean showHour) {
		this(time, showHour, 1, 0, null);
	}

	/**
	 * Construtor do objeto gráfico de inserção de datas, dando a opção de
	 * selecionar a data e hora atuais ou uma data qualquer
	 * 
	 * @param time  data e hora iniciais
	 * @param shift se for um número positivo, há somente os objetos gráficos de
	 *              inserção; se for um número negativo, há a opção de escolher a
	 *              data e hora 'atuais' (onde o valor representa o tempo em minutos
	 *              do distanciamento da função que retorna a
	 *              {@link Calendar#getInstance() hora realmente atual})
	 */
	public TimeInput(Calendar time, int shift) {
		this(time, true, shift, 0, null);
	}

	/**
	 * Construtor do objeto gráfico de inserção de datas, incluindo dois butões para
	 * avançar
	 * 
	 * 
	 * @param time          data e hora iniciais
	 * @param jump
	 * @param actionCommand
	 */
	public TimeInput(Calendar time, int jump, String actionCommand) {
		this(time, true, 1, jump, actionCommand);
	}

	private TimeInput(Calendar time, boolean showHour, int shift, int jump, String actionCommand) {
		this.shift = shift;
		boolean atual = shift <= 0;

		this.d = new DateInput();
		this.d.addChangeListener(this);

		this.h = new JSpinner(new SpinnerDateModel());
		this.h.setEditor(new JSpinner.DateEditor(h, "HH:mm"));
		this.h.setVisible(showHour);
		this.h.addChangeListener(this);

		if (atual) {
			ButtonGroup bg = new ButtonGroup();

			atu = new JRadioButton("Atual");
			bg.add(atu);
			atu.addActionListener(this);
			add(atu, 1, 0, 2, 1);

			sel = new JRadioButton("Selecionar horário", true);
			bg.add(sel);
			sel.addActionListener(this);
			add(sel, 1, 1, 2, 1);
		}

		if (jump > 0) {
			JButton b = new JButton(Icons.loadUtilsIcon("Previous.gif"));
			b.setPreferredSize(SwingUtils.DIM_BUTTON_SMALL_ICON);
			b.addActionListener(this);
			b.setActionCommand(jump + "-B-" + actionCommand);
			add(b, 0, 0, 1, 3);

			b = new JButton(Icons.loadUtilsIcon("Next.gif"));
			b.setPreferredSize(SwingUtils.DIM_BUTTON_SMALL_ICON);
			b.addActionListener(this);
			b.setActionCommand(jump + "-F-" + actionCommand);
			add(b, 3, 0, 1, 3);
		}

		add(d, 1, 2, 1, 1);
		add(h, 2, 2, 1, 1);

		this.set(time);
	}

	@Override
	public void setEnabled(boolean enabled) {
		if (atu != null) {
			atu.setEnabled(enabled);
			sel.setEnabled(enabled);

			d.setEnabled(sel.isSelected() && enabled);
			h.setEnabled(sel.isSelected() && enabled);
		} else {
			d.setEnabled(enabled);
			h.setEnabled(enabled);
		}
	}

	@Override
	public boolean isEnabled() {
		return d.isEnabled();
	}

	@Override
	public void setFont(Font font) {
		super.setFont(font);
		if (d != null) {
			d.deriveFont(font.getSize());
			h.setFont(font);
		}
	}

	// -------------------- INPUT --------------------

	@Override
	public void set(Calendar time) {
		Date d1 = null;
		if (time != null)
			d1 = time.getTime();
		else
			d1 = Calendar.getInstance().getTime();
		d.set(d1);
		h.setValue(d1);
	}

	@Override
	public Calendar get() {
		Calendar h = TimeUtils.date2Calendar((Date) this.h.getValue());
		Calendar d = TimeUtils.date2Calendar(this.d.get());

		h.set(Calendar.YEAR, d.get(Calendar.YEAR));
		h.set(Calendar.MONTH, d.get(Calendar.MONTH));
		h.set(Calendar.DAY_OF_MONTH, d.get(Calendar.DAY_OF_MONTH));

		return h;
	}

	@Override
	public Component getComponent() {
		return this;
	}

	// -------------------------- LISTENER --------------------------

	public void addActionListener(ActionListener listener) {
		if (listener != null)
			super.listenerList.add(ActionListener.class, listener);
	}

	public void addChangeListener(ChangeListener listener) {
		if (listener != null) // listener externo
			super.listenerList.add(ChangeListener.class, listener);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource().equals(atu)) {
			Calendar c = Calendar.getInstance();
			c = new GregorianCalendar(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH),
					c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
			c.add(Calendar.MINUTE, this.shift);
			this.set(c);

			d.setEnabled(false);
			h.setEnabled(false);
		} else if (event.getSource().equals(sel)) {
			d.setEnabled(true);
			h.setEnabled(true);
		} else {
			// avançar ou recuar
			String[] ss = event.getActionCommand().split("-");
			int jump = Integer.parseInt(ss[0]);
			boolean f = "F".equals(ss[1]);

			Calendar h = TimeUtils.date2Calendar((Date) this.h.getValue());
			h.add(Calendar.MINUTE, f ? jump : -jump);
			this.h.setValue(h.getTime());

			// Guaranteed to return a non-null array
			Object[] listeners = super.listenerList.getListenerList();
			// Process the listeners last to first, notifying
			// those that are interested in this event
			if (listeners.length > 0) {
				ActionEvent ae = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ss[2]);
				for (int i = listeners.length - 2; i >= 0; i -= 2) {
					if (listeners[i] == ActionListener.class) {
						ActionListener al = (ActionListener) listeners[i + 1];
						al.actionPerformed(ae);
					}
				}
			}
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// Guaranteed to return a non-null array
		Object[] listeners = super.listenerList.getListenerList();
		// Process the listeners last to first, notifying
		// those that are interested in this event
		if (listeners.length > 0) {
			ChangeEvent ce = new ChangeEvent(this);
			for (int i = listeners.length - 2; i >= 0; i -= 2) {
				if (listeners[i] == ChangeListener.class) {
					ChangeListener cl = (ChangeListener) listeners[i + 1];
					cl.stateChanged(ce);
				}
			}
		}
	}
}
