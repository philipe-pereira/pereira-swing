package br.com.pereiraeng.swing.input.time;

import java.awt.Component;
import java.awt.Font;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import br.com.pereiraeng.swing.input.Input;
import br.com.pereiraeng.core.TimeUtils;

/**
 * Componente para inserção e edição de horas e minutos
 * 
 * @author Philipe Pereira
 * 
 */
public class HourTZInput extends JPanel implements Input<Calendar> {
	private static final long serialVersionUID = 1L;

	private JSpinner hm;
	private JComboBox<TimeZone> tzCb;

	public HourTZInput() {
		this(null);
	}

	/**
	 * Construtor do componente, indicando os valores iniciais de hora e minuto a
	 * serem exibidos
	 * 
	 * @param hour valor inicial das horas e minutos
	 */
	public HourTZInput(Calendar hour) {
		JFormattedTextField text = null;

		// seletor de horário

		this.hm = new JSpinner(new SpinnerDateModel());
		this.hm.setEditor(new JSpinner.DateEditor(hm, "HH:mm"));

		text = ((JSpinner.DateEditor) this.hm.getEditor()).getTextField();
		text.setFont(new Font("Courier New", Font.BOLD, 18));
		text.setColumns(5);
		text.setText("");

		add(hm);

		// seletor de fuso horário

		this.tzCb = new JComboBox<TimeZone>(TimeUtils.tzValues());
		this.tzCb.setRenderer(new TimeUtils.TimeZoneRenderer());
		add(tzCb);

		this.set(hour);
	}

	@Override
	public void set(Calendar hm) {
		Calendar c = null;

		if (hm != null) {
			c = hm;
		} else {
			c = Calendar.getInstance();
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
		}

		this.hm.setValue(c.getTime());
		this.tzCb.setSelectedItem(c.getTimeZone());
	}

	@Override
	public Calendar get() {
		Calendar c = TimeUtils.date2Calendar((Date) this.hm.getValue());
		return TimeUtils.getCalendar(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), 0,
				(TimeZone) this.tzCb.getSelectedItem(), false);
	}

	@Override
	public Component getComponent() {
		return this;
	}
}