package br.com.pereiraeng.swing.mecabay;

import java.util.HashMap;

public interface InterfaceGrade {
	void add(HashMap<String, Componente> lista);

	void setDistancia(int top, int right, int bottom, int left);

	void setXYMax(int xMax, int yMax);
}
