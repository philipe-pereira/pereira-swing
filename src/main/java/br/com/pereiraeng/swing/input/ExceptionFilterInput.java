package br.com.pereiraeng.swing.input;

import java.awt.Dimension;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JLabel;

import br.com.pereiraeng.core.collections.ArrayUtils;
import br.com.pereiraeng.swing.Grade;
import br.com.pereiraeng.swing.input.clc.SetInput;

public class ExceptionFilterInput extends Grade {
	private static final long serialVersionUID = 4059720244990413407L;

	private ButtonInput<Set<Integer>> excListInputP;

	private ButtonInput<Set<Integer>> excListInputN;

	public ExceptionFilterInput(int[] epi, int[] eni) {
		this(new TreeSet<>(Arrays.asList(ArrayUtils.box(epi))), new TreeSet<>(Arrays.asList(ArrayUtils.box(eni))));
	}

	public ExceptionFilterInput(Set<Integer> ep, Set<Integer> en) {
		add(new JLabel("Filtro seleciona errado"), 0, 0, 1, 1);
		this.excListInputP = new ButtonInput<>(new SetInput<>(ep), "Adicionar exceções");
		excListInputP.setPreferredSize(new Dimension(120, 20));
		add(excListInputP, 1, 0, 1, 1);

		add(new JLabel("Filtro deixa de selecionar"), 0, 1, 1, 1);
		this.excListInputN = new ButtonInput<>(new SetInput<>(en), "Adicionar exceções");
		excListInputN.setPreferredSize(new Dimension(120, 20));
		add(excListInputN, 1, 1, 1, 1);
	}

	public Set<Integer> getP() {
		return excListInputP.get();
	}

	public Set<Integer> getN() {
		return excListInputN.get();
	}

}
