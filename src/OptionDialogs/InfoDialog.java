package OptionDialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.Controller;
import main.Statics;

public class InfoDialog extends OptionDialog {

	private final static int SIZE = 3, CENTER_PLUS = 1;
	private static String nameInfopage[];

	private JEditorPane manual;
	private JPanel btnPanel;
	private JLabel selAlg[];
	private JButton nextRight, nextLeft;
	private int currentIndex = 0, activeIndex;

	public InfoDialog(Controller controller,int currentIndex,String title, int width,
			int height) {
		
		this.currentIndex = currentIndex;
		this.controller = controller;
		initComponents();
		setSize(width, height);
		setTitle(title);
		setLocationRelativeTo(null);
		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == nextRight) {

			btnPanel.removeAll();
			btnPanel.add(nextLeft);

			if (activeIndex < currentIndex + CENTER_PLUS)
				activeIndex++;

			else if (currentIndex + SIZE < Statics.SORT_ALGORITHMNS.length) {
				currentIndex++;
				activeIndex = currentIndex + CENTER_PLUS;
			} else if (activeIndex < Statics.SORT_ALGORITHMNS.length - 1)
				activeIndex++;

			for (int i = currentIndex; i < currentIndex + SIZE; i++) {

				if (i != activeIndex)
					selAlg[i].setEnabled(false);
				else
					selAlg[i].setEnabled(true);
				btnPanel.add(selAlg[i]);

			}
			
			setPage(activeIndex);

			btnPanel.add(nextRight);
			revalidate();
			repaint();

		}

		else if (e.getSource() == nextLeft) {

			if (activeIndex > currentIndex + CENTER_PLUS)
				activeIndex--;
			else if (currentIndex > 0) {
				currentIndex--;
				activeIndex = currentIndex + CENTER_PLUS;
			} else if (activeIndex > 0)
				activeIndex--;

			btnPanel.removeAll();
			btnPanel.add(nextLeft);
			for (int i = currentIndex; i < currentIndex + SIZE; i++) {

				if (i != activeIndex)
					selAlg[i].setEnabled(false);
				else
					selAlg[i].setEnabled(true);
				btnPanel.add(selAlg[i]);

			}
			setPage(activeIndex);

			btnPanel.add(nextRight);
			revalidate();
			repaint();
		}
		
		
	}
	
	private void setPage(int index){
		
		java.net.URL helpURL = InfoDialog.class.getClassLoader().getResource(
				"resources/".concat(nameInfopage[index]));
		if (helpURL != null) {
			try {
				System.out.println("URL: "+("resources/".concat(nameInfopage[index])));
				manual.setPage(helpURL);
			} catch (IOException e) {
				System.err.println("Attempted to read a bad URL: " + helpURL);
			}
		} else {
			System.err.println("Couldn't find file: ".concat(nameInfopage[index]));
		}

	}

	@Override
	protected void initComponents() {

		setLayout(new BorderLayout());

		final int length = Statics.SORT_ALGORITHMNS.length;

		btnPanel = new JPanel(new GridLayout(0, SIZE + 2));
		selAlg = new JLabel[length];
		nextLeft = new JButton("<-");
		nextLeft.addActionListener(this);
		nextLeft.setBorder(BorderFactory.createEmptyBorder());
		nextRight = new JButton("->");
		nextRight.setBorder(BorderFactory.createEmptyBorder());
		nextRight.addActionListener(this);

		btnPanel.add(nextLeft);
		activeIndex = currentIndex + CENTER_PLUS;
		for (int i = 0; i < length; i++) {
			selAlg[i] = new JLabel(Statics.SORT_ALGORITHMNS[i]);
			if (i != activeIndex)
				selAlg[i].setEnabled(false);
			if (i>=currentIndex && i<currentIndex+SIZE)
				btnPanel.add(selAlg[i]);

		}
		

		btnPanel.add(nextRight);

		JEditorPane manual = new JEditorPane();
		manual.setEditable(false);

		java.net.URL helpURL = InfoDialog.class.getClassLoader().getResource(
				"resources/infopage_qsort.html");
		if (helpURL != null) {
			try {
				manual.setPage(helpURL);
			} catch (IOException e) {
				System.err.println("Attempted to read a bad URL: " + helpURL);
			}
		} else {
			System.err.println("Couldn't find file: infopage_qsort.html");
		}

		JScrollPane editorScrollPane = new JScrollPane(manual);
		editorScrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		editorScrollPane.setPreferredSize(new Dimension(250, 145));
		editorScrollPane.setMinimumSize(new Dimension(10, 10));

		add(BorderLayout.NORTH, btnPanel);
		add(BorderLayout.CENTER, editorScrollPane);


	}
	
	public static void setInfoPageNames(String nameInfopage[]){
		InfoDialog.nameInfopage = nameInfopage;
	}

}
