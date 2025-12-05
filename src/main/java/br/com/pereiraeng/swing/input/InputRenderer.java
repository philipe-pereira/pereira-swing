package br.com.pereiraeng.swing.input;

import java.awt.Component;

public interface InputRenderer<I> extends Input<I> {

	public void getRendererComponent(Component component, I value, Object def);
}
