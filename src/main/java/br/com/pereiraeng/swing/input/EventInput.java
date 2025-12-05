package br.com.pereiraeng.swing.input;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.Icon;

import br.com.pereiraeng.swing.ActionResult;
import br.com.pereiraeng.swing.button.ChangeableButton;
import br.com.pereiraeng.swing.image.Pointer;

public class EventInput extends ChangeableButton implements Input<ActionResult> {
	private static final long serialVersionUID = -7978993067408445180L;

	private ActionResult ar;

	public EventInput(ActionResult ar) {
		super(new Icon[] { new Pointer(Pointer.BAD), new Pointer(Pointer.NO), new Pointer(Pointer.OK) });
		this.set(ar);
	}

	@Override
	public void set(ActionResult ar) {
		this.ar = ar;
		if (ar.getState() == 0) {
			setSelected(1);
		} else if (ar.getState() > 0) {
			setSelected(2);
		} else {
			setSelected(0);
		}
	}

	@Override
	public ActionResult get() {
		return ar;
	}

	@Override
	public Component getComponent() {
		return this;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
//		super.actionPerformed(event); esperar resposta antes de mudar o selecionado
		ar.actionPerformed(event);
	}
}
