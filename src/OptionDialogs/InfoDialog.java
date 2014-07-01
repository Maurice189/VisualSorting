package OptionDialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.Controller;
import main.Statics;
import main.Statics.SORTALGORITHMS;

public class InfoDialog extends OptionDialog {

	private final static int SIZE = 3, CENTER_PLUS = 1;
	private final static Font NFONT_SIZE = new Font("Monospace", Font.PLAIN, 14),
							  SFONT_SIZE = new Font("Monospace", Font.PLAIN, 10);
	
	private static HashMap<SORTALGORITHMS,String> infoPageRes;

	private JEditorPane manual;
	private JPanel btnPanel;
	private JLabel selAlg[];
	private JButton nextRight, nextLeft;
	private int currentIndex = 0, activeIndex;

	public InfoDialog(Controller controller,SORTALGORITHMS sortAlgorithms,String title, int width,
			int height) {
		
		
		this.activeIndex = sortAlgorithms.ordinal();
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
			btnPanel.add(Box.createHorizontalGlue());

			if (activeIndex < currentIndex + CENTER_PLUS)
				activeIndex++;

			else if (currentIndex + SIZE < SORTALGORITHMS.length()) {
				currentIndex++;
				activeIndex = currentIndex + CENTER_PLUS;
			} else if (activeIndex < SORTALGORITHMS.length() - 1)
				activeIndex++;

			for (int i = currentIndex; i < currentIndex + SIZE; i++) {

				if (i != activeIndex){
					selAlg[i].setEnabled(false);
					selAlg[i].setFont(SFONT_SIZE);
				}
				else{
					selAlg[i].setEnabled(true);
					selAlg[i].setFont(NFONT_SIZE);
				}
				btnPanel.add(selAlg[i]);
				btnPanel.add(Box.createHorizontalGlue());

			}
			
			//setPage(activeIndex);

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
			btnPanel.add(Box.createHorizontalGlue());
			for (int i = currentIndex; i < currentIndex + SIZE; i++) {

				if (i != activeIndex){
					selAlg[i].setEnabled(false);
					selAlg[i].setFont(SFONT_SIZE);
				}
				else{
					selAlg[i].setEnabled(true);
					selAlg[i].setFont(NFONT_SIZE);
				}
				btnPanel.add(selAlg[i]);
				btnPanel.add(Box.createHorizontalGlue());

			}
			//setPage(activeIndex);

			btnPanel.add(nextRight);
			revalidate();
			repaint();
		}
		
		
	}
	
	private void setPage(int index){
		
		java.net.URL helpURL = InfoDialog.class.getClassLoader().getResource(
				"resources/".concat(infoPageRes.get(SORTALGORITHMS.values()[index])));
		if (helpURL != null) {
			try {
				manual.setPage(helpURL);
			} catch (IOException e) {
				System.err.println("Attempted to read a bad URL: " + helpURL);
			}
		} 

	}

	@Override
	protected void initComponents() {

		setLayout(new BorderLayout());

		final int length = SORTALGORITHMS.length();

		btnPanel = new JPanel();
		btnPanel.setLayout(new BoxLayout(btnPanel,BoxLayout.X_AXIS));
		selAlg = new JLabel[length];
		nextLeft = new JButton(new ImageIcon(Statics.class.getResource("/resources/next_left_visualsort.png")));
		nextLeft.addActionListener(this);
		nextLeft.setBorder(BorderFactory.createEmptyBorder());
		nextRight = new JButton(new ImageIcon(Statics.class.getResource("/resources/next_right_visualsort.png")));
		nextRight.setBorder(BorderFactory.createEmptyBorder());
		nextRight.addActionListener(this);

	
		btnPanel.add(nextLeft);
		btnPanel.add(Box.createHorizontalGlue());
		currentIndex=activeIndex-CENTER_PLUS;
		if(currentIndex<0) currentIndex = 0;
		else if(currentIndex+SIZE>SORTALGORITHMS.length()) currentIndex = SORTALGORITHMS.length()-SIZE;
		
		for (int i = 0; i < length; i++) {
			selAlg[i] = new JLabel(SORTALGORITHMS.values()[i].toString());
			if (i != activeIndex){
				selAlg[i].setEnabled(false);
				selAlg[i].setFont(SFONT_SIZE);
			}
			else selAlg[i].setFont(NFONT_SIZE);
			
			if (i>=currentIndex && i<currentIndex+SIZE){
				btnPanel.add(selAlg[i]);
				btnPanel.add(Box.createHorizontalGlue());
			}

		}
		
		btnPanel.add(nextRight);

		manual = new JEditorPane();
		manual.setEditable(false);
		setPage(activeIndex);

		JScrollPane editorScrollPane = new JScrollPane(manual);
		editorScrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		editorScrollPane.setPreferredSize(new Dimension(250, 145));
		editorScrollPane.setMinimumSize(new Dimension(10, 10));

		add(BorderLayout.NORTH, btnPanel);
		add(BorderLayout.CENTER, editorScrollPane);


	}
	
	public static void initInfoPageResolver(HashMap<SORTALGORITHMS,String> infoPageRes){
		InfoDialog.infoPageRes = infoPageRes;
		
	}
	

}
