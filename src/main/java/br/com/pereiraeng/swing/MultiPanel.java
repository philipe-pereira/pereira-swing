package br.com.pereiraeng.swing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * Componente a ser usado para mostrar painéis sucessivos em diferentes abas,
 * onde cada painel possui um dado componente.
 * 
 * @author Philipe Pereira
 * 
 */
public class MultiPanel extends JTabbedPane {
	private static final long serialVersionUID = 1L;

	public static final String CLOSE = "close";

	/**
	 * Função que adiciona um painel ao componente
	 * 
	 * @param title
	 *            título do painel a ser exibido na aba.
	 * @param component
	 *            componente a ser exibido no painel.
	 * @param closeListener
	 *            escutador a ser adicionado aos botões de fechamento das abas
	 */
	public void addComponent(String title, Component component, ActionListener closeListener) {
		super.addTab(title, component);
		super.setTabComponentAt(this.indexOfComponent(component), new ClosableTab(this, closeListener));
	}

	/**
	 * Componente a ser exibido na aba, composto de um rótulo mais o botão de
	 * fechamento
	 * 
	 * @author Philipe Pereira
	 * 
	 */
	private class ClosableTab extends JPanel {
		private static final long serialVersionUID = 1L;
		private JTabbedPane pane;

		/**
		 * Contrutor do componente da aba
		 * 
		 * @param pane
		 *            painel com abas ao qual pertence
		 * @param closeListener
		 *            escutador de eventos para o fechamento da aba
		 */
		public ClosableTab(JTabbedPane pane, ActionListener closeListener) {
			this.pane = pane;
			add(new JLabel() {
				private static final long serialVersionUID = 1L;

				public String getText() {
					int i = ClosableTab.this.pane.indexOfTabComponent(ClosableTab.this);
					if (i != -1) {
						return ClosableTab.this.pane.getTitleAt(i);
					}
					return null;
				}
			});
			add(new CloseButton(closeListener));
		}

		/**
		 * Botão de fechamento da aba.
		 * 
		 * @author Philipe Pereira
		 */
		private class CloseButton extends JButton implements ActionListener {
			private static final long serialVersionUID = 1L;

			private ActionListener closeListener;

			public CloseButton(ActionListener closeListener) {
				setPreferredSize(new Dimension(17, 17));
				this.closeListener = closeListener;
				addActionListener(this);
			}

			protected void paintComponent(Graphics g2) {
				super.paintComponent(g2);
				Graphics2D g = (Graphics2D) g2.create();
				g.setStroke(new BasicStroke(1.3f));
				g.setColor(Color.BLACK);
				int corner = 5;
				g.drawLine(corner, corner, getWidth() - corner - 1, getHeight() - corner - 1);
				g.drawLine(getWidth() - corner - 1, corner, corner, getHeight() - corner - 1);
				g.dispose();
			}

			public void actionPerformed(ActionEvent event) {
				Integer i = pane.indexOfTabComponent(ClosableTab.this);
				if (i != -1) {
					if (this.closeListener != null)
						closeListener.actionPerformed(new ActionEvent(i, event.getID(), CLOSE));
					pane.remove(i);
				}
			}
		}
	}
}
